package com.encrypting.encdemo;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface PatientRepository extends Repository<Patient, String> {

  <S extends Patient> S save(S entity);
  
  List<Patient> findByLastNameAndFirstNameIgnoreCase(String lastName, String firstName, Pageable pageable);
  
}
