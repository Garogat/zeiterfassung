package zeiterfassung.controllers;

import zeiterfassung.models.Project;

public class ProjectController {
    private Project project;

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
