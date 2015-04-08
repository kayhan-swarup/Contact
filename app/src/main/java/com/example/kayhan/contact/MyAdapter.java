package com.example.kayhan.contact;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Kayhan on 4/9/2015.
 */
public class MyAdapter extends CursorAdapter {


    public MyAdapter(Context context, Cursor c) {
        super(context, c);

    }





    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_layout,null);
        ViewHolder holder = new ViewHolder();
        holder.name  = (TextView)view.findViewById(R.id.name_textView);
        holder.number = (TextView)view.findViewById(R.id.number_textView);
        view.setTag(holder);




        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();
        holder.name.setText(cursor.getString(1));
        holder.number.setText(cursor.getString(2));

    }
    class ViewHolder{
        TextView name;
        TextView number;
    }
}
