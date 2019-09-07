package com.dodge.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{//WebSecurityConfigurerAdapter Ŭ������ ����� ��ü�� ������ ��ϵǱ⸸�ص� �α��ΰ��� X
	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {//�� ��ť��Ƽ�� ���õ� �پ��� ������ �߰�
		security.userDetailsService(userDetailsService);
		
		security.authorizeRequests().antMatchers("/", "/system/**", "/board/getBoardList", "/board/getBoard").permitAll(); //����㰡
		security.authorizeRequests().antMatchers("/board/insertBoard", "/board/updateBoard").authenticated(); //������ ����� �㰡
		security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN"); //Role�� ADMIN�� ����� �㰡
		
		security.csrf().disable();
		security.formLogin().loginPage("/system/login").defaultSuccessUrl("/home", true).failureUrl("/system/login?result=fail"); //�α���ȭ�� ����
		security.exceptionHandling().accessDeniedPage("/system/accessDenied"); //���� ȭ���� �ٸ�������(system/accessDenied) �����ֱ�
		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/home"); //��������
		
		
	}
	//�н����� ���ڴ� �� ���
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
