package zeiterfassung;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ZeitErfassung {
    private Stage stage;
    private DataStore store;

    public ZeitErfassung(Stage stage) throws Exception {
        this.stage = stage;

        // init data store
        this.store = new DataStore();
        this.store.load();

        // load scene
        Parent page = FXMLLoader.load(getClass().getResource("/zeiterfassung/views/Base.fxml"));

        stage.setScene(new Scene(page));
        stage.setTitle("Zeiterfassung");
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * stop ZeitErfassung (unload data store)
     */
    public void stop() {
        store.unload();
    }
}
