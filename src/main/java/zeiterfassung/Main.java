package zeiterfassung;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static ZeitErfassung tool;

    /**
     * load ZeitErfassung with {@code stage} and start application
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        tool = new ZeitErfassung(stage);
    }


    /**
     * unload ZeitErfassung (data store) before closing application
     *
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        if (tool != null) {
            tool.save();
            tool.stop();
        }

        super.stop();
    }

    /**
     * restart application
     */
    public static void restart() {
        if (tool != null) {
            tool.stop();
        }

        tool = new ZeitErfassung(new Stage());
    }

    /**
     * main start method of java
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
