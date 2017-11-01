package brandenc.com.typeahead.Models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Performer implements Parcelable {

    public abstract String name();

    @Nullable
    public abstract String image();

    public static Performer create(String name, String image) {
        return new AutoValue_Performer(name, image);
    }

    public static TypeAdapter<Performer> typeAdapter(Gson gson) {
        return new AutoValue_Performer.GsonTypeAdapter(gson);
    }

}
