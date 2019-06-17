package zeiterfassung;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import zeiterfassung.controllers.BaseController;
import zeiterfassung.controllers.TaskController;
import zeiterfassung.models.Task;
import zeiterfassung.models.TimeRegistrationRoot;
import zeiterfassung.xml.DataStore;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * The base class that contains the hole program instances
 */
public class ZeitErfassung {
    private Stage stage;
    private DataStore store;

    /**
     * Constructor to create a new tool instace
     *
     * @param stage stage to
     */
    public ZeitErfassung(Stage stage) {
        this.stage = stage;

        // init data store
        this.store = new DataStore();
        this.store.load();

        // load scene
        Parent page = null;
        FXMLLoader loader = new FXMLLoader(Charset.forName("UTF-8"));
        loader.setLocation(getClass().getResource("/zeiterfassung/views/Base.fxml"));

        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BaseController baseController = loader.getController();
        baseController.setDataStore(this.store);

        Scene scene = new Scene(page);
        scene.getStylesheets().add(getClass().getResource("/zeiterfassung/styles/main.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Zeiterfassung");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/clipboard.png")));
        stage.setMaximized(true);
        stage.show();

        baseController.setActiveTask();
    }

    /**
     * stop ZeitErfassung
     */
    public void stop() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * save currently loaded data store
     */
    public void save() {
        if (this.store != null) {
            TimeRegistrationRoot root = store.getRoot();
            if(root.isTaskActive()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aktiver Task");
                Task task = root.getActiveTask();
                alert.setHeaderText("Soll der aktive Task ("+ task.getName()+") beendet werden?\n"
                    + "\n\n(aktive Tasks werden auch gemessen, wenn das Programm aus ist)");
                ButtonType button_ja = new ButtonType("JA");
                ButtonType button_nein = new ButtonType("NEIN");
                alert.getButtonTypes().setAll(button_ja, button_nein);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == button_ja){
                    task.stop();
                }
            }
        }
        this.store.unload();

    }
}
