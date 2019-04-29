package zeiterfassung.models;

import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;

public class TimeRegistrationRoot {

    private List<Area> areaList = new ArrayList<Area>();

    public void addArea(Area newArea) {
        if (hasArea(newArea.getName())) {
            String exceptionExplanation = "Area with name "
                    + newArea.getName()
                    + " already exists";

            throw new IllegalArgumentException(exceptionExplanation);
        }
        areaList.add(newArea);
    }

    public Area getArea(String areaName) {
        for (Area area : areaList) {
            if (area.getName().equals(areaName)) {
                return area;
            }
        }
        return null;
    }

    public boolean removeArea(String areaName) {
        Area area = getArea(areaName);
        if (area == null) return false;
        return areaList.remove(area);
    }

    public boolean hasArea(String areaName) {
        for (Area area : areaList) {
            if (area.getName().equals(areaName)) {
                return true;
            }
        }
        return false;
    }
}
