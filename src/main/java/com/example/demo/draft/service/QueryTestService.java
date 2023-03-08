package com.example.demo.draft.service;

import com.example.demo.draft.entity.SalaryRegistryDocumentListItemEntity;
import com.example.demo.draft.model.Department;
import com.example.demo.draft.model.NFODepartmentDTO;
import com.example.demo.draft.model.SalaryRegistriesRequest;
import com.example.demo.draft.model.TestExcRequest;
import com.example.demo.draft.model.TestExcResponse;
import com.example.demo.draft.model.modelwithdsl.RegistryListItem;

import java.util.List;

public interface QueryTestService {

    NFODepartmentDTO getAddressByCode(String code);

    List<RegistryListItem> getRegistryDocumentList(SalaryRegistriesRequest request);

    String getTestStringQuerry(SalaryRegistriesRequest request);

    TestExcResponse getTestException(TestExcRequest request);

    List<SalaryRegistryDocumentListItemEntity> getSalaryRegistryDocumentList(SalaryRegistriesRequest request);

    List<Department> getActiveList();

}
