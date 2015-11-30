package br.unicid.livraria.Views;

import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.CategoriaMOD;
import br.unicid.livraria.Model.ConvertImage;
import br.unicid.livraria.Model.LivroMOD;
import br.unicid.livraria.R;

public class CadastroLivro extends AppCompatActivity {
    private ImageButton bt2;
    private Button bt1;

    private EditText imagens;
    private String imagePath;
    private int column_index;
    private Spinner spinner;
    private DataBase item;
    private String valorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_livro);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        // getSupportActionBar().setIcon(R.drawable.saraivamini);
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Cadastro de Livro");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bt2 = (ImageButton) findViewById(R.id.btn2);
        bt2.setVisibility(View.GONE);
        bt1 = (Button) findViewById(R.id.btn1);
        item = new DataBase(this);

        imagens = (EditText) findViewById(R.id.imagens);

        spinner = (Spinner) findViewById(R.id.spnCategoria);
        criaSpinner();
    }

    public void PegaImagem(View b) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void abreGaleria(int req_code) {

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Escolha o arquivo para upload "), req_code);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cadastro_livro, menu);
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
        column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        imagePath = cursor.getString(column_index);
        return cursor.getString(column_index);
    }

    @Override
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
                        String resutlado = new ConvertImage().Codifica(bitmap);
                        bt1.setVisibility(View.GONE);
                        bt2.setVisibility(View.VISIBLE);

                        imagens.setText(resutlado);

                        Bitmap imagem = new ConvertImage().Decodifica(resutlado);
                        bt2.setImageBitmap(imagem);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
    }

    public void cadastroDoLivro(View v) {


        // l.categoria = item.pesquisaCategoria(spinner.getSelectedItem().toString(), true).id;
        String sp = spinner.getSelectedItem().toString();

        LivroMOD l = new LivroMOD();
        String erro = "";

        if (TextUtils.isEmpty(sp)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Selecione uma categoria";
        } else {
            l.categoria = spinner.getSelectedItem().toString();
        }


        EditText txtISBN = (EditText) findViewById(R.id.txtISBN);
        String isbn = txtISBN.getText().toString();

        if (TextUtils.isEmpty(isbn)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "ISBN é obrigatório";
        } else {
            l.isbn = isbn;
        }


        EditText txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        String titulo = txtTitulo.getText().toString();

        if (TextUtils.isEmpty(titulo)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Titulo é obrigatório";
        } else {
            l.titulo = titulo;
        }


        EditText txtSubtitulo = (EditText) findViewById(R.id.txtSubtitulo);
        String subtitulo = txtSubtitulo.getText().toString();

        if (TextUtils.isEmpty(subtitulo)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Subtitulo é obrigatório";
        } else {
            l.subtitulo = subtitulo;
        }

        EditText txtEdicao = (EditText) findViewById(R.id.txtEdicao);
        String edicao = txtEdicao.getText().toString();
        if (TextUtils.isEmpty(edicao)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Edição é obrigatório";
        } else {
            l.edicao = edicao;
        }


        EditText txtAutores = (EditText) findViewById(R.id.txtAutores);
        String Autores = txtAutores.getText().toString();
        if (TextUtils.isEmpty(Autores)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Autor é obrigatório";
        } else {
            l.autor = Autores;
        }

        EditText txtPagina = (EditText) findViewById(R.id.txtPagina);
        String pagina = txtPagina.getText().toString();
        if (TextUtils.isEmpty(pagina)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Pagina é obrigatório";
        } else {
            l.paginas = Integer.parseInt(pagina);
        }


        EditText txtAno = (EditText) findViewById(R.id.txtAno);
        String ano = txtAno.getText().toString();
        if (TextUtils.isEmpty(ano)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Ano é obrigatório";
        } else {
            l.ano = Integer.parseInt(ano);
        }

        EditText txtEditora = (EditText) findViewById(R.id.txtEditora);
        String editora = txtEditora.getText().toString();
        if (TextUtils.isEmpty(editora)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Editora é obrigatório";
        } else {
            l.editora = editora;
        }


        EditText txtImagens = (EditText) findViewById(R.id.imagens);
        String imagens = txtImagens.getText().toString();
        if (TextUtils.isEmpty(imagens)) {
            if (!TextUtils.isEmpty(erro)) {
                erro += "\n";
            }
            erro += "Imagem é obrigatório";
        } else {
            l.imagem = imagens;
        }

        if (!TextUtils.isEmpty(erro)) {
            Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
        } else {

            if (item.cadastraLivro(l)) {
                Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                txtAno.setText("");
                txtAutores.setText("");
                txtEdicao.setText("");
                txtEditora.setText("");
                txtISBN.setText("");
                txtPagina.setText("");
                txtSubtitulo.setText("");
                txtTitulo.setText("");
                txtImagens.setText("");
                spinner.setSelection(0);
                bt2.setVisibility(View.GONE);
                bt1.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(this, "Livro encontrado no sistema!", Toast.LENGTH_SHORT).show();
            }


        }


//        spinner.setSelection(((ArrayAdapter<String>) spinner.getAdapter()).getPosition(pro.categoria));

    }

    private void criaSpinner() {
        spinner.setPrompt("Selecione uma categoria");

        List<String> list = new ArrayList<String>();
        list.add("");


        ArrayList<CategoriaMOD> m = item.pesquisaCategoria();

        if (!m.isEmpty()) {
            for (CategoriaMOD x : m) {
                list.add(x.categoria);
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }


}



