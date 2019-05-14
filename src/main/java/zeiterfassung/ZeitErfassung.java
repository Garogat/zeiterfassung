package zeiterfassung;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import zeiterfassung.controllers.Base;
import zeiterfassung.xml.DataStore;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class ZeitErfassung {
    private Stage stage;
    private DataStore store;

    public ZeitErfassung(Stage stage) throws JAXBException {
        this.stage = stage;

        // init data store
        this.store = new TestStore();
        // this.store = new DataStore(); // production store with xml backend
        this.store.load();

        // load scene
        Parent page = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/zeiterfassung/views/Base.fxml"));
        try {
            page = loader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }

        Base baseController = loader.getController();
        baseController.setDataStore(this.store);

        stage.setScene(new Scene(page));
        stage.setTitle("Zeiterfassung");
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * stop ZeitErfassung (unload data initstore)
     */
    public void stop() throws JAXBException{
        if (this.store != null) {
            this.store.unload();
        }
    }
}
