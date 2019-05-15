package zeiterfassung.xml;

import zeiterfassung.TestData;
import zeiterfassung.models.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class DataStore {
    private static final String XMLFilePath = "ZeitErfassung.xml";
    private TimeRegistrationRoot root;

    public void load() {
        File file = new File(XMLFilePath);

        // load some sample data if no DataStore exists
        if (!file.exists()) {
            root = TestData.getData();
            return;
        }

        try {
            root = loadFromXML(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        System.out.println("XML DataStore successfully loaded");
    }

    public void unload() {
        try {
            saveToXML(new File(XMLFilePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public TimeRegistrationRoot getRoot() {
        return root;
    }

    private TimeRegistrationRoot loadFromXML(File file) throws JAXBException {
        Unmarshaller jaxbUnmarshaller = getXMlContext().createUnmarshaller();
        TimeRegistrationRoot root = (TimeRegistrationRoot) jaxbUnmarshaller.unmarshal(file);

        // TODO: various .set(Variable) calls
        return root;
    }

    private void saveToXML(File file) throws JAXBException {
        Marshaller jaxbMarshaller = getXMlContext().createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(root, file);
    }

    private JAXBContext getXMlContext() throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(TimeRegistrationRoot.class, Area.class, Project.class, SubProject.class, Task.class, WorkChunk.class);
        return jbc;
    }
}
