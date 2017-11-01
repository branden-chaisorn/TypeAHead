package brandenc.com.typeahead;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import brandenc.com.typeahead.ImageProcessing.CircleTransformation;
import brandenc.com.typeahead.Models.Event;
import brandenc.com.typeahead.StringManipulations.StringManipulationsKt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends AppCompatActivity {

    private static final String TAG = EventDetailActivity.class.getSimpleName();

    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.event_detail_name)
    TextView eventName;

    @BindView(R.id.event_detail_city)
    TextView eventCity;

    @BindView(R.id.event_detail_date)
    TextView eventDate;

    @BindView(R.id.performer_detail_image)
    ImageView performerImage;

    private Event currentEvent;
    private DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference()
                                            .child("events");

    private final int IMAGE_HEIGHT = 200;
    private final int IMAGE_WIDTH = 200;

    private final String EVENT_MAP_NAME = "name";
    private final String EVENT_MAP_CITY = "city";
    private final String EVENT_MAP_DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        (((MainApplication) getApplication()).applicationComponent()).inject(this);

        currentEvent  = getIntent().getParcelableExtra("event");

        String performerString = StringManipulationsKt.createEventName(currentEvent.performers());

        getSupportActionBar().setTitle(performerString);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        eventName.setText(performerString);
        eventCity.setText(currentEvent.venue().displayLocation());
        eventDate.setText(currentEvent.eventDate().toString());

        // Always just show the first performers image
        if (currentEvent.performers().get(0).image() != null) {
            Picasso.with(getApplicationContext()).load(currentEvent.performers().get(0).image())
                    .transform(new CircleTransformation())
                    .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                    .placeholder(R.drawable.stockdog)
                    .into(performerImage);
        } else {
            // Done this way because of the limitations with placeholder images found here
            // https://github.com/square/picasso/issues/337
            Picasso.with(getApplicationContext()).load(R.drawable.stockdog)
                    .transform(new CircleTransformation())
                    .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                    .into(performerImage);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.favorite_icon);

        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(currentEvent.id()).exists()) {
                    item.setIcon(R.drawable.ic_favorite_black_24px);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Error when checking if value exists: " + databaseError.getMessage());
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favorite_icon) {
            eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.child(currentEvent.id()).exists()) {
                        item.setIcon(R.drawable.ic_favorite_black_24px);
                        Map<String, String> eventMap = new HashMap<>();

                        eventMap.put(EVENT_MAP_NAME, StringManipulationsKt
                                                    .createEventName(currentEvent.performers()));
                        eventMap.put(EVENT_MAP_CITY, currentEvent.venue().displayLocation());
                        eventMap.put(EVENT_MAP_DATE, currentEvent.eventDate().toString());

                        eventsRef.child(currentEvent.id()).setValue(eventMap);
                    } else {
                        item.setIcon(R.drawable.ic_favorite_border_black_24px);
                        eventsRef.child(currentEvent.id()).removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Error when interacting with DB: " + databaseError.getMessage());
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
