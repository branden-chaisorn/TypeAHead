package brandenc.com.typeahead.Models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@AutoValue
public abstract class Event implements Parcelable {

    public abstract String id();

    abstract String url();

    public abstract List<Performer> performers();

    public abstract Venue venue();

    @SerializedName("datetime_local")
    public abstract Date eventDate();

    static Event create(String id, String url, List<Performer> performer, Venue venue, Date eventDate) {
        return new AutoValue_Event(id, url, performer, venue, eventDate);
    }

    public static TypeAdapter<Event> typeAdapter(Gson gson) {
        return new AutoValue_Event.GsonTypeAdapter(gson);
    }

}
