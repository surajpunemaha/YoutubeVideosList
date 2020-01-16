package suraj.android.demos;

import android.view.View;

/**
 * Created by abhinav on 2/1/20.
 */

public interface OnRecyclerItemClicked
{
    public void onRecyclerItemClicked(int recycler_id, View itemView, Object item, int position);
}