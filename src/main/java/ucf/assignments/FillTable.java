package ucf.assignments;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class FillTable {
    public static Item fillTable(Scanner itemScanner) throws FileNotFoundException {
            // setting up an array with a split of ", " since that's just how I formatted the data
            String[] data = itemScanner.nextLine().split(", ");
            // changing the data for the date from a string to a LocalDate type
            LocalDate dueDate = LocalDate.parse(data[1]);
            // creating an item with the extracted data
            Item addedItem = new Item(data[0], dueDate, data[2]);

            return addedItem;
    }
}