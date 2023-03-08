package com.example.demo.draft.model.modelwithdsl;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;


public class RegistryFile {

    /** Идентификационный номер файла реестра */
    private Long externalId;

    /** Номер родительского реестра */
   // private Long parent;

    /** Имя и тип файла реестра */
    private String objName;

    public RegistryFile(Long externalId, String objName) {
        this.externalId = externalId;
        this.objName = objName;
    }

    public RegistryFile() {
    }

    public RegistryFile(Object o, Object o1) {
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    /** Имя и тип файла реестра */
    /*@Column(name = "OBJ_TYPE")
    private String objType;*/
}
