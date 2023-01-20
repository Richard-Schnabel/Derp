package com.example.derp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        //najdi kalendář
        CalendarView kalendar = findViewById(R.id.calendarViewInput);

        //kalendar onclick = ...
        kalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //zformátuj datum
                String date = (i1 + 1) + "/" + i2 + "/" + i;

                //vytvoř intent a přidej do něj zvolene datum date
                Intent intent = new Intent(Calendar.this, AddToDatabase.class);
                intent.putExtra("date", date);

                //jdi zpět na "new log"
                startActivity(intent);
            }
        });
    }
}
