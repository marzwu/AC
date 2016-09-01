package com.google.zxing.datamatrix.encoder;

import android.support.v4.view.MotionEventCompat;

final class EdifactEncoder implements Encoder {
    EdifactEncoder() {
    }

    private static void encodeChar(char c, StringBuilder stringBuilder) {
        if (c >= ' ' && c <= '?') {
            stringBuilder.append(c);
        } else if (c < '@' || c > '^') {
            HighLevelEncoder.illegalCharacter(c);
        } else {
            stringBuilder.append((char) (c - 64));
        }
    }

    private static String encodeToCodewords(CharSequence charSequence, int i) {
        int i2 = 0;
        int length = charSequence.length() - i;
        if (length == 0) {
            throw new IllegalStateException("StringBuilder must not be empty");
        }
        char charAt = charSequence.charAt(i);
        int charAt2 = length >= 2 ? charSequence.charAt(i + 1) : 0;
        int charAt3 = length >= 3 ? charSequence.charAt(i + 2) : 0;
        if (length >= 4) {
            i2 = charSequence.charAt(i + 3);
        }
        i2 += (charAt3 << 6) + ((charAt2 << 12) + (charAt << 18));
        char c = (char) ((i2 >> 16) & MotionEventCompat.ACTION_MASK);
        char c2 = (char) ((i2 >> 8) & MotionEventCompat.ACTION_MASK);
        char c3 = (char) (i2 & MotionEventCompat.ACTION_MASK);
        StringBuilder stringBuilder = new StringBuilder(3);
        stringBuilder.append(c);
        if (length >= 2) {
            stringBuilder.append(c2);
        }
        if (length >= 3) {
            stringBuilder.append(c3);
        }
        return stringBuilder.toString();
    }

    private static void handleEOD(EncoderContext encoderContext, CharSequence charSequence) {
        int i = 1;
        try {
            int length = charSequence.length();
            if (length != 0) {
                int dataCapacity;
                if (length == 1) {
                    encoderContext.updateSymbolInfo();
                    dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
                    if (encoderContext.getRemainingCharacters() == 0 && dataCapacity <= 2) {
                        encoderContext.signalEncoderChange(0);
                        return;
                    }
                }
                if (length > 4) {
                    throw new IllegalStateException("Count must not exceed 4");
                }
                dataCapacity = length - 1;
                String encodeToCodewords = encodeToCodewords(charSequence, 0);
                if ((encoderContext.hasMoreCharacters() ? 0 : 1) == 0 || dataCapacity > 2) {
                    i = 0;
                }
                if (dataCapacity <= 2) {
                    encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + dataCapacity);
                    if (encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount() >= 3) {
                        encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + encodeToCodewords.length());
                        i = 0;
                    }
                }
                if (i != 0) {
                    encoderContext.resetSymbolInfo();
                    encoderContext.pos -= dataCapacity;
                } else {
                    encoderContext.writeCodewords(encodeToCodewords);
                }
                encoderContext.signalEncoderChange(0);
            }
        } finally {
            encoderContext.signalEncoderChange(0);
        }
    }

    public void encode(EncoderContext encoderContext) {
        Object stringBuilder = new StringBuilder();
        while (encoderContext.hasMoreCharacters()) {
            encodeChar(encoderContext.getCurrentChar(), stringBuilder);
            encoderContext.pos++;
            if (stringBuilder.length() >= 4) {
                encoderContext.writeCodewords(encodeToCodewords(stringBuilder, 0));
                stringBuilder.delete(0, 4);
                if (HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode()) != getEncodingMode()) {
                    encoderContext.signalEncoderChange(0);
                    break;
                }
            }
        }
        stringBuilder.append('\u001f');
        handleEOD(encoderContext, stringBuilder);
    }

    public int getEncodingMode() {
        return 4;
    }
}
