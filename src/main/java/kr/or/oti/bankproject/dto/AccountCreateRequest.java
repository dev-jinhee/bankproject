package kr.or.oti.bankproject.dto;

public class AccountCreateRequest {
    private String accountNo;
    private String name;

    // 기본 생성자 (스프링이 JSON을 객체로 변환할 때 필수)
    public AccountCreateRequest() {}

    public AccountCreateRequest(String accountNo, String name) {
        this.accountNo = accountNo;
        this.name = name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}