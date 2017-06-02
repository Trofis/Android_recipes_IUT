package com.example.eddymalou.iut_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ResearchRecFragment extends Fragment {
    public ArrayList<DataModelIngredient> dataModels;
    public AdapterIngredient adapter;
    public IngredientDataSource ingsDb;
    public View vglob;
    public Spinner sp;
    public ResearchRecFragment(){super();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_research_recette, container, false);
        sp = (Spinner) view.findViewById(R.id.et_ou);
        String[] l_s = new String[2];
        l_s[0] = "et";
        l_s[1] = "ou";
        ArrayAdapter<String> adapter_sp = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, l_s);
        adapter_sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter_sp);
        dataModels= new ArrayList<>();
        ingsDb = new IngredientDataSource(this.getContext());
        ingsDb.open();
        List<Ingredient> ing_list = ingsDb.getAllIngredient();
        Log.i("Test", Integer.toString(ing_list.size()));
        for (Ingredient ing : ing_list)
        {
            Log.e("Test", ing.getIngredient_name());
            dataModels.add(new DataModelIngredient(ing.getIngredient_name(),ing.getIngredient_name()));
        }
        ingsDb.close();
        GridView lv = (GridView)view.findViewById(R.id.buttonList);
        vglob = view;
        adapter= new AdapterIngredient(dataModels,getContext());

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                Context  context = getContext();
                DataModelIngredient d = (DataModelIngredient)adapter.getItemAtPosition(position);
                TextView textview = (TextView) vglob.findViewById(R.id.list_ing_select);

                if (String.valueOf(textview.getText()).contains(d.getName()))
                {
                    ((Button) vglob.findViewWithTag(d.getId())).setBackgroundColor(Color.parseColor("#ff424242"));
                    ArrayList<String> l = new ArrayList<String>(Arrays.asList(String.valueOf(textview.getText()).split(",")));
                    l.remove(d.getName());
                    String res = join(",", l);
                    textview.setText(res);
                }
                else
                {
                    textview.append(d.getName()+",");
                    ((Button) vglob.findViewWithTag(d.getId())).setBackgroundColor(Color.GREEN);
                }

            }
        });



        Button b = (Button) view.findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecetteDataSource recDat = new RecetteDataSource(getContext());

                TextView ings = (TextView) vglob.findViewById(R.id.list_ing_select);

                String textParams = String.valueOf(ings.getText());
                ArrayList<String> l_ings = new ArrayList<String>(Arrays.asList(textParams.split(",")));
                recDat.open();
                List<Recette> recettes = recDat.getRecipeByIngs(l_ings, sp.getSelectedItem().toString());
                recDat.close();

                Intent in = new Intent(v.getContext(),research_result.class );
                in.putExtra("ings", new Gson().toJson(recettes));
                startActivity(in);
            }
        });

        return view;

    }

    public String join(String sep, List<String> params)
    {
        String res ="";
        int i = 0;
        for (String elem : params)
        {
            res += elem+sep;
        }

        return  res;
    }



}