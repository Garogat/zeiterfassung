package zeiterfassung;

import zeiterfassung.models.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class DataStore {
    private TimeRegistrationRoot root;

    public DataStore() {
        // TODO: autoload?
    }

    public void load() {
        // TODO: load data from xml
        // root = ...
    }

    public void unload() throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(TimeRegistrationRoot.class, Area.class, Project.class, SubProject.class);
        Marshaller jaxbMarshaller = jbc.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        TimeRegistrationRoot root = getTestRoot();

        jaxbMarshaller.marshal(root, new File("Save.xml"));
    }

    public TimeRegistrationRoot getRoot() {
        return root;
    }

    /**
     * For use as test data for marshalling
     * @return some test Data (Area, Project, SubProject, Tasks)
     */
    public TimeRegistrationRoot getTestRoot(){
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

        Area areaFH = new Area("FH", "Hier findet man FH Kram");
        Project aem = new Project("Agile Entwicklungsmethoden", "Modul AEM im SS2019");
        Project seg = new Project("Software Engineering", "Modul SEG im SS2019");
        SubProject sprint4 = new SubProject("4. Sprint", "Unser 4. Sprint");
        SubProject anaphase = new SubProject("Analysephase", "Anforderungsanalyse und Lastenheft");
        aem.addSubProject(sprint4);
        seg.addSubProject(anaphase);
        areaFH.addProject(aem);
        areaFH.addProject(seg);
        root.addArea(areaFH);

        return root;
    }
}
