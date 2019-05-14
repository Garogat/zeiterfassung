package zeiterfassung.models;



import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class TimeRegistrationRoot extends Observable {

    private List<Area> areaList = new ArrayList<>();

    public void addArea(Area newArea) {
        areaList.add(newArea);
    }

    public boolean removeArea(Area area) {
        boolean tmp = areaList.remove(area);
        if (tmp) {
            setChanged();
            notifyObservers();
        }
        return tmp;
    }

    public boolean hasArea(Area area) {
        return areaList.contains(area);
    }

    public void getAreas(Listable<Area> areas){
        areas.getList(areaList);
    }
}
