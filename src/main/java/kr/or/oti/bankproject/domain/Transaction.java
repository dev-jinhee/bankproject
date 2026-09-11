package kr.or.oti.bankproject.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class Transaction {
	private LocalDate transactionDate; 
    private LocalTime transactionTime; 
    private String kind; //입,출금
    private long amount;          
    private long balance; //거래후 잔액 
}
