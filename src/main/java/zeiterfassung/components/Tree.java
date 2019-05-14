package zeiterfassung.components;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import zeiterfassung.models.*;

import java.util.Observable;
import java.util.Observer;

public class Tree {

    private TimeRegistrationRoot root;
    private TreeView<TreeContextItem> mtree;
    private Observer observer;

    public Tree(TreeView<TreeContextItem> tree, TimeRegistrationRoot root) {
        this.root = root;
        this.mtree = tree;
        observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                refreshTreeItem(getTreeViewItem(mtree.getRoot(), o));
                tree.refresh();
            }
        };
    }

    private void refreshTreeItem(TreeItem<TreeContextItem> item){
        TreeItem<TreeContextItem> parent = item.getParent();
        parent.getChildren().remove(item);
        switch (item.getValue().getType()){
            case AREA: parent.getChildren().add(getArea((Area)item.getValue().getItem())); break;
            case PROJECT: parent.getChildren().add(getProject((Project)item.getValue().getItem())); break;
            case SUBPROJECT: parent.getChildren().add(getSubProject((SubProject)item.getValue().getItem())); break;
            case TASK: parent.getChildren().add(new TreeItem<>(new TreeContextItem((Task)item.getValue().getItem()))); break;
        }
    }


    public static TreeItem<TreeContextItem> getTreeViewItem(TreeItem<TreeContextItem> item , Object value)
    {
        if (item != null && item.getValue().equals(value))
            return  item;

        for (TreeItem<TreeContextItem> child : item.getChildren()){
            TreeItem<TreeContextItem> s=getTreeViewItem(child, value);
            if(s!=null)
                return s;

        }
        return null;
    }


    public TreeItem<TreeContextItem> getTree() {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(this.root));
        rootItem.setExpanded(true);

        this.root.addObserver(observer);


        this.root.getAreas(list -> {
            for (Area area : list) {
                TreeItem<TreeContextItem> item = getArea(area);
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> getArea(Area area) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<>(new TreeContextItem(area));
        rootItem.setExpanded(true);
        area.addObserver(observer);


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
        project.addObserver(observer);

        project.getSubProjects(subProjects -> {
            for (SubProject subProject : subProjects) {
                TreeItem item = getSubProject(subProject);
                rootItem.getChildren().add(item);
            }
        });

        project.getTasks(tasks -> {
            for (Task task : tasks) {
                TreeItem<TreeContextItem> item = new TreeItem<>(new TreeContextItem(task));
                task.addObserver(observer);
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }

    private TreeItem<TreeContextItem> getSubProject(SubProject subProject) {
        TreeItem<TreeContextItem> rootItem = new TreeItem<TreeContextItem>(new TreeContextItem(subProject));
        rootItem.setExpanded(true);
        subProject.addObserver(observer);

        subProject.getTasks(tasks -> {
            for (Task task : tasks) {
                TreeItem<TreeContextItem> item = new TreeItem<TreeContextItem>(new TreeContextItem(task));
                task.addObserver(observer);
                rootItem.getChildren().add(item);
            }
        });

        return rootItem;
    }
}
