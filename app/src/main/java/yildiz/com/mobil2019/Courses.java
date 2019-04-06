package yildiz.com.mobil2019;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Courses extends AppCompatActivity {
    private static Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Courses.context=this;
        getSupportActionBar().setTitle("Ders Listesi"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<myAdapter.viewModel> data=new ArrayList<>();
        data.add(new myAdapter.viewModel("Bilgisayar Bilimlerine Giriş","AA"));
        data.add(new myAdapter.viewModel("Programlama Dilleri","AA"));
        data.add(new myAdapter.viewModel("Algoritma Analizi","BA"));


        recyclerView=(RecyclerView) findViewById(R.id.CourseList);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mngr=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mngr);

        mAdapter=new myAdapter(data);
        recyclerView.setAdapter(mAdapter);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public static Context getAppContext() {
        return Courses.context;
    }



}


