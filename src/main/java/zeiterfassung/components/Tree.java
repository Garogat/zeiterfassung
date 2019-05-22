package zeiterfassung.components;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        this.root.areaListProperty().addListener((ListChangeListener.Change<? extends Area> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Area area : change.getAddedSubList()) {
                        rootItem.getChildren().add(build(area));
                    }
                } else if (change.wasRemoved()) {
                    for (Area area : change.getRemoved()) {
                        // TODO: fix removing
                        rootItem.getChildren().remove(area);
                    }
                }
            }

        });

        this.root.getAreas(list -> {
            for (Area area : list) {
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
                        rootItem.getChildren().add(build(project));
                    }
                } else if (change.wasRemoved()) {
                    for (Project project : change.getRemoved()) {
                        // TODO: fix removing
                        rootItem.getChildren().remove(project);
                    }
                }
            }
        });

        area.getProjectList(projects -> {
            for (Project project : projects) {
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
                        rootItem.getChildren().add(build(subProject));
                    }
                } else if (change.wasRemoved()) {
                    for (SubProject subProject : change.getRemoved()) {
                        // TODO: fix removing
                        rootItem.getChildren().remove(subProject);
                    }
                }
            }
        });

        project.getSubProjects(subProjects -> {
            for (SubProject subProject : subProjects) {
                rootItem.getChildren().add(build(subProject));
            }
        });

        project.taskListProperty().addListener((ListChangeListener.Change<? extends Task> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Task task : change.getAddedSubList()) {
                        rootItem.getChildren().add(build(task));
                    }
                } else if (change.wasRemoved()) {
                    for (Task task : change.getRemoved()) {
                        // TODO: fix removing
                        rootItem.getChildren().remove(task);
                    }
                }
            }
        });

        project.getTasks(tasks -> {
            for (Task task : tasks) {
                rootItem.getChildren().add(build(task));
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> build(SubProject subProject) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(subProject));
        rootItem.setExpanded(true);

        subProject.taskListProperty().addListener((ListChangeListener.Change<? extends Task> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Task task : change.getAddedSubList()) {
                        rootItem.getChildren().add(build(task));
                    }
                } else if (change.wasRemoved()) {
                    for (Task task : change.getRemoved()) {
                        System.out.println("remove task: " + task.getName());
                        // TODO: fix removing
                        rootItem.getChildren().remove(task);
                    }
                }
            }
        });

        subProject.getTasks(tasks -> {
            for (Task task : tasks) {
                rootItem.getChildren().add(build(task));
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> build(Task task) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(task));
        return rootItem;
    }
}
