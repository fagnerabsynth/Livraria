package br.unicid.livraria.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.LivroAdapter;
import br.unicid.livraria.Model.LivroMOD;
import br.unicid.livraria.R;

public class ListarLivros extends AppCompatActivity {

    protected static final int Opcao1 = 1;
    protected static final int Opcao2 = 2;
    protected static final int Opcao3 = 3;
    private EditText pesquisarCategoria;
    private ListView listView;
    private DataBase con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_livros);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Listar livros");
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


    private void iniciar() {
        String pesquisa = pesquisarCategoria.getText().toString();
        con = new DataBase(this);
        ArrayList<LivroMOD> cat;

        if (TextUtils.isEmpty(pesquisa)) {
            cat = con.pesquisaLivro();

        } else {
            cat = con.pesquisaLivro(pesquisa);
        }


        LivroAdapter adapter = new LivroAdapter(this, cat);
        listView = (ListView) findViewById(R.id.listaLivro);




        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                View rowView = listView.getChildAt(position);
                TextView textview = (TextView) rowView.findViewById(R.id.ids);
                String ids = textview.getText().toString();

                LivroMOD cm = new LivroMOD();
                cm.id = Integer.parseInt(ids);


                iniciarLivro(cm);


            }
        });

        registerForContextMenu(listView);

        listView.setAdapter(adapter);

    }

    private void iniciarLivro(LivroMOD cm) {
        Intent intentado = new Intent(this, Livro.class);
        intentado.putExtra("id", "" + cm.id);
        startActivity(intentado);
    }



}
