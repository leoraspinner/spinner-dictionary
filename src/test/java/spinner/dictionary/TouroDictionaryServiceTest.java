package spinner.dictionary;

import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TouroDictionaryServiceTest {
    private TouroDictionaryService service;
    private final String lambdaUrl = "https://3bfbhimanmuhzbmmmdjkhovsa40osneh.lambda-url.us-east-2.on.aws/";

    @Test
    public void lookupValidWord() throws IOException {
        TouroDictionaryServiceFactory factory = new TouroDictionaryServiceFactory();
        service = factory.getService(lambdaUrl);

        //Given - a word exits
        DictionaryRequest request = new DictionaryRequest("DAYSTAR");

        //When - looking up the word
        DictionaryResponse response = service.lookup(request).blockingGet();

        //Then - get response with definition
        assertNotNull(response);
        assertEquals("DAYSTAR", response.getWord());
        assertNotNull(response.getDefinition());
    }

    @Test
    public void lookupInvalidWord() throws IOException {
        TouroDictionaryServiceFactory factory = new TouroDictionaryServiceFactory();
        service = factory.getService(lambdaUrl);

        // Given - a word that doesn't exist in the dictionary
        DictionaryRequest request = new DictionaryRequest("XYZABC");

        // When - look up the word
        DictionaryResponse response = service.lookup(request).blockingGet();

        // Then - should get a successful response with null definition
        assertNotNull(response);
        assertEquals("XYZABC", response.getWord());
        assertNull(response.getDefinition());
    }

    @Test
    public void lookupCaseInsensitive() throws IOException {
        TouroDictionaryServiceFactory factory = new TouroDictionaryServiceFactory();
        service = factory.getService(lambdaUrl);

        // Given - a lowercase word of word that exists
        DictionaryRequest request = new DictionaryRequest("daystar");

        // When - look up the word
        DictionaryResponse response = service.lookup(request).blockingGet();

        // Then - should still get the definition
        assertNotNull(response);
        assertNotNull(response.getDefinition());
    }
}
