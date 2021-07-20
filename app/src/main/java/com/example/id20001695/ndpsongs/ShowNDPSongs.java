package com.example.id20001695.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShowNDPSongs extends AppCompatActivity {
    Spinner spnFilter;
    ListView lv;
    Button btnShow;
    ArrayList<Song> al;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ndpsongs);

        lv = findViewById(R.id.lv);
        btnShow = findViewById(R.id.buttonShow5Star);
        spnFilter = findViewById(R.id.spinner);

        al = new ArrayList<>();
        aa = new ArrayAdapter(ShowNDPSongs.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        DBHelper dbh = new DBHelper(ShowNDPSongs.this);
        al.addAll(dbh.getAllSongs());
        aa.notifyDataSetChanged();

        ArrayList<Integer> items = new ArrayList<>();
        items = dbh.getDate();
        dbh.close();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, items);
        spnFilter.setAdapter(adapter);

        spnFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ShowNDPSongs.this);
                al.clear();
                al.addAll(dbh.getAllSongsByDate(Integer.parseInt(spnFilter.getSelectedItem().toString())));
                aa.notifyDataSetChanged();
                dbh.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowNDPSongs.this);
                al.clear();
                al.addAll(dbh.getAllSongsByStars(5));
                aa.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {
                Song data = al.get(position);
                Intent i = new Intent(ShowNDPSongs.this, ModifyNDPSongs.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(ShowNDPSongs.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        aa.notifyDataSetChanged();
        dbh.close();
    }
}