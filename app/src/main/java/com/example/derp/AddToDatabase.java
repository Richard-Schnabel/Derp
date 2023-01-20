package com.example.derp;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class AddToDatabase extends AppCompatActivity {

    String alertUlozeno = "$Záznam byl uložen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //najdi všechny inputy
        EditText jazykInput = findViewById(R.id.jazykinput);
        EditText popisInput = findViewById(R.id.descriptioninput);
        SeekBar rateInput = findViewById(R.id.rateinput);
        EditText dateInput = findViewById(R.id.dateinput);
        EditText timeInput = findViewById(R.id.timeinput);

        //ulož datum z kalendáře
        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        //vlož vlož datum do inputu
        dateInput.setText(date);

        //najdi buttony
        Button saveButton = findViewById(R.id.savebtn);
        Button dateBtn = findViewById(R.id.datebtn);

        //připoj se k databázi
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        //button onclick = zobraz kalendář
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddToDatabase.this, Calendar.class);
                startActivity(intent);
            }
        });

        //button onclick = ulož data
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //získej hodnoty z inputů
                String jazyk = jazykInput.getText().toString();
                String popis = popisInput.getText().toString();
                String rate = String.valueOf(rateInput.getProgress());
                String time = timeInput.getText().toString();
                String date = dateInput.getText().toString();

                //přidej čas vytvoření
                long createdTime = System.currentTimeMillis();

                //ulož data do databáze
                realm.beginTransaction();
                TDA data = realm.createObject(TDA.class);

                data.setDate(date);
                data.setJazyk(jazyk);
                data.setPopis(popis);
                data.setTime(time);
                data.setRate(rate);
                data.setCreatedTime(createdTime);

                realm.commitTransaction();

                //vrať se zpět na Main
                Intent intent = new Intent(AddToDatabase.this, Main.class);
                startActivity(intent);

            }
        });
    }
}