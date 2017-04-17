package com.zinalabs.mahberkidanemihret;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import java.util.ArrayList;

import Adapter.RecyclerAdapter;

public class LovesActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    CoordinatorLayout parent;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    ProgressBar progressBar;
    NavigationView nvView;
    RecyclerAdapter recyclerAdapter;

    private void initialization(Context toContext) {
        context = toContext;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        //listview= (ListView) findViewById(R.id.listview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        nvView = (NavigationView) findViewById(R.id.nvView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loves);

        initialization(this);

        recyclerAdapter = new RecyclerAdapter(context);
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);



    }
}
