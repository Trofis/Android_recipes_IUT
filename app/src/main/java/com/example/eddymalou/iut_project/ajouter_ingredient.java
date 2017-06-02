package com.example.eddymalou.iut_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ajouter_ingredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_ingredient);

        setTitle("Ajouter Ingrédient");
    }

    public void ajouterIngredient(View v)
    {
        IngredientDataSource ingData = new IngredientDataSource(this);
        TextView b = (TextView) findViewById(R.id.ingAddText);
        ingData.open();
        ingData.createIngredient(String.valueOf(b.getText()));
        ingData.close();
        Toast.makeText(this, "Ingrédient ajouté !", Toast.LENGTH_SHORT).show();

        Intent in = new Intent(this, MainActivity.class);
        this.startActivity(in);
    }
}
