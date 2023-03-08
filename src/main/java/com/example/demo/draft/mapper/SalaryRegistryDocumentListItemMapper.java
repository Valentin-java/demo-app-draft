package com.example.demo.draft.mapper;

import com.example.demo.draft.controller.model.RegistryFileItem;
import com.example.demo.draft.controller.model.RegistryFileType;
import com.example.demo.draft.controller.model.SalaryRegistryDocumentListItem;
import com.example.demo.draft.entity.SalaryRegistryDocumentListItemEntity;
import com.example.demo.draft.model.RegistryStatus;
import com.example.demo.draft.model.enums.DocumentStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SalaryRegistryDocumentListItemMapper {

    SalaryRegistryDocumentListItemMapper INSTANCE = Mappers.getMapper(SalaryRegistryDocumentListItemMapper.class);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "id", source = "docSerial")
    SalaryRegistryDocumentListItem toDomain(SalaryRegistryDocumentListItemEntity jpa);

    @AfterMapping
    default void statusMapping(SalaryRegistryDocumentListItemEntity jpa, @MappingTarget SalaryRegistryDocumentListItem target) {
        if (jpa != null && jpa.getStatusCode() != null) {
            target.setStatus(RegistryStatus.builder()
                    //.code(DocumentStatus.fromValue(jpa.getStatusCode()))
                    .name(Optional.ofNullable(jpa.getStatusName()).orElse("Value from entity archive was null"))
                    .build());
        }
    }

    @AfterMapping
    default void addRegistryItemMapping(SalaryRegistryDocumentListItemEntity jpa, @MappingTarget SalaryRegistryDocumentListItem target) {
        if (jpa != null
                && jpa.getObjType() != null
                && jpa.getObjName() != null) {
            var registryItem = RegistryFileItem.builder()
                    .externalId(jpa.getDocSerial())
                    .name(jpa.getObjName() + "." + jpa.getObjType())
                    .type(RegistryFileType.REGISTRY)
                    .build();
            List<RegistryFileItem> files = new ArrayList<>();
            files.add(registryItem);
            target.setFiles(files);
        } else {
            target.setFiles(new ArrayList<>());
        }
    }
}