package br.unicid.livraria.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.unicid.livraria.Inicial;
import br.unicid.livraria.R;


public class Principal extends AppCompatActivity {

    boolean sessao;

    private ImageButton btnEncerrar;
    private TextView txtEncerrar;


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
        btnEncerrar = (ImageButton) findViewById(R.id.btnEncerrar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("sessao", 0);
        String valor = pref.getString("sessao", null);

        txtEncerrar = (TextView) findViewById(R.id.txtEncerrar);


        sessao = !TextUtils.isEmpty(valor);
        if (sessao) {
            txtEncerrar.setText("Fechar Sessão");
        } else {
            txtEncerrar.setText("Fechar APP");
        }

    }



    public void catalogoLivros(View btn) {
        Intent i = new Intent(this, ListarTudo.class);
        startActivity(i);
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
        if (sessao) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("sessao", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("sessao");
            editor.commit();
            Intent intentado = new Intent(this, Inicial.class);
            startActivity(intentado);
            finish();
        } else {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            System.exit(0);
        }

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
