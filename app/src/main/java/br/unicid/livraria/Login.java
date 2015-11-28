package br.unicid.livraria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private EditText lblUsuario, lblSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        lblUsuario = (EditText) findViewById(R.id.lblUsuario);
        lblSenha = (EditText) findViewById(R.id.lblSenha);

        getSupportActionBar().setTitle("Saravá");
        getSupportActionBar().setSubtitle("Principal");
        getSupportActionBar().setIcon(R.drawable.saraivamini);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void logIn(View btn) {

        String usuario, senha;



        usuario = lblUsuario.getText().toString();

        senha = lblSenha.getText().toString();

        String erro = "";

        if (TextUtils.isEmpty(usuario)) {

            erro += "O Campo Email não pode ficar em branco! ";
        } else if (!usuario.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            lblUsuario.setText("");

            if (erro != "") {
                erro += "\n";
            }

            erro += "O Email não é válido!";

        }

        if (TextUtils.isEmpty(senha)) {

            if (erro == "") {
                erro += "\n";

            }

            erro += "O campo Senha não pode ficar em branco!";

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //  getMenuInflater().inflate(R.menu.listar, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
