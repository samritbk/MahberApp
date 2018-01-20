package com.zinalabs.mahberkidanemihret;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView bigArticleTitle;
    TextView topDate;
    TextView articleTitle;
    TextView dateCreated;
    WebView webview;
    int post_id;
    String post_title;
    String post_text;
    String post_text_short;
    String date_created;
    String post_category_name;
    String post_url;
    ImageView extended_background;
    CollapsingToolbarLayout collapsingToolbar;
    Context context;
    JSONObject articleObject;
    ImageButton ic_share_IB;
    ImageButton ic_love_IB;

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
        ic_share_IB = (ImageButton) findViewById(R.id.ic_share_IB);
        ic_love_IB = (ImageButton) findViewById(R.id.ic_love_IB);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initialization();
        toolbarSettings(toolbar);


        Bundle extras=this.getIntent().getExtras();
        if(extras.getString("post_title") != null && extras.getString("post_title") != null){
            post_id =extras.getInt("post_id");
            post_title =extras.getString("post_title");
            post_text =extras.getString("post_text");
            post_text_short =extras.getString("post_text_short");
            date_created=extras.getString("date_created");
            post_category_name =extras.getString("post_category_name");
            post_url =extras.getString("url");
            int related = extras.getInt("related");
            String img_name=extras.getString("img_name");

            try {
                articleObject=makeArticleObject(post_id,
                        post_title,
                        post_text,
                        post_text_short,
                        date_created,
                        post_category_name,
                        post_url,
                        related,
                        img_name);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(related != 0){

                Picasso.with(context).load(img_name).fit().into(extended_background);
            }

            changeTitle(post_title);
            changeDate(date_created);
            changeWebview(post_text);
            ic_share_IB.setOnClickListener(this);
            ic_love_IB.setOnClickListener(this);


            if(getLoves("posts", post_id)){
                ic_love_IB.setImageResource(R.drawable.ic_favorite);
            }else{
                ic_love_IB.setImageResource(R.drawable.ic_favorite_border);
            }
        }

    }

    private JSONObject makeArticleObject(int post_id,
                                   String post_title,
                                   String post_text,
                                   String post_text_short,
                                   String date_created,
                                   String post_category_name,
                                   String post_url,
                                   int related,
                                   String img_name) throws JSONException {

        JSONObject articleObject=new JSONObject();

            articleObject.put("post_id", post_id);
            articleObject.put("post_title", post_title);
            articleObject.put("post_text", post_text);
            articleObject.put("post_text_short", post_text_short);
            articleObject.put("date_created", date_created);
            articleObject.put("post_category_name", post_category_name);
            articleObject.put("post_url", post_url);
            articleObject.put("related", related);
            if(related != 0){
                articleObject.put("img_name", img_name);
            }

        return articleObject;
    }

    private void toolbarSettings(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        if (getSupportActionBar() !=  null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void changeTitle(String post_title) {
        bigArticleTitle.setText(post_title);
    }

    private void changeDate(String date_created) {
        topDate.setText(date_created);
    }

    private void changeWebview(String post_text){
        WebSettings webViewSetting=webview.getSettings();
        webViewSetting.setTextSize(WebSettings.TextSize.NORMAL);
        post_text= "<div style='font-size:18px; text-align: justify; line-height:30px;'>"+post_text+"</div>";
        webview.loadData(post_text,"text/html; charset=UTF-8;", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_activity_menu, menu);

            MenuItem lovesMenu=menu.getItem(0);

            if(getLoves("posts", post_id)){
                lovesMenu.setIcon(R.drawable.ic_favorite_white_24dp);
            }else{
                lovesMenu.setIcon(R.drawable.ic_favorite_border_white_24dp);
            }

        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("");

        //articleTitle.setText(post_text);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.ic_share:
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, post_title +"\n"+ post_url);

                startActivity(share);

                break;
            case R.id.ic_love:
                Drawable iconDrawable=item.getIcon();

                Drawable borderLove=getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp);

                if(getBitmap(iconDrawable).sameAs(getBitmap(borderLove))){
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                }else{
                    item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                }

                try {
                    loveArticle("posts", articleObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean getLoves(String sharedName, int current_article_id){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedName,0);
        String loves=sharedPreferences.getString("loves","null");
        if(!loves.equals("null")){

            JSONArray lovesArray= null;
            try {
                lovesArray = new JSONArray(loves);
                int length=lovesArray.length();

                for(int i=0; i < length; i++){
                    int article_id=lovesArray.getJSONObject(i).getInt("post_id");
                    if(article_id == current_article_id){
                        Log.e("tag", article_id+"==="+current_article_id);
                        return true;
                    }
                }
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }
    private void loveArticle(String sharedName, JSONObject articleObject) throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences(sharedName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();

        String sharedJsonName="loves";

        String loves=sharedPreferences.getString(sharedJsonName,"null");

        if(loves.equals("null")){
            JSONArray lovesArray= new JSONArray();
            lovesArray.put(articleObject);

            editor.putString(sharedJsonName, lovesArray.toString());
            editor.commit();

        }else{
            if(!loves.equals("null")){
                JSONArray lovesArray= new JSONArray(loves);
                Log.e("tag",lovesArray.length()+"");
                int length=lovesArray.length();
                boolean exists=false;
                for(int i=0; i < length; i++){
                    int post_id=lovesArray.getJSONObject(i).getInt("post_id");
                    if(post_id == articleObject.getInt("post_id")){
                        lovesArray.remove(i);
                        editor.remove(sharedJsonName);
                        editor.putString(sharedJsonName, lovesArray.toString());
                        editor.commit();
                        exists=true;
                        break;
                    }
                }
                if(!exists){
                    lovesArray.put(articleObject);
                    Log.e("tag",lovesArray.length()+"");
                    editor.remove(sharedJsonName);
                    editor.putString(sharedJsonName, lovesArray.toString());
                    editor.commit();
                }
            }

        }

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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();

        switch(id){
            case R.id.ic_share_IB:
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, post_title +"\n"+ post_url);

                startActivity(share);
                break;
            case R.id.ic_love_IB:
                Drawable iconDrawable=ic_love_IB.getDrawable();

                Drawable borderLove=getResources().getDrawable(R.drawable.ic_favorite_border);

                if(getBitmap(iconDrawable).sameAs(getBitmap(borderLove))){
                    ic_love_IB.setImageResource(R.drawable.ic_favorite);
                }else{
                    ic_love_IB.setImageResource(R.drawable.ic_favorite_border);
                }

                try {
                    loveArticle("posts", articleObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
