package suraj.android.demos;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterVideosRecycler extends RecyclerView.Adapter<AdapterVideosRecycler.DataHolder>
{
    private ArrayList<VideoDetails> al_videoDetails;
    private Activity activity;
    private int recv_id;
    OnRecyclerItemClicked listener;
    private YouTubePlayer mYouTubePlayer;

    public AdapterVideosRecycler(Activity activity, OnRecyclerItemClicked listener,
                                 ArrayList<VideoDetails> al_videoDetails, int recv_id)
    {
        this.activity = activity;
        this.listener = listener;
        this.al_videoDetails = al_videoDetails;
        this.recv_id = recv_id;
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_video_details, parent, false);
        return new AdapterVideosRecycler.DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataHolder holder, final int position)
    {
        holder.txtv_videoName.setText(al_videoDetails.get(position).getVideoName());
        holder.txtv_videoDuration.setText(al_videoDetails.get(position).getVideoDuration());

        String video_id = extractVideoIdFromUrl(al_videoDetails.get(position).getVideoURL());

        /*----------------------------- Load Thumbnail ---------------------------------------*/

        String url = "https://img.youtube.com/vi/"+video_id+"/default.jpg";
        //Picasso.get().load(url).into(holder.imgv_thumbnail);
        System.out.println("URL=> "+url);

        /*----------------------------------------------------------------------------------------------*/
        holder.imgv_play.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
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

                String video_url = al_videoDetails.get(position).getVideoURL();
                final String video_id = extractVideoIdFromUrl(video_url);

                holder.youtube_player.initialize("AIzaSyAYtzVLsbTj4LtVElRxShJ1ytv6xHEhVdU",
                new YouTubePlayer.OnInitializedListener()
                {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored)
                    {
                        mYouTubePlayer = youTubePlayer;
                        try
                        {
                            if(mYouTubePlayer!=null)
                            {
                                mYouTubePlayer.loadVideo(video_id);
                                mYouTubePlayer.play();
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
                    {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return al_videoDetails.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder
    {
        TextView txtv_videoName, txtv_videoDuration;
        ImageView imgv_play;
        YouTubePlayerView youtube_player;

        public DataHolder(View itemView)
        {
            super(itemView);

            txtv_videoName = (TextView) itemView.findViewById(R.id.txtv_videoName);
            txtv_videoDuration = (TextView) itemView.findViewById(R.id.txtv_videoDuration);
            imgv_play = (ImageView) itemView.findViewById(R.id.imgv_play);
            youtube_player = (YouTubePlayerView) itemView.findViewById(R.id.youtube_player);
        }
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
