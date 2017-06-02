package com.example.eddymalou.iut_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecListFragment extends Fragment {

    public RecListFragment(){
        super();
    }
    public ArrayList<DataModel> dataModels;
    public MonAdapter adapter;
    RecetteDataSource recettesDb;
    IngredientDataSource ingsDb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recette_list, container, false);
        recettesDb = new RecetteDataSource(this.getContext());
        recettesDb.open();


        ListView lv = (ListView)view.findViewById(R.id.list_recettes);

        dataModels= new ArrayList<>();

        List<Recette> recettes = recettesDb.getAllRecipe();
        Log.i("Test", Integer.toString(recettes.size()));
        for (Recette r : recettes)
        {
            Log.e("Test", r.getRecette_name());
            dataModels.add(new DataModel(r.getRecette_name(), String.valueOf(r.getId()), r.getImgsrc()));
        }

        adapter= new MonAdapter(dataModels,getContext());

        lv.setAdapter(adapter);

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        //lv.setAdapter(adapter);
        Log.d("Test", "OnCreateView");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getContext();
                DataModel d = (DataModel)adapter.getItem(i);
                Intent intent = new Intent(getActivity(), RecipePage.class);
                intent.putExtra("Recette_name", d.getName());
                intent.putExtra("Recette_id", d.getId());
                intent.putExtra("Recette_imgSrc", d.getImgsrc());
                startActivity(intent);

            }
        });
        recettesDb.close();
        return view;

    }
}