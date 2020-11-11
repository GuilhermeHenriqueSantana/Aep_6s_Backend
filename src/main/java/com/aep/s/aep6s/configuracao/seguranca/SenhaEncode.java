package com.aep.s.aep6s.configuracao.seguranca;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaEncode {
	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("123456"));
		
		Long a = new Long("1605044654020");
		
		Date presente = new Date(a);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataString = sdf.format(presente);
		System.out.println(dataString);
		System.out.println(presente.getTime());
	}
}
