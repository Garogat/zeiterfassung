package zeiterfassung.xml;

import zeiterfassung.models.TimeRegistrationRoot;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class DataStore {
    /**
     * Path to main xml file (database)
     */
    public static final String XMLFilePath = "ZeitErfassung.xml";
    private TimeRegistrationRoot root;

    /**
     * Load database from xml file
     */
    public void load() {
        File file = new File(XMLFilePath);

        // load some sample data if no DataStore exists
        if (!file.exists()) {
            root = TestData.getData();
            return;
        }

        try {
            root = loadFromXML(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("XML DataStore successfully loaded");
    }

    /**
     * Save database to xml file
     */
    public void unload() {
        try {
            saveToXML(new File(XMLFilePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get root model of database
     *
     * @return root model
     */
    public TimeRegistrationRoot getRoot() {
        return root;
    }

    /**
     * Save database to xml file
     *
     * @param file database file (.xml)
     * @throws JAXBException
     */
    public void saveToXML(File file) throws JAXBException {
        Marshaller jaxbMarshaller = getXMlContext().createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        jaxbMarshaller.marshal(root, file);
    }

    private TimeRegistrationRoot loadFromXML(File file) throws JAXBException, UnsupportedEncodingException, FileNotFoundException {
        Unmarshaller jaxbUnmarshaller = getXMlContext().createUnmarshaller();
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");

        return (TimeRegistrationRoot) jaxbUnmarshaller.unmarshal(reader);
    }

    private JAXBContext getXMlContext() throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(TimeRegistrationRoot.class);
        return jbc;
    }
}
