/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Christian Williams
 */

package ucf.assignments;

import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SaveList {
    public static void saveList() throws IOException {
        // prompting the user to choose a directory to save the file
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        // checking that the user selected a directory properly
        if (selectedDirectory != null ) {
            // creating a File variable for the file that already exists in the ucf.assignments directory
            File oldFile = new File(".\\src\\main\\java\\ucf\\assignments\\List.txt");
            // another File variable for the location where the new file will be copied and saved to
            File newFile = new File(selectedDirectory.getAbsolutePath() + "\\List.txt");

            // checking if a list already exists in the new directory
            if (newFile.exists()) {
                System.out.println("Didn't create duplicate file");
            }
            else {
                // copying the file to wherever the user wants it to be saved to
                Files.copy(oldFile.toPath(), newFile.toPath());
            }
        }
        else {
            System.out.println("Something went wrong");
        }
    }
}
