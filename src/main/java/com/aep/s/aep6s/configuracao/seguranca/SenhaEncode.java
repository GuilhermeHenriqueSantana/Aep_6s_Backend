package com.aep.s.aep6s.configuracao.seguranca;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaEncode {
	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("123456"));
	}
}
