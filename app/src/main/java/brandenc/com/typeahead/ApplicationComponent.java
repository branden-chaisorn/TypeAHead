package brandenc.com.typeahead;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules=ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
    Retrofit retrofit();
    SharedPreferences sharedPreferences();
    Gson gson();

    void inject(MainActivity mainActivity);
    void inject(EventDetailActivity detailActivity);
}
