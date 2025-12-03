package spinner.dictionary;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TouroDictionaryServiceFactory {
    public TouroDictionaryService getService(String lambdaUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(lambdaUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TouroDictionaryService.class);
    }
}
