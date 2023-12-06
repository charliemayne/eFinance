package com.atzfinance.efinance.dto;

import lombok.Data;
/**
 * RegistrationDto
 * Date: 11/19/23
 * @authors charlimayene
 */

@Data
public class RegistrationDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
