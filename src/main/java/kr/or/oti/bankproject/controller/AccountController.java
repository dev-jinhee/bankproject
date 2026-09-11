package kr.or.oti.bankproject.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.oti.bankproject.domain.Account;
import kr.or.oti.bankproject.dto.AccountCreateRequest;
import kr.or.oti.bankproject.dto.TransactionResponse;
import kr.or.oti.bankproject.dto.AmountRequest;
import kr.or.oti.bankproject.service.BankService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	private final BankService bankService;
	
	public AccountController(BankService bankService) {
		this.bankService=bankService;
	}
	
	// 1. 신규 계좌 개설
    // POST /accounts
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountCreateRequest request) {
        bankService.createAccount(request.getAccountNo(), request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("계좌가 성공적으로 개설되었습니다.");
    }

    // 2. 전체 계좌 목록 조회
    // GET /accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = bankService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // 3. 계좌 단건 조회
    // GET /accounts/{accountNo}
    @GetMapping("/{accountNo}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNo) {
        Account account = bankService.getAccount(accountNo);
        return ResponseEntity.ok(account);
    }

    // 4. 입금 처리
    // POST /accounts/{accountNo}/deposit
    @PostMapping("/{accountNo}/deposit")
    public ResponseEntity<String> deposit(
            @PathVariable String accountNo, 
            @RequestBody AmountRequest request) {
        bankService.deposit(accountNo, request.getAmount());
        return ResponseEntity.ok(request.getAmount() + "원이 정상적으로 입금되었습니다.");
    }

    // 5. 출금 처리
    // POST /accounts/{accountNo}/withdraw
    @PostMapping("/{accountNo}/withdraw")
    public ResponseEntity<String> withdraw(
            @PathVariable String accountNo, 
            @RequestBody AmountRequest request) {
        bankService.withdraw(accountNo, request.getAmount());
        return ResponseEntity.ok(request.getAmount() + "원이 정상적으로 출금되었습니다.");
    }

    // 6. 거래 내역 조회 (Transaction 엔티티 -> TransactionResponse DTO 변환)
    // GET //accounts/{accountNo}/transactions
    @GetMapping("/{accountNo}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable String accountNo) {
        List<TransactionResponse> responses = bankService.getTransactions(accountNo)
                .stream()
                .map(TransactionResponse::new) // 도메인 객체를 화면용 DTO로 변환
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // 7. 예외 처리 핸들러 (계좌 없음, 잔액 부족, 중복 계좌 등)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
