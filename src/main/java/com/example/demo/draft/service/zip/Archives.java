package com.example.demo.draft.service.zip;

import com.example.demo.draft.model.FileDTO;

import java.io.IOException;

public interface Archives {

    void decompress(FileDTO file) throws IOException;
}
