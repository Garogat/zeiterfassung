package zeiterfassung.xml;

import zeiterfassung.models.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class DataStore {
    public static final String XMLFilePath = "ZeitErfassung.xml";
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
        } catch(JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
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

    public void saveToXML(File file) throws JAXBException {
        Marshaller jaxbMarshaller = getXMlContext().createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");


        jaxbMarshaller.marshal(root, file);
    }

    /**
     *
     * @param file
     * @return Root of our Business Application Logic
     * @throws JAXBException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private TimeRegistrationRoot loadFromXML(File file) throws Exception {
        Unmarshaller jaxbUnmarshaller = getXMlContext().createUnmarshaller();
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");

        return (TimeRegistrationRoot) jaxbUnmarshaller.unmarshal(inputStream);
    }

    private JAXBContext getXMlContext() throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(TimeRegistrationRoot.class, Area.class, Project.class, SubProject.class, Task.class, WorkChunk.class);
        return jbc;
    }
}
