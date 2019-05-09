package zeiterfassung.models;

import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;

public class TimeRegistrationRoot {

    private List<Area> areaList = new ArrayList<Area>();

    public void addArea(Area newArea) {
        areaList.add(newArea);
    }

    public boolean removeArea(Area area) {
        return areaList.remove(area);
    }

    public boolean hasArea(Area area) {
        return areaList.contains(area);
    }

    public void getAreas(Listable<Area> areas){
        areas.getList(areaList);
    }
}
