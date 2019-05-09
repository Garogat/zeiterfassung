package zeiterfassung.models;

import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;
import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Root")
public class TimeRegistrationRoot {
    @XmlElement(name = "Area")
    private List<Area> areaList = new ArrayList<Area>();

    public TimeRegistrationRoot(){
    }

    public TimeRegistrationRoot(Area area){
        this.areaList.add(area);
    }

    public void addArea(Area newArea) {
        areaList.add(newArea);
    }


    public boolean removeArea(Area area) {
        return areaList.remove(area);
    }

    public boolean hasArea(Area area) {
        return areaList.contains(area);
    }
}
