package zeiterfassung.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Project extends SubProject {
    @XmlElement(name = "Role")
    //List<Role> roleList = new ArrayList<>();
    private ObservableList<Role> roleList = FXCollections.observableArrayList();

    @XmlElement(name = "SubProject")
    private ListProperty<SubProject> subProjectList = new SimpleListProperty(FXCollections.observableArrayList());

    public Project() {
        super();
        setName("Neues Projekt");
        setDescription("Dies ist ein Projekt");
    }

    public Project(String name, String description) {
        super(name, description);
    }

    public void getSubProjects(Listable<SubProject> subProjects) {
        subProjects.getList(subProjectList);
    }

    public ObservableList<Role> roleListProperty() {
        return roleList;
    }

    public ListProperty<SubProject> subProjectListProperty() {
        return subProjectList;
    }

    /**
     * @throws IllegalArgumentException
     */
    public boolean addSubProject(SubProject newSubProject) {
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

    public Role getRole(String id){
        for (Role role: roleList){
            if (role.getId().equals(id)){
                return role;
            }
        }
        return getDefaultRole();
    }

    public Role getRoleByIdx(int idx){
        return roleList.get(idx);
    }

    public Role getDefaultRole(){
        if (roleList.size() > 0){
            return roleList.get(0);
        }
        return null;
    }

    public boolean addRole(Role newRole) {
        return roleList.add(newRole);
    }

    public boolean removeRole(Role newRole) {
        return roleList.remove(newRole);
    }

    public boolean hasRole(Role role) {
        return roleList.contains(role);
    }

    public Role findRoleByID(String id){
        for (Role role: roleList){
            if (role.getId().equals(id)){
                return role;
            }
        }
        return null;
    }


    @Override
    public Duration getDuration(LocalDateTime start, LocalDateTime stop) {
        Duration duration = super.getDuration(start, stop);
        for (SubProject sp : subProjectList) {
            duration = duration.plus(sp.getDuration(start, stop));
        }
        return duration;
    }

    @Override
    public double getCosts(LocalDateTime start, LocalDateTime stop) {
        double costs = super.getCosts(start, stop);
        for (SubProject sp : subProjectList) {
            costs += sp.getCosts(start, stop);
        }
        return costs;
    }
}
