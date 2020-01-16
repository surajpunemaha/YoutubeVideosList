package suraj.android.demos;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends YouTubeBaseActivity implements OnRecyclerItemClicked
{
    RecyclerView recv_videos;

    private YouTubePlayer mYouTubePlayer;
    int old_posintion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        getVideoDetails();
        setVideoRecyclerAdapter();
    }

    public void initUI()
    {
        recv_videos = (RecyclerView)findViewById(R.id.recv_videos);
    }

    public void getVideoDetails()
    {
        Utilities.al_videoDetails.clear();

        Utilities.al_videoDetails.add(new VideoDetails("Mission Mangal | Official Trailer","2.52","https://www.youtube.com/watch?v=q10nfS9V090"));
        Utilities.al_videoDetails.add(new VideoDetails("Android Oreo Launch","3.21","https://www.youtube.com/watch?v=tkOGeNoFD5o&t=21s"));
        Utilities.al_videoDetails.add(new VideoDetails("Surf Excel #RangLaayeSang, Holi Ad","1.00","https://www.youtube.com/watch?v=Zq7mN8oi8ds"));
        Utilities.al_videoDetails.add(new VideoDetails("Colgate School Director's Cut","0.40","https://www.youtube.com/watch?v=ww68GN-CQMY"));
        Utilities.al_videoDetails.add(new VideoDetails("Boost Ad Kohli","1.25","https://www.youtube.com/watch?v=n-YiJq4Xj_c"));
        Utilities.al_videoDetails.add(new VideoDetails("Parle Kismi Ad","0.30","https://www.youtube.com/watch?v=Br19BUAkGJQ"));
        Utilities.al_videoDetails.add(new VideoDetails("Kiss Me...Cadbury Dairy Milk Silk (Staircase Romance)","0.44","https://www.youtube.com/watch?v=ALaqqMY2m0k"));
    }

    public void setVideoRecyclerAdapter()
    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        AdapterVideosRecycler adapterAPIVideo = new AdapterVideosRecycler(MainActivity.this,
                MainActivity.this,  recv_videos.getId());
        recv_videos.setLayoutManager(mLayoutManager);
        recv_videos.setAdapter(adapterAPIVideo);
    }

    @Override
    public void onRecyclerItemClicked(int recycler_id, View itemView, Object item, final int position)
    {
        if(mYouTubePlayer!=null  && mYouTubePlayer.isPlaying())
        {
            mYouTubePlayer.pause();
            mYouTubePlayer.release();
        }
        else if(mYouTubePlayer!=null  && !mYouTubePlayer.isPlaying())
        {
            mYouTubePlayer.release();
        }

        /*---------------------------------------------------------------*/

        // show thumbnail after video paused

        if(old_posintion!=-1)
        {
            Utilities.al_videoDetails.get(old_posintion).setShow_thumbnail(true);
            recv_videos.getAdapter().notifyDataSetChanged();
        }

        /*------------------------------------------------------------*/

        String video_url = Utilities.al_videoDetails.get(position).getVideoURL();
        final String video_id = extractVideoIdFromUrl(video_url);

        /*---------------------------------------------------------*/
        TextView txtv_videoName = (TextView) itemView.findViewById(R.id.txtv_videoName);
        TextView txtv_videoDuration = (TextView) itemView.findViewById(R.id.txtv_videoDuration);
        ImageView imgv_play = (ImageView) itemView.findViewById(R.id.imgv_play);
        ImageView imgv_thumbnail = (ImageView) itemView.findViewById(R.id.imgv_thumbnail);
        final YouTubePlayerView youtube_player = (YouTubePlayerView) itemView.findViewById(R.id.youtube_player);
        /*---------------------------------------------------------*/

        imgv_thumbnail.setVisibility(View.GONE);
        Utilities.al_videoDetails.get(position).setShow_thumbnail(false);

        youtube_player.initialize("AIzaSyAYtzVLsbTj4LtVElRxShJ1ytv6xHEhVdU",
        new YouTubePlayer.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b)
            {
                mYouTubePlayer = youTubePlayer;
                try
                {
                    if(mYouTubePlayer!=null)
                    {
                        old_posintion = position;
                        mYouTubePlayer.loadVideo(video_id);
                        mYouTubePlayer.play();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult)
            {
                Toast.makeText(MainActivity.this, "Initialization Failed",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    public String extractVideoIdFromUrl(String url)
    {
        final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for(String regex : videoIdRegex)
        {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if(matcher.find())
            {
                return matcher.group(1);
            }
        }

        return null;
    }

    private String youTubeLinkWithoutProtocolAndDomain(String url)
    {
        String youtubeURLRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        Pattern compiledPattern = Pattern.compile(youtubeURLRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find())
        {
            return url.replace(matcher.group(), "");
        }
        return url;
    }
}
