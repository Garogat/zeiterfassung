package zeiterfassung.controllers;

import javafx.scene.control.TreeItem;
import zeiterfassung.models.*;
import zeiterfassung.models.Project;

public class Tree {

    TimeRegistrationRoot root;

    public Tree(TimeRegistrationRoot root) {
        this.root = root;
    }

    public TreeItem<String> getTree() {
        TreeItem<String> rootItem = new TreeItem<String>();
        rootItem.setExpanded(true);

        this.root.getAreas(list -> {
            for (Area area: list) {
                TreeItem item = getArea(area);
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem getArea(Area area) {
        TreeItem<String> rootItem = new TreeItem<String>(area.getName());
        rootItem.setExpanded(true);

        area.getProjectList(projects -> {
            for (Project project: projects) {
                TreeItem item = getProject(project);
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem getProject(Project project) {
        TreeItem<String> rootItem = new TreeItem<String>(project.getName());
        rootItem.setExpanded(true);

        project.getSubProjects(subProjects -> {
            for (SubProject subProject: subProjects) {
                TreeItem item = getSubProject(subProject);
                rootItem.getChildren().add(item);
            }
        });

        project.getTasks(tasks -> {
            for (Task task: tasks) {
                TreeItem<String> item = new TreeItem<String>(task.getName());
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem getSubProject(SubProject subProject) {
        TreeItem<String> rootItem = new TreeItem<String>(subProject.getName());
        rootItem.setExpanded(true);

        subProject.getTasks(tasks -> {
            for (Task task: tasks) {
                TreeItem<String> item = new TreeItem<String>(task.getName());
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }
}
