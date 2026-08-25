package com.hospitalmanagement.AppointmentService.serviceImpl;

import com.hospitalmanagement.AppointmentService.dto.*;
import com.hospitalmanagement.AppointmentService.entity.Appointment;
import com.hospitalmanagement.AppointmentService.entity.AppointmentStatus;
import com.hospitalmanagement.AppointmentService.entity.BookingRole;
import com.hospitalmanagement.AppointmentService.exception.AppointmentNotFoundException;
import com.hospitalmanagement.AppointmentService.feign.DoctorFeignClient;
import com.hospitalmanagement.AppointmentService.feign.PatientFeignClient;
import com.hospitalmanagement.AppointmentService.repository.AppointmentRepository;
import com.hospitalmanagement.AppointmentService.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleUnresolved;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final DoctorFeignClient doctorFeignClient;

    private final PatientFeignClient patientFeignClient;

    private  final AppointmentRepository appointmentRepository;

    @Override
    public List<AvailableSlotResponse> generateAvailableSlots(Long doctorId, LocalDate appointmentDate) {

        DoctorAvailabilityResponse availability = doctorFeignClient.getAvailabilityByDate(doctorId, appointmentDate);
       List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentDateAndStatus(
               doctorId, appointmentDate, AppointmentStatus.CONFIRMED
       );
        Set<LocalTime> bookedSlotTimes = appointments.stream().map(Appointment::getSlotStartTime)
                .collect(Collectors.toSet());
        List<AvailableSlotResponse> slots = new ArrayList<>();
        LocalTime currenttime = availability.getAvailableFrom();
        LocalTime availableTo = availability.getAvailableTo();
        Integer slotDuration = availability.getSlotDuration();
        while (currenttime.plusMinutes(slotDuration).compareTo(availableTo)<=0){

            LocalTime slotEndTime = currenttime.plusMinutes(slotDuration);
            boolean available = !bookedSlotTimes.contains(currenttime);
            slots.add(AvailableSlotResponse.builder()
                    .slotStartTime(currenttime)
                    .slotEndTime(slotEndTime)
                    .available(available)
                    .build());
            currenttime = slotEndTime;
        }

        return slots;
    }
        //book appointment starts here
    @Override
    @Transactional
    public AppointmentResponse bookAppointment(AppointmentRequest request) {

        //1 validate patient

        PatientResponse patient = patientFeignClient.getpatientbyUserId(request.getPatientUserId());

        if(patient == null){
            throw new AppointmentNotFoundException("patient with the userid not found" +patient.getUserId());
        }
        if(!patient.isActive()){
            throw new AppointmentNotFoundException("patient is in active");
        }

        //validate doctor

        DoctorAppointmentResponse doctor = doctorFeignClient.getDoctorbyId_2(request.getDoctorId());

        if (doctor == null) {
            throw new AppointmentNotFoundException("Doctor not found");
        }

        if (!doctor.isActive()) {
            throw new AppointmentNotFoundException("Doctor is inactive");
        }
        //get doctor availability
        DoctorAvailabilityResponse availability = doctorFeignClient.getAvailabilityByDate(request.getDoctorId(), request.getAppointmentDate());
        if (availability == null || !availability.isActive()) {
            throw new AppointmentNotFoundException("Doctor is not available on " + request.getAppointmentDate());
        }
        //calculate slot endtime

        LocalTime slotStartTime = request.getSlotStartTime();
        LocalTime slotEndTime = slotStartTime.plusMinutes(availability.getSlotDuration());

        //validate slot time

        if(slotStartTime.isBefore(availability.getAvailableFrom())){
            throw new AppointmentNotFoundException("Requested slot is before doctors availability");
        }
        if(slotEndTime.isAfter(availability.getAvailableTo())){
            throw new AppointmentNotFoundException("requested slot is after doctor availability");
        }

        //check weather requested time is a valid slot
        List<AvailableSlotResponse> slots = generateAvailableSlots(request.getDoctorId(), request.getAppointmentDate());
        AvailableSlotResponse requestedslot = slots.stream().filter(slot -> slot.getSlotStartTime().equals(slotStartTime)
        ).findFirst().orElseThrow(() -> new AppointmentNotFoundException("invalid appointment slot"));

        //check if slot is already booked

        if(!requestedslot.isAvailable())
        {
            throw new AppointmentNotFoundException("this slot is booked");
        }

        //prevent same patient booking same doctor twice on same date

        boolean patientbooked = appointmentRepository.existsByPatientUserIdAndDoctorIdAndAppointmentDateAndStatus(request.getPatientUserId(),
                request.getDoctorId(), request.getAppointmentDate(), AppointmentStatus.CONFIRMED);

        if(patientbooked){
            throw new AppointmentNotFoundException("patient already has an appointment with this doctor on this date");
        }

        //database level slot conflict check

        boolean checkslot = appointmentRepository.existsByDoctorIdAndAppointmentDateAndSlotStartTimeAndStatus(
                request.getDoctorId(), request.getAppointmentDate(),
                request.getSlotStartTime(), AppointmentStatus.CONFIRMED
        );
        if(checkslot){
             throw new AppointmentNotFoundException("this appointment slot has already been booked");
        }
        //get existing appointments
        List<Appointment> existingappointments = appointmentRepository.findByDoctorIdAndAppointmentDateAndStatus(
                request.getDoctorId(), request.getAppointmentDate(), AppointmentStatus.CONFIRMED
        );

        //calculate queue position

        int queueposition = existingappointments.size() + 1;

            /// calculate estimated waiting time
        int estimatedwaiting = (queueposition - 1)*availability.getSlotDuration();

        // generate appointment token
        String appointmentToken = "APT-" + UUID.randomUUID();

        //create appointment entity
        Appointment appointment = Appointment.builder()
                .appointmentToken(appointmentToken)
                .patientUserId(request.getPatientUserId())
                .doctorId(request.getDoctorId())
                .appointmentDate(request.getAppointmentDate())
                .slotStartTime(slotEndTime)
                .slotEndTime(slotEndTime).status(AppointmentStatus.CONFIRMED)
                .queuePosition(queueposition)
                .estimatedWaitingMinutes(estimatedwaiting)
                .bookedByRole(BookingRole.PATIENT)
                .bookedByUserId(request.getPatientUserId())
                .build();

        //save appointment
         Appointment saveAppointment = appointmentRepository.save(appointment);

        //return appointment confirmation
        return AppointmentResponse.builder()
                .appointmentId(saveAppointment.getId())
                .appointmentToken(saveAppointment.getAppointmentToken())
                .patientUserId(saveAppointment.getPatientUserId())
                .doctorId(saveAppointment.getDoctorId())
                .appointmentDate(saveAppointment.getAppointmentDate())
                .slotStartTime(saveAppointment.getSlotStartTime())
                .slotEndTime(saveAppointment.getSlotEndTime())
                .status(saveAppointment.getStatus())
                .queuePosition(saveAppointment.getQueuePosition())
                .estimatedWaitingMinutes(saveAppointment.getEstimatedWaitingMinutes())
                .message("Appointment booked successfully").build();
    }
}
