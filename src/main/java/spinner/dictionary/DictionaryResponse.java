package spinner.dictionary;
/*holds the data that gets sent back to the user (outgoing)*/

public class DictionaryResponse {
    private String word;
    private String definition;

    public DictionaryResponse(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
