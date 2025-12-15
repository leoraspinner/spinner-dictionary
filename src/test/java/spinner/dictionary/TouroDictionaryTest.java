package spinner.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TouroDictionaryTest {
    private TouroDictionary dictionary;

    @Test
    public void lookUpWord() {
        TouroDictionary dictionary = new TouroDictionary();

        // Given
        String word = "AA";
        String expected = "rough, cindery lava [n -S]";

        // When
        String result = dictionary.lookup(word);

        // Then
        assertEquals(expected, result);
    }

}