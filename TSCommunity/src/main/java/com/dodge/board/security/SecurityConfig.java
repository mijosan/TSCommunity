package com.dodge.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{//WebSecurityConfigurerAdapter 클래스를 상속한 객체가 빈으로 등록되기만해도 로그인강제 X
	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {//웹 시큐리티와 관련된 다양한 설정을 추가
		security.userDetailsService(userDetailsService);
		
		security.authorizeRequests().antMatchers("/", "/system/**", "/board/getBoardList", "/board/getBoard").permitAll(); //모두허가
		security.authorizeRequests().antMatchers("/board/insertBoard", "/board/updateBoard").authenticated(); //인증된 사용자 허가
		security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN"); //Role이 ADMIN인 사용자 허가
		
		security.csrf().disable();
		security.formLogin().loginPage("/system/login").defaultSuccessUrl("/home", true).failureUrl("/system/login?result=fail"); //로그인화면 제공
		security.exceptionHandling().accessDeniedPage("/system/accessDenied"); //에러 화면대신 다른페이지(system/accessDenied) 보여주기
		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/home"); //세션종료
		
		
	}
	//패스워드 인코더 빈 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
