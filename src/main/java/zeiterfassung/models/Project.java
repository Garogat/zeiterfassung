package zeiterfassung.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a project. A Project is part of an area and contains tasks and subprojects.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Project extends SubProject {
    /**
     * The customer for to generate the invoice
     */
    private StringProperty customer = new SimpleStringProperty();

    /**
     * A List of Roles which can be assigned to the tasks
     */
    @XmlElement(name = "Role")
    private ObservableList<Role> roleList = FXCollections.observableArrayList();

    /**
     * Containing subproject
     */
    @XmlElement(name = "SubProject")
    private ListProperty<SubProject> subProjectList = new SimpleListProperty(FXCollections.observableArrayList());


    public Project() {
        this("Neues Projekt", "Dies ist ein Projekt");
    }

    public Project(String name, String description) {
        super(name, description);
        addRole(Role.DEFAULT_ROLE);
    }

    /**
     * Iterates the list of subprojects
     * @param subProjects Callback Interface
     */
    public void getSubProjects(Listable<SubProject> subProjects) {
        subProjects.getList(subProjectList);
    }

    public ObservableList<Role> roleListProperty() {
        return roleList;
    }

    public ListProperty<SubProject> subProjectListProperty() {
        return subProjectList;
    }

    public boolean addSubProject(SubProject newSubProject) {
        newSubProject.setParent(this);
        return subProjectList.add(newSubProject);
    }

    public boolean removeSubProject(SubProject subProject) {
        return subProjectList.remove(subProject);
    }

    public boolean hasSubProject(SubProject subProject) {
        return subProjectList.contains(subProject);
    }

    public void getRoles(Listable<Role> roles) {
        roles.getList(roleList);
    }

    public int getRolesSize() {
        return roleList.size();
    }

    /**
     * Returns the default role. This is the first role
     * @return the first role in list
     */
    public Role getDefaultRole() {
        if (roleList.size() > 0) {
            return roleList.get(0);
        }
        return null;
    }

    public boolean addRole(Role role) {
        role.setParent(this);
        return roleList.add(role);
    }

    public boolean removeRole(Role role) {
        // remove role from tasks as well
        for (Task task : getAllTasks()) {
            if (task.getRole().equals(role)) {
                task.setRole(null);
            }
        }
        return roleList.remove(role);
    }

    public boolean hasRole(Role role) {
        return roleList.contains(role);
    }

    @XmlElement(name = "Customer")
    public String getCustomer() {
        return customer.getValue();
    }

    public void setCustomer(String customer) {
        this.customer.setValue(customer);
    }

    public StringProperty customerProperty() {
        return customer;
    }

    /**
     * Returns the overall duration from all tasks and subprojects in an interval
     * @param start Start time of the interval
     * @param stop End time of the interval
     * @return the overall duration
     */
    @Override
    public Duration getDuration(LocalDateTime start, LocalDateTime stop) {
        Duration duration = super.getDuration(start, stop);
        for (SubProject sp : subProjectList) {
            duration = duration.plus(sp.getDuration(start, stop));
        }
        return duration;
    }

    /**
     * Returns the overall estimated duration from all tasks and subprojects
     * @return the overall estimated duration
     */
    @Override
    public Duration getEstimatedDuration() {
        Duration duration = super.getEstimatedDuration();
        for (SubProject sp : subProjectList) {
            duration = duration.plus(sp.getEstimatedDuration());
        }
        return duration;
    }

    /**
     * Returns the overall cost from all tasks and subprojects in an interval
     * @param start Start time of the interval
     * @param stop End time of the interval
     * @return the overall costs
     */
    @Override
    public double getCosts(LocalDateTime start, LocalDateTime stop) {
        double costs = super.getCosts(start, stop);
        for (SubProject sp : subProjectList) {
            costs += sp.getCosts(start, stop);
        }
        return costs;
    }

    /**
     * Returns a task list of all tasks and subprojects
     * @return
     */
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> res = new ArrayList<>();

        getSubProjects(subProjects -> {
            for (SubProject subProject : subProjects) {
                subProject.getTasks(tasks -> res.addAll(tasks));
            }
        });

        getTasks(tasks -> res.addAll(tasks));

        return res;
    }
}
