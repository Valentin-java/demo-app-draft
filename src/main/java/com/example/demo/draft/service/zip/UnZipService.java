package com.example.demo.draft.service.zip;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AbstractFileHeader;
import net.lingala.zip4j.model.FileHeader;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Qualifier("withoutcyrillic")
public class UnZipService implements UnZipArchive {

    private static final String PASSWORD_KEY = "123";
    private static final List<String> passwords = List.of("654", "321", "123");

    @Override
    public void unpack(File file, File targetDir) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        List<FileHeader> headers = zipFile.getFileHeaders();
        List<Boolean> charsetFilesName = headers.stream().map(AbstractFileHeader::isFileNameUTF8Encoded).toList();
        int count = 0;
        for (var charset : charsetFilesName) {
            zipFile = new ZipFile(file);
            zipFile.setCharset(Charset.forName(charset ? "UTF-8" : "CP866"));
            headers = zipFile.getFileHeaders();
            if (zipFile.isEncrypted()) {
                int badPass = 0;
                for (String password : passwords) {
                    try {
                        zipFile.setPassword(password.toCharArray());
                        extractFileZip(zipFile, headers.get(count), targetDir);
                    } catch (ZipException e) {
                        log.warn("[unpack] неправильный пароль");
                        badPass++;
                    }
                }
                if (passwords.size() == badPass) {
                    log.error("[unpack] Не найден пароль для файла {}", file.getName());
                    FileUtils.cleanDirectory(targetDir);
                    throw new ZipException("Не найден пароль для файла: " + file.getName());
                }
            } else {
                extractFileZip(zipFile, headers.get(count), targetDir);
            }
            count++;
        }
        Files.delete(file.toPath());
    }

    private void extractFileZip(ZipFile zipFile, FileHeader header, File targetDir) throws ZipException {
        if (header.isDirectory()) {
            File f = new File(targetDir.getPath() + "/" + header.getFileName());
            f.mkdirs();
        } else {
            zipFile.extractFile(header, targetDir.getPath());
        }
    }

    private void extractZipFile(File file, File targetDir) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        List<FileHeader> headers = zipFile.getFileHeaders();
        List<Boolean> charsetFilesName = headers.stream().map(AbstractFileHeader::isFileNameUTF8Encoded).toList();
        int count = 0;
        for (var charset : charsetFilesName) {
            zipFile = new ZipFile(file);
            zipFile.setCharset(Charset.forName(charset ? "UTF-8" : "CP866"));
            headers = zipFile.getFileHeaders();
            if (zipFile.isEncrypted()) {
                int badPass = 0;
                for (String password : passwords) {
                    try {
                        zipFile.setPassword(password.toCharArray());
                        var header = headers.get(count);
                        if (header.isDirectory()) {
                            File f = new File(targetDir.getPath() + "/" + header.getFileName());
                            f.mkdirs();
                        } else {
                            zipFile.extractFile(header, targetDir.getPath());
                        }
                    } catch (ZipException e) {
                        log.warn("[unpack] неправильный пароль");
                        badPass++;
                    }
                }
                if (passwords.size() == badPass) {
                    log.error("[unpack] Не найден пароль для файла {}", file.getName());
                    throw new ZipException("Не найден пароль для файла: " + file.getName());
                }
            } else {
                var header = headers.get(count);
                if (header.isDirectory()) {
                    File f = new File(targetDir.getPath() + "/" + header.getFileName());
                    f.mkdirs();
                } else {
                    zipFile.extractFile(header, targetDir.getPath());
                }
            }
            count++;
        }
        Files.delete(file.toPath());
    }
}
