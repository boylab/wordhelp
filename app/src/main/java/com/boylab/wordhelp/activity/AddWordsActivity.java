package com.boylab.wordhelp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.boylab.wordhelp.R;
import com.boylab.wordhelp.model.MessageEvent;
import com.boylab.wordhelp.model.Word;
import com.boylab.wordhelp.room.WordDatabase;
import com.boylab.wordhelp.utils.MenuPopup;
import com.boylab.wordhelp.utils.ViewClick;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AddWordsActivity extends AppCompatActivity implements View.OnClickListener,MenuPopup.OnMenuClickListener {

    private Button btn_Header_Left, btn_Header_Right;
    private EditText editText_Input;

    private long unitID;
    private MenuPopup menuPopup;

    public static void launchAddWords(Context context, long unitID){
        Intent intent = new Intent(context, AddWordsActivity.class);
        intent.putExtra("unitID", unitID);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        initView();
        initData();
    }

    protected void initView(){
        btn_Header_Left = findViewById(R.id.btn_Header_Left);
        btn_Header_Right = findViewById(R.id.btn_Header_Right);
        editText_Input = findViewById(R.id.editText_Input);

        btn_Header_Left.setOnClickListener(this);
        btn_Header_Right.setOnClickListener(this);
    }
    protected void initData(){
        menuPopup = new MenuPopup(this, this, "确认添加");

        unitID = getIntent().getLongExtra("unitID", 0);

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
    public void onPopupClick(View v, int position) {
        switch (position){
            case MenuPopup.MENU_01:
                addWords();
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                break;
            case MenuPopup.MENU_02:
                addWords();
                break;
        }
    }

    public void addWords(){
        String wordText = editText_Input.getText().toString().trim();

        wordText = wordText.replaceAll("\n", " ");
        wordText = wordText.trim();
        wordText = wordText.replaceAll("\\s+", " ");

        String[] text = wordText.split(" ");

        final Word[] words = new Word[text.length];
        for (int i = 0; i <text.length ; i++) {
            words[i] = new Word();
            words[i].setWord(text[i]);
            words[i].setUnit(unitID);
            words[i].setUpdatetime(System.currentTimeMillis());
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                WordDatabase.getInstance(AddWordsActivity.this).wordDao().insert(words);
                EventBus.getDefault().postSticky(new MessageEvent(0x01,"添加成功"));
            }
        }).start();

    }

}
