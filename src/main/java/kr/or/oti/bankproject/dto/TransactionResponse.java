package kr.or.oti.bankproject.dto;

import java.time.format.DateTimeFormatter;

import kr.or.oti.bankproject.domain.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor 
public class TransactionResponse {
	private String date; 
	private String time; 
	private String kind; 
	private long amount; 
	private long balance;
	
	public TransactionResponse(Transaction transaction) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH시 mm분");

        this.date = transaction.getTransactionDate().format(dateFormatter);
        this.time = transaction.getTransactionTime().format(timeFormatter);
        this.kind = transaction.getKind();
        this.amount = transaction.getAmount();
        this.balance = transaction.getBalance();		
	}	
}