package zeiterfassung.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Area extends DescribableModel {
    @XmlElement(name = "Project")
    private ObservableList<Project> projectsList = FXCollections.observableArrayList();

    public Area() {
        super();
        setName("Neuer Bereich");
        setDescription("Dies ist ein Bereich");
    }

    public Area(String name, String description) {
        super(name, description);
    }

    public void getProjectList(Listable<Project> projectsList) {
        projectsList.getList(this.projectsList);
    }

    public ObservableList<Project> projectsListProperty() {
        return projectsList;
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
