package br.unicid.livraria.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        TextView nome = (TextView) view.findViewById(R.id.categoria);
        nome.setText(dados.titulo);

        TextView ids = (TextView) view.findViewById(R.id.ids);
        ids.setText("" + dados.id);

        TextView valor = (TextView) view.findViewById(R.id.descricao);
        valor.setText(dados.subtitulo);

        return view;
    }
}