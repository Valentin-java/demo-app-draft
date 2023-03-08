package com.example.demo.draft.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class RegistryFileEntity {

    /** Идентификационный номер файла реестра */
    @Id
    @Column(name = "EXT_ID")
    private Long externalId;

    /** Номер родительского реестра */
    @Column(name = "PARENT")
    private Long parent;

    /** Имя и тип файла реестра */
    @Column(name = "OBJ_NAME")
    private String objName;

    /** Имя и тип файла реестра */
    @Column(name = "OBJ_TYPE")
    private String objType;
}
