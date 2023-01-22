package com.example.derp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;

public class Edit extends AppCompatActivity {

    //definuj texty
    String alertUlozeno = "Záznam byl uložen";
    String alertJazykNenastaven = "Zadejte prosím Jazyk";
    String alertDateNenastaven = "Zadejte prosím Datum";
    String alertTimeNenastaven = "Zadejte prosím Čas";
    String alertPopisNenastaven = "Zadejte prosím Popis";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //získej createdTime editovaného záznamu
        Intent incomingIntent = getIntent();
        String createdTime = incomingIntent.getStringExtra("id");

        //najdi všechny inputy
        EditText jazykInput = findViewById(R.id.jazykinput);
        EditText popisInput = findViewById(R.id.descriptioninput);
        SeekBar rateInput = findViewById(R.id.rateinput);
        EditText dateInput = findViewById(R.id.dateinput);
        EditText timeInput = findViewById(R.id.timeinput);

        //najdi všechny buttony
        Button saveButton = findViewById(R.id.savebtn);

        //připoj se k databázi
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        //najdi zaznam podle createdTime
        TDA zaznam = realm.where(TDA.class).equalTo("createdTime",Long.valueOf(createdTime)).findFirst();

        //nastav inputy na data z editovaného záznamu
        jazykInput.setText(zaznam.getJazyk());
        popisInput.setText(zaznam.getPopis());
        rateInput.setProgress(Integer.parseInt(zaznam.getRate()));
        dateInput.setText(zaznam.getDate());
        timeInput.setText(zaznam.getTime());

        //po kliknutí na save
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //získej hodnoty z inputů
                String jazyk = jazykInput.getText().toString();
                String popis = popisInput.getText().toString();
                String rate = String.valueOf(rateInput.getProgress());
                String time = timeInput.getText().toString();
                String date = dateInput.getText().toString();

                //zkontroluj, jestli jsou hodnoty nastavené a pokud ne tak to hlaš a nepokračuj
                //U rate je to zbytečné, protože nejde nastavim neplatná hodnota
                if (date.equals("")) {
                    Toast.makeText(v.getContext(),alertDateNenastaven,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (jazyk.equals("")) {
                    Toast.makeText(v.getContext(),alertJazykNenastaven,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (time.equals("")) {
                    Toast.makeText(v.getContext(),alertTimeNenastaven,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (popis.equals("")) {
                    Toast.makeText(v.getContext(),alertPopisNenastaven,Toast.LENGTH_SHORT).show();
                    return;
                }

                //smaž starý záznam
                realm.beginTransaction();
                zaznam.deleteFromRealm();

                //vytvoř nový záznam a přidej do něj nová data + staré id
                TDA novyZazanm = realm.createObject(TDA.class);

                novyZazanm.setDate(date);
                novyZazanm.setJazyk(jazyk);
                novyZazanm.setPopis(popis);
                novyZazanm.setTime(time);
                novyZazanm.setRate(rate);
                novyZazanm.setCreatedTime(Long.parseLong(createdTime));

                realm.commitTransaction();

                //křič uloženo
                Toast.makeText(v.getContext(),alertUlozeno,Toast.LENGTH_SHORT).show();

                //vrať se zpět na Main
                Intent intent = new Intent(Edit.this, Main.class);
                startActivity(intent);

            }
        });
    }
}
