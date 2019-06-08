package zeiterfassung.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Area extends DescribableModel {
    @XmlElement(name = "Project")
    private ObservableList<Project> projectsList = FXCollections.observableArrayList();

    /**
     * Generates a new Area Instance
     */
    public Area() {
        super();
        setName("Neuer Bereich");
        setDescription("Dies ist ein Bereich");
    }

    /**
     * Generates a new Area Instance
     * @param name name of the area
     * @param description description of the area
     */
    public Area(String name, String description) {
        super(name, description);
    }

    /**
     * Returns a list of all containing projects
     * @param projectsList List of containing Projects
     */
    public void getProjectList(Listable<Project> projectsList) {
        projectsList.getList(this.projectsList);
    }

    /**
     * Obervable List Property for javafx
     * @return Obervable List Property for javafx
     */
    public ObservableList<Project> projectsListProperty() {
        return projectsList;
    }

    /**
     * Adds a new project
     * @param newProject Project to add
     */
    public void addProject(Project newProject) {
        newProject.setParent(this);
        projectsList.add(newProject);
    }

    /**
     * Checks if the area owns this project
     * @param projectToSearch Project to check
     * @return true if the project exists in this area
     */
    public boolean hasProject(Project projectToSearch) {
        for (Project project : projectsList) {
            if (project.equals(projectToSearch)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a project from this area
     * @param project Project instance to remove
     * @return true if the project has existed in this area
     */
    public boolean removeProject(Project project) {
        return projectsList.remove(project);
    }
}
