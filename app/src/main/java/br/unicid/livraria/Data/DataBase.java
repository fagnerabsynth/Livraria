package br.unicid.livraria.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import br.unicid.livraria.Model.CategoriaMOD;

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
    public void onCreate(SQLiteDatabase db) {

        //INCIO DA TABELA USUARIOS

        //Cria tabela dos usuarios

        //adicionei o valor unique para impedir usuarios com o menos login... deixa de modo unico
        String query1 = "Create table " + TABELA_USUARIOS + "( " +
                "id integer primary key autoincrement," +
                " usuario text not null unique, " +
                "senha text not null);";
        db.execSQL(query1);

        //adiciona o usuario admin e a senha admin
        Cursor rs1 = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS, null);
        if (rs1.getCount() == 0) {
            String[] dadosAdmin = {"admin", md5("admin")};
            db.execSQL("Insert into " + TABELA_USUARIOS + "(usuario,senha) values (?,?)", dadosAdmin);
        }

        //FIM DA TABELA USUARIOS


        //INCIO DA TABELA CATEGORIA

        //Cria a tabela categoria
        String query2 = "Create table " + TABELA_CATEGORIA + " (id integer primary key autoincrement, " +
                "categoria text not null unique," +
                "descricao text )";
        db.execSQL(query2);

        // vasculha para ver se o banco de dados esta vazio para inserir os alunos
        Cursor rs2 = db.rawQuery("SELECT * FROM " + TABELA_CATEGORIA, null);
        if (rs2.getCount() == 0) {
            List<String[]> categoria = new ArrayList<String[]>();
            categoria.add(new String[]{"Infantil", "Contos, Histórias e Brincadeira"});
            categoria.add(new String[]{"Informática", "Livros voltados a Tecnologia da Informação"});
            for (String[] m : categoria) {
                db.execSQL("Insert into " + TABELA_CATEGORIA + "(categoria,descricao) values (?,?)", m);
            }
        }

        //FIM DA TABELA CATEGORIA


        //INCIO DA TABELA ALUNO

        //Cria a tabela alunos
        String query3 = "Create table " + TABELA_ALUNOS + " (id integer primary key autoincrement, nome text not null ,ca text not null, imagem text not null )";
        db.execSQL(query3);

        // vasculha para ver se o banco de dados esta vazio para inserir os alunos
        Cursor rs3 = db.rawQuery("SELECT * FROM " + TABELA_ALUNOS, null);

        if (rs3.getCount() == 0) {
            //coloca os itens dentro de um array
            List<String[]> alunos = new ArrayList<String[]>();
            //adiciona os alunos rg e imagem do drawable
            alunos.add(new String[]{"Ricardo Fagner Castelo Bracno", "14026201", "fagner"});
            //da um loop p inserir cada aluno na tabela
            for (String[] x : alunos) {
                db.execSQL("Insert into " + TABELA_ALUNOS + "(nome,ca,imagem) values (?,?,?)", x);
            }
        }

        //FIM DA TABELA ALUNO


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //verifica o login do usuario
    public boolean login(String usuario, String senha) {
        db = this.getWritableDatabase();
        String[] dados = {usuario, md5(senha)};
        Cursor rs = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS + " where usuario = ? and senha = ?", dados);
        return rs.getCount() == 1;
    }


    //compara se existe um usuario e senha... retorna um boolean
    public boolean pesquisaUsuario(String usuario) {
        db = this.getWritableDatabase();
        String[] dados = {usuario};
        Cursor rs = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS + " where usuario = ? ", dados);
        return rs.getCount() == 1;
    }

    //altera a senha de um usuario... retorna um boolean
    public boolean alteraSenha(String usuario, String senha) {
        db = this.getWritableDatabase();
        String[] dados = {md5(senha), usuario};
        try {
            db.execSQL("UPDATE " + TABELA_USUARIOS + " SET senha = ? where usuario = ? ", dados);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean categoria(CategoriaMOD cat) {
        db = this.getWritableDatabase();
        try {
            if (cat.id == 0) {
                db.execSQL("insert into " + TABELA_CATEGORIA + " " +
                        "(categoria,descricao) values (?,?)" +
                        "", new String[]{cat.categoria, cat.descricao});
            } else {
                db.execSQL("update " + TABELA_CATEGORIA + " SET categoria = ?, " +
                        "descricao = ? " +
                        "where id = ? ", new String[]{cat.categoria, cat.descricao, "" + cat.id});
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ArrayList<CategoriaMOD> pesquisaCategoria(String pesquisa) {
        db = this.getWritableDatabase();
        ArrayList<CategoriaMOD> retorno = new ArrayList<CategoriaMOD>();
        String query;
        Cursor rs;
        if (TextUtils.isEmpty(pesquisa)) {
            rs = db.rawQuery("select * from " + TABELA_CATEGORIA + " order by categoria asc", null);
        } else {
            rs = db.rawQuery("select * from " + TABELA_CATEGORIA + " where categoria like ? order by categoria asc", new String[]{"%" + pesquisa.replace(" ", "%") + "%"});
        }
        CategoriaMOD cat;
        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {
                do {
                    cat = new CategoriaMOD();
                    cat.categoria = rs.getString(rs.getColumnIndex("categoria"));
                    cat.descricao = rs.getString(rs.getColumnIndex("descricao"));
                    cat.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                    retorno.add(cat);
                } while (rs.moveToNext());
            }
        }
        return retorno;
    }


    public ArrayList<CategoriaMOD> pesquisaCategoria() {
        return pesquisaCategoria(null);
    }


}
