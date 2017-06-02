package com.example.eddymalou.iut_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class profil extends AppCompatActivity {
    public ArrayList<DataModel> dataModels;
    public MonAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        setTitle("Profil");

        UserDataSource us = new UserDataSource(getApplicationContext());
        us.open();
        List<Recette> rec_list = us.getLike(((int) UserConnected.getUser().getId()));
        us.close();


        dataModels= new ArrayList<>();
        ListView lv = (ListView)findViewById(R.id.recipe_list_profil);
        for (Recette r : rec_list)
        {
            dataModels.add(new DataModel(r.getRecette_name(), String.valueOf(r.getId()), r.getImgsrc()));
        }

        adapter= new MonAdapter(dataModels,this);

        lv.setAdapter(adapter);

        TextView text = (TextView) findViewById(R.id.user_name_p);
        text.setText(UserConnected.getUser().getUsername());

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
    }
}
