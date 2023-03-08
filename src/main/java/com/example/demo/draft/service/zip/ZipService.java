package com.example.demo.draft.service.zip;

import com.example.demo.draft.model.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service

@Slf4j
public class ZipService implements Archives {

    private final UnZipArchive unZipService;
    public String tempFolderPath = "C:\\rb_projects\\temp";
    public String testName = "C:\\rb_projects\\temp\\temp\\test.zip";
    private final List<String> archiveFileExtension = List.of("rar", "zip", "7z");

    public ZipService(@Qualifier("withoutcyrillic") UnZipArchive unZipService) {
        this.unZipService = unZipService;
    }


    @Override
    public void decompress(FileDTO file) throws IOException {

        unpackArchive(file);

    }

    public List<FileDTO> unpackArchive(FileDTO fileDTO) throws IOException {
        File tempDir = new File(tempFolderPath + "/temp");
        tempDir.mkdir();
        //FileUtils.cleanDirectory(tempDir);

        File archiveFile = new File(tempDir, fileDTO.getName());
        archiveFile.createNewFile();

//        File targetFile = new File(tempDir, "target.zip");
//        targetFile.createNewFile();

        byte[] decode = Base64.getDecoder().decode(fileDTO.getBase64());
        Files.write(archiveFile.toPath(), decode);

        // Сохраняем вр файл, оттуда вычитываем и конвертим, затем сохраняем еще один файл
//
//        transform2(archiveFile, "UTF-8", targetFile, "UTF-8");
//
//        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
//                new FileInputStream(testName), 4096), Charset.forName("CP866"));
//
//        var newData = zis.readAllBytes();
//
//        Files.write(targetFile.toPath(), newData);

        var archiveName = archiveFile.getName().endsWith(".zip")
                ? archiveFile.getName().split(".zip")[0]
                : archiveFile.getName();
        System.out.println(archiveName);

        String testName = null;

        var fileName = testName != null && archiveFileExtension.stream().anyMatch(testName::endsWith)
                ? testName
                : archiveName;

        unZipService.unpack(archiveFile, tempDir);

        List<FileDTO> result = Files.walk(tempDir.toPath())
                .filter(f -> !Files.isDirectory(f))
                .map(path -> {
                    FileDTO resultFile = new FileDTO();
                    resultFile.setName(path.getFileName().toString());
                    resultFile.setArchiveName(fileName);
                    byte[] bytes;
                    try {
                        bytes = Files.readAllBytes(path);
                    } catch (IOException e) {
                        log.error(String.valueOf(e));
                        throw new RuntimeException("Ошибка чтения файла");
                    }
                    resultFile.setBase64(Base64.getEncoder().encodeToString(bytes));
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        log.error(String.valueOf(e));
                        throw new RuntimeException("Ошибка удаления файла");
                    }
                    return resultFile;
                })
                .collect(Collectors.toList());

        FileUtils.cleanDirectory(tempDir);

        return result;
    }

    public static void transform2(File source, String srcEncoding, File target, String tgtEncoding) throws IOException {
        try {
            // Create channel on the source
            FileChannel srcChannel = new FileInputStream(source).getChannel();

            // Create channel on the destination
            FileChannel dstChannel = new FileOutputStream(target).getChannel();

            // Copy file contents from source to destination
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

            // Close the channels
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {
        }
    }


}
