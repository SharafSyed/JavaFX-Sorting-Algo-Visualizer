package se2203b.assignment1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class SortingHubApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SortingHubApplication.class.getResource("SortingHub-view.fxml"));
        //removed pre-assigned dimensions of windows to instead use preferred dimensions established in GUI
        Scene scene = new Scene(fxmlLoader.load());
        //establish title of window
        stage.setTitle("Sorting Hub");
        stage.setScene(scene);
        //add icon of Western Logo
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/se2203b/assignment1/WesternLogo.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        System.out.println("helo world");
    }

}