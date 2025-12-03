package spinner.dictionary;

import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TouroDictionaryServiceTest {
    private TouroDictionaryService service;
    private final String LAMBDA_URL = "https://3bfbhimanmuhzbmmmdjkhovsa40osneh.lambda-url.us-east-2.on.aws/";

    @Test
    public void testLookupValidWord () throws IOException {
        TouroDictionaryServiceFactory factory = new TouroDictionaryServiceFactory();
        service = factory.getService(LAMBDA_URL);

        //Given - a word exits
        DictionaryRequest request = new DictionaryRequest("DAYSTAR");

        //When - looking up the word
        Call<DictionaryResponse> call = service.lookup(request);
        Response<DictionaryResponse> response = call.execute();

        //Then - get response with definition
        assertTrue(response.isSuccessful());
        assertNotNull(response.body());
        assertEquals("DAYSTAR", response.body().getWord());
        assertNotNull(response.body().getDefinition());

    }

    @Test
    public void testLookupInvalidWord() throws IOException {
        TouroDictionaryServiceFactory factory = new TouroDictionaryServiceFactory();
        service = factory.getService(LAMBDA_URL);

        // Given - a word that doesn't exist in the dictionary
        DictionaryRequest request = new DictionaryRequest("XYZABC");

        // When - look up the word
        Call<DictionaryResponse> call = service.lookup(request);
        Response<DictionaryResponse> response = call.execute();

        // Then - should get a successful response with null definition
        assertTrue(response.isSuccessful());
        assertNotNull(response.body());
        assertEquals("XYZABC", response.body().getWord());
        assertNull(response.body().getDefinition());
    }

    @Test
    public void testLookupCaseInsensitive() throws IOException {
        TouroDictionaryServiceFactory factory = new TouroDictionaryServiceFactory();
        service = factory.getService(LAMBDA_URL);

        // Given - a lowercase word of word that exists
        DictionaryRequest request = new DictionaryRequest("daystar");

        // When - look up the word
        Call<DictionaryResponse> call = service.lookup(request);
        Response<DictionaryResponse> response = call.execute();

        // Then - should still get the definition
        assertTrue(response.isSuccessful());
        assertNotNull(response.body());
        assertNotNull(response.body().getDefinition());
    }
}
