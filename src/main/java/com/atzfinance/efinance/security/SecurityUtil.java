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
    /**
     * Utility class for handling security-related operations.
     *
     * This class provides methods for authentication, authorization, and other security features.
     */
    public static String getSesstionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Authenticate user using SecurityUtil
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            return currentUsername;
        }
        return null;
    }
}
