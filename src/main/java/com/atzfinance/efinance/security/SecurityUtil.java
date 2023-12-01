package com.atzfinance.efinance.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Security Util
 * Date: 11/19/23
 * @authors charlimayene
 */
public class SecurityUtil {
    public static String getSesstionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            return currentUsername;
        }
        return null;
    }
}
