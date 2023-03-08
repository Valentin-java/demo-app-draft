package com.example.demo.draft.mapper;

import com.example.demo.draft.model.Department;
import com.example.demo.draft.model.NFODepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface NFODepartmentMapper {

    @Mapping(target = "code", source = "id")
    @Mapping(target = "address", source = "departmentAddress")
    @Mapping(target = "name", source = "aliasName")
    Department toDomain(NFODepartmentDTO rest);
}