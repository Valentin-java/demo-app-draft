package com.example.demo.draft.service.zip;

import java.io.File;
import java.io.IOException;

public interface UnZipArchive {

    void unpack(File file, File targetDir) throws IOException;
}
