package com.boylab.wordhelp.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.boylab.wordhelp.R;
import com.boylab.wordhelp.adapter.WordsAdapter;
import com.boylab.wordhelp.model.MessageEvent;
import com.boylab.wordhelp.model.Unit;
import com.boylab.wordhelp.model.Word;
import com.boylab.wordhelp.room.WordDatabase;
import com.boylab.wordhelp.utils.MenuPopup;
import com.boylab.wordhelp.utils.ViewClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class StudyWordsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MenuPopup.OnMenuClickListener {

    private Button btn_Header_Left, btn_Header_Right;
    private ListView lv_Study_Words;
    private List<Word> allWords = new ArrayList<>();
    private WordsAdapter wordsAdapter ;

    private long unitID;

    private MenuPopup menuPopup;

    public static void launchStudyWords(Context context, long unitId){
        Intent intent = new Intent(context, StudyWordsActivity.class);
        intent.putExtra("unitID", unitId);
        context.startActivity(intent);

        // TODO: 2019/7/24 需要传入unit
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_words);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    protected void initView(){
        btn_Header_Left = findViewById(R.id.btn_Header_Left);
        btn_Header_Right = findViewById(R.id.btn_Header_Right);

        lv_Study_Words = findViewById(R.id.lv_Study_Words);
        wordsAdapter = new WordsAdapter(this,allWords );
        lv_Study_Words.setAdapter(wordsAdapter);


        btn_Header_Left.setOnClickListener(this);
        btn_Header_Right.setOnClickListener(this);
        lv_Study_Words.setOnItemClickListener(this);
    }
    protected void initData(){
        menuPopup = new MenuPopup(this, this, "添加单词", "编辑单词");

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        freshData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        final Word word = allWords.get(position);
        new Thread(new Runnable() {
            @Override
            public void run() {

                int studytimes = word.getStudytimes();
                word.setStudytimes(studytimes + 1);

                List<Word> temple = WordDatabase.getInstance(StudyWordsActivity.this).wordDao().getAllWords(unitID);
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




        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, word.getWord()));


    }

    @Override
    public void onPopupClick(View v, int position) {
        switch (position){
            case MenuPopup.MENU_01:
                AddWordsActivity.launchAddWords(this, unitID);
                break;

            case MenuPopup.MENU_02:
                ModifyWordActivity.launchModifyWords(this, unitID);
                break;
        }
    }

    public void freshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Word> temple = WordDatabase.getInstance(StudyWordsActivity.this).wordDao().getAllWords(unitID);
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

    /*public void addfreshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Unit unit = new Unit();
                unit.setUnit("123");
                unit.setUnittime(System.currentTimeMillis());
                WordDatabase.getInstance(StudyWordsActivity.this).unitDao().insert(unit);

                List<Word> temple = WordDatabase.getInstance(StudyWordsActivity.this).wordDao().getAllWords(unitID);
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
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
