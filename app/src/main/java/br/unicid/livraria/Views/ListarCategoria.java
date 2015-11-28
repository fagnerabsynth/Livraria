package br.unicid.livraria.Views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.CategoriaAdapter;
import br.unicid.livraria.Model.CategoriaMOD;
import br.unicid.livraria.R;

public class ListarCategoria extends AppCompatActivity {

    private EditText pesquisarCategoria;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_categoria);
        pesquisarCategoria = (EditText) findViewById(R.id.pesquisarCategoria);

        pesquisarCategoria.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iniciar();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        iniciar();
    }

    private void iniciar() {
        String pesquisa = pesquisarCategoria.getText().toString();
        DataBase c = new DataBase(this);
        ArrayList<CategoriaMOD> cat;
        if (TextUtils.isEmpty(pesquisa)) {
            cat = c.pesquisaCategoria();
        } else {
            cat = c.pesquisaCategoria(pesquisa);
        }
        CategoriaAdapter adapter = new CategoriaAdapter(this, cat);
        listView = (ListView) findViewById(R.id.listaCategoria);
        listView.setAdapter(adapter);
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
