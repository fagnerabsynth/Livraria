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
import android.widget.ImageView;
import android.widget.TextView;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.ConvertImage;
import br.unicid.livraria.Model.LoginMOD;
import br.unicid.livraria.R;

public class DadosDoUsuario extends AppCompatActivity {

    DataBase con;
    LoginMOD Logado;
    private String login, sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dados_do_usuario);
        inicia();
    }

    private void inicia() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("nome", 0);
        login = pref.getString("nome", null);
        pref = getApplicationContext().getSharedPreferences("sessao", 0);
        sessao = pref.getString("sessao", null);
        con = new DataBase(this);
        Logado = con.login(sessao);

        TextView t;

        ImageView img = (ImageView) findViewById(R.id.imgImagem);
        if (!TextUtils.isEmpty(Logado.imagem)) {
            img.setImageBitmap(new ConvertImage().Decodifica(Logado.imagem));
        } else {
            img.setImageResource(R.drawable.cadastrousuario);
        }

        t = (TextView) findViewById(R.id.txtNOME);
        t.setText(Logado.nome);
        t = (TextView) findViewById(R.id.txtEMAIL);
        t.setText("Email: " + Logado.email);
        t = (TextView) findViewById(R.id.txtBAIRRO);
        t.setText("Bairro: " + Logado.bairro);
        t = (TextView) findViewById(R.id.txtCIDADE);
        t.setText("Cidade: " + Logado.cidade);
        t = (TextView) findViewById(R.id.txtCOMPLEMENTO);
        t.setText("Complemento: " + Logado.complemento);
        t = (TextView) findViewById(R.id.txtENDERECO);
        t.setText("Endereço: " + Logado.endereco);
        t = (TextView) findViewById(R.id.txtFONE);
        t.setText("Telefone: " + Logado.telefone);
        t = (TextView) findViewById(R.id.txtESTADO);
        t.setText("UF: " + Logado.estado);
        getSupportActionBar().setSubtitle("Dados do " + login);


    }


    public void alterarDados(View v) {
        Intent i = new Intent(this, DadosDoUsuarioEdicao.class);
        startActivityForResult(i, 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Dados do " + login);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                inicia();
            }
        }
    }


}
