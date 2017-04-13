package com.zinalabs.mahberkidanemihret;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import Adapter.RecyclerAdapter;
import Ease.SnackBar;

public class ReadActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView bigArticleTitle;
    TextView topDate;
    TextView articleTitle;
    TextView dateCreated;
    WebView webview;
    String article_title;
    String article_text;
    String date_created;
    String article_url;
    ImageView extended_background;
    CollapsingToolbarLayout collapsingToolbar;
    Context context;

    private void initialization(){
        context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        articleTitle = (TextView) findViewById(R.id.articleTitle);
        webview = (WebView) findViewById(R.id.webview);
        //articleTitle = (TextView) findViewById(R.id.articleTitle);
        //dateCreated = (TextView) findViewById(R.id.dateCreated);
        bigArticleTitle = (TextView) findViewById(R.id.bigArticleTitle);
        topDate = (TextView) findViewById(R.id.topDate);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        extended_background = (ImageView) findViewById(R.id.extended_background);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initialization();
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        if (getSupportActionBar() !=  null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle extras=this.getIntent().getExtras();
        if(extras.getString("post_title") != null && extras.getString("post_title") != null){
            article_title=extras.getString("post_title");
            article_text=extras.getString("post_text");
            date_created=extras.getString("date_created");
            article_url=extras.getString("url");

            Toast.makeText(context, extras.getInt("related")+"", Toast.LENGTH_LONG).show();
            if(extras.getInt("related") != 0){
                String img_name=extras.getString("img_name");
                Picasso.with(context).load(img_name).fit().into(extended_background);
                extended_background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show();
                    }
                });
            }

            bigArticleTitle.setText(article_title);
            topDate.setText(date_created);
            //articleTitle.setText(article_title);
            //dateCreated.setText(date_created);
            WebSettings webViewSetting=webview.getSettings();
            webViewSetting.setTextSize(WebSettings.TextSize.NORMAL);
            article_text= "<div style='font-size:18px; text-align: justify; line-height:30px;'>"+article_text+"</div>";
            webview.loadData(article_text,"text/html; charset=UTF-8;", null);
        }
        Toast.makeText(this, extras.getString("post_title"), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.ic_share:
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, article_title+"\n"+article_url);

                startActivity(share);
                //item.setIcon(R.drawable.ripple);
                break;
            case R.id.ic_love:
                Drawable iconDrawable=item.getIcon();

                Drawable borderLove=getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp);

                if(getBitmap(iconDrawable).sameAs(getBitmap(borderLove))){
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                }else{
                    item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                }
                //loveArticle("");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loveArticle(String sharedName) throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences(sharedName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();

        String loves=sharedPreferences.getString("loves", null);

        if(loves != null){
            JSONArray jsonArray= new JSONArray(loves);
            int length=jsonArray.length();
            if(length != 0) {
                for (int i = 0; i < length; i++) {

                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    //jsonObject.getInt

                }
            }
        }


    }
    private void picassioToViewBackground(String imageUri, final View view){
        final ImageView img = new ImageView(this);
        Picasso.with(this)
        .load(imageUri)
        .fit()
        .centerCrop()
        .into(img, new Callback() {
            @Override
            public void onSuccess() {
                view.setBackground(img.getDrawable());
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("");

        //articleTitle.setText(article_text);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
