package com.nikhil.SpringBootLangChain4JIntegration.service;

import org.springframework.stereotype.Service;

import dev.langchain4j.model.chat.ChatModel;

@Service
public class AiService {

	private final ChatModel chatModel;

	public AiService(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	public String chat(String message) {
		return chatModel.chat(message);
	}
}
