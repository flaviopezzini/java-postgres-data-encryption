package com.encrypting.encdemo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class PatientController {
  
  private PatientService patientService;
  
  @Autowired
  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping("patients")
  public ResponseEntity<List<Patient>> findByLastNameAndFirstName(@RequestParam("lastName") String lastName,
      @RequestParam("firstName") String firstName, @RequestParam("page") int page) {
    List<Patient> retList = new ArrayList<>(0);
    Pageable pageable = PageRequest.of(page, 5);
    List<Patient> dbList = patientService.findByLastNameAndFirstName(lastName, firstName, pageable);
    HttpStatus status;
    if (dbList == null || dbList.size() == 0) {
      status = HttpStatus.NOT_FOUND;
    } else {
      for (Patient dbPatient : dbList) {
        Patient retPatient = new Patient();
        BeanUtils.copyProperties(dbPatient, retPatient);
        retList.add(retPatient);
      }
      status = HttpStatus.OK;
    }
    return new ResponseEntity<List<Patient>>(retList, status);
  }

  @PostMapping("patient")
  public ResponseEntity<Void> add(@RequestBody Patient patientInfo,
      UriComponentsBuilder builder) {
    Patient patient = new Patient();
    BeanUtils.copyProperties(patientInfo, patient);
    patientService.save(patient);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(
        builder.path("/patient/{id}").buildAndExpand(patient.getUuid()).toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

}
