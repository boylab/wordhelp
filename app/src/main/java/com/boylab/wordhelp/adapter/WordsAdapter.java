package com.boylab.wordhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boylab.wordhelp.R;
import com.boylab.wordhelp.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Author pengle on 2019/7/24 10:02
 * Email  pengle609@163.com
 */
public class WordsAdapter extends BaseAdapter {

    private Context context;
    private  List<Word> list = new ArrayList<>();

    public WordsAdapter(Context context, List<Word> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_words, null);
            viewHolder.tv_Words = convertView.findViewById(R.id.tv_Words);
            //viewHolder.tv_Words_Times = convertView.findViewById(R.id.tv_Words_Times1);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_Words.setText(list.get(position).getWord());
        //viewHolder.tv_Words_Times.setText(String.valueOf(list.get(position).getStudytimes()));

        return convertView;
    }

    private class ViewHolder{
        private TextView tv_Words, tv_Words_Times;
    }
}
