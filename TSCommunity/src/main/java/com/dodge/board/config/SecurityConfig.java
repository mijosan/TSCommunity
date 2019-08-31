package com.dodge.board.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableOAuth2Sso // OAuth2의 초기화와 자동설정을 지원한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		/*
		 * security .antMatcher("/**") .authorizeRequests() .antMatchers("/",
		 * "/callback", "/login**", "/webjars/**", "/error**") .permitAll()
		 * .anyRequest() .authenticated();
		 */
		
		
		security.authorizeRequests().antMatchers("/", "/system/**", "/callback").permitAll(); //모두허가
		security.authorizeRequests().antMatchers("/board/**").authenticated(); //인증된 사용자 허가
		security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN"); //Role이 ADMIN인 사용자 허가
		
		security.csrf().disable();
		security.formLogin().loginPage("/system/login").defaultSuccessUrl("/board/getBoardList", true); //로그인화면 제공
		security.exceptionHandling().accessDeniedPage("/system/accessDenied"); //에러 화면대신 다른페이지(system/accessDenied) 보여주기
		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/"); //세션종료
	}
}
