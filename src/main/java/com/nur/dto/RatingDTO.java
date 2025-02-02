package com.nur.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RatingDTO implements BaseDTO, DTOEntity {

    private String country;
    private LocalDate ratingDate;
    private Integer oldCrg;
    private String oldCrrOutlook;
    private Integer newCrg;
    private String newCrrOutlook;
    private String rattingComment;
    private BigDecimal oldCrr;
    private BigDecimal newCrr;
}
