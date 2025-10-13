package com.bank.management_system.config;

import com.bank.management_system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Test implements CommandLineRunner {

    @Autowired
    private TransactionService transactionService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Starting Transaction Test ===");

        try {
            transactionService.deposit("ACC100001", BigDecimal.valueOf(2000.0),"Depositing the money");
            System.out.println("Deposited 2000 into ACC100001");

            transactionService.withdraw("ACC100001", BigDecimal.valueOf(500.0),"withdrawing the money");
            System.out.println("Withdrew 500 from ACC100001");

            transactionService.transfer("ACC100001", "ACC100002", BigDecimal.valueOf(1000.0),"Transferring the money");
            System.out.println("Transferred 1000 from ACC100001 to ACC100002");
        } catch (Exception e) {
            System.out.println("Transaction Error: " + e.getMessage());
        }
        System.out.println("=== Transaction Test Complete ===");
    }
}
