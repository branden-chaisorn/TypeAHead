package brandenc.com.typeahead;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class GsonAdapterFactory implements TypeAdapterFactory {

    // Generates TypeAdapterFactory that allows for each type adapter to be unified under factory
    public static TypeAdapterFactory create() {
            return new AutoValueGson_GsonAdapterFactory();
    }

}
