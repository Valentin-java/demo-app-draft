package com.example.demo.draft.service;


import com.example.demo.draft.entity.SalaryRegistryDocumentListItemEntity;
import com.example.demo.draft.mapper.DepartmentDTOMapper;
import com.example.demo.draft.mapper.NFODepartmentMapper;
import com.example.demo.draft.model.*;
import com.example.demo.draft.model.modelwithdsl.RegistryListItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.OrderField;
import org.jooq.SQLDialect;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.jsonObject;
import static org.jooq.impl.DSL.key;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.SQLDataType.VARCHAR;

@Slf4j
@Component
public class SqlQeryBuild implements QueryTestService {

    private final int SALARY_REGISTRY_DOC_TYPE = 55;
    private final int REGISTRY_DELETE_STATUS = 0;

    // Число месяцев, которым ограничивается выборка по дате архивации из архивных таблиц
    private final int ARCHIVE_MONTH_IN_THE_PAST = 6;

    private final Field<Object> documentSerialField = field("d.doc_serial");
    private final Field<Object> clientIdsField = field("d.cli");
    private final Field<Object> documentDateField = field("d.i_date");
    private final Field<Object> documentStatusField = field("d.status");
    private final Field<Object> documentStatusNameField = field("cs.sname");
    private final Field<Object> registryNumberField = field("d.doc_num");
    private final Field<Object> clientNameField = field("c.sname");
    private final Field<Object> documentTextField = field("mi.ltext");

    private final Field<Object> documentTableArchiveDateField = field("d.a_date");

    private final Field<Object> objectNameOfFileRegistryField = field("mi.obj_name");
    private final Field<Object> objectTypeOfFileRegistryField = field("mi.obj_type");

    private final Field<Object> externalIdOfFileRegistryField = field("d2.doc_serial");
    private final Field<Object> parentIdOfFileReportField = field("d2.parent");
    private final Field<Object> objectNameOfFileReportField = field("mi2.obj_name");
    private final Field<Object> objectTypeOfFileReportField = field("mi2.obj_type");

    private final DSLContext dslContext = DSL.using(SQLDialect.DEFAULT);

    private static final String ACTION_NOT_ALLOWED = "Действие с документом недоступно";

    /*public SqlQeryBuild(DSLContext dslContext) {
        this.dslContext = dslContext;
    }*/

    private final EntityManager entityManager;
    private final RestTemplate restTemplate;
    private String dictionaryNewUrl;
    private String dictionaryGetByCodeUrl;
    private String dictionaryOldUrl;
    private String dictionaryToken;

    public SqlQeryBuild(
            EntityManager entityManager,
            RestTemplate restTemplate,
            @Value("${dictionary.new.url}") String dictionaryNewUrl,
            @Value("${dictionary.get.by.code.url}") String dictionaryGetByCodeUrl,
            @Value("${dictionary.old.url}") String dictionaryOldUrl,
            @Value("${dictionary.token}") String dictionaryToken) {
        this.dictionaryNewUrl = dictionaryNewUrl;
        this.dictionaryOldUrl = dictionaryOldUrl;
        this.dictionaryGetByCodeUrl = dictionaryGetByCodeUrl;
        this.dictionaryToken = dictionaryToken;
        this.entityManager = entityManager;
        this.restTemplate = restTemplate;
    }

    @Override
    public NFODepartmentDTO getAddressByCode(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + dictionaryToken);
        HttpEntity entity = new HttpEntity(code, headers);

        ResponseEntity<NFODepartmentDTO> jsonResponse = null;
        try {
            jsonResponse = restTemplate.exchange(
                    dictionaryGetByCodeUrl,
                    HttpMethod.POST,
                    entity,
                    NFODepartmentDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (jsonResponse == null)
            return null; //return error
        NFODepartmentDTO nfoDepartmentDTO = new NFODepartmentDTO();

        return nfoDepartmentDTO;
    }

    @Override
    public List<Department> getActiveList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + dictionaryToken);

        HttpEntity entity = new HttpEntity(null, headers);

