package com.example.eddymalou.iut_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecipePage extends AppCompatActivity {

    public int id;
    public ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        Intent intent = getIntent();
        TextView title = (TextView) this.findViewById(R.id.title_recipe);
        imgView = (ImageView) this.findViewById(R.id.recipeImg);
        TextView listeIng = (TextView) this.findViewById(R.id.ingredientsListe);

        title.setText(intent.getStringExtra("Recette_name"));
        id = Integer.parseInt(intent.getStringExtra("Recette_id"));
        Log.i("recipePage", String.valueOf(id));

        new DownloadImage().execute(intent.getStringExtra("Recette_imgSrc"));

        IngredientDataSource db = new IngredientDataSource(this);
        db.open();
        List<Ingredient> liste = db.getIngredients(Integer.parseInt(intent.getStringExtra("Recette_id")));
        String res= "";
        Log.e("recipePage", "count :"+String.valueOf(liste.size()));
        for(Ingredient ing : liste)
        {
            Log.i("ing", String.valueOf(ing.getQuantite()));
            res+=" - "+ing.getIngredient_name()+" , "+String.valueOf(ing.getQuantite()) + " " +ing.getMesure()+"\n";
        }

        listeIng.setText(res);

    }

    public void supprimerRecette(View v)
    {
        RecetteDataSource rec = new RecetteDataSource(this);
        rec.open();
        Log.d("suppRec", String.valueOf(this.id));
        rec.deleteRecipe(this.id);
        rec.close();
        Toast t = Toast.makeText(this, "Recette supprimée", Toast.LENGTH_SHORT);
        t.show();
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }

    public void likeRecette(View v)
    {
        Toast toast;

        UserDataSource us = new UserDataSource(this);
        us.open();
        if(us.ifLikeExist(((int) UserConnected.getUser().getId())))
        {
            toast = Toast.makeText(this, "Recette déja liké !", Toast.LENGTH_SHORT);


        }
        else
        {
            us.like_recette(((int) UserConnected.getUser().getId()),id );
            toast = Toast.makeText(this, "Recette liké !", Toast.LENGTH_SHORT);

        }

        us.close();
        toast.show();

    }

    private void setImage(Drawable drawable)
    {
        imgView.setBackgroundDrawable(drawable);
    }

    public class DownloadImage extends AsyncTask<String, Integer, Drawable> {

        @Override
        protected Drawable doInBackground(String... arg0) {
            // This is done in a background thread
            return downloadImage(arg0[0]);
        }

        protected void onPostExecute(Drawable image) {
            setImage(image);
        }

        private Drawable downloadImage(String _url) {
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;

            //BufferedInputStream buf;
            try {
                url = new URL(_url);
                in = url.openStream();

                buf = new BufferedInputStream(in);

                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                return new BitmapDrawable(bMap);

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

            return null;
        }
    }

}
