package zeiterfassung;
import zeiterfassung.models.Area;
import zeiterfassung.models.Project;
import zeiterfassung.models.SubProject;
import zeiterfassung.models.TimeRegistrationRoot;

import javax.xml.bind.*;
import java.io.File;

public class DataStore {
    public DataStore() {
        // TODO: autoload?
    }

    public void load() throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(TimeRegistrationRoot.class);
        Unmarshaller jaxbUnmarshaller = jbc.createUnmarshaller();
        TimeRegistrationRoot root = (TimeRegistrationRoot) jaxbUnmarshaller.unmarshal(new File("Save.xml"));
        // TODO: various .set(Variable) calls
    }

    public void unload() throws JAXBException{
        JAXBContext jbc = JAXBContext.newInstance(TimeRegistrationRoot.class, Area.class, Project.class, SubProject.class);
        Marshaller jaxbMarshaller = jbc.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //Testing Marshaller
        TimeRegistrationRoot timeRegistrationRoot = new TimeRegistrationRoot();
        Area area = new Area("My custom Area", "My custom Area descr");
        Project project = new Project("First Project", "First descr");
        Project project1 = new Project("Second Project", "Second descr");
        SubProject subProject1 = new SubProject();
        project.addSubProject(subProject1);
        project1.addSubProject(subProject1);
        area.addProject(project);
        area.addProject(project1);
        timeRegistrationRoot.addArea(area);
        jaxbMarshaller.marshal(timeRegistrationRoot, new File("Save.xml"));
    }
}
