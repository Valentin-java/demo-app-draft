package com.example.demo.draft.controller;

import com.example.demo.draft.entity.RegistryFileEntity;
import com.example.demo.draft.model.SalaryRegistriesRequest;
import com.example.demo.draft.service.SqlQueryForReportFiles;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReportFilesController {

    private final SqlQueryForReportFiles service;

    @PostMapping(
            value = "/registry/get-file-list",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<RegistryFileEntity> getRegistryFileList(@RequestBody List<Long> request) {
        return service.getRegistryFileList(request);
    }

    @PostMapping(
            value = "/registry/get-file-query-string",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String buildRegistryFileListQuery(@RequestBody List<Long> request) {
        return service.buildRegistryFileListQuery(request);
    }
}
