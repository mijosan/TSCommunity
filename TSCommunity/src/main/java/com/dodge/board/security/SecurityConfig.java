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
		
		security.authorizeRequests().antMatchers("/", "/system/login", "/boards").permitAll(); //����㰡
		security.authorizeRequests().antMatchers("/system/myInfo", "/boards/create", "/boards/updateBoard", 
		"/boards/like-list", "/boards/my-list", "/boards/my-cm-list").authenticated(); //������ ����� �㰡
		security.headers().frameOptions().disable();
		
		security.csrf().disable();//ũ�ν� ����Ʈ���� ��û�� ���� ���� ��Ȱ��ȭ
		security.formLogin().loginPage("/system/login").defaultSuccessUrl("/home", true).failureUrl("/system/login?result=fail"); //�α���ȭ�� ����
		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/home"); //��������
		
		//���� �� ��������� �̵� URL + �ִ���� �ߺ����� 1 + ������ �ߺ��Ǹ� �̵��� ������(���߿� �α����� ����� ������ ������ Ŭ���̾�Ʈ�� �������̵�)
		security.sessionManagement().invalidSessionUrl("/system/login").maximumSessions(1).expiredUrl("/system/login");
		
	}
	//�н����� ���ڴ� �� ���
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
