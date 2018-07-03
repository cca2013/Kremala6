package com.example.androidgridview;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
import android.widget.Toast;
public class CustomAdapter extends BaseAdapter{   
	 List<Player>  result;
    Context context;

      private static LayoutInflater inflater=null;
    public CustomAdapter(UserActivity userActivity,  List<Player> players) {
        // TODO Auto-generated constructor stub
        result=players;
        context=userActivity;

         inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView tv1;
    
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;       
             rowView = inflater.inflate(R.layout.user1, null);
             holder.tv=(TextView) rowView.findViewById(R.id.text1);
             holder.tv1=(TextView) rowView.findViewById(R.id.text2);
            // holder.img=(ImageView) rowView.findViewById(R.id.imageView1);       
         holder.tv.setText(result.get(position).getName());
         holder.tv1.setText(result.get(position).getScore());
         //holder.img.setImageResource(imageId[position]);         
         rowView.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });   
        return rowView;
    }

}