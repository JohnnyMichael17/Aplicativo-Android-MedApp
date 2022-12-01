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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityList_Espec extends AppCompatActivity {

    private TextView txtDescricao;
    private ListView listaEspec;

    Especializacao especializacao;
    ArrayList<Especializacao> especArrayList;
    ArrayAdapter<Especializacao> especArrayAdapter;
    DBHelper helper;

    private int id1, id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_espec);

        listaEspec=findViewById(R.id.listEspec);
        registerForContextMenu(listaEspec);

        listaEspec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Especializacao especAux = (Especializacao) especArrayAdapter.getItem(position);
                Intent intent= new Intent(ActivityList_Espec.this, ActivityCad_Espec.class);
                intent.putExtra("chaveEspec", especAux);
                startActivity(intent);
            }
        });

        listaEspec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                especializacao = especArrayAdapter.getItem(position);
                return false;
            }
        });

    }

    public void preencheLista(){

        helper= new DBHelper(ActivityList_Espec.this);
        especArrayList = helper.buscarEspec();
        helper.close();

        if (listaEspec!=null){
            especArrayAdapter = new ArrayAdapter<Especializacao>(
                    ActivityList_Espec.this,
                    android.R.layout.simple_list_item_1,
                    especArrayList
            );
            listaEspec.setAdapter(especArrayAdapter);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){

        MenuItem deletar = menu.add(Menu.NONE, 1, 1,"Deletar Especialização");
        MenuItem cancelar = menu.add(Menu.NONE, 2, 2,"Cancelar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD=1;
                helper=new DBHelper(ActivityList_Espec.this);
                retornoBD = helper.excluirEspec(especializacao);
                helper.close();
                if(retornoBD==-1){
                    alert("Erro de exclusão!");
                }
                else if(retornoBD == 999){
                    alert("Erro, existem médicos com essa especialização!");
                }else{
                    alert("Especialização excluída com sucesso!");
                }
                preencheLista();
                return false; }
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