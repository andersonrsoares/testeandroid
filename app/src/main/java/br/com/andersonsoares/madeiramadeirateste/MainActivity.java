package br.com.andersonsoares.madeiramadeirateste;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.andersonsoares.madeiramadeirateste.adapter.TransporteAdapter;
import br.com.andersonsoares.madeiramadeirateste.model.Transporte;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private android.widget.ListView lista;

    TransporteAdapter transporteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lista = (ListView) findViewById(R.id.lista);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        List<Transporte> list = new ArrayList<>();
        list.add(new Transporte("Entrega:","Avenida Marechal Floriano Peixoto, 306 - Centro, Curitiba - PR",
                -25.4333139,-49.2733416
                ,"554112345678"
                ,"55912345678",
                "João da silva","Falar com cicrano"));


        list.add(new Transporte("Entrega:","Rua Izaac Ferreira da Cruz 1000- Sítio Cercado, Curitiba - PR",
                25.5302751,-49.291249
                ,"554112345678"
                ,"55912345678",
                "Jose da silva","Falar com cicrano"));

        list.add(new Transporte("Coleta:","Avenida Marechal Floriano Peixoto, 306 - Centro, Curitiba - PR",
                -25.4333139,-49.2733416
                ,"554112345678"
                ,"55912345678",
                "João da silva","Falar com cicrano"));


        list.add(new Transporte("Coleta:","Rua Izaac Ferreira da Cruz 1000- Sítio Cercado, Curitiba - PR",
                25.5302751,-49.291249
                ,"554112345678"
                ,"55912345678",
                "Jose da silva","Falar com cicrano"));



        transporteAdapter = new TransporteAdapter(this,list);
        lista.setAdapter(transporteAdapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Transporte transporte = (Transporte) adapterView.getItemAtPosition(i);

                startActivity(new Intent(MainActivity.this,DetalheActivity.class).putExtra("transporte",transporte));
             }
        });
    }
}
