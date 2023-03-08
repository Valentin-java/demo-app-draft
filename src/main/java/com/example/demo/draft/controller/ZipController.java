package com.example.demo.draft.controller;

import com.example.demo.draft.model.FileDTO;
import com.example.demo.draft.service.zip.Archives;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v2/payroll/registry")
public class ZipController {

    private final Archives registryAdapter;

    @PostMapping(path = "/import", produces = "application/json", consumes = "application/json")
    public void registryImport(@RequestBody FileDTO file) throws IOException {
        registryAdapter.decompress(file);
    }
}
