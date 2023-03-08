package com.example.demo.draft.controller;

import com.example.demo.draft.controller.model.SalaryRegistryDocumentListItem;
import com.example.demo.draft.entity.SalaryRegistryDocumentListItemEntity;
import com.example.demo.draft.model.Department;
import com.example.demo.draft.model.NFODepartmentDTO;
import com.example.demo.draft.model.PaymentRegistryDTO;
import com.example.demo.draft.model.PaymentRegistryRequestDTO;
import com.example.demo.draft.model.SalaryRegistriesRequest;
import com.example.demo.draft.model.TestExcRequest;
import com.example.demo.draft.model.TestExcResponse;
import com.example.demo.draft.model.modelwithdsl.RegistryListItem;
import com.example.demo.draft.service.QueryTestService;
import com.example.demo.draft.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Random;

@RestController
@AllArgsConstructor
public class Controller {

    private final QueryTestService service;
    private final TestService testService;

    @GetMapping(value = "/registry/test/request")
    public String getQueryString(@PathParam("id") Long id) {
        return String.valueOf(Math.random() * id);
    }

    @PostMapping(path = "/update", produces = "application/json")
    public void update(@RequestBody PaymentRegistryDTO request) {
        testService.updateFiles(request);
    }

    @PostMapping(
            value = "/registry/get-list",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getQueryString(@RequestBody SalaryRegistriesRequest request){
        return service.getTestStringQuerry(request);
    }

    @PostMapping(
            value = "/registry/get-list-obj",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<SalaryRegistryDocumentListItemEntity> getQueryObj(@RequestBody SalaryRegistriesRequest request){
        return service.getSalaryRegistryDocumentList(request);
    }

    @PostMapping(
            value = "/registry/get-list-dto",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<SalaryRegistryDocumentListItem> getListByMapper(@RequestBody SalaryRegistriesRequest request){
        return testService.getListByMapper(request);
    }

    @PostMapping(
            value = "/registry/get-list-obj2",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<RegistryListItem> getQueryObj2(@RequestBody SalaryRegistriesRequest request){
        return service.getRegistryDocumentList(request);
    }

    @PostMapping(
            value = "/test-exception",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public TestExcResponse getTestException(@RequestBody TestExcRequest request) {
        return service.getTestException(request);
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<TestExcResponse> statusRuntimeExceptionHandler(ResponseStatusException e) {
        var response = TestExcResponse.builder()
                .code(e.getStatus().value())
                .description(e.getReason())
                .build();
        return new ResponseEntity<>(response, e.getStatus());
    }

    @PostMapping(
            value = "/test-get-active",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Department> getActiveList() {
        return service.getActiveList();
    }

    @PostMapping(
            value = "/test-get-address-by-code",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public NFODepartmentDTO getAddressByCode(@RequestBody String code) {
        return service.getAddressByCode(code);
    }
}
