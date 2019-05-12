package zeiterfassung.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Root")
public class TimeRegistrationRoot {
    @XmlElement(name = "Area")
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
