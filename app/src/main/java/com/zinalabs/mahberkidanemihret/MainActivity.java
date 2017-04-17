package com.zinalabs.mahberkidanemihret;

import android.content.Context;
import android.content.Intent;
import android.drm.DrmManagerClient;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import Adapter.RecyclerAdapter;
import Ease.SnackBar;
import ViewHolder.ViewHolderAdapter;

import static com.zinalabs.mahberkidanemihret.R.id.categoryName;
import static com.zinalabs.mahberkidanemihret.R.id.nvView;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    ListView listview;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout parent;
    RecyclerAdapter recyclerAdapter;
    DrawerLayout drawerLayout;
    NavigationView nvView;
    ProgressBar progressBar;
    static JSONArray data;
    String responseOutside="null";
    SnackBar snackBar;

    RequestQueue requestQueue = null;

    //final String url = "http://192.168.0.102/church/app_request.php";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    final String url="http://eriotc.org/app_request.php";

    private void initialization(Context toContext) {
        context = toContext;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        //listview= (ListView) findViewById(R.id.listview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        nvView = (NavigationView) findViewById(R.id.nvView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization(MainActivity.this); // Vars to be used
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        enableDrawerHamBurger(this, drawerLayout, toolbar, R.string.app_name);


        recyclerAdapter = new RecyclerAdapter(context);
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);



        requestQueue = Volley.newRequestQueue(context);
        makeWebRequest(requestQueue, url);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeWebRequest(requestQueue, url);
            }
        });


        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.category1:
                        makeWebRequest(requestQueue, url+"?id=1");

                        break;
                    case R.id.category2:
                        makeWebRequest(requestQueue, url+"?id=2");

                        break;
                    case R.id.category3:
                        makeWebRequest(requestQueue,  url+"?id=3");

                        break;
                    case R.id.category4:
                        makeWebRequest(requestQueue, url+"?id=4");

                        break;
                    case R.id.category5:
                        makeWebRequest(requestQueue, url+"?id=5");

                        break;
                    case R.id.category6:
                        makeWebRequest(requestQueue, url+"?id=6");

                        break;
                    case R.id.category7:
                        makeWebRequest(requestQueue, url+"?id=7");

                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void enableDrawerHamBurger(Context context, DrawerLayout drawerLayout, Toolbar toolbar, int appname) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
                //drawerOpened = false;
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
                //drawerOpened = true;
            }
        };


        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerAdapter.getItemCount() != 0) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static void startReadActivity(Context context, int positionOfClicked) {
        Intent ReadActivity = new Intent(context, com.zinalabs.mahberkidanemihret.ReadActivity.class);

        try {
            int article_id = data.getJSONObject(positionOfClicked).getInt("post_id");
            String article_title = data.getJSONObject(positionOfClicked).getString("post_title");
            String article_text = data.getJSONObject(positionOfClicked).getString("post_text");
            String article_text_short= data.getJSONObject(positionOfClicked).getString("post_text");
            article_text_short= Html.fromHtml(article_text_short.substring(0, 120))+"...";
            String date_created = data.getJSONObject(positionOfClicked).getString("date_created");
            String article_url = data.getJSONObject(positionOfClicked).getString("url");
            int related = data.getJSONObject(positionOfClicked).getInt("related");


            ReadActivity.putExtra("post_id", article_id);
            ReadActivity.putExtra("post_title", article_title);
            ReadActivity.putExtra("post_text", article_text);
            ReadActivity.putExtra("post_text_short", article_text_short);
            ReadActivity.putExtra("date_created", date_created);
            ReadActivity.putExtra("url", article_url);
            ReadActivity.putExtra("related", related);

            if(related != 0) {
                JSONArray imageArray = data.getJSONObject(positionOfClicked).getJSONArray("images");
                String img_name= imageArray.getJSONObject(0).getString("img_name");
                ReadActivity.putExtra("img_name", "http://eriotc.org/images/" + img_name);
            }

            context.startActivity(ReadActivity);

        } catch (JSONException e) {
            Log.e("tag", e.getMessage());
        }
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://eriotc.org"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    public void makeWebRequest(RequestQueue requestQueue, String url) {
        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                addDataToAdapter(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                snackBar = new SnackBar();
                snackBar.showForever(parent, "Network error. Check your Internet connection!");
                progressBar.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        requestQueue.add(stringRequest);

        Toast.makeText(context, url, Toast.LENGTH_LONG).show();
    }

    private void addDataToAdapter(final String response){

        try {
            String value = new String(response.getBytes(Charset.forName("UTF-8")));
            data=new JSONArray(value);
            recyclerAdapter.addList(data);
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
