package br.unicid.livraria.Views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import br.unicid.livraria.Data.DataBase;
import br.unicid.livraria.Inicial;
import br.unicid.livraria.Model.AlunosMOD;
import br.unicid.livraria.R;

public class Sobre extends AppCompatActivity {

    DataBase con;
    private ImageButton img1, img2, img3, img4, img5, img6;
    private int aluno1, aluno2, aluno3, aluno4, aluno5, aluno6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobre);
        inicia();
    }

    private void inicia() {
        con = new DataBase(this);
        ArrayList<AlunosMOD> aluno = con.alunos();
        int i = 1;


        img1 = (ImageButton) findViewById(R.id.img1);
        img2 = (ImageButton) findViewById(R.id.img2);
        img3 = (ImageButton) findViewById(R.id.img3);
        img4 = (ImageButton) findViewById(R.id.img4);
        img5 = (ImageButton) findViewById(R.id.img5);
        img6 = (ImageButton) findViewById(R.id.img6);


        for (AlunosMOD x : aluno) {


            switch (i) {
                case 1:
                    img1.setImageResource(getResources().getIdentifier(x.imagem, "drawable", this.getPackageName()));

                    aluno1 = x.id;
                    break;
                case 2:
                    img2.setImageResource(getResources().getIdentifier(x.imagem, "drawable", this.getPackageName()));
                    aluno2 = x.id;
                    break;
                case 3:
                    img3.setImageResource(getResources().getIdentifier(x.imagem, "drawable", this.getPackageName()));
                    aluno3 = x.id;

                    break;
                case 4:
                    img4.setImageResource(getResources().getIdentifier(x.imagem, "drawable", this.getPackageName()));
                    aluno4 = x.id;
                    break;
                case 5:
                    img5.setImageResource(getResources().getIdentifier(x.imagem, "drawable", this.getPackageName()));
                    aluno5 = x.id;
                    break;
                case 6:
                    aluno6 = x.id;
                    img6.setImageResource(getResources().getIdentifier(x.imagem, "drawable", this.getPackageName()));
                    break;
            }
            i++;
        }


    }


    public void abre(View m) {

        int id = 0;
        switch (m.getId()) {
            case R.id.img1:
                id = aluno1;
                break;
            case R.id.img2:
                id = aluno2;
                break;
            case R.id.img3:
                id = aluno3;
                break;
            case R.id.img4:
                id = aluno4;
                break;
            case R.id.img5:
                id = aluno5;
                break;
            case R.id.img6:
                id = aluno6;
                break;
        }
        if (id > 0) {
            Intent i = new Intent(this, Aluno.class);
            i.putExtra("id", "" + id);
            startActivityForResult(i, 1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Inicial.Cor())));
        getSupportActionBar().setTitle(Inicial.TITULO());
        getSupportActionBar().setSubtitle("Sobre o APP");
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

