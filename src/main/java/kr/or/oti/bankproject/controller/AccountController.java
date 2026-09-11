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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.oti.bankproject.domain.Account;
import kr.or.oti.bankproject.dto.AccountCreateRequest;
import kr.or.oti.bankproject.dto.AmountRequest;
import kr.or.oti.bankproject.dto.TransactionResponse;
import kr.or.oti.bankproject.service.BankService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final BankService bankService;

    public AccountController(BankService bankService) {
        this.bankService = bankService;
    }

    // 1. 신규 계좌 개설
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountCreateRequest request) {
        log.info("[계좌 개설 요청] accountNo={}, name={}", request.getAccountNo(), request.getName());
        bankService.createAccount(request.getAccountNo(), request.getName());
        log.info("[계좌 개설 완료] accountNo={}", request.getAccountNo());
        return ResponseEntity.status(HttpStatus.CREATED).body("계좌가 성공적으로 개설되었습니다.");
    }

    // 2. 전체 계좌 목록 조회
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        log.info("[전체 계좌 목록 조회 요청]");
        List<Account> accounts = bankService.getAllAccounts();
        log.info("[전체 계좌 목록 조회 완료] count={}", accounts.size());
        return ResponseEntity.ok(accounts);
    }

    // 3. 소유자명으로 계좌 목록 검색 (GET /accounts/search?name=홍길동)
    @GetMapping("/search")
    public ResponseEntity<List<Account>> searchByName(@RequestParam("name") String name) {
        log.info("[소유자명 계좌 검색 요청] name={}", name);
        List<Account> accounts = bankService.findAccountByname(name);
        log.info("[소유자명 계좌 검색 완료] name={}, count={}", name, accounts.size());
        return ResponseEntity.ok(accounts);
    }

    // 4. 계좌 단건 조회 (GET /accounts/{accountNo})
    @GetMapping("/{accountNo}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountNo") String accountNo) {
        log.info("[단건 계좌 조회 요청] accountNo={}", accountNo);
        Account account = bankService.getAccount(accountNo);
        return ResponseEntity.ok(account);
    }

    // 5. 입금 처리
    @PostMapping("/{accountNo}/deposit")
    public ResponseEntity<String> deposit(
            @PathVariable("accountNo") String accountNo,
            @RequestBody AmountRequest request) {
        log.info("[입금 요청] accountNo={}, amount={}", accountNo, request.getAmount());
        bankService.deposit(accountNo, request.getAmount());
        log.info("[입금 완료] accountNo={}, amount={}", accountNo, request.getAmount());
        return ResponseEntity.ok(request.getAmount() + "원이 정상적으로 입금되었습니다.");
    }

    // 6. 출금 처리
    @PostMapping("/{accountNo}/withdraw")
    public ResponseEntity<String> withdraw(
            @PathVariable("accountNo") String accountNo,
            @RequestBody AmountRequest request) {
        log.info("[출금 요청] accountNo={}, amount={}", accountNo, request.getAmount());
        bankService.withdraw(accountNo, request.getAmount());
        log.info("[출금 완료] accountNo={}, amount={}", accountNo, request.getAmount());
        return ResponseEntity.ok(request.getAmount() + "원이 정상적으로 출금되었습니다.");
    }

    // 7. 거래 내역 조회
    @GetMapping("/{accountNo}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable("accountNo") String accountNo) {
        log.info("[거래 내역 조회 요청] accountNo={}", accountNo);
        List<TransactionResponse> responses = bankService.getTransactions(accountNo)
                .stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());
        log.info("[거래 내역 조회 완료] accountNo={}, txCount={}", accountNo, responses.size());
        return ResponseEntity.ok(responses);
    }

    // 8. 클라이언트 검증 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        log.error("[클라이언트 요청 오류] {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // 9. DB 연동 및 서버 내부 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception e) {
        log.error("[서버 시스템 오류 발생]", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
    }
}