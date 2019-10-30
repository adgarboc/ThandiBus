package com.metroreal.thandibus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class RutasCActivity extends AppCompatActivity {

    ListView lsRutas;
    FirebaseFirestore fDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas_c);

        lsRutas = (ListView) findViewById(R.id.lstRutas);
        fDatabase = FirebaseFirestore.getInstance();
        llenarListaRutas();

        lsRutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void llenarListaRutas()
    {
        fDatabase.collection("ruta").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    ArrayList<Map<String,Object>> listaRutas = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult())
                    {

                        listaRutas.add(document.getData());
                    }
                    if (listaRutas != null)
                    {
                        ArrayAdapter adapter = new ArrayAdapter(RutasCActivity.this,R.layout.support_simple_spinner_dropdown_item,listaRutas);
                        lsRutas.setAdapter(adapter);
                    }
                    else 
                    {
                        Toast.makeText(RutasCActivity.this, "No hay rutas disponibles", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RutasCActivity.this, "Error al obtener rutas", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}