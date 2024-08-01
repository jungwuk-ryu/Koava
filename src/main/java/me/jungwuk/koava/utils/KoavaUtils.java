package me.jungwuk.koava.utils;

import me.jungwuk.koava.enums.RealTypes;

import java.util.Arrays;
import java.util.List;

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
}
