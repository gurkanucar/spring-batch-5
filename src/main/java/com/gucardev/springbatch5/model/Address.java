package com.gucardev.springbatch5.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String street;
  private String city;
  private String country;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
}
