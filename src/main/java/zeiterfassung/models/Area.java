package zeiterfassung.models;

import java.util.ArrayList;
import java.util.List;

public class Area extends DescribableModel {

    private List<Project> projectsList = new ArrayList<>();

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
