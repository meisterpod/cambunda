package com.poc.cambunda.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final WebClient.Builder webClientBuilder;
    private final Environment env;
    private String cachedToken;
    private Instant tokenExpiration;

    public String getToken() {
        if (cachedToken != null && tokenExpiration != null && tokenExpiration.isAfter(Instant.now())) {
            return cachedToken;
        }

        WebClient webClient = webClientBuilder.build();

        String tokenUrl = env.getProperty("keycloak.token-url");
        String clientId = env.getProperty("keycloak.client-id");
        String clientSecret = env.getProperty("keycloak.client-secret");
        String grantType = env.getProperty("keycloak.grant-type");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", grantType);

        assert tokenUrl != null;
        TokenResponse response = webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();

        assert response != null;
        cachedToken = response.getAccessToken();
        tokenExpiration = Instant.now().plusSeconds(response.getExpiresIn() - 60); // refresh 1 min early

        System.out.println("cachedToken : " + cachedToken);

        return cachedToken;
    }

    @Data
    static class TokenResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_in")
        private long expiresIn;
    }
}

