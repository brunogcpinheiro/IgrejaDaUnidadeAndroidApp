package com.brunogcpinheiro.igrejadaunidade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;
import static android.view.View.Y;

public class VideoActivity extends AppCompatActivity {

    private RecyclerView mVideosList;
    private String GOOGLE_KEY = "AIzaSyA7DItR001YFqapevFQzkyM2CJSLuQNIk0";
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Video");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideosList = (RecyclerView) findViewById(R.id.videos_list);
        mVideosList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mVideosList.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        mVideosList.smoothScrollToPosition(0);
        final FirebaseRecyclerAdapter<Video, VideoViewHolder> adapter =
                new FirebaseRecyclerAdapter<Video, VideoViewHolder>(
                        Video.class,
                        R.layout.video_row,
                        VideoViewHolder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(VideoViewHolder viewHolder, final Video model, int position) {
                        viewHolder.setVideo_desc(model.getVideo_desc());
                        viewHolder.setVideo_date(model.getVideo_date());
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
        adapter.notifyDataSetChanged();

        if(isOnline()){
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(0, itemCount);
                    progressDialog.dismiss();
                    mVideosList.smoothScrollToPosition(positionStart);
                }
            });
        } else {
            progressDialog.dismiss();
            adapter.cleanup();
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.error, null);

            // fill in any details dynamically here
            TextView textView = (TextView) v.findViewById(R.id.error);
            textView.setText("Não foi possível cerregar. Sem conexão com a Internet. Conecte-se e entre novamente :)");

            // insert into main view
            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.insert_point);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        YouTubeThumbnailView youTubeThumbnailView;
        View mView;

        public VideoViewHolder(View v) {
            super(v);
            mView = v;
            youTubeThumbnailView = (YouTubeThumbnailView) v.findViewById(R.id.youtube_thumbnail);
        }

        public void setVideo_desc(String desc){
            TextView video_desc = (TextView) mView.findViewById(R.id.video_text);
            video_desc.setText(desc);
        }

        public void setVideo_date(String date){
            TextView video_date = (TextView) mView.findViewById(R.id.video_date);
            video_date.setText(date);
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
