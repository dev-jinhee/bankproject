package kr.or.oti.bankproject.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
	private LocalDate transactionDate; 
    private LocalTime transactionTime; 
    private String kind; //입,출금
    private long amount;          
    private long balance; //거래후 잔액 
    
    // 생성자: 거래가 발생할 때 호출하여세팅    
    public Transaction(LocalDate transactionDate, LocalTime transactionTime, String kind, long amount, long balance) {
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.kind = kind; 
        this.amount = amount;
        this.balance = balance;
    }

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public LocalTime getTransactionTime() {
		return transactionTime;
	}

	public String getKind() {
		return kind;
	}

	public long getAmount() {
		return amount;
	}

	public long getBalance() {
		return balance;
	}    

}
