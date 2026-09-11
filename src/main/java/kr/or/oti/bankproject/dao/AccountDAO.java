package kr.or.oti.bankproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.oti.bankproject.domain.Account;
import kr.or.oti.bankproject.domain.Transaction;

@Mapper
public interface AccountDAO {
    // 1. 계좌 등록 (INSERT INTO account ...)
    void addAccount(@Param("accountNo") String accountNo, @Param("name") String name);

    // 2. 계좌 단건 조회 (SELECT * FROM account WHERE accountNo = ?)
    Account getAccount(String accountNo);

    // 3. 소유자명으로 계좌 목록 조회 (SELECT * FROM account WHERE name = ?)
    List<Account> findAccounts(String name);

    // 4. 전체 계좌 목록 조회 (SELECT * FROM account)
    List<Account> getAccounts();

    // 5. 총 계좌 수 조회 (SELECT COUNT(*) FROM account)
    int getTotalAccount();

    // 6. 잔액 업데이트 (UPDATE account SET balance = ? WHERE accountNo = ?)
    void updateBalance(@Param("accountNo") String accountNo, @Param("balance") long balance);

    // 7. 거래 내역 저장 (INSERT INTO transactions ...)
    void insertTransaction(@Param("accountNo") String accountNo, @Param("transaction") Transaction transaction);

    // 8. 특정 계좌의 거래 내역 조회 (SELECT * FROM transactions WHERE accountNo = ?)
    List<Transaction> getTransactions(String accountNo); 
}