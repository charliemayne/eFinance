package com.atzfinance.efinance.dev;

import com.atzfinance.efinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
/**
 * The AfterStartup class
 * Date: 11/19/23
 * @authors charlimayene,roselam
 */

@Component
public class AfterStartup {
    @Autowired
    private UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        System.out.println("Creating test accounts if the don't exist...");
        if (userService.getUserByUsername("customer").isEmpty()) {
            userService.saveUser("customer", "customer", "customer@email.com", "CUSTOMER");
            System.out.println("Created 'customer' account");
        }
        if (userService.getUserByUsername("employee").isEmpty()) {
            userService.saveUser("employee", "employee", "employee@atzfinance.com", "EMPLOYEE");
            System.out.println("Created 'employee' account");
        }
        System.out.println("Done.");
    }
}
