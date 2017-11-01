package brandenc.com.typeahead;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import brandenc.com.typeahead.Models.BaseResponse;
import brandenc.com.typeahead.Network.SearchApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private static final String BASE_URL = "https://api.seatgeek.com/2/";
    private final Context applicationContext;

    public ApplicationModule(Application application) {
        applicationContext = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return applicationContext;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .registerTypeAdapterFactory(GsonAdapterFactory.create())
                .create();
    }

    @Provides
    @Singleton
    SearchApi provideSearchApi(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(SearchApi.class);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("favoritedEvents", context.MODE_PRIVATE);
    }
}