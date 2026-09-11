package kr.or.oti.bankproject.dao;

import java.util.List;

import kr.or.oti.bankproject.domain.Account;
import kr.or.oti.bankproject.domain.Transaction;

public interface AccountDAO {
    // 1. 계좌 등록 (INSERT INTO account ...)
    void addAccount(String accountNo, String name);

    // 2. 계좌 단건 조회 (SELECT * FROM account WHERE account_no = ?)
    Account getAccount(String accountNo);

    // 3. 소유자명으로 계좌 목록 조회 (SELECT * FROM account WHERE name = ?)
    List<Account> findAccounts(String name);

    // 4. 전체 계좌 목록 조회 (SELECT * FROM account)
    List<Account> getAccounts();

    // 5. 총 계좌 수 조회 (SELECT COUNT(*) FROM account)
    int getTotalAccount();

    // 6. 잔액 업데이트 (UPDATE account SET balance = ? WHERE account_no = ?)
    void updateBalance(String accountNo, long balance);

    // 7. 거래 내역 저장 (INSERT INTO transaction ...)
    void insertTransaction(String accountNo, Transaction transaction);

    // 8. 특정 계좌의 거래 내역 조회 (SELECT * FROM transaction WHERE account_no = ?)
    List<Transaction> getTransactions(String accountNo); 
}
