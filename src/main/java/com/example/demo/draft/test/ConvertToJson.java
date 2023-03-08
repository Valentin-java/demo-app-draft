package com.example.demo.draft.test;

import com.example.demo.draft.model.NFODepartmentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class ConvertToJson {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        var test1Obj = new NFODepartmentDTO();
        test1Obj.setId(1L);
        test1Obj.setShortName("AAA");
        test1Obj.setStartDate(LocalDate.now());
        test1Obj.setEndDate(LocalDate.now());

        String json = mapper.writeValueAsString(test1Obj);
        System.out.println(json);

        ObjectMapper mapper1 = new ObjectMapper();

        String response = "{\n" +
                "    \"id\": 9643,\n" +
                "    \"shortName\": \"Красные ворота\",\n" +
                "    \"aliasName\": \"Красные ворота\",\n" +
                "    \"branchId\": 2,\n" +
                "    \"branchCode\": null,\n" +
                "    \"parentId\": \"9757\",\n" +
                "    \"typeCode\": null,\n" +
                "    \"city\": \"Москва\",\n" +
                "    \"departmentAddress\": \"107078, г. Москва, ул. Маши Порываевой, д.34\",\n" +
                "    \"email\": null,\n" +
                "    \"isCorpVisible\": true,\n" +
                "    \"sortOrder\": 249,\n" +
                "    \"startDate\": \"2006-02-13\",\n" +
                "    \"endDate\": null,\n" +
                "    \"timezoneDelta\": null\n" +
                "}";
        System.out.println(response);

        var responseObj = mapper.readValue(response, NFODepartmentDTO.class);

        System.out.println(responseObj.toString());



    }
}
