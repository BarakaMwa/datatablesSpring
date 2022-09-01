package com.example.datatables.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActorIdFirstLastDto {

    private Long Id;
    private String FirstName;
    private String LastName;

}
