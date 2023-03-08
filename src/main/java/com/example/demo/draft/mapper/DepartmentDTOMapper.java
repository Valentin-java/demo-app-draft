package com.example.demo.draft.mapper;

import com.example.demo.draft.model.Department;
import com.example.demo.draft.model.DepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DepartmentDTOMapper {

    Department toDomain(DepartmentDTO rest);
}
