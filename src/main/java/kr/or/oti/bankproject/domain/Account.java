package kr.or.oti.bankproject.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account {
	private String accountNo;
	private String name;
	private long balance;
	private List<Transaction> transactions;
	
	public Account(String accountNo, String name) {
		this.accountNo=accountNo;
		this.name=name;
		this.balance=0;
		this.transactions= new ArrayList<Transaction>();			
	}
	
	
	public void deposit(long amount) {	
		//입금 메서드
		//amount:입금액
		
		balance+=amount;  
        transactions.add(
        		new Transaction(LocalDate.now(), LocalTime.now(), "입금", amount, this.balance));				
		System.out.println(amount+"원이 입금되었습니다.");
		
	}
	
	public void withdraw(long amount) {
		//출금 메서드
		if(balance<amount)
		{
			System.out.println("출금액이 입금액보다 큽니다.");
		}else {
			balance-=amount;
			transactions.add(new Transaction(LocalDate.now(), LocalTime.now(), "출금", amount, this.balance));
			System.out.println(amount+"원이 출금되었습니다.");					
		}
		
	}


	public String getAccountNo() {
		return accountNo;
	}


	public String getName() {
		return name;
	}


	public long getBalance() {
		return balance;
	}


	public List<Transaction> getTransactions() {
		return transactions;
	}


	

}