package br.unicid.livraria.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.ConvertImage;
import br.unicid.livraria.Model.LoginMOD;
import br.unicid.livraria.R;

public class DadosDoUsuarioEdicao extends AppCompatActivity {

    private String login, sessao;
    private DataBase con;
    private LoginMOD dados;

    private EditText txtImagem, txtUF, txtCEP, txtCIDADE, txtBAIRRO, txtCOMPLEMENTO, txtENDERECO, txtFONE, txtEMAIL, txtNOME;
    private ImageButton btnIMAGEMC;
    private Button btnIMAGEMS;


    public void BuscarImagem(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void AlterarDados(View b) {
        LoginMOD m = new LoginMOD();
        String temp, erro = "";
        m.imagem = txtImagem.getText().toString();
        m.id = dados.id;


        temp = txtUF.getText().toString();
        m.estado = temp;
        if (TextUtils.isEmpty(temp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "A UF é obrigatório";
        }


        temp = txtCEP.getText().toString();
        m.cep = temp;
        if (TextUtils.isEmpty(temp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "O CEP é obrigatório";
        }


        temp = txtCIDADE.getText().toString();
        m.cidade = temp;
        if (TextUtils.isEmpty(temp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "O Cidade é obrigatório";
        }


        temp = txtBAIRRO.getText().toString();
        m.bairro = temp;
        if (TextUtils.isEmpty(temp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "O Bairro é obrigatório";
        }

        temp = txtCOMPLEMENTO.getText().toString();
        m.complemento = temp;

        temp = txtENDERECO.getText().toString();
        m.endereco = temp;
        if (TextUtils.isEmpty(temp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "O Endereço é obrigatório";
        }

        temp = txtFONE.getText().toString();
        m.telefone = temp;
        if (TextUtils.isEmpty(temp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "O Telefone é obrigatório";
        }

        temp = txtEMAIL.getText().toString();
        m.email = temp;
        if (TextUtils.isEmpty(temp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "O Email é obrigatório";
        }

        temp = txtNOME.getText().toString();
        m.nome = temp;
        if (TextUtils.isEmpty(temp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }

            erro += "O Nome é obrigatório";
        }

        if (!TextUtils.isEmpty(erro)) {
            Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
        } else {
            if (con.alteraSenha(m)) {
                Toast.makeText(this, "Dados alterados com sucesso!", Toast.LENGTH_LONG).show();
                setResult(AppCompatActivity.RESULT_OK, new Intent());

                GravaSessao();


                onBackPressed();
            } else {
                Toast.makeText(this, "Não foi possivel alterar as informações\nPor favor verifique!", Toast.LENGTH_LONG).show();

            }
        }

    }

    private void GravaSessao() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("sessao", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sessao", Login.getSESSAO());
        editor.commit();
        pref = getApplicationContext().getSharedPreferences("nome", 0);
        editor = pref.edit();
        editor.putString("nome", Login.getNOME());
        editor.commit();
    }


    private void inicia() {
        con = new DataBase(this);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("nome", 0);
        login = pref.getString("nome", null);
        pref = getApplicationContext().getSharedPreferences("sessao", 0);
        sessao = pref.getString("sessao", null);
        dados = con.login(sessao);

        btnIMAGEMS = (Button) findViewById(R.id.btnIMAGEMS);
        btnIMAGEMC = (ImageButton) findViewById(R.id.btnIMAGEMC);
        txtImagem = (EditText) findViewById(R.id.txtImagem);
        txtUF = (EditText) findViewById(R.id.txtUF);
        txtCEP = (EditText) findViewById(R.id.txtCEP);
        txtCIDADE = (EditText) findViewById(R.id.txtCIDADE);
        txtBAIRRO = (EditText) findViewById(R.id.txtBAIRRO);
        txtCOMPLEMENTO = (EditText) findViewById(R.id.txtCOMPLEMENTO);
        txtENDERECO = (EditText) findViewById(R.id.txtENDERECO);
        txtFONE = (EditText) findViewById(R.id.txtFONE);
        txtEMAIL = (EditText) findViewById(R.id.txtEMAIL);
        txtNOME = (EditText) findViewById(R.id.txtNOME);


        imagem();

        if (dados.id > 0) {
            txtUF.setText(dados.estado);
            txtCEP.setText(dados.cep);
            txtCIDADE.setText(dados.cidade);
            txtBAIRRO.setText(dados.bairro);
            txtCOMPLEMENTO.setText(dados.complemento);
            txtENDERECO.setText(dados.endereco);
            txtFONE.setText(dados.telefone);
            txtEMAIL.setText(dados.email);
            txtNOME.setText(dados.nome);
        }

    }

    private void imagem() {

        if (TextUtils.isEmpty(dados.imagem)) {
            txtImagem.setText("");
            btnIMAGEMS.setVisibility(View.VISIBLE);
            btnIMAGEMC.setVisibility(View.GONE);
        } else {
            btnIMAGEMS.setVisibility(View.GONE);
            btnIMAGEMC.setVisibility(View.VISIBLE);
            btnIMAGEMC.setImageBitmap(new ConvertImage().Decodifica(dados.imagem));
            txtImagem.setText(dados.imagem);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dados_do_usuario_edicao);
        //
        inicia();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Edição: " + login);
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

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);
        return cursor.getString(column_index);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                Uri selectedImage = data.getData();

                String filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);

                Toast.makeText(this, "Selecionando a Imagem:\n" + filePath, Toast.LENGTH_LONG).show();

                if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        dados.imagem = new ConvertImage().Codifica(bitmap);
                        btnIMAGEMC.setImageBitmap(bitmap);
                        imagem();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
    }


}
