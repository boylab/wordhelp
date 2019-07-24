package com.boylab.wordhelp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.boylab.wordhelp.R;
import com.boylab.wordhelp.adapter.WordsAdapter;
import com.boylab.wordhelp.model.MessageEvent;
import com.boylab.wordhelp.model.Word;
import com.boylab.wordhelp.room.WordDatabase;
import com.boylab.wordhelp.utils.MenuPopup;
import com.boylab.wordhelp.utils.ViewClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ModifyWordActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Button btn_Header_Left, btn_Header_Right;
    private ListView lv_Study_Words;
    private List<Word> allWords = new ArrayList<>();
    private WordsAdapter wordsAdapter ;

    private long unitID;

    private MenuPopup menuPopup;

    public static void launchModifyWords(Context context, long unitId){
        Intent intent = new Intent(context, ModifyWordActivity.class);
        intent.putExtra("unitID", unitId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_word);
        initView();
        initData();

    }

    protected void initView(){
        btn_Header_Left = findViewById(R.id.btn_Header_Left);
        btn_Header_Right = findViewById(R.id.btn_Header_Right);
        btn_Header_Right.setVisibility(View.INVISIBLE);

        lv_Study_Words = findViewById(R.id.lv_Study_Words);
        wordsAdapter = new WordsAdapter(this,allWords );
        lv_Study_Words.setAdapter(wordsAdapter);


        btn_Header_Left.setOnClickListener(this);
        btn_Header_Right.setOnClickListener(this);
        lv_Study_Words.setOnItemClickListener(this);
        lv_Study_Words.setOnItemLongClickListener(this);
    }
    protected void initData(){


        unitID = getIntent().getLongExtra("unitID", 0);
        freshData();
    }

    @Override
    public void onClick(View v) {
        if (ViewClick.isFastDoubleClick()){
            return;
        }
        switch (v.getId()){
            case R.id.btn_Header_Left:
                finish();
                break;
            case R.id.btn_Header_Right:
                menuPopup.showAsDropDown(btn_Header_Right);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Word word = allWords.get(position);

        View rootView = getLayoutInflater().inflate(R.layout.layout_input,null);
        final EditText editText = rootView.findViewById(R.id.editText_Input);
        editText.setText(word.getWord());

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("温馨提示！")
                .setMessage("修改此单词 -- "+word.getWord())
                .setView(rootView)
                .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                word.setWord(editText.getText().toString().trim());
                                WordDatabase.getInstance(ModifyWordActivity.this).wordDao().update(word);

                                List<Word> temple = WordDatabase.getInstance(ModifyWordActivity.this).wordDao().getAllWords(unitID);
                                allWords.clear();
                                allWords.addAll(temple);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wordsAdapter.notifyDataSetChanged();
                                        EventBus.getDefault().post(new MessageEvent(0x03,"修改成功"));
                                    }
                                });
                            }
                        }).start();
                    }
                })
                .setNegativeButton("取消", null);
        builder.create().show();
    }

    public void freshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Word> temple = WordDatabase.getInstance(ModifyWordActivity.this).wordDao().getAllWords(unitID);
                allWords.clear();
                allWords.addAll(temple);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wordsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Word word = allWords.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("温馨提示！")
                .setMessage("删除此单词 -- "+word.getWord())
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                WordDatabase.getInstance(ModifyWordActivity.this).wordDao().delete(word);

                                List<Word> temple = WordDatabase.getInstance(ModifyWordActivity.this).wordDao().getAllWords(unitID);
                                allWords.clear();
                                allWords.addAll(temple);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wordsAdapter.notifyDataSetChanged();
                                        EventBus.getDefault().post(new MessageEvent(0x02,"删除成功"));
                                    }
                                });
                            }
                        }).start();
                    }
                })
                .setNegativeButton("取消", null);
        builder.create().show();
        return true;
    }

}
