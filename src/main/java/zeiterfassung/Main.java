package zeiterfassung;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

        // only a test
        // (new Tester()).mytest();
    }
    static class Tester {
        void mytest() {
            File f = null;
            String file_name = "/zeiterfassung/views/Base.fxml";
            try {
                f = new File(getClass().getResource(file_name).toURI());
                //f = new File("/home/r0hot/test/getName.py");

            } catch(Exception e) {
                System.out.println("error with " + file_name);
            }

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch(Exception e) {
                System.out.println("error 2");

            }
        }
    }
}
