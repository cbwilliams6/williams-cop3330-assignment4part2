/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Christian Williams
 */

/* BIG NOTE: I totally misread the part 1 assignment and ended up doing just about the whole assignment back when that was still ongoing
*  as a result a lot of the code here is gonna be similar, though modified for the new requirements */

package ucf.assignments;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage mainStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("appTodo.fxml")); // tries to find and load the main app.fxml file

        // these next lines load the main scene, which contains a single list
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
}