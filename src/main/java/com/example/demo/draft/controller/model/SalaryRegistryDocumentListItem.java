package com.example.demo.draft.controller.model;

import com.example.demo.draft.model.RegistryStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


/** Модель содержит информацию об элементе списка зарплатных реестров */
@Data
public class SalaryRegistryDocumentListItem {
    /** Идентификатор документа */
    private Long id;
    /** Список файлов реестра */
    private List<RegistryFileItem> files;
    /** Номер реестра в системе */
    private String registryNumber;
    /** Дата реестра в системе */
    private LocalDate registryDate;
    /** Статус */
    private RegistryStatus status;
    /** Наименование организации */
    private String clientName;
    /** Текст реестра */
    private String registryText;
}