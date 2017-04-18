package com.example.vinaykl.bs2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Select extends Activity implements AdapterView.OnItemSelectedListener {

    String[] items;
    String doctype;
    String time;
    String location;
    String patientname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        patientname = getIntent().getExtras().getString("patient");

        Spinner dropdown1 = (Spinner)findViewById(R.id.spinner1);
        items = new String[]{"children specalist", "general psychiatrist", "surgeon","allergist"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown1.setAdapter(adapter);
        dropdown1.setOnItemSelectedListener(this);

        Spinner dropdown2 = (Spinner)findViewById(R.id.spinner2);
        String[] items2 = new String[]{"Avaliable-Now", "Later-Schedule"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        dropdown2.setOnItemSelectedListener(this);

        Spinner dropdown3 = (Spinner)findViewById(R.id.spinner3);
        String[] items3 = new String[]{"Online", "In-Person"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown3.setAdapter(adapter3);
        dropdown3.setOnItemSelectedListener(this);

        Button Search = (Button) findViewById(R.id.search);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ResultsActivity = new Intent(Select.this, Results.class);
                Bundle b = new Bundle();
                b.putString("Doc", doctype);
                b.putString("Time",time);
                b.putString("location", location);
                b.putString("Patient",patientname);
                ResultsActivity.putExtras(b);
                startActivity(ResultsActivity);
                //finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin = (Spinner) parent;
        if(spin.getId() == R.id.spinner1)
        {
            System.out.println("Here we go");
            doctype = parent.getItemAtPosition(position).toString();
        }
        else if(spin.getId() == R.id.spinner2)
        {
           time = parent.getItemAtPosition(position).toString();
            //do this
        }else if(spin.getId() == R.id.spinner3)
        {
            location = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
