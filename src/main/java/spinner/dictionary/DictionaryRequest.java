package spinner.dictionary;
/* This class holds the data from the incoming JSON request (incoming) */

public class DictionaryRequest {
    private String word;

    public DictionaryRequest(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
