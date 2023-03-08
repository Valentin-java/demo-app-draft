package com.example.demo.draft.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
//Сокращенная модель для реестра
public class RegistryShort {
    //Идентификатор реестра
    private Long id;
    //Сумма реестра
    private String registrySum;


}
