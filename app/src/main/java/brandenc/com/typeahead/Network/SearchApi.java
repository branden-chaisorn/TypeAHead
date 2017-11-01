package brandenc.com.typeahead.Network;

import brandenc.com.typeahead.Models.BaseResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchApi {

    String clientSecret = "4597a14b1d2e34d86b099ea4b3c4c3dd7b95a1d985b72887cacce9fc1efb1cd2";
    String clientId = "OTQwMDE0NnwxNTA4OTY2NTA2LjQ0";

    @GET("events?client_id=" + clientId + "&client_secret=" + clientSecret)
    Call<BaseResponse> getEventData(@Query("q") String query);

}
