package dev.acobano.springsecurity.basicauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class AutenticacionRequest
{
    private String username;
    private String password;
}
