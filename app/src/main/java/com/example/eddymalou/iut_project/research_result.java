package com.example.eddymalou.iut_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class research_result extends AppCompatActivity {

    public research_result(){
        super();
    }
    public ArrayList<DataModel> dataModels;
    public MonAdapter adapter;
    RecetteDataSource recettesDb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_result);
        setTitle("Résultat recherche");
        recettesDb = new RecetteDataSource(this);
        recettesDb.open();
        Intent intent = this.getIntent();
        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Recette>>(){}.getType();

        List<Recette> recettes =gson.fromJson((String) intent.getSerializableExtra("ings"), listType); ;
        dataModels= new ArrayList<>();
        ListView lv = (ListView)findViewById(R.id.list_recettes);
        Log.i("Test", Integer.toString(recettes.size()));
        for (Recette r : recettes)
        {
            dataModels.add(new DataModel(r.getRecette_name(), String.valueOf(r.getId()), r.getImgsrc()));
        }

        adapter= new MonAdapter(dataModels,this);

        lv.setAdapter(adapter);

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        //lv.setAdapter(adapter);
        Log.d("Test", "OnCreateView");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataModel d = (DataModel)adapter.getItem(i);
                Intent intent = new Intent(view.getContext(), RecipePage.class);
                intent.putExtra("Recette_name", d.getName());
                intent.putExtra("Recette_id", d.getId());
                intent.putExtra("Recette_imgSrc", d.getImgsrc());
                startActivity(intent);

            }
        });
        recettesDb.close();
    }


}