package zeiterfassung.components;

import javafx.event.Event;
import javafx.scene.control.TreeItem;
import zeiterfassung.models.*;

import java.util.Observable;
import java.util.Observer;

public class Tree {

    private TimeRegistrationRoot root;

    public Tree(TimeRegistrationRoot root) {
        this.root = root;
    }

    public TreeItem<TreeContextItem> getTree() {
        TreeItem<TreeContextItem> rootItem = new TreeItem<TreeContextItem>(new TreeContextItem(this.root));
        rootItem.setExpanded(true);

        this.root.getAreas(list -> {
            for (Area area : list) {
                TreeItem<TreeContextItem> item = getArea(area);
                area.addObserver(new Observer() {
                    @Override
                    public void update(Observable observable, Object o) {
                        System.out.println("tt:" + ((Area) observable).getName());
                        TreeItem.TreeModificationEvent<TreeContextItem> event = new TreeItem.TreeModificationEvent<>(TreeItem.valueChangedEvent(), item);
                        Event.fireEvent(item, event);
                    }
                });
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> getArea(Area area) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<TreeContextItem>(new TreeContextItem(area));
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
        TreeItem<TreeContextItem> rootItem = new TreeItem<TreeContextItem>(new TreeContextItem(project));
        rootItem.setExpanded(true);

        project.getSubProjects(subProjects -> {
            for (SubProject subProject : subProjects) {
                TreeItem item = getSubProject(subProject);
                rootItem.getChildren().add(item);
            }
        });

        project.getTasks(tasks -> {
            for (Task task : tasks) {
                TreeItem<TreeContextItem> item = new TreeItem<TreeContextItem>(new TreeContextItem(task));
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> getSubProject(SubProject subProject) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<TreeContextItem>(new TreeContextItem(subProject));
        rootItem.setExpanded(true);

        subProject.getTasks(tasks -> {
            for (Task task : tasks) {
                TreeItem<TreeContextItem> item = new TreeItem<TreeContextItem>(new TreeContextItem(task));
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }
}
