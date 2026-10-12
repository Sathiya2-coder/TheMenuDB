package com.example.menudb;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MenuDbService {

    private final RestClient restClient;
    private final String apiUrl;

    public MenuDbService(
            RestClient.Builder restClientBuilder,
            @Value("${menudb.api-url}") String apiUrl) {
        this.restClient = restClientBuilder.build();
        this.apiUrl = apiUrl;
    }

    public ResponseEntity<byte[]> query(MultiValueMap<String, String> queryParams) {
        URI uri = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParams(queryParams)
                .build()
                .encode()
                .toUri();

        return restClient.get()
                .uri(uri)
                .exchange((request, response) -> {
                    byte[] body = response.getBody().readAllBytes();
                    HttpHeaders headers = new HttpHeaders();
                    if (response.getHeaders().getContentType() != null) {
                        headers.setContentType(response.getHeaders().getContentType());
                    }
                    return ResponseEntity.status(response.getStatusCode())
                            .headers(headers)
                            .body(body);
                });
    }
}
