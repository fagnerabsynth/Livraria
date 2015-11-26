package br.unicid.livraria;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class Principal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
    }


    public void catalogoLivros(View btn) {
        Intent pulo = new Intent(this,Catalogo.class);

        startActivity(pulo);


    }

    public void administrar(View btn){
        Intent pulo = new Intent(this,Administracao.class);

        startActivity(pulo);


    }

    public void sobreAplicacao(View btn) {

        Intent pulo = new Intent(this,Sobre.class);

        startActivity(pulo);
    }

    public void finalizarSessao(View btn){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("sessao", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("sessao");
        editor.commit();

        Intent pulo = new Intent(this,Inicial.class);

        startActivity(pulo);
        finish();


    }





}
