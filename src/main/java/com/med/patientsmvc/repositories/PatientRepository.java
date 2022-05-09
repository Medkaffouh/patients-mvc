package com.med.patientsmvc.repositories;

import com.med.patientsmvc.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
