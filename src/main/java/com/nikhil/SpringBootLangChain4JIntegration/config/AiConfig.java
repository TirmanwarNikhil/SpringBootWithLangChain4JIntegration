package com.nikhil.SpringBootLangChain4JIntegration.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

	@Value("${app.ai.openai.api-key}")
	private String apiKey;

	@Value("${app.ai.openai.model-name}")
	private String modelName;

	@Value("${app.ai.openai.temperature}")
	private double temperature;

	@Bean
	public ChatModel chatModel() {
		return OpenAiChatModel.builder().apiKey(apiKey).modelName(modelName).temperature(temperature).build();
	}
}
