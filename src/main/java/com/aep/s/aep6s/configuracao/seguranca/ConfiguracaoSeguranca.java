package com.aep.s.aep6s.configuracao.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aep.s.aep6s.servico.ServicoPersonalizadoDetalhesUsuario;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter{
	@Autowired
	private ServicoPersonalizadoDetalhesUsuario servicoPersonalizadoDetalhesUsuario;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.httpBasic()
			.and()
			.cors()
			.and()
			.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(servicoPersonalizadoDetalhesUsuario).passwordEncoder(new BCryptPasswordEncoder());
	}
}
