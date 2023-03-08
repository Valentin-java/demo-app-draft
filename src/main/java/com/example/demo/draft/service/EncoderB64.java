package com.example.demo.draft.service;

import org.jooq.impl.QOM;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncoderB64 {

    private static final String UTF = "UTF-8";
    private static final String CP = "CP866";
    private static final String WIN = "Windows-1251";
    private static final String EXTRACT_PATH = "C:\\rb_projects\\charsets4correction\\result";
    private static final String SOURCE_PATH = "C:\\rb_projects\\charsets4correction\\encode";

    public static void main(String[] args) throws IOException {

//        // Пройдемся по директории с закодированными файлами
//        List<FileDTO> result = Files.walk(new File(SOURCE_PATH).toPath())
//                .filter(f -> !Files.isDirectory(f))
//                .map(file -> {
//                    FileDTO resultFile = new FileDTO();
//                    var typeFile = file.getFileName().toString().split("\\.");
//                    resultFile.setType(typeFile.length > 0 ? typeFile[typeFile.length-1] : null);
//                    resultFile.setName(file.getFileName().toString().replaceAll(".txt", ""));
//                    byte[] bytes;
//                    try {
//                        bytes = Files.readAllBytes(file);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Ошибка чтения файла");
//                    }
//                    resultFile.setBase64(Base64.getEncoder().encodeToString(bytes));
//                    return resultFile;
//                })
//                .collect(Collectors.toList());
//
//        // Создадим директорию куда будем записывать результат
//        File tempDir = new File(EXTRACT_PATH);
//        if (!tempDir.exists()) tempDir.mkdir();
//
//        // Пройдемся по списку файлов и декодируем
//        for (FileDTO f : result) {
//            // Создадим файл
//            File archiveFile = new File(EXTRACT_PATH, f.getName() + "." + f.getType());
//            archiveFile.createNewFile();
//
//            // Декодируем содержимое в него
//
//            /**
//             * НЕ РАБОТАЕТ КОДИРОВКА
//             */
//            try (PrintWriter out = new PrintWriter(archiveFile.getAbsoluteFile())) {
//                String t = f.getBase64();
//                byte[] data = Base64.getDecoder().decode(t);
//                String result1 = new String(data, StandardCharsets.UTF_8);
//                System.out.println(result1);
////                var contentFileBytes = Base64.getDecoder().decode(f.getBase64());
////                String contentFile = new String(contentFileBytes, StandardCharsets.UTF_8);
////                System.out.println(contentFile);
////                out.print(contentFile);
//            }
//
//        }
        List<String> charsetsForTest = List.of("ASCII", "UTF-8", "Windows-1251");

        String inputUTF8 = "W0FjY291bnRdIDQwNzAyODEwNTQ1NTEwMDAwMDE1CltDb250cmFjdF0gNDU1MTExMTkKW1JlZ2lzdHJ5TnVtYmVyXSA0CltSZWdpc3RyeURhdGVdIDEzMDEyMDIzCltSZWdpc3RyeVN1bV0gNTA3NjkuMjgKW1BheW1lbnROdW1iZXJdIDAwMDAwNApbUGF5bWVudERhdGVdIDEzMDEyMDIzCltQYXltZW50UHVycG9zZV0g0J7RgtC/0YPRgdC60L3Ri9C1INC30LAg0Y/QvdCy0LDRgNGMIDIwMjMg0LMKW1BheW1lbnRPcmRlcl0gMwpbVk9Db2RlXSAKW0luY29tZUNvZGVdIDEKWzExMF0KW1JlZ2lzdHJ5XSDQqNCw0LHQu9C+0L06IE51bWJlcjtGSU87QWNjb3VudDtTdW07UGF5bWVudE51bWJlcjtCSUM7UGF5bWVudFB1cnBvc2U7SW5jb21lQ29kZQoxO9Ca0YDQuNCy0YvRhSDQkNC90LTRgNC10Lkg0JLRj9GH0LXRgdC70LDQstC+0LLQuNGHOzQwODE3ODEwMTA5NDIwMDc0MTc4OzUwNzY5LjI4OzAwMDAwMDswNDA1MDc4ODY7Ly/QktCX0KEvLzUwNzY5LTI4Ly/QntGC0L/Rg9GB0LrQvdGL0LUg0LfQsCDRj9C90LLQsNGA0YwgMjAyMyDQszsx";
        String inputMac = "W0FjY291bnRdIDQwNzAyODEwNTc0ODkwMDAyNjEzDQpbQ29udHJhY3RdIFlSNTY5MDA5TVANCltSZWdpc3RyeU51bWJlcl0gMTANCltSZWdpc3RyeURhdGVdIDExMTEyMDIyDQpbUmVnaXN0cnlTdW1dIDQ0NTAwDQpbUGF5bWVudE51bWJlcl0gMzE1MA0KW1BheW1lbnREYXRlXSAxMTExMjAyMg0KW1BheW1lbnRQdXJwb3NlXSDA4uDt8SDn4CDt7v/h8Pwg7+4g8OXl8fLw8w0KW1BheW1lbnRPcmRlcl0gMw0KW1ZPQ29kZV0gDQpbSW5jb21lQ29kZV0gMQ0KWzExMF0gDQpbUmVnaXN0cnldDQoxO8/g8vDz+OXiINHl8OPl6SDM6PXg6evu4uj3OzQwODE3ODEwNTAwMDE4ODMxMzkzOzE1MDA7MDAwMDAxOzA0NDUyNTk3NDvA4uDt8SDn4CDt7v/h8Pwg7+4g8OXl8fLw8zsxDQoyO8zg6/v47uogwuvg5Ojs6PAgyOLg7e7i6Pc7NDA4MTc4MTA0MDQ4MTAzNTE2MjY7MjUwMDswMDAwMDI7MDQ0NTI1NTkzO8Di4O3xIOfgIO3u/+Hw/CDv7iDw5eXx8vDzOzENCjM7weDy+/Dl4iDR5ezl7SDA6+Xq8eXl4uj3OzQwODE3ODEwMjA4MDEwMTgxMTA2OzIwMDA7MDAwMDAzOzA0NDUyNTU5MzvA4uDt8SDn4CDt7v/h8Pwg7+4g8OXl8fLw8zsxDQo0O8Hg5OjtIMLo8uDr6OkgwuDr5fD85eLo9zs0MDgxNzgxMDkwODAxMDE4NTQyODsyMDAwOzAwMDAwNDswNDQ1MjU1OTM7wOLg7fEg5+Ag7e7/4fD8IO/uIPDl5fHy8PM7MQ0KNTvA8Ojx8u7iIN7w6Okgwujq8u7w7uLo9zs0MDgxNzgxMDcwODAxMDE4NjMwMDs1MDAwOzAwMDAwNTswNDQ1MjU1OTM7wOLg7fEg5+Ag7e7/4fD8IO/uIPDl5fHy8PM7MQ0KNjvQ8+ToIMji4O0gwujq8u7w7uLo9zs0MDgxNzgxMDAwODAxMDE3NTkyNzsyMDAwOzAwMDAwNjswNDQ1MjU1OTM7wOLg7fEg5+Ag7e7/4fD8IO/uIPDl5fHy8PM7MQ0KNzvQ7uTo7SDN6Oru6+DpINHl8OPl5eLo9zs0MDgxNzgxMDkwNDgxMDM3Mjk5ODs1NTAwOzAwMDAwNzswNDQ1MjU1OTM7wOLg7fEg5+Ag7e7/4fD8IO/uIPDl5fHy8PM7MQ0KODvO8ejv7uIgwuDr5e3y6O0gwuDr5e3y6O3u4uj3OzQwODE3ODEwNjAwMDUzMDcxMjM4OzIwMDA7MDAwMDA4OzA0NDUyNTk3NDvA4uDt8SDn4CDt7v/h8Pwg7+4g8OXl8fLw8zsxDQo5O8/r/vnl4iDC4OTo7CDA6+Xq8eXl4uj3OzQwODE3ODEwMjc0NTUwMDMwMDQyOzQwMDA7MDAwMDA5OzA0MDQwNzM4ODvA4uDt8SDn4CDt7v/h8Pwg7+4g8OXl8fLw8zsxDQoxMDvA4vLz+OXt6u4gwujy4Ovo6SDC6Ory7vDu4uj3OzQwODE3ODEwODA4MDEwMTc3MjM1OzIwMDA7MDAwMDEwOzA0NDUyNTU5MzvA4uDt8SDn4CDt7v/h8Pwg7+4g8OXl8fLw8zsxDQoxMTvK7u3u+OXt6u4gyOLg7SDM6PXg6evu4uj3OzQwODE3ODEwNjAyMDAyNTM2MTMxOzUwMDA7MDAwMDExOzA0MDE3MzYwNDvA4uDt8SDn4CDt7v/h8Pwg7+4g8OXl8fLw8zsxDQoxMjvK4Pjo8OjtIMDt8u7tIMPl7e3g5Pzl4uj3OzQwODE3ODEwMDAyMDAzNDk2MDAzOzM1MDA7MDAwMDEyOzA0MDE3MzYwNDvA4uDt8SDn4CDt7v/h8Pwg7+4g8OXl8fLw8zsxDQoxMzvQ++ru4iDP4OLl6yDR5fDj5eXi6Pc7NDA4MTc4MTA3MzA4NTI1MDUwNjA7MjUwMDswMDAwMTM7MDQwMzQ5NjAyO8Di4O3xIOfgIO3u/+Hw/CDv7iDw5eXx8vDzOzENCjE0O8zl8uv/6u7i4CDS4PL8/+3gIMDr5erx4O3k8O7i7eA7NDA4MTc4MTA5MDAwNjMxMDQ5OTI7NTAwMDswMDAwMTQ7MDQ0NTI1OTc0O8Di4O3xIOfgIO3u/+Hw/CDv7iDw5eXx8vDzOzENCg==";
        String inputAnsi = "zvLv8/Hq4A0Kx8Lfw8jNIMDLxcrRwM3E0CDRxdDDxcXCyNcgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgNDA4MTc4MTAwNDY3ODAwMDcwOTYgICAgICAgICAgICAgICAgICAgMjI0NjAuNjkgICAgICAgICAgICAgICAgICAgICAgICAgICANCsjSzsPOOjAwMDEgICAgICAgICAyMjQ2MC42OQ0K";
        String inputWin = "W0FjY291bnRdIDQwNzAyODEwNTAwMDAwMTE0NzI3DQpbQ29udHJhY3RdIDg3MTENCltSZWdpc3RyeU51bWJlcl0gNjIzDQpbUmVnaXN0cnlEYXRlXSAwMjEyMjAyMg0KW1JlZ2lzdHJ5U3VtXSAxMDA5MC4wMA0KW1BheW1lbnROdW1iZXJdIDYyMw0KW1BheW1lbnREYXRlXSAwMjEyMjAyMg0KW1BheW1lbnRQdXJwb3NlXSDP5fDl4u7kIOTl7S7x8C4g5Ov/IPDg8ffl8u7iIO/uIMTu4y65IDQ2NDk3NDA1TVAg7vIgMjguMDUuMjAyMOMuIOzg8uXw6ODr/O3g/yDv7uzu+fwg8e7j6+Dx7e4g0OXl8fLw8yC5NjIzIO7yIDAyMTIyMDIy4y4gzcTRIO3lIO7h6+Dj4OXy8f8NCltQYXltZW50T3JkZXJdIDUNCltWT0NvZGVdIA0KW0luY29tZUNvZGVdIDENClsxMTBdIA0KW1JlZ2lzdHJ5XQ0KMTvP6Org6+7iIMDt8u7tIMDr5erx5eXi6Pc7NDA4MTc4MTAyMDAwMDM3Mjg2MDE7MTAwOTAuMDA7NjIzOzA0NDUyNTk3NDvs4PLl8Ojg6/zt4P8g7+7s7vn8OzENCg==";

        String recognizedCharset = checkEachCharset(charsetsForTest, inputUTF8);
        System.out.println(recognizedCharset);

        byte[] data = Base64.getDecoder().decode(inputUTF8);
        System.out.println(new String(data, Charset.forName(recognizedCharset)));
    }

    private static String checkEachCharset(List<String> charsetName, String b64) {
        Map<String, Integer> result = new HashMap<>();
        for (String charset : charsetName) {
            result.put(charset, calculateCyrillicLinesByCharset(b64, charset));
        }
        return Collections.max(result.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    private static int calculateCyrillicLinesByCharset(String b64, String charsetName) {

        byte[] data = Base64.getDecoder().decode(b64);
        var text = new String(data, Charset.forName(charsetName));
        var textLines = text.split("\n");


        var percentCyrInOneLine = 0;
        for (String line : textLines) {
            percentCyrInOneLine += calculateCyrillicLettersInLine(line);
        }

        return percentCyrInOneLine;
    }

    private static int calculateCyrillicLettersInLine(String line) {
        var arrText = line.split("");

        int caughtCyrillicLatter = 0;
        for (String e : arrText) {
            if (isCyrillic(e)) {
                caughtCyrillicLatter++;
            }
        }
        return 100 * caughtCyrillicLatter / arrText.length;
    }

    private static boolean isCyrillic(String s) {
        boolean result = false;
        for (char a : s.toCharArray()) {
            if (Character.UnicodeBlock.of(a) == Character.UnicodeBlock.CYRILLIC) {
                result = !result;
                break;
            }
        }
        return result;
    }
}
