package zeiterfassung.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Area implements DescribableContainer {

    private String name;
    private String description;
    @XmlElement(name = "Project")
    private List<Project> projectsList = new ArrayList<Project>();

    public Area(){
        this.name = "Default name";
        this.description = "Default description";
    }

    public Area(String name, String description){
        setName(name);
        setDescription(description);
    }

    public void getProjectList(Listable<Project> projectsList){
        projectsList.getList(this.projectsList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addProject(Project newProject) {
        projectsList.add(newProject);
    }

    public boolean hasProject(Project projectToSearch) {
        for (Project project : projectsList) {
            if (project.equals(projectToSearch)) {
                return true;
            }
        }
        return false;
    }
}
