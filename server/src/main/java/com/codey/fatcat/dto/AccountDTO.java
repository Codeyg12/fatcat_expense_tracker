package com.codey.fatcat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

  private UUID id;
  private String accountType;
  private BigDecimal balance;
  private UUID userId;

}

