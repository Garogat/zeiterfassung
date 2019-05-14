package zeiterfassung;

import zeiterfassung.models.*;
import zeiterfassung.xml.DataStore;

public class TestStore extends DataStore {
    @Override
    public TimeRegistrationRoot getRoot() {
        TimeRegistrationRoot root = new TimeRegistrationRoot();

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

        return root;
    }
}
