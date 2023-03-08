package com.example.demo.draft.model.modelwithdsl;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class RegistryListItem {

    /** Идентификатор документа */
    private Long docSerial;

    /** Номер реестра в системе */
    private String registryNumber;

    private List<RegistryFile> files;

}
