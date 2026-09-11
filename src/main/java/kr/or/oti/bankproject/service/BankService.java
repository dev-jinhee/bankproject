package kr.or.oti.bankproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.oti.bankproject.dao.AccountDAO;
import kr.or.oti.bankproject.domain.Account;
import kr.or.oti.bankproject.domain.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("[Service: 계좌 개설 시작] accountNo={}, name={}", accountNo, name);
		Account existing = accountDAO.getAccount(accountNo);
		if (existing != null) {
			log.warn("[Service: 계좌 개설 실패] 중복 계좌번호: accountNo={}", accountNo);
			throw new IllegalArgumentException("이미 존재하는 계좌번호입니다: " + accountNo);
		}
		accountDAO.addAccount(accountNo, name);
		log.info("[Service: 계좌 개설 완료] accountNo={}", accountNo);
	}
	
	// 2. 전체 계좌 목록 조회: 등록된 계좌 목록 반환
	public List<Account> getAllAccounts() {
		log.debug("[Service: 전체 계좌 목록 조회]");
		List<Account> accounts = accountDAO.getAccounts();
		log.debug("[Service: 전체 계좌 목록 조회 완료] count={}", accounts.size());
		return accounts;
	}
	
	// 3. 계좌 번호로 단건 조회: 계좌번호 조회하고 없으면 예외
	public Account getAccount(String accountNo) {
		log.debug("[Service: 계좌 단건 조회 시도] accountNo={}", accountNo);
		Account account = accountDAO.getAccount(accountNo);
		if (account == null) {
			log.warn("[Service: 계좌 단건 조회 실패] 계좌 미존재: accountNo={}", accountNo);
			throw new IllegalArgumentException("일치하는 계좌가 없습니다. 계좌번호: " + accountNo);
		}
		return account;		
	}
	
	// 4. 소유자 명으로 계좌 조회: 이름 조회 계좌 목록 반환
	public List<Account> findAccountByname(String name) {
		log.debug("[Service: 소유자명으로 계좌 조회] name={}", name);
		List<Account> accounts = accountDAO.findAccounts(name);
		log.debug("[Service: 소유자명으로 계좌 조회 완료] name={}, count={}", name, accounts.size());
		return accounts;
	}

	// 5. 입금(deposit): 계좌 조회 -> 계좌에 입금 실행
	public void deposit(String accountNo, long amount) {
		log.info("[Service: 입금 처리 시작] accountNo={}, amount={}", accountNo, amount);
		Account account = getAccount(accountNo);
		
		account.deposit(amount);
		accountDAO.updateBalance(accountNo, account.getBalance());
		
		List<Transaction> trsList = account.getTransactions();
		Transaction latestTrs = trsList.get(trsList.size() - 1);
		accountDAO.insertTransaction(accountNo, latestTrs);

		log.info("[Service: 입금 처리 완료] accountNo={}, 입금액={}, 최종잔액={}", 
				accountNo, amount, account.getBalance());
	}
	
	// 6. 출금(withdraw): 계좌 조회 -> 계좌에서 출금 실행(잔액 부족 시 도메인 예외 발생)
	public void withdraw(String accountNo, long amount) {
		log.info("[Service: 출금 처리 시작] accountNo={}, amount={}", accountNo, amount);
		Account account = getAccount(accountNo);
		
		account.withdraw(amount);
		accountDAO.updateBalance(accountNo, account.getBalance());
		
		List<Transaction> trsList = account.getTransactions();
		Transaction latestTrs = trsList.get(trsList.size() - 1);
		accountDAO.insertTransaction(accountNo, latestTrs);

		log.info("[Service: 출금 처리 완료] accountNo={}, 출금액={}, 최종잔액={}", 
				accountNo, amount, account.getBalance());
	}
	
	// 7. 거래 내역 조회(getTransaction): 특정 계좌의 거래 내역 목록 반환
	public List<Transaction> getTransactions(String accountNo) {
		log.info("[Service: 거래 내역 조회] accountNo={}", accountNo);
		getAccount(accountNo); // 계좌가 존재하는지 사전 검증
		List<Transaction> transactions = accountDAO.getTransactions(accountNo);
		log.info("[Service: 거래 내역 조회 완료] accountNo={}, count={}", accountNo, transactions.size());
		return transactions;
	}
}