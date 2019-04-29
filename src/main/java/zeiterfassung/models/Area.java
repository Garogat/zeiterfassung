package zeiterfassung.models;

import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;

public class Area implements DescribableContainer {

    private String name;
    private String description;
    private List<Project> projectsList = new ArrayList<Project>();

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
        if (hasProject(newProject.getName())) {
            String exceptionExplanation;
            exceptionExplanation = "Project with name "
                    + newProject.getName()
                    + " already exists in Area "
                    + getName();

            throw new IllegalArgumentException(exceptionExplanation);
        }
        projectsList.add(newProject);
    }

    public Project getProject(String projectName) {
        for (Project project : projectsList) {
            if (project.getName().equals(projectName)) {
                return project;
            }
        }
        return null;
    }

    public boolean hasProject(String projectName) {
        for (Project project : projectsList) {
            if (project.getName().equals(projectName)) {
                return true;
            }
        }
        return false;
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
