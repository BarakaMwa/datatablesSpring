package com.example.datatables.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class SelectInputDto {

    private String q;
    private String term;
    private int page;
    private String _type;

}
