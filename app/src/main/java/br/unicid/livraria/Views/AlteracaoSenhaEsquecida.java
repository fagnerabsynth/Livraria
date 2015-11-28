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
import android.widget.EditText;
import android.widget.Toast;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.R;

public class AlteracaoSenhaEsquecida extends AppCompatActivity {

    private EditText txtUsuario, txtSenha, txtConfSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alteracao_senha);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtConfSenha = (EditText) findViewById(R.id.txtConfSenha);

    }

    public void alterarAgora(View v) {
        DataBase c = new DataBase(this);

        String usuario = txtUsuario.getText().toString();
        String senha = txtSenha.getText().toString();
        String csenha = txtConfSenha.getText().toString();

        String erro = "";

        if (TextUtils.isEmpty(usuario)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Usuário não pode ficar em branco!";
        } else if (!c.pesquisaUsuario(usuario)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Usuário não foi encontrado em nossa base de dados!";
        }

        if (TextUtils.isEmpty(senha)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Senha não pode ficar em branco!";
        }


        if (TextUtils.isEmpty(csenha)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "A confirmação de senha não pode ficar em branco!";
        }


        if (!TextUtils.isEmpty(csenha) && !(TextUtils.isEmpty(senha))) {

            if (senha.length() < 4) {
                if (!TextUtils.isEmpty(erro)) {
                    erro += "\n";
                }
                erro += "A senha precisa ter no minimo 4 caracteres!";
            }

            if (!senha.equals(csenha)) {
                if (!TextUtils.isEmpty(erro)) {
                    erro += "\n";
                }
                erro += "As senhas não coesidem!";
            }

        }


        if (!TextUtils.isEmpty(erro)) {
            Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
        } else {
            String senhas;
            if (c.alteraSenha(usuario, senha)) {
                senhas = "Senha alterada com sucesso!";
                Intent i = new Intent(this, Inicial.class);
                startActivity(i);
                finish();
            } else {
                senhas = "Não foi possivel alterar sua senha, Por favor tente novamente!";
            }
            Toast.makeText(this, senhas, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Alterar senha");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //getMenuInflater().inflate(R.menu.administracao, menu);
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
