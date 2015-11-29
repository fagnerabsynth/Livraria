package br.unicid.livraria.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.CategoriaAdapter;
import br.unicid.livraria.Model.CategoriaMOD;
import br.unicid.livraria.R;

public class ListarCategoria extends AppCompatActivity {

    protected static final int Opcao1 = 1;
    protected static final int Opcao2 = 2;
    protected static final int Opcao3 = 3;
    private EditText pesquisarCategoria;
    private ListView listView;
    private DataBase con;

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
        con = new DataBase(this);
        ArrayList<CategoriaMOD> cat;
        if (TextUtils.isEmpty(pesquisa)) {
            cat = con.pesquisaCategoria();
        } else {
            cat = con.pesquisaCategoria(pesquisa);
        }
        CategoriaAdapter adapter = new CategoriaAdapter(this, cat);
        listView = (ListView) findViewById(R.id.listaCategoria);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                View rowView = listView.getChildAt(position);
                TextView textview = (TextView) rowView.findViewById(R.id.ids);
                String ids = textview.getText().toString();
                TextView descr = (TextView) rowView.findViewById(R.id.descricao);
                String desc = descr.getText().toString();
                TextView cate = (TextView) rowView.findViewById(R.id.categoria);
                String cat = cate.getText().toString();

                CategoriaMOD cm = new CategoriaMOD();
                cm.id = Integer.parseInt(ids);
                cm.descricao = desc;
                cm.categoria = cat;

                IniciarCategoria(cm);


            }
        });


        registerForContextMenu(listView);

        listView.setAdapter(adapter);
    }

    private void IniciarCategoria(CategoriaMOD cm) {
        Intent intentado = new Intent(this, Categoria.class);
        intentado.putExtra("id", "" + cm.id);
        intentado.putExtra("categoria", cm.categoria);
        intentado.putExtra("descricao", cm.descricao);
        startActivity(intentado);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecione:");
        menu.add(Menu.NONE, Opcao1, 0, "Alterar categoria");
        menu.add(Menu.NONE, Opcao2, 1, "Apagar categoria");
        menu.add(Menu.NONE, Opcao3, 2, "Cancelar");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = menuInfo.position;
        View rowView = listView.getChildAt(id);
        TextView textview = (TextView) rowView.findViewById(R.id.ids);
        String ids = textview.getText().toString();
        TextView descr = (TextView) rowView.findViewById(R.id.descricao);
        String desc = descr.getText().toString();

        TextView cate = (TextView) rowView.findViewById(R.id.categoria);
        String cat = cate.getText().toString();

        switch (item.getItemId()) {
            case Opcao1:
                Intent intentado = new Intent(this, EdicaoCategoria.class);
                intentado.putExtra("id", "" + ids);
                intentado.putExtra("categoria", cat);
                intentado.putExtra("descricao", desc);
                startActivityForResult(intentado, 1);
                break;
            case Opcao2:
                if (con.apagaCategoria(Integer.parseInt(ids))) {
                    Toast.makeText(this, "A categoria selecionada: \"" + cat + "\"\nfoi removida com sucesso!", Toast.LENGTH_LONG).show();
                    iniciar();
                } else {
                    Toast.makeText(this, "Não foi possivel apagar produto selecionado: \"" + cat + "\"\n\nPor favor, tente novamente!", Toast.LENGTH_LONG).show();
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
