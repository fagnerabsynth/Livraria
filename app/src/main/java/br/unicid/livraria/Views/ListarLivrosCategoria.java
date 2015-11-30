package br.unicid.livraria.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.CategoriaMOD;
import br.unicid.livraria.Model.LivroAdapter;
import br.unicid.livraria.Model.LivroMOD;
import br.unicid.livraria.R;

public class ListarLivrosCategoria extends AppCompatActivity {

    protected static final int Opcao1 = 1;
    protected static final int Opcao2 = 2;
    protected static final int Opcao3 = 3;
    private Spinner spinner;
    private ListView listView;
    private DataBase con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_livros_categoria);

        con = new DataBase(this);
        spinner = (Spinner) findViewById(R.id.pesquisarCategoria);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                iniciar();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        criaSpinner();

    }


    private void criaSpinner() {
        spinner.setPrompt("Seleciona uma categoria");

        List<String> list = new ArrayList<String>();
        list.add("");


        ArrayList<CategoriaMOD> dados = con.pesquisaCategoria();
        if (!dados.isEmpty()) {
            for (CategoriaMOD x : dados) {
                list.add(x.categoria);
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

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
        String pesquisa = spinner.getSelectedItem().toString();
        ArrayList<LivroMOD> cat;

        if (TextUtils.isEmpty(pesquisa)) {
            cat = con.pesquisaLivro();

        } else {
            cat = con.pesquisaLivro(pesquisa, 0);
        }


        LivroAdapter adapter = new LivroAdapter(this, cat);
        listView = (ListView) findViewById(R.id.listaLivro);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("sessao", 0);
        String valor = pref.getString("sessao", null);

        boolean sessao = !TextUtils.isEmpty(valor);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                View rowView = listView.getChildAt(position);
                TextView textview = (TextView) rowView.findViewById(R.id.ids);
                String ids = textview.getText().toString();

                LivroMOD cm = new LivroMOD();
                cm = con.pesquisaLivro(Integer.parseInt(ids));


                iniciarLivro(cm);


            }
        });
        if (sessao) {
            registerForContextMenu(listView);

        }
        listView.setAdapter(adapter);

    }

    private void iniciarLivro(LivroMOD cm) {
        Intent intentado = new Intent(this, Livro.class);
        intentado.putExtra("titulo", "" + cm.titulo);
        intentado.putExtra("subtitulo", "" + cm.subtitulo);
        intentado.putExtra("autor", "" + cm.autor);
        intentado.putExtra("paginas", "" + cm.paginas);
        intentado.putExtra("edicao", "" + cm.edicao);
        intentado.putExtra("ano", "Ano: " + cm.ano);
        intentado.putExtra("categoria", "" + cm.categoria);
        intentado.putExtra("isbn", "" + cm.isbn);
        intentado.putExtra("imagem", "" + cm.imagem);
        startActivity(intentado);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecione:");
        menu.add(Menu.NONE, Opcao1, 0, "Alterar livro");
        menu.add(Menu.NONE, Opcao2, 1, "Apagar livro");
        menu.add(Menu.NONE, Opcao3, 2, "Cancelar");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = menuInfo.position;
        View rowView = listView.getChildAt(id);
        TextView textview = (TextView) rowView.findViewById(R.id.ids);
        String ids = textview.getText().toString();

        TextView titu = (TextView) rowView.findViewById(R.id.titulo);
        String liv = titu.getText().toString();

        switch (item.getItemId()) {
            case Opcao1:
                Intent intentado = new Intent(this, EdicaoLivro.class);
                LivroMOD cm = con.pesquisaLivro(Integer.parseInt(ids));
                intentado.putExtra("id", "" + cm.id);
                intentado.putExtra("titulo", "" + cm.titulo);
                intentado.putExtra("subtitulo", "" + cm.subtitulo);
                intentado.putExtra("autor", "" + cm.autor);
                intentado.putExtra("editora", "" + cm.editora);
                intentado.putExtra("paginas", "" + cm.paginas);
                intentado.putExtra("edicao", "" + cm.edicao);
                intentado.putExtra("ano", "" + cm.ano);
                intentado.putExtra("categoria", "" + cm.categoria);
                intentado.putExtra("isbn", "" + cm.isbn);
                intentado.putExtra("imagem", "" + cm.imagem);
                startActivityForResult(intentado, 1);
                break;
            case Opcao2:
                if (con.apagaLivro(Integer.parseInt(ids))) {
                    Toast.makeText(this, "o livro selecionada: \"" + liv + "\"\nfoi removida com sucesso!", Toast.LENGTH_LONG).show();
                    iniciar();
                } else {
                    Toast.makeText(this, "Não foi possivel apagar livro selecionado: \"" + liv + "\"\n\nPor favor, tente novamente!", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                iniciar();
            }
        }
    }


}
