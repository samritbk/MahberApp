package com.zinalabs.mahberkidanemihret;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.nio.charset.Charset;
import java.util.ArrayList;

import Adapter.LoveAdapter;
import Adapter.RecyclerAdapter;
import Ease.SnackBar;

public class LovesActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    CoordinatorLayout parent;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    ProgressBar progressBar;
    NavigationView nvView;
    LoveAdapter recyclerAdapter;
    static JSONArray data;

    private void initialization(Context toContext) {
        context = toContext;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        //listview= (ListView) findViewById(R.id.listview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        nvView = (NavigationView) findViewById(R.id.nvView);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loves);

        initialization(this);

        getSupportActionBar().setTitle("ሴቭ ዝተገብረ");

        recyclerAdapter = new LoveAdapter(context);
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        data= getLoves("posts");

        if(data != null) {
            addDataToAdapter(data);
        }

    }

    private void addDataToAdapter(JSONArray data){

            try {
                String value = new String(data.toString().getBytes(Charset.forName("UTF-8")));
                data=new JSONArray(value);
                recyclerAdapter.addList(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    private JSONArray getLoves(String sharedName){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedName,0);
        String loves=sharedPreferences.getString("loves","null");
        JSONArray lovesArray = null;

        if(!loves.equals("null")){
            try {
                lovesArray = new JSONArray(loves);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return lovesArray;
    }

    public static void startReadActivity(Context context, int positionOfClicked) {
        Intent ReadActivity = new Intent(context, com.zinalabs.mahberkidanemihret.ReadActivity.class);

        try {
            int post_id = data.getJSONObject(positionOfClicked).getInt("post_id");
            String post_title = data.getJSONObject(positionOfClicked).getString("post_title");
            String post_text = data.getJSONObject(positionOfClicked).getString("post_text");

            String post_text_short=null;
            if(post_text.length() > 120){
                post_text_short= Html.fromHtml(post_text.substring(0, 120))+"...";
            }else{
                post_text_short= Html.fromHtml(post_text).toString();
            }

            String date_created = data.getJSONObject(positionOfClicked).getString("date_created");
            String post_category_name = data.getJSONObject(positionOfClicked).getString("post_category_name");
            String post_url = data.getJSONObject(positionOfClicked).getString("post_url");
            int related = data.getJSONObject(positionOfClicked).getInt("related");

            ReadActivity.putExtra("post_id", post_id);
            ReadActivity.putExtra("post_title", post_title);
            ReadActivity.putExtra("post_text", post_text);
            ReadActivity.putExtra("post_text_short", post_text_short);
            ReadActivity.putExtra("date_created", date_created);
            ReadActivity.putExtra("post_category_name", post_category_name);
            ReadActivity.putExtra("url", post_url);
            ReadActivity.putExtra("related", related);

            if(related != 0) {
                String img_name= data.getJSONObject(positionOfClicked).getString("img_name");
                ReadActivity.putExtra("img_name", img_name);
            }

            context.startActivity(ReadActivity);

        } catch (JSONException e) {
            Log.e("tag", e.getMessage());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}