package com.hospitalmanagement.AppointmentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointment_table", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {
                        "doctor_id",
                        "appointment_date",
                        "slot_start_time"
                }
        )
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String appointmentToken;

    @Column(nullable = false)
    private Long patientUserId;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime slotStartTime;

    @Column(nullable = false)
    private LocalTime slotEndTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private Integer queuePosition;

    private Integer estimatedWaitingMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingRole bookedByRole;

    @Column(nullable = false)
    private Long bookedByUserId;

    private String cancellationReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Version
    private Long version;
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
