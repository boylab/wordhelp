package com.boylab.wordhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boylab.wordhelp.R;
import com.boylab.wordhelp.activity.MainActivity;
import com.boylab.wordhelp.model.Unit;
import com.boylab.wordhelp.room.WordDatabase;

import java.util.List;

/**
 * Author pengle on 2019/7/24 10:02
 * Email  pengle609@163.com
 */
public class UnitsAdapter extends BaseAdapter {

    private Context context;
    private  List<Unit> list;

    public UnitsAdapter(Context context, List<Unit> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_units, null);
            viewHolder.tv_Words_Unit = convertView.findViewById(R.id.tv_Words_Unit);
            viewHolder.tv_Words_Percent = convertView.findViewById(R.id.tv_Words_Percent);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Unit unit = list.get(position);
        viewHolder.tv_Words_Unit.setText("Unit "+unit.getId());
        //viewHolder.tv_Words_Percent.setText(unit);

        return convertView;
    }

    private class ViewHolder{
        private TextView tv_Words_Unit, tv_Words_Percent;
    }
}
