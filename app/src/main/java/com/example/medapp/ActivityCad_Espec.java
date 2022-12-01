package com.example.medapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityCad_Espec extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText editDesc;
    private Button btnSalvar;
    private Especializacao especializacao;
    private Especializacao alterarEspec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_espec);

        editDesc = findViewById(R.id.editDesc);
        btnSalvar = findViewById(R.id.btnSalvar);

        Intent it=getIntent();
        alterarEspec = (Especializacao) it.getSerializableExtra("chaveEspec");
        especializacao = new Especializacao();

        if (alterarEspec!= null){
            btnSalvar.setText("ALTERAR");
            editDesc.setText(alterarEspec.getDescricao());
            especializacao.setIdEspec(alterarEspec.getIdEspec());
        }else{
            btnSalvar.setText("SALVAR");
        }
    }

    public void cadastrar(View view){
        String descricao = editDesc.getText().toString();
        especializacao.setDescricao(descricao);
        if(btnSalvar.getText().toString().equals("SALVAR")) {
            helper.insereEspec(especializacao);
            Toast toast = Toast.makeText(ActivityCad_Espec.this,
                    "Especialização cadastrada com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            helper.atualizarEspec(especializacao);
            helper.close();
        }
        limpar();
        finish();
    }

    public void limpar(){
        editDesc.setText("");
    }
    public void cancelar(View view) {
        finish();
    }
}