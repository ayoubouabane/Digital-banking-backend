package net.ouabane.digitalbankingback.dtos;

import lombok.Data;

import net.ouabane.digitalbankingback.enums.OperationType;


import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
