/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Christian Williams
 */

package ucf.assignments;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LoadList {
    public static void loadLists() throws IOException {
        // the user is prompted to choose their external list file
        FileChooser fileChooser = new FileChooser();
        // the file selection window will only show .txt files, which is what the list is saved as
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);

        // checking that the user actually selected a file and it's a list file
        if (selectedFile != null && selectedFile.getName().equals("List.txt")) {
            // setting the directory to be the existing list
            String directory = ".\\src\\main\\java\\ucf\\assignments\\List.txt";

            // setting File variable to whatever directory the file being loaded is in right now
            File oldFile = new File(selectedFile.getAbsolutePath());
            // another File variable that just points to the lists folder
            File newFile = new File(".\\src\\main\\java\\ucf\\assignments\\List.txt");

            if (newFile.exists()) {
                // deleting the existing list and replacing it with the list being loaded in
                newFile.delete();
                Files.copy(oldFile.toPath(), newFile.toPath());
            }
            else
                System.out.print("Something is wrong");
        }
        else {
            System.out.println("Something went wrong");
        }
    }
}
