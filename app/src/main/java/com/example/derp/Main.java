package com.example.derp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.prefs.Preferences;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //najdi buttony
        Button pridatZaznamBtn = findViewById(R.id.pridatzaznambtn);
        Button sortOrderBtn = findViewById(R.id.sortorderbtn);
        Button sortByBtn = findViewById(R.id.sortbybtn);

        //po klkiknutí na "přidat záznam"...
        pridatZaznamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //otevři formulžř
                Intent intent = new Intent(Main.this, AddToDatabase.class);
                startActivity(intent);
            }
        });

        //po kliknutí na "$asc/desc$"
        sortOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //získej uložené nastavení
                Preferences prefs = Preferences.userNodeForPackage(this.getClass());
                String sortOrder = prefs.get("sortOrder", Sort.DESCENDING.toString());

                //otoč asc/desc
                sortOrder = Objects.equals(sortOrder, Sort.ASCENDING.toString()) ? Sort.DESCENDING.toString() : Sort.ASCENDING.toString();
                prefs.put("sortOrder", sortOrder);

                //obnov Main
                Intent intent = new Intent(Main.this, Main.class);
                startActivity(intent);
                overridePendingTransition(0, 0);  //odstraň přechod
            }
        });

        //po kliknutí na "sortby"
        sortByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //definuj možnosti a jejich hodnoty
                String[] sortMoznosti = {"Čas vytvoření", "Hodnocení", "Čas strávený", "Datum", "Programovací jazyk"};
                String[] sortIndex = {"createdTime", "rate", "time", "date", "jazyk"};

                //získej uložené nastavení
                Preferences prefs = Preferences.userNodeForPackage(this.getClass());
                int selected = Arrays.asList(sortIndex).indexOf(prefs.get("sortBy", "createdTime"));

                //postav Alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                builder.setTitle("Vyber podle čeho sort");

                //po kliknutí na možnost
                builder.setSingleChoiceItems(sortMoznosti, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int polozka) {

                        dialogInterface.dismiss();
                        //nastav podle čeho má být řazeno
                        String sortBy = sortIndex[polozka];

                        //změň nastavení na to vybrané
                        prefs.put("sortBy", sortBy);

                        //obnov Main
                        Intent intent = new Intent(Main.this, Main.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);  //odstraň přechod
                    }
                });

                //zobraz Alert Dialog
                builder.show();
            }
        });

        //připoj se k databázi
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<TDA> notesList = realm.where(TDA.class).findAllSorted("createdTime", Sort.DESCENDING);

        //najdi místo pro záznamy
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TDAAdapter TDAAdapter = new TDAAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(TDAAdapter);

        /*
        //nefunguje nevim proč, reloaduju ručně
        notesList.addChangeListener(new RealmChangeListener<RealmResults<TDA>>() {
            @Override
            public void onChange(RealmResults<TDA> TDAS) {
                TDAAdapter.notifyDataSetChanged();
            }
        });*/
    }

    //přepiš back button na nic
    @Override
    public void onBackPressed(){

    }
}