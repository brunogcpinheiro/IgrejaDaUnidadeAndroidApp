package com.brunogcpinheiro.igrejadaunidade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

public class VideoActivity extends AppCompatActivity {

    private RecyclerView mVideosList;
    private String GOOGLE_KEY = "AIzaSyA7DItR001YFqapevFQzkyM2CJSLuQNIk0";
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Video");
//    private String VIDEO_ID = "IoezWBPGRAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideosList = (RecyclerView) findViewById(R.id.videos_list);
        mVideosList.setHasFixedSize(true);
        mVideosList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Video, VideoViewHolder> adapter =
                new FirebaseRecyclerAdapter<Video, VideoViewHolder>(
                        Video.class,
                        R.layout.video_row,
                        VideoViewHolder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(VideoViewHolder viewHolder, final Video model, int position) {
                        viewHolder.youTubeThumbnailView.initialize(GOOGLE_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                                youTubeThumbnailLoader.setVideo(model.getVideo());
                                youTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(getApplicationContext(), VideoDetailActivity.class);
                                        i.putExtra(VideoDetailActivity.KEY_VIDEO_ID, model.getVideo());
                                        startActivity(i);
                                    }
                                });
                            }

                            @Override
                            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                            }
                        });
                    }
                };
        mVideosList.setAdapter(adapter);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        YouTubeThumbnailView youTubeThumbnailView;

        public VideoViewHolder(View v) {
            super(v);
            youTubeThumbnailView = (YouTubeThumbnailView) v.findViewById(R.id.youtube_thumbnail);
        }
    }
}
