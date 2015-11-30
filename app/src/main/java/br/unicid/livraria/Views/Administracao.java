package br.unicid.livraria.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.unicid.livraria.Inicial;
import br.unicid.livraria.R;


public class Administracao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administracao);


    }

    public void cadastroLivros(View btn) {
        Intent pulo = new Intent(this, CadastroLivro.class);
        startActivity(pulo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Administração");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //getMenuInflater().inflate(R.menu.administracao, menu);
        return true;
    }

    public void listarLivros(View e) {
        Intent i = new Intent(this, ListarLivros.class);
        startActivity(i);
    }

    public void alterarSenha(View v) {
        Intent i = new Intent(this, AlteraSenhaLogado.class);
        startActivity(i);
    }


    public void perfil(View e) {
        Intent i = new Intent(this, DadosDoUsuario.class);
        startActivity(i);
    }

    public void listarCategoria(View e) {
        Intent i = new Intent(this, ListarCategoria.class);
        startActivity(i);
    }

    public void cadastraCategoria(View e) {
        Intent i = new Intent(this, CadastroCategoria.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:

                int id = item.getItemId();
                if (id == R.id.action_settings) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }
    }
}
