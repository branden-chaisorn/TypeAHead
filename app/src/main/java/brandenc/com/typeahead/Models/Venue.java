package brandenc.com.typeahead.Models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Venue implements Parcelable{

    @SerializedName("display_location")
    public abstract String displayLocation();

    static Venue create(String displayLocation) {
        return new AutoValue_Venue(displayLocation);
    }

    public static TypeAdapter<Venue> typeAdapter(Gson gson) {
        return new AutoValue_Venue.GsonTypeAdapter(gson);
    }

}