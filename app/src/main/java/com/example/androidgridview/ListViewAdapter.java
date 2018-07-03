package com.example.androidgridview;

import static com.example.androidgridview.Constants.FIRST_COLUMN;
import static com.example.androidgridview.Constants.SECOND_COLUMN;






import java.util.ArrayList;
import java.util.HashMap;
 
 
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class ListViewAdapter extends BaseAdapter{
 
    public ArrayList<HashMap<String, String>> list1;
    public List<Player> list;
    Activity activity;
     //ArrayList<HashMap<String, String>
    public ListViewAdapter(Activity activity,List<Player> list){
        super();
        this.activity=activity;
        this.list=list;
    }
     
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
 
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
 
    private class ViewHolder{
        TextView txtFirst;
        TextView txtSecond;
       
    }
     
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
     
        ViewHolder holder;
         
        LayoutInflater inflater=activity.getLayoutInflater();
         
        if(convertView == null){
             
            convertView=inflater.inflate(R.layout.column_row, null);
            holder=new ViewHolder();
             
            holder.txtFirst=(TextView) convertView.findViewById(R.id.TextFirst);
            holder.txtSecond=(TextView) convertView.findViewById(R.id.TextSecond);
          
             
            convertView.setTag(holder);
        }else{
             
            holder=(ViewHolder) convertView.getTag();
        }
         Player map=list.get(position);
       // HashMap<String, String> map=list1.get(position);
      // holder.txtFirst.setText(map.get(FIRST_COLUMN));
      //  holder.txtSecond.setText(map.get(SECOND_COLUMN));
     
         holder.txtFirst.setText(map.getName());
         holder.txtSecond.setText(String.valueOf(map.getScore()));
        return convertView;
    }

}


