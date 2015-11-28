package br.unicid.livraria.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import br.unicid.livraria.Inicial;
import br.unicid.livraria.R;


public class Principal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Principal");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        //   getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    public void catalogoLivros(View btn) {

    }

    public void administrar(View btn) {
        Intent pulo;

        if (Inicial.sessao()) {
            pulo = new Intent(this, Administracao.class);
        } else {
            pulo = new Intent(this, Login.class);
        }

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
