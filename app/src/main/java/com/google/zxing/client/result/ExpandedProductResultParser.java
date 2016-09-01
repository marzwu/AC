package com.google.zxing.client.result;

public final class ExpandedProductResultParser extends ResultParser {
    private static String findAIvalue(int i, String str) {
        if (str.charAt(i) == '(') {
            CharSequence substring = str.substring(i + 1);
            StringBuilder stringBuilder = new StringBuilder();
            int i2 = 0;
            while (i2 < substring.length()) {
                char charAt = substring.charAt(i2);
                if (charAt == ')') {
                    return stringBuilder.toString();
                }
                if (charAt >= '0' && charAt <= '9') {
                    stringBuilder.append(charAt);
                    i2++;
                }
            }
            return stringBuilder.toString();
        }
        return null;
    }

    private static String findValue(int i, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String substring = str.substring(i);
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == '(') {
                if (findAIvalue(i2, substring) != null) {
                    break;
                }
                stringBuilder.append('(');
            } else {
                stringBuilder.append(charAt);
            }
        }
        return stringBuilder.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.zxing.client.result.ExpandedProductParsedResult parse(com.google.zxing.Result r23) {
        /*
        r22 = this;
        r3 = r23.getBarcodeFormat();
        r4 = com.google.zxing.BarcodeFormat.RSS_EXPANDED;
        if (r3 == r4) goto L_0x000a;
    L_0x0008:
        r3 = 0;
    L_0x0009:
        return r3;
    L_0x000a:
        r4 = com.google.zxing.client.result.ResultParser.getMassagedText(r23);
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r17 = 0;
        r18 = new java.util.HashMap;
        r18.<init>();
        r3 = 0;
    L_0x0023:
        r19 = r4.length();
        r0 = r19;
        if (r3 < r0) goto L_0x0031;
    L_0x002b:
        r3 = new com.google.zxing.client.result.ExpandedProductParsedResult;
        r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18);
        goto L_0x0009;
    L_0x0031:
        r20 = findAIvalue(r3, r4);
        if (r20 != 0) goto L_0x0039;
    L_0x0037:
        r3 = 0;
        goto L_0x0009;
    L_0x0039:
        r19 = r20.length();
        r19 = r19 + 2;
        r3 = r3 + r19;
        r19 = findValue(r3, r4);
        r21 = r19.length();
        r3 = r3 + r21;
        r21 = r20.hashCode();
        switch(r21) {
            case 1536: goto L_0x005c;
            case 1537: goto L_0x0067;
            case 1567: goto L_0x0072;
            case 1568: goto L_0x007d;
            case 1570: goto L_0x0088;
            case 1572: goto L_0x0093;
            case 1574: goto L_0x009e;
            case 1567966: goto L_0x00aa;
            case 1567967: goto L_0x00bf;
            case 1567968: goto L_0x00c8;
            case 1567969: goto L_0x00d1;
            case 1567970: goto L_0x00db;
            case 1567971: goto L_0x00e5;
            case 1567972: goto L_0x00ef;
            case 1567973: goto L_0x00f9;
            case 1567974: goto L_0x0103;
            case 1567975: goto L_0x010d;
            case 1568927: goto L_0x0117;
            case 1568928: goto L_0x012c;
            case 1568929: goto L_0x0136;
            case 1568930: goto L_0x0140;
            case 1568931: goto L_0x014a;
            case 1568932: goto L_0x0154;
            case 1568933: goto L_0x015e;
            case 1568934: goto L_0x0168;
            case 1568935: goto L_0x0172;
            case 1568936: goto L_0x017c;
            case 1575716: goto L_0x0186;
            case 1575717: goto L_0x0199;
            case 1575718: goto L_0x01a3;
            case 1575719: goto L_0x01ad;
            case 1575747: goto L_0x01b7;
            case 1575748: goto L_0x01cc;
            case 1575749: goto L_0x01d6;
            case 1575750: goto L_0x01e0;
            default: goto L_0x0052;
        };
    L_0x0052:
        r0 = r18;
        r1 = r20;
        r2 = r19;
        r0.put(r1, r2);
        goto L_0x0023;
    L_0x005c:
        r21 = "00";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x0064:
        r6 = r19;
        goto L_0x0023;
    L_0x0067:
        r21 = "01";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x006f:
        r5 = r19;
        goto L_0x0023;
    L_0x0072:
        r21 = "10";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x007a:
        r7 = r19;
        goto L_0x0023;
    L_0x007d:
        r21 = "11";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x0085:
        r8 = r19;
        goto L_0x0023;
    L_0x0088:
        r21 = "13";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x0090:
        r9 = r19;
        goto L_0x0023;
    L_0x0093:
        r21 = "15";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x009b:
        r10 = r19;
        goto L_0x0023;
    L_0x009e:
        r21 = "17";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x00a6:
        r11 = r19;
        goto L_0x0023;
    L_0x00aa:
        r21 = "3100";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x00b2:
        r13 = "KG";
        r12 = 3;
        r0 = r20;
        r14 = r0.substring(r12);
        r12 = r19;
        goto L_0x0023;
    L_0x00bf:
        r21 = "3101";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x00c7:
        goto L_0x0052;
    L_0x00c8:
        r21 = "3102";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x00d0:
        goto L_0x0052;
    L_0x00d1:
        r21 = "3103";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x00d9:
        goto L_0x0052;
    L_0x00db:
        r21 = "3104";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x00e3:
        goto L_0x0052;
    L_0x00e5:
        r21 = "3105";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x00ed:
        goto L_0x0052;
    L_0x00ef:
        r21 = "3106";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x00f7:
        goto L_0x0052;
    L_0x00f9:
        r21 = "3107";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x0101:
        goto L_0x0052;
    L_0x0103:
        r21 = "3108";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x010b:
        goto L_0x0052;
    L_0x010d:
        r21 = "3109";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x00b2;
    L_0x0115:
        goto L_0x0052;
    L_0x0117:
        r21 = "3200";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x011f:
        r13 = "LB";
        r12 = 3;
        r0 = r20;
        r14 = r0.substring(r12);
        r12 = r19;
        goto L_0x0023;
    L_0x012c:
        r21 = "3201";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x0134:
        goto L_0x0052;
    L_0x0136:
        r21 = "3202";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x013e:
        goto L_0x0052;
    L_0x0140:
        r21 = "3203";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x0148:
        goto L_0x0052;
    L_0x014a:
        r21 = "3204";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x0152:
        goto L_0x0052;
    L_0x0154:
        r21 = "3205";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x015c:
        goto L_0x0052;
    L_0x015e:
        r21 = "3206";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x0166:
        goto L_0x0052;
    L_0x0168:
        r21 = "3207";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x0170:
        goto L_0x0052;
    L_0x0172:
        r21 = "3208";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x017a:
        goto L_0x0052;
    L_0x017c:
        r21 = "3209";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x011f;
    L_0x0184:
        goto L_0x0052;
    L_0x0186:
        r21 = "3920";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x018e:
        r15 = 3;
        r0 = r20;
        r16 = r0.substring(r15);
        r15 = r19;
        goto L_0x0023;
    L_0x0199:
        r21 = "3921";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x018e;
    L_0x01a1:
        goto L_0x0052;
    L_0x01a3:
        r21 = "3922";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x018e;
    L_0x01ab:
        goto L_0x0052;
    L_0x01ad:
        r21 = "3923";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x018e;
    L_0x01b5:
        goto L_0x0052;
    L_0x01b7:
        r21 = "3930";
        r21 = r20.equals(r21);
        if (r21 == 0) goto L_0x0052;
    L_0x01bf:
        r15 = r19.length();
        r16 = 4;
        r0 = r16;
        if (r15 >= r0) goto L_0x01ea;
    L_0x01c9:
        r3 = 0;
        goto L_0x0009;
    L_0x01cc:
        r21 = "3931";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x01bf;
    L_0x01d4:
        goto L_0x0052;
    L_0x01d6:
        r21 = "3932";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x01bf;
    L_0x01de:
        goto L_0x0052;
    L_0x01e0:
        r21 = "3933";
        r21 = r20.equals(r21);
        if (r21 != 0) goto L_0x01bf;
    L_0x01e8:
        goto L_0x0052;
    L_0x01ea:
        r15 = 3;
        r0 = r19;
        r15 = r0.substring(r15);
        r16 = 0;
        r17 = 3;
        r0 = r19;
        r1 = r16;
        r2 = r17;
        r17 = r0.substring(r1, r2);
        r16 = 3;
        r0 = r20;
        r1 = r16;
        r16 = r0.substring(r1);
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.client.result.ExpandedProductResultParser.parse(com.google.zxing.Result):com.google.zxing.client.result.ExpandedProductParsedResult");
    }
}
