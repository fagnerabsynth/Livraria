package br.unicid.livraria;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {

    private EditText lblUsuario, lblSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        lblUsuario = (EditText) findViewById(R.id.lblUsuario);
        lblSenha = (EditText) findViewById(R.id.lblSenha);

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


}
