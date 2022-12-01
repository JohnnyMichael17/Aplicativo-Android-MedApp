package com.example.medapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityCad_Med extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    private EditText editNome;
    private EditText editTelefone;
    private EditText editIdade;
    private EditText editEndereco;
    private EditText editIdEspec;
    private Medico medico;
    private Medico alterarMed;
    private Button btnSalvar;

    private ListView listCategorias;
    ArrayList<Especializacao> especArrayList;
    ArrayAdapter<Especializacao> especArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_med);

        EditText et = (EditText) findViewById(R.id.editIdade);
        et.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        editNome = findViewById(R.id.editName);
        editTelefone = findViewById(R.id.editTel);
        editIdade = findViewById(R.id.editIdade);
        editEndereco = findViewById(R.id.editEndereco);
        editIdEspec = findViewById(R.id.editIdEspec);
        btnSalvar = findViewById(R.id.btnSalvar);

        Intent it= getIntent();
        alterarMed = (Medico) it.getSerializableExtra("chaveMedico");
        medico = new Medico();



       if (alterarMed != null){
            btnSalvar.setText("ALTERAR");
            editNome.setText(alterarMed.getNome());
            editTelefone.setText(alterarMed.getTelefone());
           editIdade.setText(Integer.toString(alterarMed.getIdEspec()));
            editEndereco.setText(alterarMed.getEndereco());
           editIdEspec.setText(Integer.toString(alterarMed.getIdEspec()));
           medico.setIdMed(alterarMed.getIdMed());
        }else{
            btnSalvar.setText("SALVAR");
        }

        listCategorias=findViewById(R.id.listCategoriaCad);
        registerForContextMenu(listCategorias);


    }


    public void cadastrar(View view){

        String nome  = editNome.getText().toString();
        String telefone  = editTelefone.getText().toString();
        int idade  = Integer.parseInt(editIdade.getText().toString());
        String endereco  = editEndereco.getText().toString();
        int idEspec = Integer.parseInt(editIdEspec.getText().toString());
        medico.setNome(nome);
        medico.setTelefone(telefone);
        medico.setIdade(idade);
        medico.setEndereco(endereco);
        medico.setIdEspec(idEspec);


        if(btnSalvar.getText().toString().equals("SALVAR")) {
            helper.insereMed(medico);
            Toast toast = Toast.makeText(ActivityCad_Med.this,
                    "Médico cadastrado com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            helper.atualizarMed(medico);
            helper.close();
        }
        limpar();
        finish();
    }

    public void limpar(){

        editNome.setText("");
        editTelefone.setText("");
        editIdade.setText("");
        editEndereco.setText("");
        editIdEspec.setText("");
    }
    public void cancelar(View view) {
        finish();
    }

    public void preencheLista(){
        helper= new DBHelper(ActivityCad_Med.this);
        especArrayList = helper.buscarEspec();
        helper.close();
        if (listCategorias!=null){
            especArrayAdapter = new ArrayAdapter<Especializacao>(
                    ActivityCad_Med.this,
                    android.R.layout.simple_list_item_1,
                    especArrayList
            );
            listCategorias.setAdapter(especArrayAdapter);
        }
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