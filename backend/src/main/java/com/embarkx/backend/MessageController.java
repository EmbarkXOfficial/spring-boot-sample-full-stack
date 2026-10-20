package com.embarkx.backend;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

	private final String message;

	private final String version;

	public MessageController(@Value("${APP_MESSAGE:Hello from EmbarkX}") String message,
			@Value("${APP_VERSION:local}") String version) {
		this.message = message;
		this.version = version;
	}

	@GetMapping("/message")
	public MessageResponse getMessage() {
		return new MessageResponse(this.message, this.version, OffsetDateTime.now().toString());
	}

	public record MessageResponse(String message, String version, String serverTime) {
	}

}
