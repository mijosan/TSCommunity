package com.dodge.board.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableOAuth2Sso // OAuth2�� �ʱ�ȭ�� �ڵ������� �����Ѵ�.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		/*
		 * security .antMatcher("/**") .authorizeRequests() .antMatchers("/",
		 * "/callback", "/login**", "/webjars/**", "/error**") .permitAll()
		 * .anyRequest() .authenticated();
		 */
		
		
		security.authorizeRequests().antMatchers("/", "/system/**", "/callback").permitAll(); //����㰡
		security.authorizeRequests().antMatchers("/board/**").authenticated(); //������ ����� �㰡
		security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN"); //Role�� ADMIN�� ����� �㰡
		
		security.csrf().disable();
		security.formLogin().loginPage("/system/login").defaultSuccessUrl("/board/getBoardList", true); //�α���ȭ�� ����
		security.exceptionHandling().accessDeniedPage("/system/accessDenied"); //���� ȭ���� �ٸ�������(system/accessDenied) �����ֱ�
		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/"); //��������
	}
}
