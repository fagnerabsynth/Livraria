package br.unicid.livraria.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.unicid.livraria.R;

/**
 * Created by Fagner on 28/11/2015.
 */
public class LivroAdapter extends BaseAdapter {


    private Context context;
    private List<LivroMOD> listadeDados;

    public LivroAdapter(Context context, List<LivroMOD> statelist) {
        this.context = context;
        this.listadeDados = statelist;
    }

    @Override
    public int getCount() {
        return listadeDados.size();
    }

    @Override
    public Object getItem(int position) {
        return listadeDados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LivroMOD dados = listadeDados.get(position);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.listviewlivros, null);

        TextView titulo = (TextView) view.findViewById(R.id.titulo);
        titulo.setText(dados.titulo);

        TextView subtitulo = (TextView) view.findViewById(R.id.subtitulo);
        subtitulo.setText(dados.subtitulo);

        TextView autor = (TextView) view.findViewById(R.id.autor);
        autor.setText("autor(es): " + dados.autor);

        TextView ano = (TextView) view.findViewById(R.id.ano);
        ano.setText("Ano: " + dados.ano);

        TextView id = (TextView) view.findViewById(R.id.ids);
        id.setText("" + dados.id);


        ImageView img = (ImageView) view.findViewById(R.id.img);

        Bitmap imagem = new ConvertImage().Decodifica(dados.imagem);
        img.setImageBitmap(imagem);


        return view;
    }
}