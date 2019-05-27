package zeiterfassung.components;

import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import zeiterfassung.models.*;

public class Tree {

    private TimeRegistrationRoot root;
    private TreeItem<TreeContextItem> rootItem;

    public Tree(TimeRegistrationRoot root) {
        this.root = root;

        this.rootItem = build(root);
    }

    public TreeItem<TreeContextItem> getTree() {
        return rootItem;
    }

    private TreeItem<TreeContextItem> build(TimeRegistrationRoot root) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(this.root));
        rootItem.setExpanded(true);

        root.areaListProperty().addListener((ListChangeListener.Change<? extends Area> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Area area : change.getAddedSubList()) {
                        area.setParent(root);
                        rootItem.getChildren().add(build(area));
                    }
                } else if (change.wasRemoved()) {
                    for (Area area : change.getRemoved()) {
                        removeChild(rootItem, area);
                    }
                }
            }
        });

        root.getAreas(list -> {
            for (Area area : list) {
                area.setParent(root);
                TreeItem<TreeContextItem> item = build(area);
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> build(Area area) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(area));
        rootItem.setExpanded(true);

        area.projectsListProperty().addListener((ListChangeListener.Change<? extends Project> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Project project : change.getAddedSubList()) {
                        project.setParent(area);
                        rootItem.getChildren().add(build(project));
                    }
                } else if (change.wasRemoved()) {
                    for (Project project : change.getRemoved()) {
                        removeChild(rootItem, project);
                    }
                }
            }
        });

        area.getProjectList(projects -> {
            for (Project project : projects) {
                project.setParent(area);
                rootItem.getChildren().add(build(project));
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> build(Project project) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(project));
        rootItem.setExpanded(true);

        project.subProjectListProperty().addListener((ListChangeListener.Change<? extends SubProject> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (SubProject subProject : change.getAddedSubList()) {
                        subProject.setParent(project);
                        rootItem.getChildren().add(build(subProject));
                    }
                } else if (change.wasRemoved()) {
                    for (SubProject subProject : change.getRemoved()) {
                        removeChild(rootItem, subProject);
                    }
                }
            }
        });

        project.getSubProjects(subProjects -> {
            for (SubProject subProject : subProjects) {
                subProject.setParent(project);
                rootItem.getChildren().add(build(subProject));
            }
        });

        addTasks(rootItem, project);

        return rootItem;
    }

    private TreeItem<TreeContextItem> build(SubProject subProject) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(subProject));
        rootItem.setExpanded(true);

        addTasks(rootItem, subProject);

        return rootItem;
    }

    private TreeItem<TreeContextItem> build(Task task) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(task));
        return rootItem;
    }

    private void addTasks(TreeItem<TreeContextItem> rootItem, SubProject subProject) {
        subProject.taskListProperty().addListener((ListChangeListener.Change<? extends Task> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Task task : change.getAddedSubList()) {
                        task.setParent(subProject);
                        rootItem.getChildren().add(build(task));
                    }
                } else if (change.wasRemoved()) {
                    for (Task task : change.getRemoved()) {
                        removeChild(rootItem, task);
                    }
                }
            }
        });

        subProject.getTasks(tasks -> {
            for (Task task : tasks) {
                task.setParent(subProject);
                rootItem.getChildren().add(build(task));
            }
        });
    }

    private void removeChild(TreeItem<TreeContextItem> rootItem, Object object) {
        for (int i = 0; i < rootItem.getChildren().size(); i++) {
            TreeItem<TreeContextItem> child = rootItem.getChildren().get(i);
            if (child.getValue().getItem().equals(object)) {
                rootItem.getChildren().remove(child);
            }
        }
    }
}
