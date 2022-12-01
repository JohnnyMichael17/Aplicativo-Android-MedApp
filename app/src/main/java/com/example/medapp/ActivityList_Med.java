package com.example.medapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityList_Med extends AppCompatActivity {

    private ListView listaMed;

    Medico medico;
    ArrayList<Medico> medicoArrayList;
    ArrayAdapter<Medico> medicoArrayAdapter;
    DBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_med);

        listaMed = findViewById(R.id.listMed);
        registerForContextMenu(listaMed);

        listaMed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Medico medicoAux = (Medico) medicoArrayAdapter.getItem(position);
                Intent intent = new Intent(ActivityList_Med.this, ActivityCad_Med.class);
                intent.putExtra("chaveMedico", medicoAux);
                startActivity(intent);
            }
        });

        listaMed.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                medico = medicoArrayAdapter.getItem(position);
                return false;
            }
        });
    }

    public void preencheLista(){

        helper= new DBHelper(ActivityList_Med.this);
        medicoArrayList = helper.buscarMed();
        helper.close();

        if (listaMed != null){
            medicoArrayAdapter = new ArrayAdapter<Medico>(
                    ActivityList_Med.this,
                    android.R.layout.simple_list_item_1,
                    medicoArrayList
            );
            listaMed.setAdapter(medicoArrayAdapter);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){

        MenuItem deletar = menu.add(Menu.NONE, 1, 1,"Deletar Médico");
        MenuItem cancelar = menu.add(Menu.NONE, 2, 2,"Cancelar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                long retornoBD = 1;
                helper = new DBHelper(ActivityList_Med.this);
                retornoBD = helper.excluirMed(medico);
                helper.close();

                if(retornoBD == -1){
                    alert("Erro de exclusão!");
                }
                else{
                    alert("Médico excluído com sucesso!");
                }
                preencheLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    protected void onResume(){
        super.onResume();
        preencheLista();
    }

    private void alert(String palavra){
        Toast.makeText(this, palavra, Toast.LENGTH_SHORT).show();
    }
}