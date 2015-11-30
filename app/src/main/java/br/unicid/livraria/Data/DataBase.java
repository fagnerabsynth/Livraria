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

import br.unicid.livraria.Model.AlunosMOD;
import br.unicid.livraria.Model.CategoriaMOD;
import br.unicid.livraria.Model.LivroMOD;
import br.unicid.livraria.Model.LoginMOD;
import br.unicid.livraria.Views.Login;

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
                "senha text not null," +
                "nome text not null," +
                "imagem text," +
                "email text not null unique," +
                "telefone text not null," +
                "endereco text not null," +
                "complemento text," +
                "bairro text not null," +
                "cidade text not null," +
                "cep text not null," +
                "estado text not null)";


        db.execSQL(query1);
        //adiciona o usuario admin e a senha admin
        Cursor rs1 = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS, null);
        if (rs1.getCount() == 0) {
            String[] dadosAdmin = {md5("admin"), "Administrador", "", "admin@admin.com", "0000-0000", "servidorlandia", "", "Vila PC", "Sum Paulu", "SP", "03309-000"};
            db.execSQL("Insert into " + TABELA_USUARIOS + "(senha,nome,imagem,email,telefone,endereco,complemento,bairro,cidade,estado,cep) values (?,?,?,?,?,?,?,?,?,?,?)", dadosAdmin);
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


        //INCIO DA TABELA CATEGORIA

        //Cria a tabela livro
        String query4 = "Create table " + TABELA_PRODUTOS + " (" +
                "id integer primary key autoincrement, " +
                "titulo text unique not null," +
                "isbn text not null," +
                "subtitulo text not null," +
                "edicao text not null," +
                "autor text not null," +
                "ano integer not null," +
                "paginas integer not null," +
                "editora text not null," +
                "imagem text not null," +
                "categoria text not null )";


        db.execSQL(query4);

        //Livros


        //INCIO DA TABELA ALUNO

        //Cria a tabela alunos
        String query3 = "Create table " + TABELA_ALUNOS + " (id integer primary key autoincrement,celular text not null, nome text not null ,ca text not null, imagem text not null )";
        db.execSQL(query3);

        // vasculha para ver se o banco de dados esta vazio para inserir os alunos
        Cursor rs3 = db.rawQuery("SELECT * FROM " + TABELA_ALUNOS, null);

        if (rs3.getCount() == 0) {
            //coloca os itens dentro de um array
            List<String[]> alunos = new ArrayList<String[]>();
            //adiciona os alunos rg e imagem do drawable
            alunos.add(new String[]{"Ricardo Fagner Castelo Branco", "14026201", "fagner", "998107418"});
            alunos.add(new String[]{"Eduardo de Souza Santos", "14019302", "eduardo", "970178379"});
            alunos.add(new String[]{"Kelwin Miranda de Holanda", "14020670", "kelwin", "995739406"});
            alunos.add(new String[]{"Leandro Silva de Oliveira", "15738558", "leandro", "958648822"});
            alunos.add(new String[]{"André William Silva Gundim", "14004542", "andre", "987943992"});
            alunos.add(new String[]{"Renan Ribeiro Pereira", "14022133", "renan", "952901937"});
            //da um loop p inserir cada aluno na tabela
            for (String[] x : alunos) {
                db.execSQL("Insert into " + TABELA_ALUNOS + "(nome,ca,imagem,celular) values (?,?,?,?)", x);
            }
        }

        //FIM DA TABELA ALUNO


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public AlunosMOD alunos(String i) {
        db = getWritableDatabase();
        AlunosMOD temp;
        temp = new AlunosMOD();
        Cursor rs;
        rs = db.rawQuery("select * from " + TABELA_ALUNOS + " where id=?", new String[]{"" + i});
        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {
                do {
                    temp = new AlunosMOD();
                    temp.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                    temp.nome = rs.getString(rs.getColumnIndex("nome"));
                    temp.celular = rs.getString(rs.getColumnIndex("celular"));
                    temp.ca = rs.getString(rs.getColumnIndex("ca"));
                    temp.imagem = rs.getString(rs.getColumnIndex("imagem"));
                } while (rs.moveToNext());
            }

        }
        return temp;
    }

    public ArrayList<AlunosMOD> alunos() {
        db = getReadableDatabase();
        ArrayList<AlunosMOD> lista = new ArrayList<>();
        Cursor rs = db.rawQuery("SELECT * FROM " + TABELA_ALUNOS + " ORDER BY RANDOM() ", null);
        AlunosMOD temp = new AlunosMOD();

        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {
                do {
                    temp = new AlunosMOD();
                    temp.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                    temp.nome = rs.getString(rs.getColumnIndex("nome"));
                    temp.celular = rs.getString(rs.getColumnIndex("celular"));
                    temp.ca = rs.getString(rs.getColumnIndex("ca"));
                    temp.imagem = rs.getString(rs.getColumnIndex("imagem"));
                    lista.add(temp);
                } while (rs.moveToNext());
            }

        }
        return lista;
    }


    //retorna os dados login do usuario
    public LoginMOD login(String email) {
        db = this.getWritableDatabase();
        String[] dados = {email};
        Cursor rs = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS + " where email = ? limit 0,1", dados);
        LoginMOD l = new LoginMOD();
        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {
                do {
                    l.bairro = rs.getString(rs.getColumnIndex("bairro"));
                    l.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                    l.cep = rs.getString(rs.getColumnIndex("cep"));
                    l.cidade = rs.getString(rs.getColumnIndex("cidade"));
                    l.complemento = rs.getString(rs.getColumnIndex("complemento"));
                    l.senha = rs.getString(rs.getColumnIndex("senha"));
                    l.email = rs.getString(rs.getColumnIndex("email"));
                    l.endereco = rs.getString(rs.getColumnIndex("endereco"));
                    l.estado = rs.getString(rs.getColumnIndex("estado"));
                    l.imagem = rs.getString(rs.getColumnIndex("imagem"));
                    l.nome = rs.getString(rs.getColumnIndex("nome"));
                    l.telefone = rs.getString(rs.getColumnIndex("telefone"));
                } while (rs.moveToNext());
            }
        }
        return l;
    }


    //verifica o login do usuario
    public boolean login(String usuario, String senha) {
        db = this.getWritableDatabase();
        String[] dados = {usuario, md5(senha)};
        Cursor rs = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS + " where email = ? and senha = ? limit 0,1", dados);
        boolean r = rs.getCount() == 1;
        if (r) {

            if (rs.getCount() > 0) {
                if (rs.moveToFirst()) {
                    do {
                        Login.Registra(rs.getString(rs.getColumnIndex("email")), rs.getString(rs.getColumnIndex("nome")));
                    } while (rs.moveToNext());
                }
            }


        }
        return r;
    }


    //compara se existe um usuario e senha... retorna um boolean
    public boolean pesquisaUsuario(String usuario) {
        db = this.getWritableDatabase();
        String[] dados = {usuario};
        Cursor rs = db.rawQuery("SELECT * FROM " + TABELA_USUARIOS + " where email = ? ", dados);
        return rs.getCount() == 1;
    }

    //altera a senha de um usuario... retorna um boolean
    public boolean alteraSenha(String usuario, String senha) {
        db = this.getWritableDatabase();
        String[] dados = {md5(senha), usuario};
        try {
            db.execSQL("UPDATE " + TABELA_USUARIOS + " SET senha = ? where email = ? ", dados);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean alteraSenha(int id, String senha) {
        db = this.getWritableDatabase();
        String[] dados = {md5(senha), "" + id};
        try {
            db.execSQL("UPDATE " + TABELA_USUARIOS + " SET senha = ? where id = ? ", dados);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    //altera tudo menos as senha
    public boolean alteraSenha(LoginMOD d) {
        db = this.getWritableDatabase();
        String[] dados = {d.imagem, d.endereco, d.email, d.complemento, d.cep, d.bairro, d.nome, d.estado, d.telefone, d.cidade, "" + d.id};
        try {
            db.execSQL("UPDATE " + TABELA_USUARIOS + " SET imagem=?,endereco=?," +
                    "email=?," +
                    "complemento=?," +
                    "cep=?," +
                    "bairro=?," +
                    "nome=?," +
                    "estado=?," +
                    "telefone=?," +
                    "cidade=? where id = ? ", dados);
            Login.Registra(d.email, d.nome);

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
                db.execSQL("UPDATE " + TABELA_CATEGORIA + " SET categoria=?,descricao=? where id=?", new String[]{cat.categoria, cat.descricao, "" + cat.id});
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public CategoriaMOD pesquisaCategoria(String pesquisa, boolean d) {
        CategoriaMOD m = new CategoriaMOD();
        Cursor rs;
        rs = db.rawQuery("select * from " + TABELA_CATEGORIA + " where categoria = ? order by categoria asc limit 0,1", new String[]{pesquisa});

        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {

                do {
                    m = new CategoriaMOD();
                    m.categoria = rs.getString(rs.getColumnIndex("categoria"));
                    m.descricao = rs.getString(rs.getColumnIndex("descricao"));
                    m.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                } while (rs.moveToNext());

            }
        }

        return m;
    }


    public CategoriaMOD pesquisaCategoria(int pesquisa) {
        CategoriaMOD m = new CategoriaMOD();
        Cursor rs;
        rs = db.rawQuery("select * from " + TABELA_CATEGORIA + " where id = ?", new String[]{"" + pesquisa});

        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {

                do {
                    m = new CategoriaMOD();
                    m.categoria = rs.getString(rs.getColumnIndex("categoria"));
                    m.descricao = rs.getString(rs.getColumnIndex("descricao"));
                    m.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                } while (rs.moveToNext());

            }
        }

        return m;
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

    public boolean apagaCategoria(int id) {
        db = getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABELA_CATEGORIA + "   " +
                    "where id = ? ", new String[]{"" + id});
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean cadastraLivro(LivroMOD m) {
        db = getWritableDatabase();
        try {
            if (m.id == 0) {
                db.execSQL("insert into " + TABELA_PRODUTOS + " " +
                        "(titulo,isbn,subtitulo,edicao,autor,paginas,editora,imagem,categoria,ano)" +
                        " values (?,?,?,?,?,?,?,?,?,?)", new String[]{m.titulo, m.isbn, m.subtitulo, m.edicao, m.autor, "" + m.paginas, m.editora, m.imagem, "" + m.categoria, "" + m.ano});
            } else {
                db.execSQL("update " + TABELA_PRODUTOS + " set " +
                        "titulo=?,isbn=?,subtitulo=?,edicao=?,autor=?,paginas=?,editora=?,imagem=?,categoria=?,ano=? " +
                        "where id=?", new String[]{m.titulo, m.isbn, m.subtitulo, m.edicao, m.autor, "" + m.paginas, m.editora, m.imagem, "" + m.categoria, "" + m.ano, "" + m.id});
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public ArrayList<LivroMOD> pesquisaLivro(String pesquisa) {
        db = this.getWritableDatabase();
        ArrayList<LivroMOD> retorno = new ArrayList<LivroMOD>();
        String query;
        Cursor rs;
        if (TextUtils.isEmpty(pesquisa)) {
            rs = db.rawQuery("select * from " + TABELA_PRODUTOS + " order by titulo asc", null);
        } else {
            rs = db.rawQuery("select * from " + TABELA_PRODUTOS + " where titulo like ? order by titulo asc", new String[]{"%" + pesquisa.replace(" ", "%") + "%"});
        }
        LivroMOD cat;
        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {
                do {
                    cat = new LivroMOD();
                    cat.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                    cat.categoria = rs.getString(rs.getColumnIndex("categoria"));
                    cat.ano = Integer.parseInt(rs.getString(rs.getColumnIndex("ano")));
                    cat.paginas = Integer.parseInt(rs.getString(rs.getColumnIndex("paginas")));
                    cat.titulo = rs.getString(rs.getColumnIndex("titulo"));
                    cat.editora = rs.getString(rs.getColumnIndex("editora"));
                    cat.edicao = rs.getString(rs.getColumnIndex("edicao"));
                    cat.autor = rs.getString(rs.getColumnIndex("autor"));
                    cat.imagem = rs.getString(rs.getColumnIndex("imagem"));
                    cat.isbn = rs.getString(rs.getColumnIndex("isbn"));
                    cat.subtitulo = rs.getString(rs.getColumnIndex("subtitulo"));
                    retorno.add(cat);
                } while (rs.moveToNext());
            }
        }
        return retorno;
    }


    public ArrayList<LivroMOD> pesquisaLivro(String pesquisa, int x) {
        db = this.getWritableDatabase();
        ArrayList<LivroMOD> retorno = new ArrayList<LivroMOD>();
        String query;
        Cursor rs;
        if (TextUtils.isEmpty(pesquisa)) {
            rs = db.rawQuery("select * from " + TABELA_PRODUTOS + " order by titulo asc", null);
        } else {
            rs = db.rawQuery("select * from " + TABELA_PRODUTOS + " where categoria = ? order by titulo asc", new String[]{pesquisa});
        }
        LivroMOD cat;
        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {
                do {
                    cat = new LivroMOD();
                    cat.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                    cat.categoria = rs.getString(rs.getColumnIndex("categoria"));
                    cat.ano = Integer.parseInt(rs.getString(rs.getColumnIndex("ano")));
                    cat.paginas = Integer.parseInt(rs.getString(rs.getColumnIndex("paginas")));
                    cat.titulo = rs.getString(rs.getColumnIndex("titulo"));
                    cat.editora = rs.getString(rs.getColumnIndex("editora"));
                    cat.edicao = rs.getString(rs.getColumnIndex("edicao"));
                    cat.autor = rs.getString(rs.getColumnIndex("autor"));
                    cat.imagem = rs.getString(rs.getColumnIndex("imagem"));
                    cat.isbn = rs.getString(rs.getColumnIndex("isbn"));
                    cat.subtitulo = rs.getString(rs.getColumnIndex("subtitulo"));
                    retorno.add(cat);
                } while (rs.moveToNext());
            }
        }
        return retorno;
    }


    public ArrayList<LivroMOD> pesquisaLivro() {
        return pesquisaLivro(null);
    }


    public LivroMOD pesquisaLivro(int i) {
        LivroMOD cat = new LivroMOD();
        Cursor rs;
        rs = db.rawQuery("select * from " + TABELA_PRODUTOS + " where id=?", new String[]{"" + i});

        if (rs.getCount() > 0) {
            if (rs.moveToFirst()) {
                do {
                    cat.id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
                    cat.categoria = rs.getString(rs.getColumnIndex("categoria"));
                    cat.ano = Integer.parseInt(rs.getString(rs.getColumnIndex("ano")));
                    cat.paginas = Integer.parseInt(rs.getString(rs.getColumnIndex("paginas")));
                    cat.titulo = rs.getString(rs.getColumnIndex("titulo"));
                    cat.editora = rs.getString(rs.getColumnIndex("editora"));
                    cat.edicao = rs.getString(rs.getColumnIndex("edicao"));
                    cat.autor = rs.getString(rs.getColumnIndex("autor"));
                    cat.imagem = rs.getString(rs.getColumnIndex("imagem"));
                    cat.isbn = rs.getString(rs.getColumnIndex("isbn"));
                    cat.subtitulo = rs.getString(rs.getColumnIndex("subtitulo"));

                } while (rs.moveToNext());
            }
        }
        return cat;
    }


    public boolean apagaLivro(int v) {
        db = getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABELA_PRODUTOS + "   " +
                    "where id = ? ", new String[]{"" + v});
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
