package zeiterfassung.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the RootNode of the data structure. It contains a list of areas
 */
@XmlRootElement(name = "Root")
public class TimeRegistrationRoot extends BaseModel {

    /**
     * List of different areas
     */
    @XmlElement(name = "Area")
    private ListProperty<Area> areaList = new SimpleListProperty(FXCollections.observableArrayList());

    /**
     * A reference to the task with the running work chunk if exists
     */
    @XmlIDREF
    private Task activeTask;

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

    public Task getActiveTask() {
        return activeTask;
    }

    public void setActiveTask(Task activeTask) {
        this.activeTask = activeTask;
    }

    /**
     * Checks if a running work chunk exists
     * @return the task that contains the running work chunk or null
     */
    public boolean isTaskActive() {
        if (activeTask == null) {
            return false;
        }

        return activeTask.isWorkActive();
    }
}
