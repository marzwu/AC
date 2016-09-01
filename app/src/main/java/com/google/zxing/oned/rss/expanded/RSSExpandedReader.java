package com.google.zxing.oned.rss.expanded;

import android.support.v4.media.TransportMediator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSSExpandedReader extends AbstractRSSReader {
    private static final int[] EVEN_TOTAL_SUBSET = new int[]{4, 20, 52, 104, 204};
    private static final int[][] FINDER_PATTERNS = new int[][]{new int[]{1, 8, 4, 1}, new int[]{3, 6, 4, 1}, new int[]{3, 4, 6, 1}, new int[]{3, 2, 8, 1}, new int[]{2, 6, 5, 1}, new int[]{2, 2, 9, 1}};
    private static final int[][] FINDER_PATTERN_SEQUENCES;
    private static final int FINDER_PAT_A = 0;
    private static final int FINDER_PAT_B = 1;
    private static final int FINDER_PAT_C = 2;
    private static final int FINDER_PAT_D = 3;
    private static final int FINDER_PAT_E = 4;
    private static final int FINDER_PAT_F = 5;
    private static final int[] GSUM;
    private static final int MAX_PAIRS = 11;
    private static final int[] SYMBOL_WIDEST = new int[]{7, 5, 4, 3, 1};
    private static final int[][] WEIGHTS = new int[][]{new int[]{1, 3, 9, 27, 81, 32, 96, 77}, new int[]{20, 60, 180, 118, 143, 7, 21, 63}, new int[]{189, 145, 13, 39, 117, 140, 209, 205}, new int[]{193, 157, 49, 147, 19, 57, 171, 91}, new int[]{62, 186, 136, 197, 169, 85, 44, 132}, new int[]{185, 133, 188, 142, 4, 12, 36, 108}, new int[]{113, 128, 173, 97, 80, 29, 87, 50}, new int[]{150, 28, 84, 41, 123, 158, 52, 156}, new int[]{46, 138, 203, 187, 139, 206, 196, 166}, new int[]{76, 17, 51, 153, 37, 111, 122, 155}, new int[]{43, 129, 176, 106, 107, 110, 119, 146}, new int[]{16, 48, 144, 10, 30, 90, 59, 177}, new int[]{109, 116, 137, 200, 178, 112, 125, 164}, new int[]{70, 210, 208, 202, 184, TransportMediator.KEYCODE_MEDIA_RECORD, 179, 115}, new int[]{134, 191, 151, 31, 93, 68, 204, 190}, new int[]{148, 22, 66, 198, 172, 94, 71, 2}, new int[]{6, 18, 54, 162, 64, 192, 154, 40}, new int[]{120, 149, 25, 75, 14, 42, TransportMediator.KEYCODE_MEDIA_PLAY, 167}, new int[]{79, 26, 78, 23, 69, 207, 199, 175}, new int[]{103, 98, 83, 38, 114, 131, 182, 124}, new int[]{161, 61, 183, TransportMediator.KEYCODE_MEDIA_PAUSE, 170, 88, 53, 159}, new int[]{55, 165, 73, 8, 24, 72, 5, 15}, new int[]{45, 135, 194, 160, 58, 174, 100, 89}};
    private final List<ExpandedPair> pairs = new ArrayList(MAX_PAIRS);
    private final List<ExpandedRow> rows = new ArrayList();
    private final int[] startEnd = new int[2];
    private boolean startFromEven = false;

    static {
        int[] iArr = new int[5];
        iArr[1] = 348;
        iArr[2] = 1388;
        iArr[3] = 2948;
        iArr[4] = 3988;
        GSUM = iArr;
        r0 = new int[10][];
        int[] iArr2 = new int[]{1, 1, iArr2};
        iArr2 = new int[]{2, 1, 3, iArr2};
        iArr2 = new int[]{4, 1, 3, 2, iArr2};
        iArr2 = new int[]{4, 1, 3, 3, 5, iArr2};
        iArr2 = new int[]{4, 1, 3, 4, 5, 5, iArr2};
        iArr2 = new int[8];
        iArr2[2] = 1;
        iArr2[3] = 1;
        iArr2[4] = 2;
        iArr2[5] = 2;
        iArr2[6] = 3;
        iArr2[7] = 3;
        r0[6] = iArr2;
        iArr2 = new int[9];
        iArr2[2] = 1;
        iArr2[3] = 1;
        iArr2[4] = 2;
        iArr2[5] = 2;
        iArr2[6] = 3;
        iArr2[7] = 4;
        iArr2[8] = 4;
        r0[7] = iArr2;
        iArr2 = new int[10];
        iArr2[2] = 1;
        iArr2[3] = 1;
        iArr2[4] = 2;
        iArr2[5] = 2;
        iArr2[6] = 3;
        iArr2[7] = 4;
        iArr2[8] = 5;
        iArr2[9] = 5;
        r0[8] = iArr2;
        iArr2 = new int[MAX_PAIRS];
        iArr2[2] = 1;
        iArr2[3] = 1;
        iArr2[4] = 2;
        iArr2[5] = 3;
        iArr2[6] = 3;
        iArr2[7] = 4;
        iArr2[8] = 4;
        iArr2[9] = 5;
        iArr2[10] = 5;
        r0[9] = iArr2;
        FINDER_PATTERN_SEQUENCES = r0;
    }

    private void adjustOddEvenCounts(int i) throws NotFoundException {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4 = null;
        Object obj5 = 1;
        int count = AbstractRSSReader.count(getOddCounts());
        int count2 = AbstractRSSReader.count(getEvenCounts());
        int i2 = (count + count2) - i;
        Object obj6 = (count & 1) == 1 ? 1 : null;
        Object obj7 = (count2 & 1) == 0 ? 1 : null;
        if (count > 13) {
            obj = 1;
            obj2 = null;
        } else if (count < 4) {
            obj = null;
            int i3 = 1;
        } else {
            obj = null;
            obj2 = null;
        }
        if (count2 > 13) {
            obj3 = null;
            obj4 = 1;
        } else if (count2 < 4) {
            int i4 = 1;
        } else {
            obj3 = null;
        }
        int i5;
        if (i2 == 1) {
            if (obj6 != null) {
                if (obj7 != null) {
                    throw NotFoundException.getNotFoundInstance();
                }
                obj = obj2;
                obj5 = obj3;
                obj3 = 1;
            } else if (obj7 == null) {
                throw NotFoundException.getNotFoundInstance();
            } else {
                i5 = 1;
                obj5 = obj3;
                obj3 = obj;
                obj = obj2;
            }
        } else if (i2 == -1) {
            if (obj6 != null) {
                if (obj7 != null) {
                    throw NotFoundException.getNotFoundInstance();
                }
                r12 = obj3;
                obj3 = obj;
                r3 = 1;
                obj5 = r12;
            } else if (obj7 == null) {
                throw NotFoundException.getNotFoundInstance();
            } else {
                obj3 = obj;
                obj = obj2;
            }
        } else if (i2 != 0) {
            throw NotFoundException.getNotFoundInstance();
        } else if (obj6 != null) {
            if (obj7 == null) {
                throw NotFoundException.getNotFoundInstance();
            } else if (count < count2) {
                i5 = 1;
                r12 = obj3;
                obj3 = obj;
                r3 = 1;
                obj5 = r12;
            } else {
                i4 = 1;
                obj = obj2;
            }
        } else if (obj7 != null) {
            throw NotFoundException.getNotFoundInstance();
        } else {
            obj5 = obj3;
            obj3 = obj;
            obj = obj2;
        }
        if (obj != null) {
            if (obj3 != null) {
                throw NotFoundException.getNotFoundInstance();
            }
            AbstractRSSReader.increment(getOddCounts(), getOddRoundingErrors());
        }
        if (obj3 != null) {
            AbstractRSSReader.decrement(getOddCounts(), getOddRoundingErrors());
        }
        if (obj5 != null) {
            if (obj4 != null) {
                throw NotFoundException.getNotFoundInstance();
            }
            AbstractRSSReader.increment(getEvenCounts(), getOddRoundingErrors());
        }
        if (obj4 != null) {
            AbstractRSSReader.decrement(getEvenCounts(), getEvenRoundingErrors());
        }
    }

    private boolean checkChecksum() {
        ExpandedPair expandedPair = (ExpandedPair) this.pairs.get(0);
        DataCharacter leftChar = expandedPair.getLeftChar();
        DataCharacter rightChar = expandedPair.getRightChar();
        if (rightChar != null) {
            int i = 2;
            int checksumPortion = rightChar.getChecksumPortion();
            for (int i2 = 1; i2 < this.pairs.size(); i2++) {
                expandedPair = (ExpandedPair) this.pairs.get(i2);
                checksumPortion += expandedPair.getLeftChar().getChecksumPortion();
                i++;
                rightChar = expandedPair.getRightChar();
                if (rightChar != null) {
                    checksumPortion += rightChar.getChecksumPortion();
                    i++;
                }
            }
            if ((checksumPortion % 211) + ((i - 4) * 211) == leftChar.getValue()) {
                return true;
            }
        }
        return false;
    }

    private List<ExpandedPair> checkRows(List<ExpandedRow> list, int i) throws NotFoundException {
        while (i < this.rows.size()) {
            ExpandedRow expandedRow = (ExpandedRow) this.rows.get(i);
            this.pairs.clear();
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.pairs.addAll(((ExpandedRow) list.get(i2)).getPairs());
            }
            this.pairs.addAll(expandedRow.getPairs());
            if (isValidSequence(this.pairs)) {
                if (checkChecksum()) {
                    return this.pairs;
                }
                List arrayList = new ArrayList();
                arrayList.addAll(list);
                arrayList.add(expandedRow);
                try {
                    return checkRows(arrayList, i + 1);
                } catch (NotFoundException e) {
                }
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private List<ExpandedPair> checkRows(boolean z) {
        List<ExpandedPair> list = null;
        if (this.rows.size() > 25) {
            this.rows.clear();
        } else {
            this.pairs.clear();
            if (z) {
                Collections.reverse(this.rows);
            }
            try {
                list = checkRows(new ArrayList(), 0);
            } catch (NotFoundException e) {
            }
            if (z) {
                Collections.reverse(this.rows);
            }
        }
        return list;
    }

    static Result constructResult(List<ExpandedPair> list) throws NotFoundException, FormatException {
        String parseInformation = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.buildBitArray(list)).parseInformation();
        ResultPoint[] resultPoints = ((ExpandedPair) list.get(0)).getFinderPattern().getResultPoints();
        ResultPoint[] resultPoints2 = ((ExpandedPair) list.get(list.size() - 1)).getFinderPattern().getResultPoints();
        return new Result(parseInformation, null, new ResultPoint[]{resultPoints[0], resultPoints[1], resultPoints2[0], resultPoints2[1]}, BarcodeFormat.RSS_EXPANDED);
    }

    private void findNextPair(BitArray bitArray, List<ExpandedPair> list, int i) throws NotFoundException {
        int[] decodeFinderCounters = getDecodeFinderCounters();
        decodeFinderCounters[0] = 0;
        decodeFinderCounters[1] = 0;
        decodeFinderCounters[2] = 0;
        decodeFinderCounters[3] = 0;
        int size = bitArray.getSize();
        if (i < 0) {
            i = list.isEmpty() ? 0 : ((ExpandedPair) list.get(list.size() - 1)).getFinderPattern().getStartEnd()[1];
        }
        Object obj = list.size() % 2 != 0 ? 1 : null;
        if (this.startFromEven) {
            obj = obj != null ? null : 1;
        }
        int i2 = 0;
        int i3 = i;
        while (i3 < size) {
            i2 = bitArray.get(i3) ? 0 : 1;
            if (i2 == 0) {
                break;
            }
            i3++;
        }
        int i4 = i3;
        i3 = 0;
        int i5 = i2;
        i2 = i4;
        for (int i6 = i3; i6 < size; i6++) {
            if ((bitArray.get(i6) ^ i5) != 0) {
                decodeFinderCounters[i3] = decodeFinderCounters[i3] + 1;
            } else {
                if (i3 == 3) {
                    if (obj != null) {
                        reverseCounters(decodeFinderCounters);
                    }
                    if (AbstractRSSReader.isFinderPattern(decodeFinderCounters)) {
                        this.startEnd[0] = i2;
                        this.startEnd[1] = i6;
                        return;
                    }
                    if (obj != null) {
                        reverseCounters(decodeFinderCounters);
                    }
                    i2 += decodeFinderCounters[0] + decodeFinderCounters[1];
                    decodeFinderCounters[0] = decodeFinderCounters[2];
                    decodeFinderCounters[1] = decodeFinderCounters[3];
                    decodeFinderCounters[2] = 0;
                    decodeFinderCounters[3] = 0;
                    i3--;
                } else {
                    i3++;
                }
                decodeFinderCounters[i3] = 1;
                i5 = i5 != 0 ? 0 : 1;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int getNextSecondBar(BitArray bitArray, int i) {
        return bitArray.get(i) ? bitArray.getNextSet(bitArray.getNextUnset(i)) : bitArray.getNextUnset(bitArray.getNextSet(i));
    }

    private static boolean isNotA1left(FinderPattern finderPattern, boolean z, boolean z2) {
        return (finderPattern.getValue() == 0 && z && z2) ? false : true;
    }

    private static boolean isPartialRow(Iterable<ExpandedPair> iterable, Iterable<ExpandedRow> iterable2) {
        for (ExpandedRow expandedRow : iterable2) {
            Object obj;
            for (ExpandedPair expandedPair : iterable) {
                for (ExpandedPair equals : expandedRow.getPairs()) {
                    if (expandedPair.equals(equals)) {
                        int i = 1;
                        continue;
                        break;
                    }
                }
                Object obj2 = null;
                continue;
                if (obj2 == null) {
                    obj = null;
                    continue;
                    break;
                }
            }
            obj = 1;
            continue;
            if (obj != null) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidSequence(List<ExpandedPair> list) {
        for (int[] iArr : FINDER_PATTERN_SEQUENCES) {
            if (list.size() <= iArr.length) {
                boolean z;
                for (int i = 0; i < list.size(); i++) {
                    if (((ExpandedPair) list.get(i)).getFinderPattern().getValue() != iArr[i]) {
                        z = false;
                        break;
                    }
                }
                z = true;
                if (z) {
                    return true;
                }
            }
        }
        return false;
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int i, boolean z) {
        int i2;
        int i3;
        int i4;
        if (z) {
            i2 = this.startEnd[0] - 1;
            while (i2 >= 0 && !bitArray.get(i2)) {
                i2--;
            }
            i3 = i2 + 1;
            i2 = this.startEnd[0] - i3;
            i4 = this.startEnd[1];
        } else {
            i3 = this.startEnd[0];
            i4 = bitArray.getNextUnset(this.startEnd[1] + 1);
            i2 = i4 - this.startEnd[1];
        }
        Object decodeFinderCounters = getDecodeFinderCounters();
        System.arraycopy(decodeFinderCounters, 0, decodeFinderCounters, 1, decodeFinderCounters.length - 1);
        decodeFinderCounters[0] = i2;
        try {
            return new FinderPattern(AbstractRSSReader.parseFinderValue(decodeFinderCounters, FINDER_PATTERNS), new int[]{i3, i4}, i3, i4, i);
        } catch (NotFoundException e) {
            return null;
        }
    }

    private static void removePartialRows(List<ExpandedPair> list, List<ExpandedRow> list2) {
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            ExpandedRow expandedRow = (ExpandedRow) it.next();
            if (expandedRow.getPairs().size() != list.size()) {
                Object obj;
                for (ExpandedPair expandedPair : expandedRow.getPairs()) {
                    for (ExpandedPair equals : list) {
                        if (expandedPair.equals(equals)) {
                            int i = 1;
                            continue;
                            break;
                        }
                    }
                    obj = null;
                    continue;
                    if (obj == null) {
                        obj = null;
                        break;
                    }
                }
                obj = 1;
                if (obj != null) {
                    it.remove();
                }
            }
        }
    }

    private static void reverseCounters(int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length / 2; i++) {
            int i2 = iArr[i];
            iArr[i] = iArr[(length - i) - 1];
            iArr[(length - i) - 1] = i2;
        }
    }

    private void storeRow(int i, boolean z) {
        boolean z2 = false;
        boolean z3 = false;
        int i2 = 0;
        while (i2 < this.rows.size()) {
            ExpandedRow expandedRow = (ExpandedRow) this.rows.get(i2);
            if (expandedRow.getRowNumber() > i) {
                z2 = expandedRow.isEquivalent(this.pairs);
                break;
            }
            i2++;
            z3 = expandedRow.isEquivalent(this.pairs);
        }
        if (!z2 && !r1 && !isPartialRow(this.pairs, this.rows)) {
            this.rows.add(i2, new ExpandedRow(this.pairs, i, z));
            removePartialRows(this.pairs, this.rows);
        }
    }

    DataCharacter decodeDataCharacter(BitArray bitArray, FinderPattern finderPattern, boolean z, boolean z2) throws NotFoundException {
        int i;
        int length;
        int[] dataCharacterCounters = getDataCharacterCounters();
        dataCharacterCounters[0] = 0;
        dataCharacterCounters[1] = 0;
        dataCharacterCounters[2] = 0;
        dataCharacterCounters[3] = 0;
        dataCharacterCounters[4] = 0;
        dataCharacterCounters[5] = 0;
        dataCharacterCounters[6] = 0;
        dataCharacterCounters[7] = 0;
        if (z2) {
            OneDReader.recordPatternInReverse(bitArray, finderPattern.getStartEnd()[0], dataCharacterCounters);
        } else {
            OneDReader.recordPattern(bitArray, finderPattern.getStartEnd()[1], dataCharacterCounters);
            i = 0;
            for (length = dataCharacterCounters.length - 1; i < length; length--) {
                int i2 = dataCharacterCounters[i];
                dataCharacterCounters[i] = dataCharacterCounters[length];
                dataCharacterCounters[length] = i2;
                i++;
            }
        }
        float count = ((float) AbstractRSSReader.count(dataCharacterCounters)) / ((float) 17);
        float f = ((float) (finderPattern.getStartEnd()[1] - finderPattern.getStartEnd()[0])) / 15.0f;
        if (Math.abs(count - f) / f > 0.3f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int length2;
        int[] oddCounts = getOddCounts();
        int[] evenCounts = getEvenCounts();
        float[] oddRoundingErrors = getOddRoundingErrors();
        float[] evenRoundingErrors = getEvenRoundingErrors();
        for (length = 0; length < dataCharacterCounters.length; length++) {
            float f2 = (1.0f * ((float) dataCharacterCounters[length])) / count;
            i = (int) (0.5f + f2);
            if (i < 1) {
                if (f2 < 0.3f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                i = 1;
            } else if (i > 8) {
                if (f2 > 8.7f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                i = 8;
            }
            int i3 = length / 2;
            if ((length & 1) == 0) {
                oddCounts[i3] = i;
                oddRoundingErrors[i3] = f2 - ((float) i);
            } else {
                evenCounts[i3] = i;
                evenRoundingErrors[i3] = f2 - ((float) i);
            }
        }
        adjustOddEvenCounts(17);
        int value = ((z2 ? 0 : 1) + ((finderPattern.getValue() * 4) + (z ? 0 : 2))) - 1;
        i2 = 0;
        i = oddCounts.length - 1;
        length = 0;
        while (i >= 0) {
            if (isNotA1left(finderPattern, z, z2)) {
                length += WEIGHTS[value][i * 2] * oddCounts[i];
            }
            i--;
            i2 = oddCounts[i] + i2;
        }
        i = 0;
        for (length2 = evenCounts.length - 1; length2 >= 0; length2--) {
            if (isNotA1left(finderPattern, z, z2)) {
                i += WEIGHTS[value][(length2 * 2) + 1] * evenCounts[length2];
            }
        }
        length += i;
        if ((i2 & 1) != 0 || i2 > 13 || i2 < 4) {
            throw NotFoundException.getNotFoundInstance();
        }
        i = (13 - i2) / 2;
        length2 = SYMBOL_WIDEST[i];
        return new DataCharacter(GSUM[i] + ((RSSUtils.getRSSvalue(oddCounts, length2, true) * EVEN_TOTAL_SUBSET[i]) + RSSUtils.getRSSvalue(evenCounts, 9 - length2, false)), length);
    }

    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        this.pairs.clear();
        this.startFromEven = false;
        try {
            return constructResult(decodeRow2pairs(i, bitArray));
        } catch (NotFoundException e) {
            this.pairs.clear();
            this.startFromEven = true;
            return constructResult(decodeRow2pairs(i, bitArray));
        }
    }

    List<ExpandedPair> decodeRow2pairs(int i, BitArray bitArray) throws NotFoundException {
        while (true) {
            try {
                this.pairs.add(retrieveNextPair(bitArray, this.pairs, i));
            } catch (NotFoundException e) {
                if (this.pairs.isEmpty()) {
                    throw e;
                } else if (checkChecksum()) {
                    return this.pairs;
                } else {
                    boolean z = !this.rows.isEmpty();
                    storeRow(i, false);
                    if (z) {
                        List<ExpandedPair> checkRows = checkRows(false);
                        if (checkRows != null) {
                            return checkRows;
                        }
                        checkRows = checkRows(true);
                        if (checkRows != null) {
                            return checkRows;
                        }
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
            }
        }
    }

    List<ExpandedRow> getRows() {
        return this.rows;
    }

    public void reset() {
        this.pairs.clear();
        this.rows.clear();
    }

    ExpandedPair retrieveNextPair(BitArray bitArray, List<ExpandedPair> list, int i) throws NotFoundException {
        FinderPattern parseFoundFinderPattern;
        boolean z = list.size() % 2 == 0;
        boolean z2 = this.startFromEven ? !z : z;
        int i2 = -1;
        boolean z3 = true;
        do {
            findNextPair(bitArray, list, i2);
            parseFoundFinderPattern = parseFoundFinderPattern(bitArray, i, z2);
            if (parseFoundFinderPattern == null) {
                i2 = getNextSecondBar(bitArray, this.startEnd[0]);
                continue;
            } else {
                z3 = false;
                continue;
            }
        } while (z3);
        DataCharacter decodeDataCharacter = decodeDataCharacter(bitArray, parseFoundFinderPattern, z2, true);
        if (list.isEmpty() || !((ExpandedPair) list.get(list.size() - 1)).mustBeLast()) {
            DataCharacter decodeDataCharacter2;
            try {
                decodeDataCharacter2 = decodeDataCharacter(bitArray, parseFoundFinderPattern, z2, false);
            } catch (NotFoundException e) {
                decodeDataCharacter2 = null;
            }
            return new ExpandedPair(decodeDataCharacter, decodeDataCharacter2, parseFoundFinderPattern, true);
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
