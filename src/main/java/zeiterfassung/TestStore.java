package zeiterfassung;

import zeiterfassung.models.*;

public class TestStore extends DataStore {
    private TimeRegistrationRoot root;

    public TestStore() {
        root = new TimeRegistrationRoot();

        Area area = new Area();
        area.setName("Privat");
        area.setDescription("Hier findet man alle meine privaten Projekte");
        root.addArea(area);

        Project project = new Project();
        project.setName("Garten");
        project.setDescription("Dieses Projekt ist für meine Garten Aufgaben");
        area.addProject(project);

        SubProject subProject = new SubProject();
        subProject.setName("Baum Pflege");
        project.addSubProject(subProject);

        Task task = new Task();
        task.setName("Äpfel plücken");
        task.setDescription("Nur die schönen dicken Äpfel dürfen gepflückt werden.");
        subProject.addTask(task);

        SubProject subProject1 = new SubProject();
        subProject1.setName("Hühner");
        project.addSubProject(subProject1);

        Task task1 = new Task();
        task1.setName("Stall ausmisten");
        subProject.addTask(task1);

        Task task2 = new Task();
        task2.setName("Eier sammeln");
        subProject.addTask(task2);

        Project project1 = new Project();
        project1.setName("FH");
        project1.setDescription("Dieses Projekt ist für meine FH Aufgaben");
        area.addProject(project1);
    }

    @Override
    public TimeRegistrationRoot getRoot() {
        return root;
    }
}
