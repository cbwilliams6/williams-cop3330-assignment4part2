/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Christian Williams
 */

/* Note: the methods for editing and removing items, as well as the different showing options aren't in separate classes as it's really just not needed
*  it's a bit messier like this, but they all manipulate the table and fields in pretty much every line, so different classes just isn't practical */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    @FXML private TableView<Item> todo_tableView;
    @FXML private TableColumn<Item, String> todo_tableDescription;
    @FXML private TableColumn<Item, LocalDate> todo_tableDate;
    @FXML private TableColumn<Item, String> todo_tableCompletion;
    @FXML private TextField descriptionField;
    @FXML private DatePicker dateField;
    @FXML private ChoiceBox completionField;

    public void fillTable() throws FileNotFoundException {
        // setting up a scanner so we can read through the .txt file
        Scanner itemScanner = new Scanner(new File(".\\src\\main\\java\\ucf\\assignments\\List.txt"));

        // adding every item to the tableview
        while (itemScanner.hasNextLine()) {
            todo_tableView.getItems().add(FillTable.fillTable(itemScanner));
        }

        itemScanner.close();
    }

    public void newItem() throws IOException {
        String descText = descriptionField.getText();
        LocalDate dateValue = dateField.getValue();
        String completionText = (String) completionField.getSelectionModel().getSelectedItem();

        todo_tableView.getItems().add(NewItem.newItem(descText, dateValue, completionText));

        itemWriter();
    }

    public void editItem() throws IOException {
        // checking if any of the 3 entry parts are empty
        if (descriptionField.getText().isEmpty() || dateField.getValue() == null || completionField.getValue() == null) {
            System.out.println("Didn't edit item because something was left empty");
        }
        else {
            Item oldItem = todo_tableView.getSelectionModel().getSelectedItem();
            int oldIndex = todo_tableView.getSelectionModel().getSelectedIndex();
            Item newItem = new Item(descriptionField.getText(), dateField.getValue(), (String) completionField.getSelectionModel().getSelectedItem());

            todo_tableView.getItems().add(oldIndex, newItem);
            todo_tableView.getItems().remove(oldItem);

            itemWriter();
        }
    }

    public void removeItem() throws IOException {
        // getting the name and index of the item selected
        Item itemSelected = todo_tableView.getSelectionModel().getSelectedItem();
        int itemIndex = todo_tableView.getSelectionModel().getSelectedIndex();

        // removing the item from the table
        todo_tableView.getItems().remove(itemSelected);

        // checking if an item is actually selected before removing it from the .txt file
        if (itemIndex >= 0) {
            itemWriter();
        }
    }

    public void removeAll() throws IOException {
        for (int i = todo_tableView.getItems().size() - 1; i >= 0; i--) {
            todo_tableView.getItems().remove(i);
        }

        itemWriter();
    }

    public void showAll() throws FileNotFoundException {
        // clears the table before filling it all back in again, so nothing dupes
        todo_tableView.getItems().clear();
        fillTable();
    }

    public void showIncomplete() throws FileNotFoundException {
        // refreshing the table, cause when going from incomplete -> complete or vice versa it would dupe items
        showAll();

        /* looping through the entire table, but from bottom item up
           when I tried a regular forward loop, it would crash since the indexes would change and it'd try to find something that didn't exist anymore */
        for (int i = todo_tableView.getItems().size() - 1; i >= 0; i--) {
            // getting the item being looped through
            Item item = todo_tableView.getItems().get(i);
            // then checking if the completion status is true or false
            if (item.getCompletion().equals("true")) {
                // if true, then we remove it since we only want to see incomplete ones
                todo_tableView.getItems().remove(item);
            }
        }
    }

    public void showComplete() throws FileNotFoundException {
        // everything here is the same as showing incomplete, but obviously checking if an item is incomplete instead, so we can remove it
        showAll();

        for (int i = todo_tableView.getItems().size() - 1; i >= 0; i--) {
            Item item = todo_tableView.getItems().get(i);
            if (item.getCompletion().equals("false")) {
                todo_tableView.getItems().remove(item);
            }
        }
    }

    public void itemWriter() throws IOException {
        List <List<String>> itemList = new ArrayList<>();

        // just looping through the whole table
        for (int i = 0; i < todo_tableView.getItems().size(); i++) {
            // creating an item for each time we loop through
            Item item = todo_tableView.getItems().get(i);
            /* adding an arraylist to the overall list, so we'll have a list with a bunch of arraylists by the end
               each arraylist will then have all the parameters for a single item / row */
            itemList.add(new ArrayList<>());
            // adding each parameter to the arraylist and separating by ", "
            itemList.get(i).add(item.getDescription());
            itemList.get(i).add(", " + item.getDueDate());
            itemList.get(i).add(", " + item.getCompletion());
        }

        ItemWriter.itemWriter(itemList);
    }

    public void saveList() throws IOException {
        SaveList.saveList();
    }

    public void loadLists() throws IOException {
        LoadList.loadLists();

        todo_tableView.getItems().clear();
        fillTable();
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        // filling in the table on startup
        fillTable();

        // setting up the table columns to take certain parts of an item
        todo_tableDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        todo_tableCompletion.setCellValueFactory(new PropertyValueFactory<Item, String>("completion"));
        todo_tableDate.setCellValueFactory(new PropertyValueFactory<Item, LocalDate>("dueDate"));

        // setting the choicebox options to be either false or true
        ObservableList<String> completionChoices = FXCollections.observableArrayList("false", "true");
        completionField.setItems(completionChoices);
    }

    @FXML
    public void clickedItem(MouseEvent clicked)
    {
        int itemIndex = todo_tableView.getSelectionModel().getSelectedIndex();

        // checking if a user clicks an item just once, then changes the bottom fields to whatever that item is
        if (clicked.getClickCount() == 1) {
            if (itemIndex >= 0) {
                descriptionField.setText(todo_tableView.getSelectionModel().getSelectedItem().getDescription());
                dateField.setValue(todo_tableView.getSelectionModel().getSelectedItem().getDueDate());
                completionField.setValue(todo_tableView.getSelectionModel().getSelectedItem().getCompletion());
            }
        }
        // clears all the fields if the user double clicks
        else if (clicked.getClickCount() == 2) {
            todo_tableView.getSelectionModel().clearSelection();
            descriptionField.setText(null);
            dateField.setValue(null);
            completionField.setValue(null);
        }
    }
}