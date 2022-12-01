package com.example.medapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText edtDiscricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cadastrarEspec(View view) {
        Intent intent = new Intent(this, ActivityList_Espec.class);
        startActivity(intent);
    }

    public void listarEspec(View view){
        Intent intent = new Intent(this, ActivityCad_Espec.class);
        startActivity(intent);
    }

    public void cadastrarMed(View view) {
        Intent intent = new Intent(this, ActivityCad_Med.class);
        startActivity(intent);
    }

    public void listarMed(View view) {
        Intent intent = new Intent(this, ActivityList_Med.class);
        startActivity(intent);
    }
}