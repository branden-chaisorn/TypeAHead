package brandenc.com.typeahead.Models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class BaseResponse implements Parcelable {

    @SerializedName("events")
    public abstract List<Event> events();

    static BaseResponse create(List<Event> events) {
        return new AutoValue_BaseResponse(events);
}

    public static TypeAdapter<BaseResponse> typeAdapter(Gson gson) {
        return new AutoValue_BaseResponse.GsonTypeAdapter(gson);
    }

}