        try {
            log.warn("[getDepartments] Start new endpoint");

            var responseNew = restTemplate.exchange(
                    dictionaryNewUrl,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<List<NFODepartmentDTO>>() {
                    });
            if (responseNew.getStatusCode().is2xxSuccessful()) {
                log.warn("[getDepartments] Success new endpoint");

                var nfoDepartmentMapper = Mappers.getMapper(NFODepartmentMapper.class);
                return responseNew.getBody() != null ? responseNew.getBody().stream()
                        .map(nfoDepartmentMapper::toDomain)
                        .collect(Collectors.toList())
                        : new ArrayList<>();
            }
        } catch (Exception e) {
            log.warn("[getDepartments] Failed new endpoint, Exception: {}, new url endpoint was: {}", e.getMessage(), dictionaryNewUrl);
            var responseOld = restTemplate.exchange(
                    dictionaryOldUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<DepartmentDTO>>() {
                    });
            if (responseOld.getStatusCode().is2xxSuccessful()) {
                log.warn("[getDepartments] Success old endpoint");

                var mapper = Mappers.getMapper(DepartmentDTOMapper.class);
                return responseOld.getBody() != null
                        ? responseOld.getBody().stream()
                        .map(mapper::toDomain)
                        .collect(Collectors.toList())
                        : new ArrayList<>();
            }
        }
        throw new RuntimeException("Не известная ошибка");
    }

        /* @Override
    public List<Department> getDepartments() {
        log.warn("[getDepartments] Start method");
        try {
            var responseOld = restTemplate.exchange(
                    payrollDictionaryUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<DepartmentDTO>>() {
                    });
            if (responseOld.getStatusCode().is2xxSuccessful()) {
                log.warn("[getDepartments] Success old endpoint");

                var mapper = Mappers.getMapper(DepartmentDTOMapper.class);
                return responseOld.getBody() != null
                        ? responseOld.getBody().stream()
                        .map(mapper::toDomain)
                        .collect(Collectors.toList())
                        : new ArrayList<>();
            }
        } catch (Exception e) {
            log.warn("[getDepartments] Start new endpoint");

            var responseNew = restTemplate.exchange(
                    payrollDictionaryNfoActiveUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<NFODepartmentDTO>>() {
                    });
            if (responseNew.getStatusCode().is2xxSuccessful()) {
                log.warn("[getDepartments] Success new endpoint");

                var nfoDepartmentMapper = Mappers.getMapper(NFODepartmentMapper.class);
                return responseNew.getBody() != null ? responseNew.getBody().stream()
                        .map(nfoDepartmentMapper::toDomain)
                        .collect(Collectors.toList())
                        : new ArrayList<>();
            }
        }
        throw new StatusRuntimeException(Status.UNKNOWN);
    }*/


    @SuppressWarnings("unchecked")
    @Override
    public List<SalaryRegistryDocumentListItemEntity> getSalaryRegistryDocumentList(SalaryRegistriesRequest request) {
        return entityManager
                .createNativeQuery(getTestStringQuerry(request),
                        SalaryRegistryDocumentListItemEntity.class)
                .setFirstResult((int) request.getPageable().getOffset())
                .setMaxResults((int) request.getPageable().getItemsLimit())
                .getResultList();
        //return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistryListItem> getRegistryDocumentList(SalaryRegistriesRequest request) {
        /*List<RegistryListItem> documentListItem = dslContext.select(
                        documentSerialField.as("docSerial"),
                        registryNumberField.as("registryNumber"),
                        field(
                                select(jsonArrayAgg(jsonObject(
                                        key("externalId").value(externalIdOfFileRegistryField),
                                        key("name").value(objectNameOfFileReportField)
                                )))
                                        .from(table("ibc.documents").as("D"))
                                        .leftJoin(table("ibc.documents").as("D2")).on(field("d.doc_serial").eq(field("d2.parent")).and(field("d2.doc_type").eq(SALARY_REGISTRY_DOC_TYPE2)))
                                        .leftJoin(table("ibc.message_info").as("MI2")).on(field("d2.doc_serial").eq(field("mi2.doc")))
                                        .where(field("d.doc_type").eq(55), field("d.cli").in(25196265, 142583931))
                        ).as("files")
                )
                .from(table("ibc.documents").as("D"))
                .where(field("d.doc_type").eq(55), field("d.cli").in(25196265, 142583931))
                .fetchInto(RegistryListItem.class);*/

       /* List<RegistryListItem> documentListItem = dslContext.select(
                        documentSerialField.as("docSerial"),
                        registryNumberField.as("registryNumber"),
                        multiset(
                                select(
                                        externalIdOfFileRegistryField.as("EXT_ID"),
                                        objectNameOfFileReportField.as("OBJ_NAME")
                                )
                                        .from(table("ibc.documents").as("D"))
                                        .leftJoin(table("ibc.documents").as("D2")).on(field("d.doc_serial").eq(field("d2.parent")).and(field("d2.doc_type").eq(SALARY_REGISTRY_DOC_TYPE2)))
                                        .leftJoin(table("ibc.message_info").as("MI2")).on(field("d2.doc_serial").eq(field("mi2.doc")))
                                        .where(field("d.doc_type").eq(55), field("d.cli").in(25196265, 142583931))

                        ).as("files").convertFrom(r -> r.map(Records.mapping(RegistryFile::new)))
                )
                .from(table("ibc.documents").as("D"))
                .where(field("d.doc_type").eq(55), field("d.cli").in(25196265, 142583931))
                .fetchInto(RegistryListItem.class);*/

        //return documentListItem;
        return null;
    }

    @Override
    public TestExcResponse getTestException(TestExcRequest request) {
        if (request.getText().equals("test")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ACTION_NOT_ALLOWED);
        }
        return TestExcResponse.builder()
                .code(200)
                .description("Для теста передавай значение: test")
                .build();
    }

    @Override
    public String getTestStringQuerry(SalaryRegistriesRequest request) {
        return buildSalaryRegistryQuery(request.getFilter(), request.getOrder());
    }

    /**
     * Метод сформирурует запрос для получения списка зарплатных реестров указанной организации,
     * с указанными фильтрами и сортировкой
     */
    @SuppressWarnings("unchecked")
    private String buildSalaryRegistryQuery(RegistryArchiveFilter filter, Sort sort) {
        log.debug("Start buildSalaryRegistryQuery in SalaryRegistryDocumentListItemCustomRepositoryImpl");

        var queryString = buildSelectSection()
                .from(buildSalaryRegistryTable(filter))
                .orderBy(buildOrderSection(sort))
                .getSQL(ParamType.INLINED);

        log.debug("Query string: {}", queryString);
        return queryString;
    }

    /**
     * Метод сформирует SELECT часть запроса получения списка зарплатных реестров
     *
     * @implNote Запрос сформируется для полей таблицы, возвращенной методом buildCurrencyPaymentTable
     */
    private SelectSelectStep<?> buildSelectSection() {
        return dslContext.select(
                field("doc_serial"),
                field("registry_number"),
                field("client_id"),
                field("registry_date"),
                field("status_code"),
                field("status_name"),
                field("client_name"),
                field("text"),
                field("obj_name"),
                field("obj_type")
        );
    }


    /**
     * Метод сформирует и заполнит таблицу значениями, необходимыми для формирования списка зарплатных реестров
     * Таблица будет сформирована для указанного идентификатора организации
     *
     * @param filter Пользовательская фильтрация
     * @return Таблица с записями, найденными для указанной организации
     */
    // Особенности конструкции as - в качестве параметра принимается ТОЛЬКО upper case строка. Иначе не поймет
    private Table<?> buildSalaryRegistryTable(RegistryArchiveFilter filter) {
        var query = dslContext
                .select(
                        documentSerialField.as("DOC_SERIAL"),
                        registryNumberField.as("REGISTRY_NUMBER"),
                        clientIdsField.as("CLIENT_ID"),
                        documentDateField.as("REGISTRY_DATE"),
                        documentStatusField.as("STATUS_CODE"),
                        documentStatusNameField.as("STATUS_NAME"),
                        clientNameField.as("CLIENT_NAME"),
                        documentTextField.as("TEXT"),
                        objectNameOfFileRegistryField.as("OBJ_NAME"),
                        objectTypeOfFileRegistryField.as("OBJ_TYPE"))
                .from(table("ibc.documents").as("D"))
                .leftJoin(table("ibc.message_info").as("MI")).on(field("d.doc_serial").eq(field("mi.doc")))
                .leftJoin(table("ibc.cmd_states").as("CS")).on(field("d.status").eq(field("cs.cs_serial")))
                .leftJoin(table("ibc.clients").as("C")).on(field("d.cli").eq(field("c.cli_serial")))
                .where(buildFilterSection(filter, false));

        var queryArchive = dslContext
                .select(
                        documentSerialField.as("DOC_SERIAL"),
                        registryNumberField.as("REGISTRY_NUMBER"),
                        clientIdsField.as("CLIENT_ID"),
                        documentDateField.as("REGISTRY_DATE"),
                        documentStatusField.as("STATUS_CODE"),
                        documentStatusNameField.as("STATUS_NAME"),
                        clientNameField.as("CLIENT_NAME"),
                        documentTextField.as("TEXT"),
                        objectNameOfFileRegistryField.as("OBJ_NAME"),
                        objectTypeOfFileRegistryField.as("OBJ_TYPE"))
                .from(table("ibc.a_documents").as("D"))
                .leftJoin(table("ibc.a_message_info").as("MI")).on(field("d.doc_serial").eq(field("mi.doc")))
                .leftJoin(table("ibc.cmd_states").as("CS")).on(field("d.status").eq(field("cs.cs_serial")))
                .leftJoin(table("ibc.clients").as("C")).on(field("d.cli").eq(field("c.cli_serial")))
                .where(buildFilterSection(filter, true));

        return query.unionAll(queryArchive).asTable("registry");
        //return query.asTable("registry");
    }

    /**
     * Метод сформирует WHERE часть запроса получения списка зарплатных реестров согласно переданным фильтрам
     *
     * @implNote Фильтр сформируется для полей таблицы, возвращенной методом buildCurrencyPaymentTable
     */
    private Condition buildFilterSection(RegistryArchiveFilter filter, boolean forArchiveTable) {

        List<Condition> conditions = new ArrayList<>();
        conditions.add(field("d.doc_type").eq(SALARY_REGISTRY_DOC_TYPE));
        conditions.add(field("d.state").notEqual(REGISTRY_DELETE_STATUS));

        // <--- Если не задан пользовательский фильтр по дате, ограничиваем выборку в архивной таблице --->
        if (filter.getRegistryDateFrom() == null && filter.getRegistryDateTo() == null && forArchiveTable) {
            conditions.add(documentTableArchiveDateField.greaterOrEqual(
                    Timestamp.valueOf(LocalDate.now()
                            .minusMonths(ARCHIVE_MONTH_IN_THE_PAST)
                            .atStartOfDay())));
        }

        // <--- Фильтрация по дате --->
        if (filter.getRegistryDateFrom() != null || filter.getRegistryDateTo() != null) {
            if (filter.getRegistryDateTo().isEqual(filter.getRegistryDateFrom())) {
                conditions.add(documentDateField.greaterOrEqual(filter.getRegistryDateFrom()));
                conditions.add(documentDateField.lessOrEqual(filter.getRegistryDateTo().plusDays(1L)));
            } else {
                if (filter.getRegistryDateFrom() != null) {
                    conditions.add(documentDateField.greaterOrEqual(filter.getRegistryDateFrom()));
                }
                if (filter.getRegistryDateTo() != null) {
                    conditions.add(documentDateField.lessOrEqual(filter.getRegistryDateTo().plusDays(1L)));
                }
            }
        }

        // <--- Фильтрация по id --->
        if (!CollectionUtils.isEmpty(filter.getClientIds())) {
            var icbClientIdsFilter = noCondition();
            icbClientIdsFilter = icbClientIdsFilter.and(
                    clientIdsField
                            .in(filter.getClientIds()));
            conditions.add(icbClientIdsFilter);
        }

        // <--- Фильтрация по RegistryNumber --->
        if (null != filter.getRegistryNumber() && !filter.getRegistryNumber().isBlank()) {
            var registryNumberFilter = noCondition();
            registryNumberFilter = registryNumberFilter.and(
                    registryNumberField
                            .eq(filter.getRegistryNumber()));
            conditions.add(registryNumberFilter);
        }


        return DSL.and(conditions);
    }

    /**
     * Метод вернет настройку сортировки результата запроса получения списка зарплатных реестров
     * в соответсвии с переданным экземпляром Sort
     * Если сортировка не передана, либо не поддерживается сортировка по необходимому столбцу -
     * метод вернет настройку сортировки по дате по убыванию.
     */
    private Collection<OrderField<Object>> buildOrderSection(Sort sort) {
        OrderField<Object> resultSortField = field("registry_date").desc();
        if (sort != null) {
            var field = getOrderFieldName(sort.getFields());
            if (field != null) {
                resultSortField = sort.getSortOrder() == SortOrder.DESC ? field.desc() : field.asc();
            }
        }
        return List.of(resultSortField, field("doc_serial").desc());
    }

    /**
     * Метод вернет название поля, по которому должна проходить сортировка
     *
     * @implNote Название полей указаны по аналогии с методом buildCurrencyPaymentTable
     */
    private Field<Object> getOrderFieldName(RegistrySortFields fields) {
        switch (fields) {
            case REGISTRY_DATE:
                return field("registry_date");
            case REGISTRY_NUMBER:
                return field("registry_number");
            default:
                return null;
        }
    }
}

