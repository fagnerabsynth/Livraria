package br.unicid.livraria.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import br.unicid.livraria.Administracao;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.R;


public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fece0f")));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.saraivamini);

    }


    public void catalogoLivros(View btn) {
        Intent pulo = new Intent(this, CadastroLivro.class);
        startActivity(pulo);
    }

    public void administrar(View btn) {
        Intent pulo = new Intent(this, Administracao.class);
        startActivity(pulo);
    }

    public void sobreAplicacao(View btn) {

        // Intent pulo = new Intent(this,Sobre.class);

        ///startActivity(pulo);
    }

    public void finalizarSessao(View btn) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("sessao", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.commit();
        Intent pulo = new Intent(this, Inicial.class);

        startActivity(pulo);
        finish();


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
