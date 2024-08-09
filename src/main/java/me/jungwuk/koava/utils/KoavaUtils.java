package me.jungwuk.koava.utils;

import me.jungwuk.koava.enums.RealTypes;

import java.util.*;

public class KoavaUtils {

    public static String fidIterableToStrList(Iterable<RealTypes.FID> fidIterable) {
        StringBuilder sb = new StringBuilder();
        boolean firstEle = true;

        for (RealTypes.FID fid : fidIterable) {
            if (firstEle) {
                firstEle = false;
            } else {
                sb.append(";");
            }

            sb.append(fid.getId());
        }

        return sb.toString();
    }

    public static String fidListToStrList(List<RealTypes.FID> fidList) {
        return fidIterableToStrList(fidList);
    }

    public static String fidListToStrList(RealTypes.FID[] fidArray) {
        return fidIterableToStrList(Arrays.asList(fidArray));
    }

    public static RealTypes.FID[] intArrayToFidArray(int[] intArray) {
        RealTypes.FID[] fidArray = new RealTypes.FID[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            fidArray[i] = RealTypes.getFid(intArray[i]);
        }
        return fidArray;
    }

    /**
     * Open API가 문자열 리스트를 "a;b;c;d;e;"처럼 ";"으로 구분하여 반환했을 때
     * 이를 자바의 리스트로 변환해주는 메소드입니다. <br>
     * 만약 요소가 빈 문자열이라면 이는 리스트에서 제외됩니다.<br>
     * 예를 들어 "a;b;c;"에서 index 3은 ""이고, 이는 리스트에 포함되지 않습니다.
     *
     * @param strList 요소가 ";"으로 구분된 문자열
     * @return List객체
     */
    public static List<String> strListToList(String strList) {
        if (strList == null || strList.isEmpty()) return Collections.emptyList();

        ArrayList<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(strList, ";");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token == null || token.isEmpty()) continue;

            list.add(token);
        }

        return list;
    }
}
