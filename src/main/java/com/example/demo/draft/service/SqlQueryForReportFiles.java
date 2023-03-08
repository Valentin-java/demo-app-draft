package com.example.demo.draft.service;

import com.example.demo.draft.entity.RegistryFileEntity;

import java.util.List;

public interface SqlQueryForReportFiles {

    List<RegistryFileEntity> getRegistryFileList(List<Long> request);

    String buildRegistryFileListQuery(List<Long> request);
}
