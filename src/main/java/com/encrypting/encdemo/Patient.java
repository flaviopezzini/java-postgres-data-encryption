package com.encrypting.encdemo;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.ColumnTransformer;
import lombok.Data;

@Entity(name = "Patient")
@Data
public class Patient {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID uuid;
  
  @ColumnTransformer(
      read =  "pgp_sym_decrypt(" +
              "    cast(firstName as bytea), " +
              "    current_setting('encrypt.key')" +
              ")",
      write = "pgp_sym_encrypt( " +
              "   ?, " +
              "    current_setting('encrypt.key')" +
              ") "
  )
  @Column(columnDefinition = "text")
  private String firstName;
  
  @ColumnTransformer(
      read =  "pgp_sym_decrypt(" +
              "    cast(lastName as bytea), " +
              "    current_setting('encrypt.key')" +
              ")",
      write = "pgp_sym_encrypt( " +
              "   ?, " +
              "    current_setting('encrypt.key')" +
              ") "
  )
  @Column(columnDefinition = "text")
  private String lastName;
  
  @Column(columnDefinition = "DATE")
  private LocalDate birthDate;
  
  @Column(columnDefinition = "varchar(100)")
  private String description;
  
}
