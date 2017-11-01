package brandenc.com.typeahead;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import brandenc.com.typeahead.ImageProcessing.CircleTransformation;
import brandenc.com.typeahead.Models.Event;
import brandenc.com.typeahead.StringManipulations.StringManipulationsKt;
import butterknife.BindView;
import butterknife.ButterKnife;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventItemViewHolder> {

    private Context context;
    private List<Event> events;
    private static final String EVENT = "event";
    private DatabaseReference eventsRef = FirebaseDatabase.getInstance()
                                            .getReference().child("events");

    private final int IMAGE_HEIGHT = 200;
    private final int IMAGE_WIDTH = 200;

    EventAdapter(Context context, List<Event> events) {
        this.events = events;
        this.context = context;
    }

    @Override
    public EventItemViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);

        return new EventItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EventItemViewHolder holder, int position) {
        final Event currentEvent = events.get(position);

        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(currentEvent.id()).exists()) {
                    holder.eventFavoriteIcon.setVisibility(View.VISIBLE);
                } else {
                    holder.eventFavoriteIcon.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Always want to just show the first performers image if available
        if (currentEvent.performers().get(0).image() != null) {
            Picasso.with(context).load(currentEvent.performers().get(0).image())
                    .transform(new CircleTransformation())
                    .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                    .into(holder.eventPerformerImage);
        } else {
            // Done this way because of the limitations with placeholder images found here
            // https://github.com/square/picasso/issues/337
            Picasso.with(context).load(R.drawable.stockdog)
                    .transform(new CircleTransformation())
                    .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                    .into(holder.eventPerformerImage);
        }

        holder.eventNameTextView.setText(StringManipulationsKt.createEventName(currentEvent.performers()));
        holder.eventCityTextView.setText(currentEvent.venue().displayLocation());
        holder.eventDateTextView.setText(currentEvent.eventDate().toString());

        holder.eventItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToEventDetails(currentEvent);
            }
        });
    }

    private void navigateToEventDetails (Event currentEvent) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra(EVENT, currentEvent);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    final class EventItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.performer_image)
        ImageView eventPerformerImage;

        @BindView(R.id.event_name)
        TextView eventNameTextView;

        @BindView(R.id.event_city)
        TextView eventCityTextView;

        @BindView(R.id.event_date)
        TextView eventDateTextView;

        @BindView(R.id.favorite_icon)
        ImageView eventFavoriteIcon;

        View eventItemLayout;

        EventItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            eventItemLayout = v;
        }
    }
}
