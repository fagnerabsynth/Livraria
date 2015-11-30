package br.unicid.livraria.Views;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.ConvertImage;
import br.unicid.livraria.Model.LoginMOD;
import br.unicid.livraria.R;

public class AlteraSenhaLogado extends AppCompatActivity {

    DataBase con;
    LoginMOD dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altera_senha_logado);
        con = new DataBase(this);
        inicia();
    }

    private void inicia() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("nome", 0);
        pref = getApplicationContext().getSharedPreferences("sessao", 0);
        dados = con.login(pref.getString("sessao", null));
        imagem();

    }

    public void alterarAgora(View b) {
        TextView txtSenhaAtual = (TextView) findViewById(R.id.txtSenhaAtual);
        TextView txtSenha = (TextView) findViewById(R.id.txtSenha);
        TextView txtConfSenha = (TextView) findViewById(R.id.txtConfSenha);

        String senhaAtual = txtSenhaAtual.getText().toString();
        String novaSenha = txtSenha.getText().toString();
        String confirmacao = txtConfSenha.getText().toString();

        String erro = "";
        if (TextUtils.isEmpty(senhaAtual)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "A Senha atual deve ser preenchida!";
        } else {
            if (!TextUtils.equals(DataBase.md5(senhaAtual), dados.senha)) {
                if (!TextUtils.isEmpty(erro)) {
                    erro += "\n";
                }
                erro += "A Senha digitada, não confere com a senha salva préviamente!";
            }
        }

        if (TextUtils.isEmpty(novaSenha)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "A nova senha deve ser preenchida!";
        } else {
            if (novaSenha.length() <= 3) {
                if (!TextUtils.isEmpty(erro)) {
                    erro += "\n";
                }
                erro += "A senha deve conter no mínimo 4 caracteres!";
            } else if (TextUtils.isEmpty(confirmacao)) {
                if (!TextUtils.isEmpty(erro)) {
                    erro += "\n";
                }
                erro += "A Confirmação de senha deve ser preenchida!";
            } else if (!TextUtils.equals(novaSenha, confirmacao)) {
                if (!TextUtils.isEmpty(erro)) {
                    erro += "\n";
                }
                erro += "As senhas não são iguais!";
            }

        }

        if (!TextUtils.isEmpty(erro)) {
            Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
        } else {
            if (con.alteraSenha(dados.id, novaSenha)) {
                Toast.makeText(this, "Senha alterada com sucesso!", Toast.LENGTH_LONG).show();
                onBackPressed();
            } else {
                Toast.makeText(this, "Erro ao alterar senha!", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void imagem() {
        ImageView imgImagem = (ImageView) findViewById(R.id.imgImagem);
        if (TextUtils.isEmpty(dados.imagem)) {
            imgImagem.setImageResource(R.drawable.usuarios);
        } else {
            imgImagem.setImageBitmap(new ConvertImage().Decodifica(dados.imagem));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Alterar senha de " + dados.nome);
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
