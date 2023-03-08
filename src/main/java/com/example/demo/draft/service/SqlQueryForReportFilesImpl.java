package com.example.demo.draft.service;

import com.example.demo.draft.entity.RegistryFileEntity;
import com.example.demo.draft.model.RegistryArchiveFilter;
import com.example.demo.draft.model.enums.DocumentType;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.table;

@Slf4j
@Component
public class SqlQueryForReportFilesImpl implements SqlQueryForReportFiles {

    private final int SALARY_REGISTRY_DOC_TYPE2 = DocumentType.BANK_MESSAGE_WITH_ATTACHMENT.getValue();
    private final DSLContext dslContext = DSL.using(SQLDialect.DEFAULT);

    private final Field<Object> documentSerialField = field("d.doc_serial");
    private final Field<Object> externalIdOfFileRegistryField = field("d2.doc_serial");
    private final Field<Object> objectNameOfFileReportField = field("mi2.obj_name");
    private final Field<Object> objectTypeOfFileReportField = field("mi2.obj_type");

    private final EntityManager entityManager;

    public SqlQueryForReportFilesImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistryFileEntity> getRegistryFileList(List<Long> request) {
        return entityManager
                .createNativeQuery(buildRegistryFileListQuery(request),
                        RegistryFileEntity.class)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public String buildRegistryFileListQuery(List<Long> request) { // переделай на private

        var queryString = buildSelectSection()
                .from(buildRegistryFileListTable(request))
                .getSQL(ParamType.INLINED);

        log.debug("Query string: {}", queryString);
        return queryString;
    }

    private SelectSelectStep<?> buildSelectSection() {
        return dslContext.select(
                field("ext_id"),
                field("parent"),
                field("obj_name"),
                field("obj_type")
        );
    }

    private Table<?> buildRegistryFileListTable(List<Long> request) {
        var query = dslContext
                .select(
                        externalIdOfFileRegistryField.as("EXT_ID"),
                        documentSerialField.as("PARENT"),
                        objectNameOfFileReportField.as("OBJ_NAME"),
                        objectTypeOfFileReportField.as("OBJ_TYPE"))
                .from(table("ibc.documents").as("D"))
                .leftJoin(table("ibc.documents").as("D2"))
                .on(field("d.doc_serial").eq(field("d2.parent"))
                        .and(field("d2.doc_type").eq(SALARY_REGISTRY_DOC_TYPE2)))
                .leftJoin(table("ibc.message_info").as("MI2"))
                .on(field("d2.doc_serial").eq(field("mi2.doc")))
                .where(buildFilterSection(request));

        var queryArchive = dslContext
                .select(
                        externalIdOfFileRegistryField.as("EXT_ID"),
                        documentSerialField.as("PARENT"),
                        objectNameOfFileReportField.as("OBJ_NAME"),
                        objectTypeOfFileReportField.as("OBJ_TYPE"))
                .from(table("ibc.a_documents").as("D"))
                .leftJoin(table("ibc.a_documents").as("D2"))
                .on(field("d.doc_serial").eq(field("d2.parent"))
                        .and(field("d2.doc_type").eq(SALARY_REGISTRY_DOC_TYPE2)))
                .leftJoin(table("ibc.a_message_info").as("MI2"))
                .on(field("d2.doc_serial").eq(field("mi2.doc")))
                .where(buildFilterSection(request));


        return query.unionAll(queryArchive).asTable("registryfilelist");
    }

    private Condition buildFilterSection(List<Long> filter) {

        List<Condition> conditions = new ArrayList<>();
        // <--- Фильтрация по id --->
        if (!CollectionUtils.isEmpty(filter)) {
            var icbClientIdsFilter = noCondition();

            int partitionSize = filter.size() / 100;
            if (partitionSize > 1) {
                var partitionedList = IntStream.range(0, filter.size())
                        .boxed()
                        .collect(Collectors.groupingBy(partition -> (partition / partitionSize),
                                Collectors.mapping(filter::get, Collectors.toList())))
                        .values();
                for (List<Long> partList : partitionedList) {
                    icbClientIdsFilter = icbClientIdsFilter.or(
                            documentSerialField.in(partList));
                    conditions.add(icbClientIdsFilter);
                }
            } else {
                icbClientIdsFilter = icbClientIdsFilter.and(
                        documentSerialField.in(filter));
                conditions.add(icbClientIdsFilter);
            }
        }
        return DSL.and(conditions);
    }
}
