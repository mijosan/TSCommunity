package com.dodge.board.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.dodge.board.domain.Chat;
import com.dodge.board.domain.Greeting;
import com.dodge.board.domain.HelloMessage;

@Controller
public class GreetingController {
	
	@MessageMapping("/hello") //hello 쪽으로 메세지를 전달하면 greeting 메서드가 실행
	@SendTo("/topic/greetings") //이쪽으로 결과를 return
	public Greeting greeting(HelloMessage message) throws Exception{
		Thread.sleep(100);
		
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}
	
	@MessageMapping("/chat")
	@SendTo("/topic/chat")
	public Chat chat(Chat chat) throws Exception {
	  return new Chat(chat.getName(), chat.getMessage());
	}
}
