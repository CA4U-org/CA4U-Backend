package com.example.ca4u_backend.filter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ca4u_backend.apiResponse.ApiKeyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiFilterTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ApiKeyService apiKeyService;

  private static final String VALID_API_KEY = " ";
  private static final String INVALID_API_KEY = "abcdefghijklmnop";

  @BeforeEach
  void setUp() {
    // Mock ApiKeyService behavior
    when(apiKeyService.isValid(VALID_API_KEY)).thenReturn(true);
    when(apiKeyService.isValid(INVALID_API_KEY)).thenReturn(false);
  }

  @Test
  void whenValidApiKey_thenRequestIsSuccessful() throws Exception {
    mockMvc
        .perform(get("/api/clubs/test").header("X-API-KEY", VALID_API_KEY))
        .andExpect(status().isOk());
  }

  @Test
  void whenInvalidApiKey_thenRequestIsUnauthorized() throws Exception {
    mockMvc
        .perform(get("/api/test").header("X-API-KEY", INVALID_API_KEY))
        .andExpect(status().isUnauthorized())
        .andExpect(content().json("{\"error\": \"Invalid or missing API Key\"}"));
  }
}
