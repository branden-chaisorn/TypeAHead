package brandenc.com.typeahead;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import brandenc.com.typeahead.Models.BaseResponse;
import brandenc.com.typeahead.Models.Event;
import brandenc.com.typeahead.Network.SearchApi;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    Retrofit retrofit;

    @BindView(R.id.event_list_view)
    RecyclerView eventListView;

    @BindView(R.id.plain_text_input)
    SearchView eventSearchEditText;

    LinearLayoutManager layoutManager;

    private List<Event> events = new ArrayList<>();

    EventAdapter eventAdapter;

    private SearchApi apiService;

    private BaseResponse savedResponse;

    private SearchView.OnQueryTextListener eventTextWatcher = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Call<BaseResponse> call = apiService.getEventData(newText.toString());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    BaseResponse baseResponse = response.body();
                    events.clear();
                    events.addAll(baseResponse.events());
                    eventAdapter.notifyDataSetChanged();
                    savedResponse = baseResponse;
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Log.e(TAG, "Error when retrieving event: " + t.getLocalizedMessage());
                }
            });
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        (((MainApplication) getApplication()).applicationComponent()).inject(this);

        layoutManager = new LinearLayoutManager(this);
        eventListView.setLayoutManager(layoutManager);

        apiService = retrofit.create(SearchApi.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        eventSearchEditText.setOnQueryTextListener(eventTextWatcher);

        eventAdapter = new EventAdapter(this, events);
        eventListView.setAdapter(eventAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (events.size() > 0) {
            events.clear();
            events.addAll(savedResponse.events());
            eventAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.favorite_icon);
        item.setVisible(false);
        return true;
    }
}
