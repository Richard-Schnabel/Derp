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

        //po kliknutí na datum
        kalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {

                //zformátuj datum
                String date = d + "/" + (m + 1) + "/" + y;

                //vrať se zpět a datum si pamatuj
                Intent intent = new Intent(Calendar.this, AddToDatabase.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}
