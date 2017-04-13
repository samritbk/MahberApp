package ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zinalabs.mahberkidanemihret.R;

/**
 * Created by Beraki on 12/17/2016.
 */

public class ViewHolderAdapter extends RecyclerView.ViewHolder{

        private TextView articleTitle;
        private ImageView image;

    public ViewHolderAdapter(View itemView) {
        super(itemView);

        articleTitle = (TextView) itemView.findViewById(R.id.title);
        //image = (ImageView) itemView.findViewById(R.id.img);
    }
}
