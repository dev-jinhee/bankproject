package kr.or.oti.bankproject.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.oti.bankproject.domain.Account;

@Repository
public class BankRepository {
	private final List<Account> accounts;
	
	public BankRepository() {
		this.accounts = new ArrayList<>();
	}
	
	//신규 계좌 가입
	public void addAccount(String accountNo, String name) {		
		accounts.add(new Account(accountNo, name));			
	}

	// 계좌 번호로 계좌 1건 찾기
	public Account getAccount(String accountNo) {
		for(Account account: accounts) {
			if(account.getAccountNo().equals(accountNo)) {			
				return account;
			}
		}
		System.out.println("일치하는 계좌가 없습니다.");
		return null;		
	}
	
	// 이름으로 계좌 찾기		
	public List<Account> findAccounts(String name){	
		List<Account> find = new ArrayList<Account>();		
	
		for(Account acc: accounts) {
			if(acc.getName().equals(name)) {			
				find.add(acc);				
			}else {
				System.out.println("일치하는 소유자가 없습니다.");
			}
		}
		return find;
	}
	
	// 전체 계좌 목록 가져오기	
	public List<Account> getAccounts(){	
		return accounts;
		
	}
	 // 총 계좌수를 반환
	public int getTotalAccount() {
		return accounts.size();
	}

}
