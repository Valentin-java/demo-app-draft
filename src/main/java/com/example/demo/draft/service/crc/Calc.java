package com.example.demo.draft.service.crc;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

public class Calc {
    public static String tempFolderPath = "C:\\rb_projects\\temp";
    private static final String    HEXES    = "0123456789ABCDEF";

    public static void main(String[] args) throws IOException {
        File file = new File(tempFolderPath + "/testcrc.txt");
        System.out.println(file.toPath());
        System.out.println("Контрольная сумма crc32 в Hex: " + getCrc32(String.valueOf(file.toPath()), new CRC32()));
        System.out.println("Контрольная сумма crc32 в Long: " + getCrc32Long(String.valueOf(file.toPath()), new CRC32()));

        try (var fis = new FileInputStream(String.valueOf(file.toPath()));
             var bis = new BufferedInputStream(fis)) {
            var bytesStr = bis.readAllBytes();
            CRC24 crc24 = new CRC24();
            for (int i = 0; i < bytesStr.length; i++) {
                crc24.update(bytesStr[i]);
            }
            System.out.println("Контрольная сумма crc24 в Hex: " + Long.toHexString(crc24.getValue()));
            System.out.println("Контрольная сумма crc24 в Long: " + crc24.getValue());
            System.out.println("----------------------");

            // V0PD - 5743c383
            // 4g6i - c3a20ec2a2
            String sHex = "5743c383";
            System.out.println("Start hex string: " + sHex);

            var enc = Base64.getEncoder().encodeToString(sHex.getBytes());
            System.out.println("binary in B64: " + enc);

            // Base64 decode
            var bytesB64 = Base64.getDecoder().decode(enc);
            var result = new String(bytesB64);
            System.out.println("B64 to hex: " + result);

            BigInteger bi = new BigInteger(result, 16);
            System.out.println("Hex to Long: " + bi);

            var test = 10;
            var mult = 1;
            var res = 10 << 1;

            System.out.println("Test: " + res);
        }
    }




    private static String getCrc32(String filename, Checksum checksum) throws IOException {
        try (
                var fis = new FileInputStream(filename);
                var bis = new BufferedInputStream(fis);
                var cis = new CheckedInputStream(bis, checksum);
        ) {
            while (cis.read() >= 0) ;
            return Long.toHexString(cis.getChecksum().getValue());
        }
    }

    private static Long getCrc32Long(String filename, Checksum checksum) throws IOException {
        try (
                var fis = new FileInputStream(filename);
                var bis = new BufferedInputStream(fis);
                var cis = new CheckedInputStream(bis, checksum);
        ) {
            while (cis.read() >= 0) ;
            return cis.getChecksum().getValue();
        }
    }
}
