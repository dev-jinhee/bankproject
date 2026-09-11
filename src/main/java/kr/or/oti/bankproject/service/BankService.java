package kr.or.oti.bankproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.oti.bankproject.domain.Account;
import kr.or.oti.bankproject.domain.Transaction;

@Service
public class BankService {
	
// 5. 입금(deposit): 계좌 조회 -> 계좌에 입금 실행
	public void deposit(String accountNo, long amount) {
		Account account = getAccount(accountNo);
		account.deposit(amount);
	}
	
// 6. 출금(withdraw): 계좌 조회 -> 계좌에서 출금 실행(잔액 부족 시 도메인 예외 발생)
	public void withdraw(String accountNo, long amount) {
		Account account = getAccount(accountNo);
		account.withdraw(amount);
	}
	
// 7. 거래 내역 조회(getTransaction): 특정 계좌의 거래 내역 목록 반환
	public List<Transaction> getTransactions(String accountNo){
		Account account = getAccount(accountNo);
		return account.getTransactions();
	}
}
