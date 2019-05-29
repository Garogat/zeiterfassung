package zeiterfassung;

import java.io.UnsupportedEncodingException;

public class UI {
    public static String utf8(String str) {
        byte strBytes[] = str.getBytes();
        String utf8Str = null;
        try {
            utf8Str = new String(strBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            utf8Str = str;
        }
        return utf8Str;
    }
}
