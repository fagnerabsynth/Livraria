package br.unicid.livraria.Views;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.ConvertImage;
import br.unicid.livraria.R;

public class Livro extends AppCompatActivity {

    private TextView titulo, subtitulo, autor, paginas, edicao, ano, categoria, isbn;
    private ImageView imagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livro);

        imagem = (ImageView) findViewById(R.id.imagem);
        titulo = (TextView) findViewById(R.id.titulo);
        subtitulo = (TextView) findViewById(R.id.subtitulo);
        autor = (TextView) findViewById(R.id.autor);
        paginas = (TextView) findViewById(R.id.paginas);
        edicao = (TextView) findViewById(R.id.edicao);
        ano = (TextView) findViewById(R.id.ano);
        categoria = (TextView) findViewById(R.id.categoria);
        isbn = (TextView) findViewById(R.id.isbn);
        Bundle intent = getIntent().getExtras();
        inicia(intent);
    }

    private void inicia(Bundle intent) {
        DataBase c = new DataBase(this);
        titulo.setText(intent.getString("titulo"));
        subtitulo.setText(intent.getString("subtitulo"));
        autor.setText(intent.getString("autor"));
        paginas.setText("Número de páginas: " + intent.getString("paginas"));
        edicao.setText("Edição: " + intent.getString("edicao"));
        ano.setText("Ano de Publicação: " + intent.getString("ano"));


        categoria.setText("Categoria: " + intent.getString("categoria"));
        isbn.setText("ISBN:" + intent.getString("isbn"));
        Bitmap bitmap = new ConvertImage().Decodifica(intent.getString("imagem"));
        bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
        imagem.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Detalhe do livro");
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
