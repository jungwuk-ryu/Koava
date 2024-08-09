
package me.jungwuk.koava.waiters;

import me.jungwuk.koava.enums.RealTypes;
import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.RealData;
import me.jungwuk.koava.utils.KoavaUtils;

import java.util.Arrays;
import java.util.HashMap;

public class RealDataWaiter extends KoavaWaiter<RealData> {
    public RealTypes.FID[] fidList;
    private HashMap<RealTypes.FID, String> fidData = new HashMap<>();

    public RealDataWaiter(int[] fidArray) {
        this(fidArray, null);
    }

    public RealDataWaiter(int[] fidArray, WaiterFilter<RealData> filter) {
        super(filter);
        fidList = KoavaUtils.intArrayToFidArray(fidArray);
    }

    public RealDataWaiter(RealTypes.FID[] fidArray) {
        this(fidArray, null);
    }

    public RealDataWaiter(RealTypes.FID[] fidArray, WaiterFilter<RealData> filter) {
        super(filter);
        fidList = Arrays.copyOf(fidArray, fidArray.length);
    }

    @Override
    public synchronized void setData(RealData data) {
        super.setData(data);
        getAllFidsData();
    }

    private void getAllFidsData() {
        fidData = new HashMap<>();
        for (RealTypes.FID fid : fidList) {
            fidData.put(fid, fid.get());
        }
    }

    public String getFidData(int fid) {
        return getFidData(RealTypes.getFid(fid));
    }

    public String getFidData(RealTypes.FID fid) {
        return fidData.get(fid);
    }
}
