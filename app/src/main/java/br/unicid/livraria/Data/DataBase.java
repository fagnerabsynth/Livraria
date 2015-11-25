package br.unicid.livraria.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Fagner on 11/11/2015.
 */


public class DataBase extends SQLiteOpenHelper {


    private static final String DB = "bancodedados.db";
    private static final int VERSAO = 1;
    private String TABELA_PRODUTOS = "produtos", TABELA_CATEGORIA = "categoria", TABELA_USUARIOS = "login", TABELA_ALUNOS = "alunos";
    private SQLiteDatabase db;


    public DataBase(Context context) {
        super(context, DB, null, VERSAO);
    }

    //Trabalha com MD5 para comparaçao - diferenciando maiusculas e minusculas para a senha
    public static String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onCreate(SQLiteDatabase dbA) {
        db = dbA;

        //Cria tabela dos usuarios
        //adicionar o valor unique para impedir usuarios com o menos login... deixa de modo unico
        String query1 = "Create table " + TABELA_USUARIOS + " (id integer primary key autoincrement, usuario text not null unique, senha text not null )";
        db.execSQL(query1);

        //adiciona o usuario admin e a senha admin
        Cursor rs = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS, null);
        if (rs.getCount() == 0) {
            String[] dadosAdmin = {"admin", md5("admin")};
            db.execSQL("Insert into " + TABELA_USUARIOS + "(email,senha) values (?,?)", dadosAdmin);
        }





    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //verifica o login do usuario
    public boolean login(String usuario, String senha) {
        db = this.getWritableDatabase();
        String[] dados = {usuario, md5(senha)};
        Cursor rs = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS + " where usuario = ? and senha = ?", dados);
        return rs.getCount() == 1;
    }


}
