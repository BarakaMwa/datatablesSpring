package com.example.datatables.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
@Setter
public class Actor {

  private long actorId;
  private String firstName;
  private String lastName;
  private java.sql.Timestamp lastUpdate;

}
