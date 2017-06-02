package com.example.eddymalou.iut_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ajouter_recette extends AppCompatActivity {

    CheckBox ch1;
    CheckBox ch2;
    CheckBox ch3;
    CheckBox ch4;
    CheckBox ch5;

    Spinner sp1;
    Spinner sp2;
    Spinner sp3;
    Spinner sp4;
    Spinner sp5;

    TextView quant1;
    TextView quant2;
    TextView quant3;
    TextView quant4;
    TextView quant5;

    TextView mes1;
    TextView mes2;
    TextView mes3;
    TextView mes4;
    TextView mes5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_recette);
        IngredientDataSource ingDat = new IngredientDataSource(this);
        setTitle("Ajouter recette");
        ingDat.open();
        List<Ingredient> l_ings = ingDat.getAllIngredient();
        ingDat.close();

        String[] ings = new String[l_ings.size()];
        int ind=0;
        for(Ingredient i : l_ings)
        {
            ings[ind] = i.getIngredient_name();
            ind++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp1.setEnabled(false);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        sp2.setEnabled(false);
        sp3 = (Spinner) findViewById(R.id.spinner3);
        sp3.setEnabled(false);
        sp4 = (Spinner) findViewById(R.id.spinner4);
        sp4.setEnabled(false);
        sp5 = (Spinner) findViewById(R.id.spinner5);
        sp5.setEnabled(false);

        sp1.setAdapter(adapter);
        sp2.setAdapter(adapter);
        sp3.setAdapter(adapter);
        sp4.setAdapter(adapter);
        sp5.setAdapter(adapter);

        ch1 = (CheckBox) findViewById(R.id.checkBox1);
        ch2 = (CheckBox) findViewById(R.id.checkBox2);
        ch3 = (CheckBox) findViewById(R.id.checkBox3);
        ch4 = (CheckBox) findViewById(R.id.checkBox4);
        ch5 = (CheckBox) findViewById(R.id.checkBox5);

        quant1 = (TextView) findViewById(R.id.quantite1);
        quant2 = (TextView) findViewById(R.id.quantite2);
        quant3 = (TextView) findViewById(R.id.quantite3);
        quant4 = (TextView) findViewById(R.id.quantite4);
        quant5 = (TextView) findViewById(R.id.quantite5);

        mes1 = (TextView) findViewById(R.id.mesure1);
        mes2 = (TextView) findViewById(R.id.mesure2);
        mes3 = (TextView) findViewById(R.id.mesure3);
        mes4 = (TextView) findViewById(R.id.mesure4);
        mes5 = (TextView) findViewById(R.id.mesure5);


        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox ch11 = (CheckBox) findViewById(R.id.checkBox1);
                Spinner sp1 = (Spinner) findViewById(R.id.spinner1);
                TextView t1 = (TextView) findViewById(R.id.quantite1);
                TextView t2 = (TextView) findViewById(R.id.mesure1);
                Log.d("addrec", "ch1");
                if (ch11.isChecked())
                {
                    Log.d("addrec", "enabled");
                    sp1.setEnabled(true);
                    t1.setEnabled(true);
                    t2.setEnabled(true);
                }
                else
                {
                    Log.d("addrec", "disabled");
                    sp1.setEnabled(false);
                    t1.setEnabled(false);
                    t2.setEnabled(false);

                }
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox ch11 = (CheckBox) findViewById(R.id.checkBox2);
                Spinner sp1 = (Spinner) findViewById(R.id.spinner2);
                TextView t1 = (TextView) findViewById(R.id.quantite2);
                TextView t2 = (TextView) findViewById(R.id.mesure2);
                if (ch11.isChecked())
                {
                    Log.d("addrec", "enabled");
                    sp1.setEnabled(true);
                    t1.setEnabled(true);
                    t2.setEnabled(true);
                }
                else
                {
                    Log.d("addrec", "disabled");
                    sp1.setEnabled(false);
                    t1.setEnabled(false);
                    t2.setEnabled(false);

                }
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox ch11 = (CheckBox) findViewById(R.id.checkBox3);
                Spinner sp1 = (Spinner) findViewById(R.id.spinner3);
                TextView t1 = (TextView) findViewById(R.id.quantite3);
                TextView t2 = (TextView) findViewById(R.id.mesure3);
                if (ch11.isChecked())
                {
                    Log.d("addrec", "enabled");
                    sp1.setEnabled(true);
                    t1.setEnabled(true);
                    t2.setEnabled(true);
                }
                else
                {
                    Log.d("addrec", "disabled");
                    sp1.setEnabled(false);
                    t1.setEnabled(false);
                    t2.setEnabled(false);

                }
            }
        });

        ch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox ch11 = (CheckBox) findViewById(R.id.checkBox4);
                Spinner sp1 = (Spinner) findViewById(R.id.spinner4);
                TextView t1 = (TextView) findViewById(R.id.quantite4);
                TextView t2 = (TextView) findViewById(R.id.mesure4);
                if (ch11.isChecked())
                {
                    Log.d("addrec", "enabled");
                    sp1.setEnabled(true);
                    t1.setEnabled(true);
                    t2.setEnabled(true);
                }
                else
                {
                    Log.d("addrec", "disabled");
                    sp1.setEnabled(false);
                    t1.setEnabled(false);
                    t2.setEnabled(false);

                }
            }
        });

        ch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox ch11 = (CheckBox) findViewById(R.id.checkBox5);
                Spinner sp1 = (Spinner) findViewById(R.id.spinner5);
                TextView t1 = (TextView) findViewById(R.id.quantite5);
                TextView t2 = (TextView) findViewById(R.id.mesure5);
                if (ch11.isChecked())
                {
                    Log.d("addrec", "enabled");
                    sp1.setEnabled(true);
                    t1.setEnabled(true);
                    t2.setEnabled(true);
                }
                else
                {
                    Log.d("addrec", "disabled");
                    sp1.setEnabled(false);
                    t1.setEnabled(false);
                    t2.setEnabled(false);

                }
            }
        });

    }

    public void addRecipe(View v)
    {

        ArrayList<Ingredient> arr = new ArrayList<Ingredient>();
        IngredientDataSource ing_dat = new IngredientDataSource(this);
        ing_dat.open();
        String title = String.valueOf(((TextView) findViewById(R.id.titleRecipe)).getText());
        String url = String.valueOf(((TextView) findViewById(R.id.url)).getText());
        if (title.equals(""))
        {
            Toast t = Toast.makeText(this, "Vous devez saisir un titre pour votre recette !", Toast.LENGTH_LONG);
            t.show();
            return;
        }
        try
        {
            for(int i = 1; i < 6; i++)
            {
                switch (i)
                {
                    case 1:
                        if (ch1.isChecked())
                        {
                            int id = ing_dat.getIdIng(sp1.getSelectedItem().toString());
                            arr.add(new Ingredient(id, sp1.getSelectedItem().toString(), Integer.parseInt(quant1.getText().toString()),mes1.getText().toString()) );
                        }
                        break;
                    case 2:
                        if (ch2.isChecked())
                        {
                            int id = ing_dat.getIdIng(sp2.getSelectedItem().toString());
                            arr.add(new Ingredient(id,sp2.getSelectedItem().toString(), Integer.parseInt(quant2.getText().toString()),mes2.getText().toString()) );
                        }
                        break;
                    case 3:
                        if (ch3.isChecked())
                        {
                            int id = ing_dat.getIdIng(sp3.getSelectedItem().toString());
                            arr.add(new Ingredient(id,sp3.getSelectedItem().toString(), Integer.parseInt(quant3.getText().toString()),mes3.getText().toString()) );
                        }
                        break;
                    case 4:
                        if (ch4.isChecked())
                        {
                            int id = ing_dat.getIdIng(sp4.getSelectedItem().toString());
                            arr.add(new Ingredient(id,sp4.getSelectedItem().toString(), Integer.parseInt(quant4.getText().toString()),mes4.getText().toString()) );
                        }
                        break;
                    case 5:
                        if (ch5.isChecked())
                        {
                            int id = ing_dat.getIdIng(sp5.getSelectedItem().toString());
                            arr.add(new Ingredient(id,sp5.getSelectedItem().toString(), Integer.parseInt(quant5.getText().toString()),mes5.getText().toString()) );
                        }
                        break;
                }
            }
            for(Ingredient ing : arr)
            {
                Log.d("rec_insert",ing.getIngredient_name());
            }
            RecetteDataSource recDat = new RecetteDataSource(this);
            recDat.open();
            recDat.createRecipe(title, arr, url);
            recDat.close();
            Toast t = Toast.makeText(this, "Recette ajoutée !", Toast.LENGTH_SHORT);
            t.show();
            ing_dat.close();
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }
        catch (Exception e)
        {
            Toast t = Toast.makeText(this, "Vous devez mettre une quantité !!", Toast.LENGTH_SHORT);
            t.show();
            Log.d("exception",e.toString());
        }

    }
}
