package zeiterfassung;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private ZeitErfassung tool;

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
            tool.stop();
        }

        super.stop();
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
