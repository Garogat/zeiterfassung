package zeiterfassung.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.IllegalArgumentException;
import java.time.Duration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Project extends SubProject {
    private String name;
    private String description;
    @XmlElement(name = "Role")
    List<Role> roleList = new ArrayList<Role>();
    @XmlElement(name = "SubProject")
    List<SubProject> subProjectList = new ArrayList<SubProject>();

    public Project(){
        this.setName("Cool Project name");
        this.setDescription("My interesting description");
    }

    public Project(String name, String description){
        this.setName(name);
        this.setDescription(description);
    }

    public void getRoles(Listable<Role> roles){
        roles.getList(roleList);
    }

    public void getSubProjects(Listable<SubProject> subProjects){
        subProjects.getList(subProjectList);
    }

    /**
     * @throws IllegalArgumentException
     */
    public void addSubProject(SubProject newSubProject) {
        subProjectList.add(newSubProject);
    }

    public boolean removeSubProject(SubProject subProject) {
        return subProjectList.remove(subProject);
    }


    public boolean hasSubProject(SubProject subProject) {
        return subProjectList.contains(subProject);
    }

    public void addRole(Role newRole) {
        roleList.add(newRole);
    }

    public boolean hasRole(Role role) {

        return roleList.contains(role);
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
