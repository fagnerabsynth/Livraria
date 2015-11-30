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

public class Sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobre);
    }


    public void irKelwin(View btn){

        Intent pulo = new Intent(this, Kelwin.class);
        startActivity(pulo);
    }


    public void irFagner(View btn){

        Intent pulo = new Intent(this, Fagner.class);
        startActivity(pulo);
    }

    public void irEdu(View btn){

        Intent pulo = new Intent(this, Eduardo.class);
        startActivity(pulo);
    }

    public void irLeandro (View btn){

        Intent pulo = new Intent(this, Leandro.class);
        startActivity(pulo);
    }

    public void irRenan (View btn){

        Intent pulo = new Intent(this, Renan.class);
        startActivity(pulo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Sobre o APP");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        return true;
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
