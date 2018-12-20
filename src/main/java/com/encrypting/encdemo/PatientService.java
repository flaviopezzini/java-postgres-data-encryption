package com.encrypting.encdemo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
  
  private PatientRepository patientRepository;
  
  @Autowired
  public PatientService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }
  
  public Patient save(Patient patient) {
    return patientRepository.save(patient);
  }
  
  public List<Patient> findByLastNameAndFirstName(String lastName, String firstName, Pageable pageable) {
    return patientRepository.findByLastNameAndFirstNameIgnoreCase(lastName, firstName, pageable);
  }
}
