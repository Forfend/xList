package com.xList.Servlets;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PageParts {

    /**
     * Add simple HTML tag as String data type
     *
     * @param tag        - tag name
     * @param context    - tag context
     * @param attributes - tag attributes
     * @return - HTML tag
     */

    public static String getTag(String tag, String context, String attributes) {
        attributes = attributes.length() > 0 ? " " + attributes : "";
        return "<" + tag + attributes + ">" + context + "</" + tag + ">";
    }

    public static String getPartialHtml(String filename){
        StringBuilder builder = new StringBuilder();
        Path file = Paths.get(filename);
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file,charset)) {
            String line = null;
            while ((line=reader.readLine())!=null){
                builder.append(line).append("\n");
            }
        }catch (IOException e){
            System.err.format("IOException: %s%n",e);
        }

        return builder.toString();
    }


}
