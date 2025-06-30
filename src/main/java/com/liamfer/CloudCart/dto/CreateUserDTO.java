package com.liamfer.CloudCart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(@NotBlank String name,
                            @NotBlank @Email String email,
                            @NotBlank @Size(min = 6,max = 12) String password,
                            @NotBlank @Size(min = 11,max = 11) String cpf) {
}
