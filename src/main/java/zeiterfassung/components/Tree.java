package zeiterfassung.components;

import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import zeiterfassung.models.*;

/**
 * Factory for Treeview content
 */
public class Tree {

    /**
     * Pointer to tree root model
     */
    private TimeRegistrationRoot root;

    /**
     * Pointer to tree root item
     */
    private TreeItem<TreeContextItem> rootItem;

    /**
     * Constructor
     *
     * @param root pointer to root model of current datastore
     */
    public Tree(TimeRegistrationRoot root) {
        this.root = root;

        this.rootItem = build(root);
    }

    /**
     * Get the generated tree content
     *
     * @return generated tree content
     */
    public TreeItem<TreeContextItem> getTree() {
        return rootItem;
    }

    /**
     * Build sub-tree of {@link TimeRegistrationRoot}
     *
     * @param root {@link TimeRegistrationRoot} model
     * @return sub-tree
     */
    private TreeItem<TreeContextItem> build(TimeRegistrationRoot root) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(root));
        rootItem.setExpanded(true);

        root.areaListProperty().addListener((ListChangeListener.Change<? extends Area> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Area area : change.getAddedSubList()) {
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
                TreeItem<TreeContextItem> item = build(area);
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    /**
     * Build sub-tree of {@link Area}
     *
     * @param area {@link Area} model
     * @return sub-tree
     */
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
                        removeChild(rootItem, project);
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


    /**
     * Build sub-tree of {@link Project}
     *
     * @param project {@link Project} model
     * @return sub-tree
     */
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
                        removeChild(rootItem, subProject);
                    }
                }
            }
        });

        project.getSubProjects(subProjects -> {
            for (SubProject subProject : subProjects) {
                rootItem.getChildren().add(build(subProject));
            }
        });

        addTasks(rootItem, project);

        return rootItem;
    }

    /**
     * Build sub-tree of {@link SubProject}
     *
     * @param subProject {@link SubProject} model
     * @return sub-tree
     */
    private TreeItem<TreeContextItem> build(SubProject subProject) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(subProject));
        rootItem.setExpanded(true);

        addTasks(rootItem, subProject);

        return rootItem;
    }

    /**
     * Build sub-tree of {@link Task}
     *
     * @param task {@link Task} model
     * @return sub-tree
     */
    private TreeItem<TreeContextItem> build(Task task) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(task));
        return rootItem;
    }

    /**
     * Add all {@link Task}s of {@link Project} or {@link SubProject}
     *
     * @param rootItem   rootItem to add tasks to
     * @param subProject {@link Project} or {@link SubProject} containing the tasks
     */
    private void addTasks(TreeItem<TreeContextItem> rootItem, SubProject subProject) {
        subProject.taskListProperty().addListener((ListChangeListener.Change<? extends Task> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Task task : change.getAddedSubList()) {
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
                rootItem.getChildren().add(build(task));
            }
        });
    }

    /**
     * Helper to find the {@link TreeItem} containing the
     *
     * @param rootItem rootItem which contains the one {@link TreeItem} which should be removed
     * @param object   object contained by one of {@code rootItem} children
     */
    private void removeChild(TreeItem<TreeContextItem> rootItem, Object object) {
        for (int i = 0; i < rootItem.getChildren().size(); i++) {
            TreeItem<TreeContextItem> child = rootItem.getChildren().get(i);
            if (child.getValue().getItem().equals(object)) {
                rootItem.getChildren().remove(child);
            }
        }
    }
}
