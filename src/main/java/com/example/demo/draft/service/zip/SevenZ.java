package com.example.demo.draft.service.zip;

import com.example.demo.draft.model.ElibFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class SevenZ {

    public static final Charset CP866 = Charset.forName("CP866");

    public static void main(String[] args) throws IOException {
        var files = decompress();
        System.out.println("Количество файлов: " + files.size());
        for (ElibFile file : files) {
            System.out.println("Decompress file name: " + file.getFileName());
        }

    }

    private static List<ElibFile> decompress() throws IOException {
        List<ElibFile> result = new ArrayList<>();

        SevenZFile zis = new SevenZFile(copyInputStreamToFile());
        SevenZArchiveEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {

            // запишем один файл
            byte[] bytes = new byte[(int) zipEntry.getSize()];
            try {
                byte[] content = new byte[(int) zipEntry.getSize()];
                zis.read(content, 0, content.length);
                bytes = content;
            } catch (Exception e) {
                log.debug("There are troubles with read byte array: {}", e.getMessage());
            }

            var file = ElibFile.builder()
                    .fileName(zipEntry.getName())
                    .base64file(Base64.getEncoder().encodeToString(bytes))
                    .build();
            result.add(file);

            zipEntry = zis.getNextEntry();
        }
        zis.close();
        return result;
    }

    // Создадим временный файл
    public static File copyInputStreamToFile() throws IOException {
        String fileZip = "C:\\rb_projects\\temp\\test1.7z";
        File fileInput = File.createTempFile("tempinput", null);
        fileInput.deleteOnExit();
        FileOutputStream out = new FileOutputStream(fileInput);
        IOUtils.copy(new BufferedInputStream(
                new FileInputStream(fileZip)), out);
        return fileInput;
    }
}
