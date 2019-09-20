package com.dodge.board.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // WebSocket�� ���õ� ������ �۵���Ų��.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	//�޼��� ���Ŀ(Message Broker)�� �۽��ڿ��� �������� ���� �޼��� �������ݷ� ��ȯ���ִ� ��� �߿� �ϳ��̴�. 
	//��û�� ���� �׿� �ش��ϴ� ��� ä�η� �������ְ� ���� ���� �Դ� ���� �״�� �ٽ� ���� ���������� �޼����� �������ֱ� ���� �κ��Դϴ�.
	
	
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic"); //Ŭ���̾�Ʈ�� �޼����� �������� �� prefix�� �����Ѵ�		���� => Ŭ���̾�Ʈ
		registry.setApplicationDestinationPrefixes("/app"); //Ŭ���̾�Ʈ���� �޼��� �۽� �� �ٿ��� prefix�� �����Ѵ�. 
															//���� <= Ŭ���̾�Ʈ
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").withSockJS(); //���� ���� ������ �ϴ� ���, endpoint�� �Ǵ� url�̴�. 
														 //���� javascript���� SockJS �����ڸ� ���� ����� ���̴�.
	}
}
