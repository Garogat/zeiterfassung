package zeiterfassung.models;



import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class TimeRegistrationRoot extends Observable {

    private List<Area> areaList = new ArrayList<>();

    public boolean addArea(Area newArea) {
        if (areaList.add(newArea)){
            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }

    public boolean removeArea(Area area) {
        if (areaList.remove(area)) {
            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }

    public boolean hasArea(Area area) {
        return areaList.contains(area);
    }

    public void getAreas(Listable<Area> areas){
        areas.getList(areaList);
    }
}
