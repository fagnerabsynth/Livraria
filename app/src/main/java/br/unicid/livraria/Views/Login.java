package br.unicid.livraria.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.R;

public class Login extends AppCompatActivity {

    private EditText lblUsuario, lblSenha;

    public void alterarSenha(View v) {
        Intent i;
        i = new Intent(this, AlteracaoSenhaEsquecida.class);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        lblUsuario = (EditText) findViewById(R.id.lblUsuario);
        lblSenha = (EditText) findViewById(R.id.lblSenha);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eece04")));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Login");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void logIn(View btn) {

        String usuario, senha;


        usuario = lblUsuario.getText().toString();

        senha = lblSenha.getText().toString();

        String erro = "";

        if (TextUtils.isEmpty(usuario)) {

            erro += "O Campo Usuário não pode ficar em branco! ";
        }

        if (TextUtils.isEmpty(senha)) {

            if (erro == "") {
                erro += "\n";

            }

            erro += "O campo Senha não pode ficar em branco!";

        }

        if (TextUtils.isEmpty(erro)) {
            DataBase conexao = new DataBase(this);

            if (!conexao.login(usuario, senha)) {
                Toast.makeText(this, "Usuário e Senha estão incorretos!!", Toast.LENGTH_SHORT).show();
            } else {
                Intent i;
                i = new Intent(this, Inicial.class);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("sessao", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("sessao", usuario);
                editor.commit();
                Toast.makeText(this, "Seja bem vindo " + usuario, Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }

        } else {
            Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
