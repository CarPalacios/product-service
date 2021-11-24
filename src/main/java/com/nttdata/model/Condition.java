package com.nttdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.model.Condition;
import java.util.List;
import lombok.Data;

/**Se crea la clase Condition donde se declara las variables de las condiciones.*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Condition {
  private List<String> customerTypeTarget;
  
  private boolean hasMaintenanceFee;
  private Double maintenanceFee;
  
  private boolean hasMonthlyTransactionLimit;
  private Integer monthlyTransactionLimit;
  
  private boolean hasDailyWithdrawalTransactionLimit;
  private Integer dailyWithdrawalTransactionLimit;
  
  private boolean hasDailyDepositTransactionLimit;
  private Integer dailyDepositTransactionLimit;
  
  private Integer productPerPersonLimit;
  
  private Integer productPerBusinessLimit; 
}
