package com.example.derp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.Objects;
import java.util.prefs.Preferences;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        //připoj se k databázi
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        //yvtvoř adaptér pro záznamy a zavolej ho
        RealmResults<TDA> notesList = realm.where(TDA.class).findAllSorted("createdTime", Sort.DESCENDING);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TDAAdapter adapter = new TDAAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(adapter);

        //najdi buttony
        Button pridatZaznamBtn = findViewById(R.id.pridatzaznambtn);
        Button sortOrderBtn = findViewById(R.id.sortorderbtn);
        Button sortByBtn = findViewById(R.id.sortbybtn);
        Button filterBtn = findViewById(R.id.filterbtn);

        //najdi input
        EditText filterInput = findViewById(R.id.filterinput);

        filterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                //získej uložené nastavení
                Preferences prefs = Preferences.userNodeForPackage(this.getClass());
                prefs.put("containsText", filterInput.getText().toString());

                //reloadni zaznamy
                TDAAdapter adapter = new TDAAdapter(getApplicationContext(), notesList);
                recyclerView.setAdapter(adapter);
            }
        });


        //po klkiknutí na "přidat záznam"...
        pridatZaznamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //otevři formulžř
                Intent intent = new Intent(Main.this, AddNew.class);
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

                //reloadni zaznamy
                TDAAdapter adapter = new TDAAdapter(getApplicationContext(), notesList);
                recyclerView.setAdapter(adapter);
            }
        });

        //po kliknutí na "fitlerby"
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //definuj možnosti a jejich hodnoty
                String[] sortMoznosti = {"Hodnocení", "Čas strávený", "Datum", "Programovací jazyk", "Popis"};
                String[] sortIndex = {"rate", "time", "date", "jazyk", "popis"};

                //získej uložené nastavení
                Preferences prefs = Preferences.userNodeForPackage(this.getClass());
                int selected = Arrays.asList(sortIndex).indexOf(prefs.get("filterBy", "popis"));

                //postav Alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                builder.setTitle("Vyber podle čeho filter");

                //po kliknutí na možnost
                builder.setSingleChoiceItems(sortMoznosti, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int polozka) {

                        dialogInterface.dismiss();
                        //nastav podle čeho má být filtrováno
                        String filterBy = sortIndex[polozka];

                        //změň nastavení na to vybrané
                        prefs.put("filterBy", filterBy);

                        //reloadni zaznamy
                        TDAAdapter adapter = new TDAAdapter(getApplicationContext(), notesList);
                        recyclerView.setAdapter(adapter);
                    }
                });

                //zobraz Alert Dialog
                builder.show();
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

                        //reloadni zaznamy
                        TDAAdapter adapter = new TDAAdapter(getApplicationContext(), notesList);
                        recyclerView.setAdapter(adapter);
                    }
                });

                //zobraz Alert Dialog
                builder.show();
            }
        });
    }

    //přepiš back button na nic
    @Override
    public void onBackPressed(){

    }
}