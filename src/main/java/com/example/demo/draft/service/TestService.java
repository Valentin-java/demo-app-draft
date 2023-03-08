package com.example.demo.draft.service;

import com.example.demo.draft.controller.model.SalaryRegistryDocumentListItem;
import com.example.demo.draft.model.PaymentRegistryDTO;
import com.example.demo.draft.model.Registry;
import com.example.demo.draft.model.SalaryRegistriesRequest;

import java.util.List;

public interface TestService {

    List<SalaryRegistryDocumentListItem> getListByMapper(SalaryRegistriesRequest request);

    void updateFiles(PaymentRegistryDTO paymentRegistry);
}
