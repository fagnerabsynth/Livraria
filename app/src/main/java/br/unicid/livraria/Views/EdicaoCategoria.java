package br.unicid.livraria.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.CategoriaMOD;
import br.unicid.livraria.R;

public class EdicaoCategoria extends AppCompatActivity {

    private EditText Categoria, Descricao;
    private String categoria, descricao;

    private int ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_categoria);
        Categoria = (EditText) findViewById(R.id.Categoria);
        Descricao = (EditText) findViewById(R.id.Descricao);

        Bundle intent = getIntent().getExtras();
        ids = Integer.parseInt(intent.getString("id"));
        categoria = intent.getString("categoria");
        descricao = intent.getString("descricao");

        Categoria.setText(categoria);
        Descricao.setText(descricao);
        Button v = (Button) findViewById(R.id.cadCat);
        v.setText("Editar Categoria");
    }

    public void criaCategoria(View v) {
        categoria = Categoria.getText().toString();
        descricao = Descricao.getText().toString();


        if (TextUtils.isEmpty(categoria)) {

            Toast.makeText(this, "O nome da categoria não pode ficar em branco!", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(descricao)) {
                descricao = " ";
            }
            DataBase c = new DataBase(this);
            CategoriaMOD dados = new CategoriaMOD();
            dados.id = ids;
            dados.descricao = descricao;
            dados.categoria = categoria;

            if (c.categoria(dados)) {
                setResult(AppCompatActivity.RESULT_OK, new Intent());
                Toast.makeText(this, "Categoria alterada com sucesso!", Toast.LENGTH_LONG).show();
                onBackPressed();
            } else {
                Toast.makeText(this, "Categoria encontrada no banco de dados!", Toast.LENGTH_LONG).show();
            }

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Cadastro de categoria");
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

