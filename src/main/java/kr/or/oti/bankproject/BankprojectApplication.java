package kr.or.oti.bankproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("kr.or.oti.bankproject.dao")
public class BankprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankprojectApplication.class, args);
	}

}
