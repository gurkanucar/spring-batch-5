package com.gucardev.springbatch5.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "USERS")
@NoArgsConstructor
public class User {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String username;

  private int processed;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Address> addresses;



  public User(String name, String username, int processed) {
    this.name = name;
    this.username = username;
    this.processed = processed;
  }

  public User(Long id, String name, String username, int processed, List<Address> addresses) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.processed = processed;
    this.addresses = addresses;
  }
}
