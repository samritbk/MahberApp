package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zinalabs.mahberkidanemihret.MainActivity;
import com.zinalabs.mahberkidanemihret.R;
import com.zinalabs.mahberkidanemihret.ReadActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ViewHolder.ViewHolderAdapter;

/**
 * Created by Beraki on 12/17/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderAdapter> {

    //ArrayList<String[]> data= new ArrayList<>();
    JSONArray data=new JSONArray();
    LayoutInflater inflater;
    Context context;
    //String server = "http://192.168.0.102/church/";
    public static String server = "http://eriotc.org/";

    public RecyclerAdapter(Context context){
        inflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolderAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.recycler_layout, parent, false);
        ViewHolderAdapter viewHolder= new ViewHolderAdapter(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderAdapter holder, int position) {

        try {
            // Main data
            JSONObject post = data.getJSONObject(position);


            String post_title=post.getString("post_title");
            String post_text=post.getString("post_text");
            String post_text_short= null;

            if(post_text.length() > 120) {
                post_text_short = Html.fromHtml(post_text.substring(0, 120)) + "...";
            }else{
                post_text_short= Html.fromHtml(post_text).toString();
            }
            String post_category_name=post.getString("post_category_name");
            String date_created=post.getString("date_created");


                holder.articleTitle.setText(post_title);
                holder.subtitle.setText(post_text_short);
                holder.categoryName.setText(post_category_name);
                holder.dateCreated.setText(date_created);

            Log.d("tagger", post.get("related")+"");

            if(( (int) post.get("related")) != 0){
                JSONArray imagesArray= post.getJSONArray("images");
                JSONObject imageToLoadLink=imagesArray.getJSONObject(0);
                String link= imageToLoadLink.getString("path");
                String fillPath= server+link;
                Picasso.with(context).load(fillPath).fit().into(holder.imageHolder);
                holder.imageHolder.setVisibility(View.VISIBLE);
            }else{
                holder.imageHolder.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final int positionOfClicked= position;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mahbera",positionOfClicked+"");
                MainActivity.startReadActivity(context, positionOfClicked);
            }
        });
    }

    public void addList(JSONArray list){
        this.data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    /**
     * Created by Beraki on 12/17/2016.
     */

    public class ViewHolderAdapter extends RecyclerView.ViewHolder{

        private CardView cardView;
        private TextView articleTitle;
        private TextView subtitle;
        private TextView dateCreated;
        private TextView categoryName;
        private ImageView imageHolder;

        public ViewHolderAdapter(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            articleTitle = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            dateCreated = (TextView) itemView.findViewById(R.id.dateCreated);
            imageHolder = (ImageView) itemView.findViewById(R.id.img);
        }
    }

}
