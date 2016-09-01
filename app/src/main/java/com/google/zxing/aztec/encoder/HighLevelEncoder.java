package com.google.zxing.aztec.encoder;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import com.google.zxing.common.BitArray;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public final class HighLevelEncoder {
    private static final int[][] CHAR_MAP = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{5, 256}));
    static final int[][] LATCH_TABLE;
    static final int MODE_DIGIT = 2;
    static final int MODE_LOWER = 1;
    static final int MODE_MIXED = 3;
    static final String[] MODE_NAMES = new String[]{"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};
    static final int MODE_PUNCT = 4;
    static final int MODE_UPPER = 0;
    static final int[][] SHIFT_TABLE = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{6, 6}));
    private final byte[] text;

    static {
        int i;
        r0 = new int[5][];
        int[] iArr = new int[]{327708, 327710, 327709, 656318, iArr};
        iArr = new int[]{590318, 327710, 327709, 656318, iArr};
        iArr = new int[]{262158, 590300, 590301, 932798, iArr};
        iArr = new int[]{327709, 327708, 656318, 327710, iArr};
        iArr = new int[]{327711, 656380, 656382, 656381, iArr};
        LATCH_TABLE = r0;
        CHAR_MAP[0][32] = 1;
        for (i = 65; i <= 90; i++) {
            CHAR_MAP[0][i] = (i - 65) + 2;
        }
        CHAR_MAP[1][32] = 1;
        for (i = 97; i <= 122; i++) {
            CHAR_MAP[1][i] = (i - 97) + 2;
        }
        CHAR_MAP[2][32] = 1;
        for (i = 48; i <= 57; i++) {
            CHAR_MAP[2][i] = (i - 48) + 2;
        }
        CHAR_MAP[2][44] = 12;
        CHAR_MAP[2][46] = 13;
        iArr = new int[28];
        iArr[1] = 32;
        iArr[2] = 1;
        iArr[3] = 2;
        iArr[4] = 3;
        iArr[5] = 4;
        iArr[6] = 5;
        iArr[7] = 6;
        iArr[8] = 7;
        iArr[9] = 8;
        iArr[10] = 9;
        iArr[11] = 10;
        iArr[12] = 11;
        iArr[13] = 12;
        iArr[14] = 13;
        iArr[15] = 27;
        iArr[16] = 28;
        iArr[17] = 29;
        iArr[18] = 30;
        iArr[19] = 31;
        iArr[20] = 64;
        iArr[21] = 92;
        iArr[22] = 94;
        iArr[23] = 95;
        iArr[24] = 96;
        iArr[25] = 124;
        iArr[26] = TransportMediator.KEYCODE_MEDIA_PLAY;
        iArr[27] = TransportMediator.KEYCODE_MEDIA_PAUSE;
        for (i = 0; i < iArr.length; i++) {
            CHAR_MAP[3][iArr[i]] = i;
        }
        iArr = new int[31];
        iArr[1] = 13;
        iArr[6] = 33;
        iArr[7] = 39;
        iArr[8] = 35;
        iArr[9] = 36;
        iArr[10] = 37;
        iArr[11] = 38;
        iArr[12] = 39;
        iArr[13] = 40;
        iArr[14] = 41;
        iArr[15] = 42;
        iArr[16] = 43;
        iArr[17] = 44;
        iArr[18] = 45;
        iArr[19] = 46;
        iArr[20] = 47;
        iArr[21] = 58;
        iArr[22] = 59;
        iArr[23] = 60;
        iArr[24] = 61;
        iArr[25] = 62;
        iArr[26] = 63;
        iArr[27] = 91;
        iArr[28] = 93;
        iArr[29] = 123;
        iArr[30] = 125;
        for (i = 0; i < iArr.length; i++) {
            if (iArr[i] > 0) {
                CHAR_MAP[4][iArr[i]] = i;
            }
        }
        for (int[] fill : SHIFT_TABLE) {
            Arrays.fill(fill, -1);
        }
        SHIFT_TABLE[0][4] = 0;
        SHIFT_TABLE[1][4] = 0;
        SHIFT_TABLE[1][0] = 28;
        SHIFT_TABLE[3][4] = 0;
        SHIFT_TABLE[2][4] = 0;
        SHIFT_TABLE[2][0] = 15;
    }

    public HighLevelEncoder(byte[] bArr) {
        this.text = bArr;
    }

    private static Collection<State> simplifyStates(Iterable<State> iterable) {
        Collection linkedList = new LinkedList();
        for (State state : iterable) {
            Object obj;
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                State state2 = (State) it.next();
                if (state2.isBetterThanOrEqualTo(state)) {
                    obj = null;
                    break;
                } else if (state.isBetterThanOrEqualTo(state2)) {
                    it.remove();
                }
            }
            obj = 1;
            if (obj != null) {
                linkedList.add(state);
            }
        }
        return linkedList;
    }

    private void updateStateForChar(State state, int i, Collection<State> collection) {
        char c = (char) (this.text[i] & MotionEventCompat.ACTION_MASK);
        Object obj = CHAR_MAP[state.getMode()][c] > 0 ? 1 : null;
        State state2 = null;
        int i2 = 0;
        while (i2 <= 4) {
            int i3 = CHAR_MAP[i2][c];
            if (i3 > 0) {
                if (state2 == null) {
                    state2 = state.endBinaryShift(i);
                }
                if (obj == null || i2 == state.getMode() || i2 == 2) {
                    collection.add(state2.latchAndAppend(i2, i3));
                }
                if (obj == null && SHIFT_TABLE[state.getMode()][i2] >= 0) {
                    collection.add(state2.shiftAndAppend(i2, i3));
                }
            }
            i2++;
        }
        if (state.getBinaryShiftByteCount() > 0 || CHAR_MAP[state.getMode()][c] == 0) {
            collection.add(state.addBinaryShiftChar(i));
        }
    }

    private static void updateStateForPair(State state, int i, int i2, Collection<State> collection) {
        State endBinaryShift = state.endBinaryShift(i);
        collection.add(endBinaryShift.latchAndAppend(4, i2));
        if (state.getMode() != 4) {
            collection.add(endBinaryShift.shiftAndAppend(4, i2));
        }
        if (i2 == 3 || i2 == 4) {
            collection.add(endBinaryShift.latchAndAppend(2, 16 - i2).latchAndAppend(2, 1));
        }
        if (state.getBinaryShiftByteCount() > 0) {
            collection.add(state.addBinaryShiftChar(i).addBinaryShiftChar(i + 1));
        }
    }

    private Collection<State> updateStateListForChar(Iterable<State> iterable, int i) {
        Iterable linkedList = new LinkedList();
        for (State updateStateForChar : iterable) {
            updateStateForChar(updateStateForChar, i, linkedList);
        }
        return simplifyStates(linkedList);
    }

    private static Collection<State> updateStateListForPair(Iterable<State> iterable, int i, int i2) {
        Iterable linkedList = new LinkedList();
        for (State updateStateForPair : iterable) {
            updateStateForPair(updateStateForPair, i, i2, linkedList);
        }
        return simplifyStates(linkedList);
    }

    public BitArray encode() {
        Object singletonList = Collections.singletonList(State.INITIAL_STATE);
        int i = 0;
        while (i < this.text.length) {
            int i2;
            byte b = i + 1 < this.text.length ? this.text[i + 1] : (byte) 0;
            switch (this.text[i]) {
                case (byte) 13:
                    if (b != (byte) 10) {
                        i2 = 0;
                        break;
                    }
                    i2 = 2;
                    break;
                case (byte) 44:
                    if (b != (byte) 32) {
                        i2 = 0;
                        break;
                    }
                    i2 = 4;
                    break;
                case (byte) 46:
                    if (b != (byte) 32) {
                        i2 = 0;
                        break;
                    }
                    i2 = 3;
                    break;
                case (byte) 58:
                    if (b != (byte) 32) {
                        i2 = 0;
                        break;
                    }
                    i2 = 5;
                    break;
                default:
                    i2 = 0;
                    break;
            }
            if (i2 > 0) {
                singletonList = updateStateListForPair(singletonList, i, i2);
                i++;
            } else {
                singletonList = updateStateListForChar(singletonList, i);
            }
            i++;
        }
        return ((State) Collections.min(singletonList, new Comparator<State>() {
            public int compare(State state, State state2) {
                return state.getBitCount() - state2.getBitCount();
            }
        })).toBitArray(this.text);
    }
}
