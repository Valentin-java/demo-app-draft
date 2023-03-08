package com.example.demo.draft.service.zip;

import com.example.demo.draft.model.ElibFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.exception.TikaException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class ZipArrayTest {

    public static final Charset CP866 = Charset.forName("CP866");

    public static void main(String[] args) throws IOException, TikaException {
        var files = decompress();
        System.out.println("Количество файлов: " + files.size());
        for (ElibFile file : files) {
            System.out.println("Decompress file name: " + file.getFileName() + " base64: " + file.getBase64file());
        }
//        File dir = new File("C:\\rb_projects\\temp"); //path указывает на директорию
//        List<File> lst = new ArrayList<>();
//        for ( File file : dir.listFiles() ){
//            if ( file.isFile() ) {
//                lst.add(file);
//                System.out.println("Файл с именем: " + new String(file.getName().getBytes("UTF-8"), "UTF-8"));
//            }
//        }

//        String test1 = "Тест61 Тестович61";
//        String test14 = new String(test1.getBytes(StandardCharsets.UTF_8));
////        String test3 = new String(test1.getBytes("UTF-8"), "windows-1251");
//        String test3 = new String(test1.getBytes(Charset.forName("Cp1251")));
//        System.out.println(test3);
//
//        // загоним сначала в поток
//        InputStream is = new ByteArrayInputStream(test3.getBytes(StandardCharsets.UTF_8));
//
//        // потом из потока в строку через кодировку
//        String result = new String(is.readAllBytes(), StandardCharsets.UTF_8);
//        System.out.println(result);

    }

    private static List<ElibFile> decompress() throws IOException {
        List<ElibFile> result = new ArrayList<>();
        String fileZip = "C:\\rb_projects\\temp\\test.zip";

        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
                new FileInputStream(fileZip), 4096), Charset.forName("CP866"));

        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {

            // write file content
            byte[] bytes = new byte[(int) zipEntry.getSize()];
            try {
                bytes = zis.readAllBytes();
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
        zis.closeEntry();
        zis.close();

        return result;

    }

}



