package dev.acobano.springsecurity.basicauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityBasicAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBasicAuthApplication.class, args);
	}

	/*
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner comandoCreacionContrasenas()
	{
		return args -> {
			System.out.println("CONTRASENA ADMINISTRADOR");
			System.out.println(passwordEncoder.encode("clave_admin"));
			System.out.println("CONTRASENA USUARIO");
			System.out.println(passwordEncoder.encode("clave_user01"));
			System.out.println("CONTRASENA ENCARGADO");
			System.out.println(passwordEncoder.encode("clave_manager77"));
		};
	}
	*/
}
