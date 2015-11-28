package br.unicid.livraria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;

import br.unicid.livraria.Views.Principal;

public class Inicial extends AppCompatActivity {

    private static boolean sessao = false;
    private String valor;

    public static String TITULO() {
        return "Saraiva";
    }

    public static String Cor() {
        return "#eece04";
    }

    public static boolean sessao() {
        return sessao;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Carregando...");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        //  getSupportActionBar().setHomeButtonEnabled(true);
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("sessao", 0);
        valor = pref.getString("sessao", null);

        sessao = !TextUtils.isEmpty(valor);


        if (!sessao) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    inicia();
                }
            }, 3000);

        } else {
            inicia();
        }

    }

    private void inicia() {

        Intent intentado;


        intentado = new Intent(this, Principal.class);

        startActivity(intentado);

        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
