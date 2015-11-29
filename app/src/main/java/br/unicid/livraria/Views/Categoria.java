package br.unicid.livraria.Views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.unicid.livraria.Inicial;
import br.unicid.livraria.R;

public class Categoria extends AppCompatActivity {

    private TextView Categoria, Descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria);

        Categoria = (TextView) findViewById(R.id.Categoria);
        Descricao = (TextView) findViewById(R.id.Descricao);

        Bundle intent = getIntent().getExtras();
        String categoria = intent.getString("categoria");
        String descricao = intent.getString("descricao");

        Categoria.setText(categoria);
        Descricao.setText(descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Pesquisa categoria");
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
