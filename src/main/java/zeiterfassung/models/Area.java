package zeiterfassung.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Area extends DescribableModel {

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
