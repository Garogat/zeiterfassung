package zeiterfassung.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Area extends Observable implements DescribableContainer {

    private String name;
    private String description;
    private List<Project> projectsList = new ArrayList<Project>();

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
