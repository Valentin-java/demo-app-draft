package com.example.demo.draft.service;

import com.example.demo.draft.controller.model.RegistryFileItem;
import com.example.demo.draft.controller.model.RegistryFileType;
import com.example.demo.draft.controller.model.SalaryRegistryDocumentListItem;
import com.example.demo.draft.entity.RegistryFileEntity;
import com.example.demo.draft.mapper.SalaryRegistryDocumentListItemMapper;
import com.example.demo.draft.model.PaymentRegistryDTO;
import com.example.demo.draft.model.Registry;
import com.example.demo.draft.model.RegistryFile;
import com.example.demo.draft.model.SalaryRegistriesRequest;
import com.example.demo.draft.model.enums.FileType;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.draft.entity.RegistryFileType.REGISTRY;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    private final QueryTestService service;
    private final SqlQueryForReportFiles filesReportService;
    private final SalaryRegistryDocumentListItemMapper itemMapper = Mappers.getMapper(SalaryRegistryDocumentListItemMapper.class);



    public TestServiceImpl(
            QueryTestService service,
            SqlQueryForReportFiles filesReportService) {
        this.service = service;
        this.filesReportService = filesReportService;
    }

    @Override
    public void updateFiles(PaymentRegistryDTO paymentRegistry) {

        Registry registry = new Registry();
        var registryFile1 = RegistryFile.builder()
                .fileName("registryFileOld")
                .fileType(FileType.REGISTRY)
                .build();
        var registryFile2 = RegistryFile.builder()
                .fileName("reportFileOld")
                .fileType(FileType.REPORT)
                .build();
        registry.setFiles(Set.of(registryFile1, registryFile2));


        paymentRegistry.getFiles()
                .stream()
                .filter(fileDTO -> fileDTO.getType().equals(REGISTRY.name()))
                .findFirst()
                .ifPresentOrElse(fileDTO -> {
                    registry.getFiles()
                            .stream()
                            .filter(file -> file.getFileType().equals(FileType.REGISTRY))
                            .findFirst()
                            .ifPresentOrElse(file -> {
                                log.debug("Обновим файл");
                            }, () -> log.debug("Перезапишем"));
                    // Если в paymentRegistry files нет элемента с type='REGISTRY',
                    // то удаляем записи из registry.files с registry_id=id и file_type=registry
                }, () -> {
                    registry.setFiles(registry.getFiles()
                                    .stream()
                                    .filter(file -> !file.getFileType().equals(FileType.REGISTRY))
                                    .collect(Collectors.toSet()));
                });
        if (registry.getFiles() != null) {
            log.debug("Файл из реестра НЕ удален");
        } else {
            log.debug("Файл из реестра удален");
        }
    }

    /*var registryFiles = registry.getFiles();
    Iterator<RegistryFile> iter = registryFiles.iterator();
                            while (iter.hasNext()) {
        var element = iter.next();
        if (element.getFileType().equals(FileType.REGISTRY)) {
            iter.remove();
        }
    }*/

    @Override
    public List<SalaryRegistryDocumentListItem> getListByMapper(SalaryRegistriesRequest request) {
        // Временное хранилище списка файлов отчета
        Map<Long, List<RegistryFileEntity>> filesStorage = new HashMap<>();

        var response = service.getSalaryRegistryDocumentList(request);

        var registries = response.stream()
                .map(itemMapper::toDomain)
                .collect(Collectors.toList());

        // собрать все id doc_serial
        List<Long> docSerials = registries.stream()
                .map(SalaryRegistryDocumentListItem::getId)
                .collect(Collectors.toList());

        if (!docSerials.isEmpty()) {
            var filesReports = filesReportService.getRegistryFileList(docSerials);

            // Разобрать все файлы в мапу
            filesReports.stream()
                    .filter(Objects::nonNull)
                    .forEach(file -> filesStorage.computeIfAbsent(file.getParent(), e -> new ArrayList<>()).add(file));
        }

        return addingReportFiles(registries, filesStorage);
    }

    private List<SalaryRegistryDocumentListItem> addingReportFiles(
            List<SalaryRegistryDocumentListItem> registries,
            Map<Long, List<RegistryFileEntity>> filesStorage) {

        // Разбираем файлы из мапы
        for (SalaryRegistryDocumentListItem item : registries) {
            // Если что-то пришло из БД для такого ID реестра, то добавим в список files
            if (filesStorage.containsKey(item.getId())) {
                // Проверим, если список уже есть, добавим, если нет - создадим
                if (item.getFiles() != null) {
                    var old = new ArrayList<>(item.getFiles());
                    old.addAll(buildFilesReport(filesStorage.get(item.getId())));
                    item.setFiles(old);
                } else {
                    item.setFiles(new ArrayList<>(buildFilesReport(filesStorage.get(item.getId()))));
                }
            }
        }
        return registries;
    }

    /**
     *
     * Собираем из списка entity в список RegistryFileItem
     */
    private List<RegistryFileItem> buildFilesReport(List<RegistryFileEntity> request) {
        List<RegistryFileItem> files = new ArrayList<>();

        for (RegistryFileEntity file : request) {
            if (file.getObjType() != null && file.getObjName() != null) {
            var registryItem = RegistryFileItem.builder()
                    .externalId(file.getExternalId())
                    .name(file.getObjName() + "." + file.getObjType())
                    .type(RegistryFileType.REPORT)
                    .build();

            files.add(registryItem);
            }
        }
        return files;
    }
}
