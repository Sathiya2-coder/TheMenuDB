package com.example.menudb;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menudb")
public class MenuDbController {

    private final MenuDbService menuDbService;

    public MenuDbController(MenuDbService menuDbService) {
        this.menuDbService = menuDbService;
    }

    @GetMapping
    public ResponseEntity<byte[]> queryMenuDb(
            @RequestParam MultiValueMap<String, String> queryParams) {
        return menuDbService.query(queryParams);
    }
}
