package com.embarkx.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

	@Autowired
	private MockMvcTester mvc;

	@Test
	void messageEndpointReturnsDefaults() {
		assertThat(this.mvc.get().uri("/api/message")).hasStatusOk()
			.bodyJson()
			.satisfies((json) -> {
				assertThat(json).extractingPath("$.message").isEqualTo("Hello from EmbarkX");
				assertThat(json).extractingPath("$.version").isEqualTo("local");
				assertThat(json).extractingPath("$.serverTime").isNotNull();
			});
	}

	@Test
	void healthEndpointIsUp() {
		assertThat(this.mvc.get().uri("/actuator/health")).hasStatusOk()
			.bodyJson()
			.extractingPath("$.status")
			.isEqualTo("UP");
	}

}
