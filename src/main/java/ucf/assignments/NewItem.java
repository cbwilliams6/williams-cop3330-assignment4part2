/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Christian Williams
 */

package ucf.assignments;

import java.io.IOException;
import java.time.LocalDate;

public class NewItem {
    public static Item newItem(String descText, LocalDate dateValue, String completionText) throws IOException {
        Item newItem = null;
        // checking if any of the 3 entry parts are empty
        if (descText.isEmpty() || dateValue == null || completionText.isEmpty()) {
            System.out.println("Didn't add item because something was left empty");
        }
        else {
            // creating a new item with all the entered data
            newItem = new Item(descText, dateValue, completionText);
        }
        return newItem;
    }
}