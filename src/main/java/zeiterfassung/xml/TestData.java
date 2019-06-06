package zeiterfassung.xml;

import zeiterfassung.models.*;

import java.time.LocalDateTime;

/**
 * Test data generator
 */
public class TestData {

    /**
     * Get some sample data
     *
     * @return Sample {@link TimeRegistrationRoot}
     */
    public static TimeRegistrationRoot getData() {
        TimeRegistrationRoot root = new TimeRegistrationRoot();

        Area area = new Area("Privat", "Hier findet man alle meine privaten Projekte");
        root.addArea(area);

        Project project = new Project("Garten", "Dieses Projekt ist für meine Garten Aufgaben");
        project.addRole(new Role("Angestellter", "Sklave", 5.8));
        project.addRole(new Role("Chöf", "Benz", 17.3));

        area.addProject(project);

        SubProject subProject = new SubProject("Baum Pflege", "");
        project.addSubProject(subProject);

        Task task = new Task("Äpfel plücken", "Nur die schönen dicken Äpfel dürfen gepflückt werden.");
        task.addWorkChunk(new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(4), "Toll"));
        task.addWorkChunk(new WorkChunk(LocalDateTime.now(), null, "1"));
        subProject.addTask(task);

        SubProject subProject1 = new SubProject("Hühner", "");
        project.addSubProject(subProject1);

        Task task1 = new Task("Stall ausmisten", "");
        subProject1.addTask(task1);

        Task task2 = new Task("Eier sammeln", "");
        subProject1.addTask(task2);

        Area areaFH = new Area("FH", "Hier findet man FH Kram");

        Project aem = new Project("Agile Entwicklungsmethoden", "Modul AEM im SS2019");
        areaFH.addProject(aem);

        Project seg = new Project("Software Engineering", "Modul SEG im SS2019");
        areaFH.addProject(seg);

        Project pij = new Project("Programmieren in Java", "Modul PIJ im SS2019");
        pij.addRole(new Role("Hiwi", "Der kleine Hilfsarbeiter", 11.2));
        areaFH.addProject(pij);

        SubProject sprint4 = new SubProject("4. Sprint", "Unser 4. Sprint");
        aem.addSubProject(sprint4);

        SubProject anaphase = new SubProject("Analysephase", "Anforderungsanalyse und Lastenheft");
        seg.addSubProject(anaphase);


        SubProject sprint2 = new SubProject("2. Sprint", "Unser 2. einwöchiger Sprint");
        pij.addSubProject(sprint2);

        Task xml = new Task("XML Import", "XML Datei erzeugen und auslesen", new Role("Student", "Ein ganz normaler Student ohne besondere Qualifikationen", 8.5));
        xml.addWorkChunk(new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(4), "Meine Beispielarbeit"));
        sprint2.addTask(xml);

        root.addArea(areaFH);

        return root;
    }
}
