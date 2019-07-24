package com.boylab.wordhelp.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.boylab.wordhelp.R;
import com.boylab.wordhelp.adapter.UnitsAdapter;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MenuPopup.OnMenuClickListener, AdapterView.OnItemLongClickListener {

    private Button btn_Header_Left, btn_Header_Right;
    private ListView lv_Units;
    private List<Unit> allUnit = new ArrayList<>();
    private UnitsAdapter unitsAdapter ;

    private MenuPopup menuPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    protected void initView(){
        btn_Header_Left = findViewById(R.id.btn_Header_Left);
        btn_Header_Left.setVisibility(View.INVISIBLE);
        btn_Header_Right = findViewById(R.id.btn_Header_Right);

        lv_Units = findViewById(R.id.lv_Units);
        unitsAdapter = new UnitsAdapter(this, allUnit);
        lv_Units.setAdapter(unitsAdapter);

        btn_Header_Right.setOnClickListener(this);
        lv_Units.setOnItemClickListener(this);
        lv_Units.setOnItemLongClickListener(this);
    }
    protected void initData(){
        menuPopup = new MenuPopup(this, this, "添加单元");



        freshData();

    }

    @Override
    public void onClick(View v) {
        if (ViewClick.isFastDoubleClick()){
            return;
        }
        switch (v.getId()){
            case R.id.btn_Header_Right:
                menuPopup.showAsDropDown(btn_Header_Right);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (ViewClick.isFastDoubleClick()){
            return;
        }
        StudyWordsActivity.launchStudyWords(this, allUnit.get(position).getId());

    }

    @Override
    public void onPopupClick(View v, int position) {
        switch (position){
            case MenuPopup.MENU_01:
                addfreshData();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        EventBus.getDefault().removeStickyEvent(messageEvent);
        freshData();
    }

    public void freshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Unit> temple = WordDatabase.getInstance(MainActivity.this).unitDao().getAllUnit();
                allUnit.clear();
                allUnit.addAll(temple);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        unitsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    public void addfreshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Unit unit = new Unit();
                unit.setUnit("123");
                unit.setUnittime(System.currentTimeMillis());
                WordDatabase.getInstance(MainActivity.this).unitDao().insert(unit);

                List<Unit> temple = WordDatabase.getInstance(MainActivity.this).unitDao().getAllUnit();
                allUnit.clear();
                allUnit.addAll(temple);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        unitsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final Unit unit = allUnit.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("温馨提示！")
                .setMessage("删除此单元 -- Unit"+unit.getId())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                List<Word> templeWords = WordDatabase.getInstance(MainActivity.this).wordDao().getAllWords(unit.getId());
                                if (templeWords != null && !templeWords.isEmpty()){
                                    Word[] arr = (Word[])templeWords.toArray(new Word[templeWords.size()]);
                                    WordDatabase.getInstance(MainActivity.this).wordDao().delete(arr);
                                }

                                WordDatabase.getInstance(MainActivity.this).unitDao().delete(unit);

                                List<Unit> temple = WordDatabase.getInstance(MainActivity.this).unitDao().getAllUnit();
                                allUnit.clear();
                                allUnit.addAll(temple);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        unitsAdapter.notifyDataSetChanged();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
