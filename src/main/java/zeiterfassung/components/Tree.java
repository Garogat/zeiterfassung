package zeiterfassung.components;

import javafx.scene.control.TreeItem;
import zeiterfassung.models.*;

public class Tree {

    private TimeRegistrationRoot root;
    private TreeItem<TreeContextItem> rootItem;

    public Tree(TimeRegistrationRoot root) {
        this.root = root;

        build();
    }

    public TreeItem<TreeContextItem> getTree() {
        return rootItem;
    }

    public void build() {
        rootItem = new TreeItem<>(new TreeContextItem(this.root));
        rootItem.setExpanded(true);

        this.root.getAreas(list -> {
            for (Area area : list) {
                TreeItem<TreeContextItem> item = getArea(area);
                rootItem.getChildren().add(item);
            }
        });
    }

    private TreeItem<TreeContextItem> getArea(Area area) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(area));
        rootItem.setExpanded(true);

        area.getProjectList(projects -> {
            for (Project project : projects) {
                TreeItem<TreeContextItem> item = getProject(project);
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> getProject(Project project) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(project));
        rootItem.setExpanded(true);

        project.getSubProjects(subProjects -> {
            for (SubProject subProject : subProjects) {
                TreeItem<TreeContextItem> item = getSubProject(subProject);
                rootItem.getChildren().add(item);
            }
        });

        project.getTasks(tasks -> {
            for (Task task : tasks) {
                TreeItem<TreeContextItem> item = new TreeItem<>(new TreeContextItem(task));
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> getSubProject(SubProject subProject) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(subProject));
        rootItem.setExpanded(true);

        subProject.getTasks(tasks -> {
            for (Task task : tasks) {
                TreeItem<TreeContextItem> item = new TreeItem<>(new TreeContextItem(task));
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }
}
