package Model.Logic;

import java.io.BufferedReader;
import java.io.FileReader;


public class IOSearcher {
    public static boolean search(String word, String... fileNames) throws Exception {
        for (String fileName : fileNames) {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(word)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        }
        return false;
    }

}