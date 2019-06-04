package zeiterfassung.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Root")
public class TimeRegistrationRoot extends BaseModel {

    @XmlElement(name = "Area")
    private ListProperty<Area> areaList = new SimpleListProperty(FXCollections.observableArrayList());

    public void addArea(Area newArea) {
        newArea.setParent(this);
        areaList.add(newArea);
    }

    public boolean removeArea(Area area) {
        return areaList.remove(area);
    }

    public boolean hasArea(Area area) {
        return areaList.contains(area);
    }

    public void getAreas(Listable<Area> areas) {
        areas.getList(areaList);
    }

    public ListProperty<Area> areaListProperty() {
        return areaList;
    }

    public TimeRegistrationRoot() {
        super();
    }
}
