package com.hospitalmanagement.AppointmentService.repository;

import com.hospitalmanagement.AppointmentService.entity.Appointment;
import com.hospitalmanagement.AppointmentService.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByPatientUserIdAndDoctorIdAndAppointmentDateAndStatus(
            Long patientUserId,
            Long doctorId,
            LocalDate appointmentDate,
            AppointmentStatus status
    );
    boolean existsByDoctorIdAndAppointmentDateAndSlotStartTimeAndStatus(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime slotStartTime,
            AppointmentStatus status
    );

    List<Appointment> findByDoctorIdAndAppointmentDateAndStatus(
            Long doctorId,
            LocalDate appointmentDate,
            AppointmentStatus status
    );

    List<Appointment> findByPatientUserIdOrderByAppointmentDateDescSlotStartTimeDesc(
            Long patientUserId
    );

    Appointment findByAppointmentToken(String appointmentToken);
}
