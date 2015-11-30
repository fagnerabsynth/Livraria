package br.unicid.livraria.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.AlunosMOD;
import br.unicid.livraria.R;

public class Aluno extends AppCompatActivity {

    int id;
    private AlunosMOD a;
    private DataBase con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aluno);
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        inicia();
        setResult(AppCompatActivity.RESULT_OK, new Intent());

    }

    private void inicia() {
        con = new DataBase(this);
        a = con.alunos("" + id);
        Toast.makeText(this, "" + a.nome, Toast.LENGTH_SHORT).show();

        ImageView c = (ImageView) findViewById(R.id.imagem);
        c.setImageResource(getResources().getIdentifier(a.imagem, "drawable", this.getPackageName()));

        TextView m;
        m = (TextView) findViewById(R.id.nome);
        m.setText(a.nome);

        m = (TextView) findViewById(R.id.ca);
        m.setText("CA: " + a.ca);

        m = (TextView) findViewById(R.id.numero);
        m.setText(a.celular);


        getSupportActionBar().setSubtitle(a.nome);

    }


    public void ligar(View m) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + a.celular));
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());

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
