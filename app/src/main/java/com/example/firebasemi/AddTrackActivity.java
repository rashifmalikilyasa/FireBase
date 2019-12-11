package com.example.firebasemi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;

    Button buttonAddTrack;

    ListView listViewTracks;

    DatabaseReference databaseTracks;

    List<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        textViewArtistName = (TextView) findViewById(R.id.textViewArtistName);
        editTextTrackName = (EditText) findViewById(R.id.editTextTrackName);
        seekBarRating = (SeekBar) findViewById(R.id.seekbarRating);
        buttonAddTrack = (Button) findViewById(R.id.buttonAddTrack);

        listViewTracks = (ListView) findViewById(R.id.listViewTracks);

        Intent intent = getIntent();

        trackList = new ArrayList<>();
        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        textViewArtistName.setText(name);

        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                trackList.clear();

                for(DataSnapshot trackSnapshot : dataSnapshot.getChildren()){
                    Track track = trackSnapshot.getValue(Track.class);
                    trackList.add(track);
                }

                TrackList trackListAdapter = new TrackList(AddTrackActivity.this, trackList);
                listViewTracks.setAdapter(trackListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveTrack(){

        String trackName = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(!TextUtils.isEmpty(trackName)){
            String id = databaseTracks.push().getKey();

            Track track = new Track(id, trackName, rating);

            databaseTracks.child(id).setValue(track);

            Toast.makeText(this, "Track Saved Successfully", Toast.LENGTH_LONG).show();
        } else{

            Toast.makeText(this, "Track Name Should not be Empty", Toast.LENGTH_LONG).show();

        }

    }
}
