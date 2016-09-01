package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Map;
import java.util.regex.Pattern;

public final class EmailAddressResultParser extends ResultParser {
    private static final Pattern COMMA = Pattern.compile(",");

    public EmailAddressParsedResult parse(Result result) {
        String[] strArr = null;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("mailto:") && !massagedText.startsWith("MAILTO:")) {
            return EmailDoCoMoResultParser.isBasicallyValidEmailAddress(massagedText) ? new EmailAddressParsedResult(massagedText) : null;
        } else {
            String[] split;
            String str;
            String str2;
            String substring = massagedText.substring(7);
            int indexOf = substring.indexOf(63);
            if (indexOf >= 0) {
                substring = substring.substring(0, indexOf);
            }
            CharSequence urlDecode = ResultParser.urlDecode(substring);
            String[] split2 = !urlDecode.isEmpty() ? COMMA.split(urlDecode) : null;
            Map parseNameValuePairs = ResultParser.parseNameValuePairs(massagedText);
            if (parseNameValuePairs != null) {
                String[] split3;
                if (split2 == null) {
                    substring = (String) parseNameValuePairs.get("to");
                    if (substring != null) {
                        split = COMMA.split(substring);
                        substring = (String) parseNameValuePairs.get("cc");
                        split3 = substring == null ? COMMA.split(substring) : null;
                        substring = (String) parseNameValuePairs.get("bcc");
                        if (substring != null) {
                            strArr = COMMA.split(substring);
                        }
                        substring = (String) parseNameValuePairs.get("subject");
                        str = (String) parseNameValuePairs.get("body");
                        split2 = split;
                        split = strArr;
                        strArr = split3;
                        str2 = substring;
                    }
                }
                split = split2;
                substring = (String) parseNameValuePairs.get("cc");
                if (substring == null) {
                }
                substring = (String) parseNameValuePairs.get("bcc");
                if (substring != null) {
                    strArr = COMMA.split(substring);
                }
                substring = (String) parseNameValuePairs.get("subject");
                str = (String) parseNameValuePairs.get("body");
                split2 = split;
                split = strArr;
                strArr = split3;
                str2 = substring;
            } else {
                str = null;
                str2 = null;
                split = null;
            }
            return new EmailAddressParsedResult(split2, strArr, split, str2, str);
        }
    }
}
