package spinner.dictionary;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TouroDictionaryService {
    @POST("/")
    Call<DictionaryResponse> lookup(@Body DictionaryRequest request);
}
