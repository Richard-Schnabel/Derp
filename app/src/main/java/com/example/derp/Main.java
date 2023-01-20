package com.example.derp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //najdi button
        Button pridatZaznamBtn = findViewById(R.id.pridatzaznambtn);

        //button onclick...
        pridatZaznamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //otevři formulžř
                Intent intent = new Intent(Main.this, AddToDatabase.class);
                startActivity(intent);
            }
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<TDA> notesList = realm.where(TDA.class).findAllSorted("createdTime", Sort.DESCENDING);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TDAAdapter TDAAdapter = new TDAAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(TDAAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<TDA>>() {
            @Override
            public void onChange(RealmResults<TDA> TDAS) {
                TDAAdapter.notifyDataSetChanged();
            }
        });
    }
}