package spinner.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class TouroDictionary {
    private HashMap<String, String> dictionary;

    public TouroDictionary() {
        dictionary = new HashMap<>();
        loadDictionary();
    }

    public TouroDictionary(InputStream touroInputStream) throws IOException {
        dictionary = new HashMap<>();
        loadDictionary();
    }

    private void loadDictionary()
    {
        InputStream dictionaryFile = TouroDictionary.class.getResourceAsStream("/dictionary.txt");

        if (dictionaryFile == null) {
            System.err.println("file not found");
            return;
        }

        loadDictionary(dictionaryFile);
    }

    private void loadDictionary(InputStream dictionaryFile) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dictionaryFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                int spaceIndex = line.indexOf(' ');
                if (spaceIndex > 0) {
                    String word = line.substring(0, spaceIndex);
                    String definition = line.substring(spaceIndex + 1);
                    dictionary.put(word, definition);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String lookup(String word) {
        String searchWord = word.toUpperCase();
        return dictionary.get(searchWord);
    }
}
