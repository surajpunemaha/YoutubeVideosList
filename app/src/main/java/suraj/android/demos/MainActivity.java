package suraj.android.demos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;

public class MainActivity extends YouTubeBaseActivity implements OnRecyclerItemClicked
{
    ArrayList<VideoDetails> al_videoDetails;
    RecyclerView recv_videos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        getVideoDetails();
        setVideoRecyclerAdapter();

        /*String url = "https://img.youtube.com/vi/"+video_id+"/default.jpg";
        Picasso.get().load(url).into(holder.imgv_thumbnail);*/

        ImageView imgv_demo = (ImageView) findViewById(R.id.imgv_demo);

        Glide.with(this).load("https://img.youtube.com/vi/q10nfS9V090/default.jpg").into(imgv_demo);
    }

    public void initUI()
    {
        al_videoDetails = new ArrayList<>();
        recv_videos = (RecyclerView)findViewById(R.id.recv_videos);
    }

    public void getVideoDetails()
    {
        al_videoDetails.clear();

        al_videoDetails.add(new VideoDetails("Mission Mangal | Official Trailer","2.52","https://www.youtube.com/watch?v=q10nfS9V090"));
        al_videoDetails.add(new VideoDetails("Android Oreo Launch","3.21","https://www.youtube.com/watch?v=tkOGeNoFD5o&t=21s"));
        al_videoDetails.add(new VideoDetails("Surf Excel #RangLaayeSang, Holi Ad","1.00","https://www.youtube.com/watch?v=Zq7mN8oi8ds"));
        al_videoDetails.add(new VideoDetails("Colgate School Director's Cut","0.40","https://www.youtube.com/watch?v=ww68GN-CQMY"));
        al_videoDetails.add(new VideoDetails("Boost Ad Kohli","1.25","https://www.youtube.com/watch?v=n-YiJq4Xj_c"));
        al_videoDetails.add(new VideoDetails("Parle Kismi Ad","0.30","https://www.youtube.com/watch?v=Br19BUAkGJQ"));
        al_videoDetails.add(new VideoDetails("Kiss Me...Cadbury Dairy Milk Silk (Staircase Romance)","0.44","https://www.youtube.com/watch?v=ALaqqMY2m0k"));
    }

    public void setVideoRecyclerAdapter()
    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        AdapterVideosRecycler adapterAPIVideo = new AdapterVideosRecycler(MainActivity.this,
                MainActivity.this, al_videoDetails, recv_videos.getId());
        recv_videos.setLayoutManager(mLayoutManager);
        recv_videos.setAdapter(adapterAPIVideo);
    }

    @Override
    public void onRecyclerItemClicked(int recycler_id, Object item, int position)
    {

    }
}
