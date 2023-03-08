package com.example.demo.draft.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class SalaryRegistryDocumentListItemEntity implements Serializable  {

    /** Идентификатор документа */
    @Id
    @Column(name = "DOC_SERIAL")
    private Long docSerial;

    /** Имя файла реестра */
    @Column(name = "OBJ_NAME")
    private String objName;

    /** Тип файла реестра */
    @Column(name = "OBJ_TYPE")
    private String objType;

    /** Номер реестра в системе */
    @Column(name = "REGISTRY_NUMBER")
    private String registryNumber;

    /** Дата реестра в системе */
    @Column(name = "REGISTRY_DATE")
    private LocalDate registryDate;

    /** Код статуса */
    @Column(name = "STATUS_CODE")
    private Integer statusCode;

    /** Название статуса */
    @Column(name = "STATUS_NAME")
    private String statusName;

    /** Название статуса */
    @Column(name = "CLIENT_NAME")
    private String clientName;

    /** Текст реестра */
    @Column(name = "TEXT")
    private String registryText;

    /** Идентификационный номер файла реестра */
    //@Column(name = "EXT_ID")
    //private Long externalId;

    /** Номер родительского реестра */
   // @Column(name = "PARENT")
   // private Long parent;

    /** Имя и тип файла реестра */
   // @Column(name = "OBJ_REPORT")
    //private String objName;
}