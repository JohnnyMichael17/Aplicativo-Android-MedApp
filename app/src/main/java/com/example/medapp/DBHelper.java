package com.example.medapp;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MedApp.db";
    private static final String TABLE_ESPEC = "espec";
    private static final String COL_ESPEC_ID = "especId";
    private static final String COL_DESCRICAO = "descricao";

    private static final String TABLE_MEDICO = "medico";
    private static final String COL_MED_ID = "idMed";
    private static final String COL_ID_ESPEC = "especId";
    private static final String COL_NOME = "nome";
    private static final String COL_TELEFONE = "telefone";
    private static final String COL_IDADE = "idade";
    private static final String COL_ENDERECO = "endereco";

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    private static final String TABLE_CREATE="create table " + TABLE_ESPEC +
            "("+COL_ESPEC_ID+" integer primary key autoincrement, "+
            COL_DESCRICAO + " text not null);";

    private static final String TABLE_CREATE2="create table " + TABLE_MEDICO +
            "("+COL_MED_ID+" integer primary key autoincrement, "+
            COL_ID_ESPEC + " integer references " + TABLE_ESPEC + "," +
            COL_NOME + " text not null, " +
            COL_TELEFONE + " integer not null, " +
            COL_IDADE + " integer not null, " +
            COL_ENDERECO + " text not null);";


    SQLiteDatabase db;
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE2);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_ESPEC;
        String query2 = "DROP TABLE IF EXISTS " + TABLE_MEDICO;
        db.execSQL(query);
        db.execSQL(query2);
        this.onCreate(db);
    }

    public void insereEspec(Especializacao espec){
        db = this.getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues values= new ContentValues();
            values.put(COL_DESCRICAO, espec.getDescricao());
            db.insertOrThrow(TABLE_ESPEC, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e) {
            Log.d(TAG, "Erro ao inserir uma especialização");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<Especializacao> buscarEspec(){
        String[] colunas = {COL_ESPEC_ID,COL_DESCRICAO};
        Cursor cursor = getReadableDatabase().query(TABLE_ESPEC, colunas,null, null, null,null,"upper(especId)");
        ArrayList<Especializacao> list = new ArrayList<Especializacao>();
        while(cursor.moveToNext()){
            Especializacao espec = new Especializacao();
            espec.setIdEspec(cursor.getInt(0));
            espec.setDescricao(cursor.getString(1));
            list.add(espec);
        }
        return list;
    }

    public int verificarEspec(int num){
        String select = "SELECT * FROM " + TABLE_MEDICO + " WHERE "+ COL_ID_ESPEC + " = " + num;
        ArrayList<Medico> list = new ArrayList<Medico>();
        SQLiteDatabase dbT = this.getWritableDatabase();
        Cursor cursor = dbT.rawQuery(select, null);
        return cursor.getCount();
    }

    public long excluirEspec(Especializacao espec){
        if(verificarEspec(espec.idEspec) == 0) {
            long retornaDB;
            db = this.getWritableDatabase();
            String[] args = {String.valueOf(espec.getIdEspec())};
            retornaDB = db.delete(TABLE_ESPEC, COL_ESPEC_ID + "=?", args);
            return retornaDB;
        }
        return 999;
    }

    public long atualizarEspec(Especializacao espec){
        long retornaDB;
        db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_DESCRICAO, espec.getDescricao());
        String[] args={String.valueOf(espec.getIdEspec())};
        retornaDB=db.update(TABLE_ESPEC, values,COL_ESPEC_ID+"=?", args);
        db.close();
        return  retornaDB;
    }

    public void insereMed(Medico medico){
        db = this.getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues values = new ContentValues();
            values.put(COL_ID_ESPEC, medico.getIdEspec());
            values.put(COL_NOME, medico.getNome());
            values.put(COL_TELEFONE, medico.getTelefone());
            values.put(COL_IDADE, medico.getIdade());
            values.put(COL_ENDERECO, medico.getEndereco());
            db.insertOrThrow(TABLE_MEDICO, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e) {
            Log.d(TAG, "Erro ao cadastrar um médico");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<Medico> buscarMed(){
        String[] colunas = {COL_MED_ID,COL_ID_ESPEC,COL_NOME, COL_TELEFONE, COL_IDADE, COL_ENDERECO};
        Cursor cursor = getReadableDatabase().query(TABLE_MEDICO, colunas,null, null, null,null,"upper(telefone)");
        ArrayList<Medico> list = new ArrayList<Medico>();
        while(cursor.moveToNext()){
            Medico medico = new Medico();
            medico.setIdMed(cursor.getInt(0));
            medico.setIdEspec(cursor.getInt(1));
            medico.setNome(cursor.getString(2));
            medico.setTelefone(cursor.getString(3));
            medico.setIdade(cursor.getInt(4));
            medico.setEndereco(cursor.getString(5));
            list.add(medico);
        }
        return list;
    }

    public long excluirMed(Medico medico){
        long retornaDB;
        db=this.getWritableDatabase();
        String[] args={String.valueOf(medico.getIdMed())};
        retornaDB=db.delete(TABLE_MEDICO, COL_MED_ID+"=?", args);
        return  retornaDB;
    }

    public long atualizarMed(Medico medico){
        long retornaDB;
        db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_ESPEC, medico.getIdEspec());
        values.put(COL_NOME, medico.getNome());
        values.put(COL_TELEFONE, medico.getTelefone());
        values.put(COL_IDADE, medico.getIdade());
        values.put(COL_ENDERECO, medico.getEndereco());

        String[] args={String.valueOf(medico.getIdMed())};
        retornaDB=db.update(TABLE_MEDICO, values,COL_MED_ID+"=?", args);
        db.close();
        return  retornaDB;
    }
}
