package com.nikhil.SpringBootLangChain4JIntegration.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.SpringBootLangChain4JIntegration.service.AiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI APIs", description = "LangChain4j OpenAI REST APIs")
public class AiController {

	private final AiService aiService;

	public AiController(AiService aiService) {
		this.aiService = aiService;
	}

	@GetMapping("/chat")
	@Operation(summary = "Chat with OpenAI using GET")
	public Map<String, String> chat(@RequestParam String message) {

		String response = aiService.chat(message);

		return Map.of("question", message, "response", response);
	}

	@PostMapping("/chat")
	@Operation(summary = "Chat with OpenAI using POST")
	public Map<String, String> chatPost(@RequestBody Map<String, String> request) {

		String message = request.get("message");

		String response = aiService.chat(message);

		return Map.of("question", message, "response", response);
	}
}