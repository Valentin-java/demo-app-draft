package com.example.demo.draft.test;

public class GetTagValue {

    public static void main(String[] args) {


        String in = "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "body {\n" +
                "\tfont-family: Geneva, Arial, Helvetica, sans-serif;\n" +
                "}\n" +
                ".em1 {\n" +
                "\ttext-decoration: underline;\n" +
                "}\n" +
                ".em2 {\n" +
                "\tfont-weight: bold;\n" +
                "\ttext-decoration: underline;\n" +
                "}\n" +
                ".em3 {\n" +
                "\tfont-weight: bold;\n" +
                "\tfont-style: oblique;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<p>\n" +
                "Уведомляем Вас о том, что закрепленный за Вами клиент<br />\n" +
                "<strong>Тестовый клиент (Тестовый ИНН)</strong><br />\n" +
                "принят на зарплатное обслуживание.\n" +
                "</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        var value = getTagValue(in, "strong");

        System.out.println(value);

    }
    public static String getTagValue(String xml, String tagName){
        return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
    }
}
