package kr.or.oti.bankproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import kr.or.oti.bankproject.dao.AccountDAO;
import kr.or.oti.bankproject.domain.Account;
import kr.or.oti.bankproject.domain.Transaction;

@Service
@Transactional
public class BankService {
	
	private final AccountDAO accountDAO;

	// 스프링이 AccountDAO 구현체를 자동 주입
	public BankService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
	
	// 1. 신규 계좌개설 : 이미 있는지 확인 하고 저장
	public void createAccount(String accountNo, String name) {
		Account existing = accountDAO.getAccount(accountNo);
        if (existing != null) {
            throw new IllegalArgumentException("이미 존재하는 계좌번호입니다: " + accountNo);
        }
        accountDAO.addAccount(accountNo, name);
    }
	
	// 2. 전체 계좌 목록 조회: 등록된 계좌 목록 반환
	public List<Account> getAllAccounts(){
		return accountDAO.getAccounts();
	}
	
	// 3. 계좌 번호로 단건 조회: 계좌번호 조회하고 없으면 예외
	public Account getAccount(String accountNo) {
		Account account= accountDAO.getAccount(accountNo);
		if(account==null) {
			throw new IllegalArgumentException("일치하는 계좌가 없습니다. 계좌번호: " + accountNo);
			}
		return account;		
	}
	
	// 4. 소유자 명으로 계좌 조회: 이름 조회 계좌 목록 반환
	public List<Account> findAccountByname(String name){
		return accountDAO.findAccounts(name);
	}
	// 5. 입금(deposit): 계좌 조회 -> 계좌에 입금 실행
	public void deposit(String accountNo, long amount) {
		Account account = getAccount(accountNo);
		account.deposit(amount);
		
		accountDAO.updateBalance(accountNo, account.getBalance());
		
		List<Transaction> trsList = account.getTransactions();
        accountDAO.insertTransaction(accountNo, trsList.get(trsList.size() - 1));
	}
	
	// 6. 출금(withdraw): 계좌 조회 -> 계좌에서 출금 실행(잔액 부족 시 도메인 예외 발생)
	public void withdraw(String accountNo, long amount) {
		Account account = getAccount(accountNo);
		account.withdraw(amount);
		
		accountDAO.updateBalance(accountNo, account.getBalance());
		
		List<Transaction> trsList = account.getTransactions();
        accountDAO.insertTransaction(accountNo, trsList.get(trsList.size() - 1));		
	}
	
	// 7. 거래 내역 조회(getTransaction): 특정 계좌의 거래 내역 목록 반환
	public List<Transaction> getTransactions(String accountNo){
		getAccount(accountNo); //계좌가 존재하는지 확인
		return accountDAO.getTransactions(accountNo);
	}
}
