package com.example.demo.draft.service.zip;

import com.example.demo.draft.model.ElibFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Component
@Qualifier("cyrillic")
public class UnZipCyrillicService implements UnZipArchive {

    @Override
    public void unpack(File file, File targetDir) throws IOException {

        String fileZip = file.getAbsolutePath();

        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
                new FileInputStream(fileZip), 4096), Charset.forName("CP866"));

        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {

            File newFile = newFile(targetDir, zipEntry);

            // write file content
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] bytes = new byte[(int) zipEntry.getSize()];
            try {
                fos.write(zis.readAllBytes());
            } catch (Exception e) {
                log.debug("There are troubles with read byte array: {}", e.getMessage());
            }

            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
