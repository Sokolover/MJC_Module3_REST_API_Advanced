package com.epam.esm.sokolov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "is mandatory")
    private String username;
    @NotBlank(message = "is mandatory")
    private String password;
}
