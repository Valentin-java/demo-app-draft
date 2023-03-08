package com.example.demo.draft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
//import ru.rb.arxiv.domain.entity.RegistryFileItem;
//import ru.rb.arxiv.repository.spring.entity.custom.salaryregistry.RegistryFileEntity;
//import ru.rb.arxiv.repository.spring.mappers.enums.RegistryFileTypeMapper;

@Mapper(//uses = RegistryFileTypeMapper.class,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface RegistryFileItemMapper {

   // RegistryFileItem toDomain(RegistryFileEntity jpa);
}