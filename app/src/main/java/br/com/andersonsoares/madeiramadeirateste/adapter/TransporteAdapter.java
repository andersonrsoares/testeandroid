package br.com.andersonsoares.madeiramadeirateste.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.com.andersonsoares.madeiramadeirateste.R;
import br.com.andersonsoares.madeiramadeirateste.model.Transporte;

/**
 * Created by andersonsoares on 04/03/2018.
 */

public class TransporteAdapter extends BaseAdapter {
    private final Context mContext;

    private ArrayList<Transporte> list = new ArrayList<>();



    public TransporteAdapter(Context context, List<Transporte> cards) {
        this.mContext = context;
        list.addAll(cards);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Transporte getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        final Transporte card = list.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_transporte, parent, false);

            holder = new ViewHolder();
            holder.endereco = (TextView) convertView.findViewById(R.id.endereco);
            holder.tipo = (TextView) convertView.findViewById(R.id.tipo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.endereco.setText(card.getEndereco());
        holder.tipo.setText(card.getTipo());

        return convertView;
    }

    class ViewHolder {
        public TextView endereco;
        public TextView tipo;
    }


}
