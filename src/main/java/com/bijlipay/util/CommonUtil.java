package com.bijlipay.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);

    public static String camelToSnake(String str) {
        log.info("Camel To Snake : Source : {}", str);
        String result = "";
        // Append first character(in lower case) to result string
        char c = str.charAt(0);
        result = result + Character.toLowerCase(c);
        // Traverse the string from ist index to last index
        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            // Check if the character is upper case then append '_' and such character (in lower case) to result string
            if (Character.isUpperCase(ch)) {
                result = result + '_';
                result = result + Character.toLowerCase(ch);
            }
            // If the character is lower case then add such character into result string
            else {
                result = result + ch;
            }
        }
        log.info("Camel To Snake : Result : {}", result);
        // return the result
        return result;
    }

    @Async
    public static void moveFileLn(List<String> leadDocs, String orgUploadDir, String bkpUploadDir) throws IOException {
        String TAG = "LEAD_DOC_PRC : ";
        leadDocs.forEach(doc -> {
            try {
                log.info(TAG + "Moving file : ---- : {}", orgUploadDir + "/" + doc);
                String cmd1 = String.format("mv " + orgUploadDir + "/" + doc + " " + bkpUploadDir + "/" + doc);
                String cmd2 = String.format("ln -Ts " + bkpUploadDir + "/" + doc + " " + orgUploadDir + "/" + doc);
                log.info(TAG + "Move CMD : ---- : \n {}", cmd1);
                log.info(TAG + "LINK CMD : ---- : \n {}", cmd2);
                Process p = Runtime.getRuntime().exec(cmd1);
                log.info(TAG + "Wating for Moving file process completion !");
                p.waitFor();
                if (p.exitValue() == 0) {
                    Process p1 = Runtime.getRuntime().exec(cmd2);
                    log.info(TAG + "Wating for Link file creation process completion !");
                    p1.waitFor();
                    if (p1.exitValue() == 0) {
                        log.info(TAG + "Link File Created Successfully!");
                    } else {
                        log.info(TAG + "Link File Creation Failed !");
                    }
                }
            } catch (Exception e) {
                log.info(TAG + "Moving & Creating Sym Link Failed : Error : {}", e.getMessage());
            }
        });
    }

    public static List<String> baseTidMidGenerate(Integer maxDigit, String prefix) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            final String tidStringFormat = prefix + "%0" + (maxDigit - prefix.length()) + "d";
            if (i == 0)
                result.add(String.format(Locale.getDefault(), tidStringFormat, i));
            else if (i == 99999)
                result.add(String.format(Locale.getDefault(), tidStringFormat, i));
        }
        log.info("BaseTidGen : result : ---- : {}", result);
        return result;
    }

    public static String parseNumFromTidMid(String tidOrMid, String prefix) {
        String result = new String();
        String regex = prefix+"(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tidOrMid);

        if (matcher.find()) {
            log.info("PARSE NUM FROM TID/MID : matcher : ---- : {}", matcher.group(1));
            result = matcher.group(1);
        }
        return result;
    }

    public static String genTidOrMidFromString(String prefix, Integer value, Integer maxDigit) {
        final String tidOrMidStringFormat = prefix + "%0" + (maxDigit - prefix.length()) + "d";
        return String.format(Locale.getDefault(), tidOrMidStringFormat, value);
    }   
    public static String parseJson(Object input) {
        String output = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            // convert user object to json string and return it
            output = mapper.writeValueAsString(input);
        }
        catch (JsonProcessingException e) {
            // catch various errors
            e.printStackTrace();
        }
        return output;
    }
}
