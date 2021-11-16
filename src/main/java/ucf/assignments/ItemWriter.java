package ucf.assignments;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ItemWriter {
    public static void itemWriter(List<List<String>> itemList) throws IOException {
        FileWriter writer = new FileWriter(".\\src\\main\\java\\ucf\\assignments\\List.txt");

        // looping through the entire list, so we can loop through every arraylist
        for (int j = 0; j < itemList.size(); j++) {
            // then looping through a single arraylist to write its data
            for (int k = 0; k < itemList.get(j).size(); k++) {
                // writing every parameter to the .txt file, with separations
                writer.write(itemList.get(j).get(k));
            }
            // checking if we're on the 2nd to last arraylist
            if (j == itemList.size() - 1)
                continue;
            else
                // printing a linebreak if not, since we don't want an extra linebreak at the very end
                writer.write("\n");
        }

        writer.close();
    }
}
