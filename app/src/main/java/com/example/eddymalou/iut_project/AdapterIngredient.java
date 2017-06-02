package com.example.eddymalou.iut_project;

/**
 * Created by EddyMalou on 09/05/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AdapterIngredient extends ArrayAdapter<DataModelIngredient> implements View.OnClickListener{

    private ArrayList<DataModelIngredient> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        Button button;
    }

    public AdapterIngredient(ArrayList<DataModelIngredient> data, Context context) {
        super(context, R.layout.list_ingredients, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModelIngredient dataModel=(DataModelIngredient)object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModelIngredient dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_ingredients, parent, false);
            viewHolder.button = (Button) convertView.findViewById(R.id.ingredienName);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.button.setText(dataModel.getName());
        viewHolder.button.setTag(dataModel.getId());


        // Return the completed view to render on screen
        return convertView;
    }

    //public void add(String txt)
    //{
    //    dataSet.add(new DataModel(txt, "none", "none"));
    //}

    public void remove(int i)
    {
        dataSet.remove(i);
    }
}


