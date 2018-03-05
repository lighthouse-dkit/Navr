package com.example.chongjiale.navr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class RoomSearching extends AppCompatActivity implements
        SearchView.OnQueryTextListener{

    private SearchView sv;
    private ListView lv;

    private final String[] mStrings = { "P1100", "P1101", "P1102", "P1103", "P1104", "P1105", "P1106", "P1107", "P1108", "P1109", "P1110", "P1111", "P1112", "P1113", "P1114", "P1115" };

    EditText from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_searching);

        String roomName= getIntent().getStringExtra("Room_Name");

        from= (EditText)findViewById(R.id.from);
        from.setText(roomName);

        /*
        destination=(EditText)findViewById(R.id.destination);
        destination.setText("test");


        //Initialize Button
        btnStartNav= (Button)findViewById(R.id.nav_button);
        btnStartNav.setOnClickListener(this);
        */


        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mStrings));
        lv.setTextFilterEnabled(true);//设置lv可以被过虑
        sv = (SearchView) findViewById(R.id.sv);
        // 设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(false);
        // 为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        sv.setSubmitButtonEnabled(false);
        // 设置该SearchView内默认显示的提示文本
        sv.setQueryHint("Search room");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long arg3)
            {
                String name = arg0.getItemAtPosition(position).toString();
                // Toast.makeText(this, "Your destination:" + name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RoomSearching.this,  ArNavigateActivity.class);
                Toast.makeText(RoomSearching.this,"destination:"+name,Toast.LENGTH_LONG).show();

                startActivity(intent);

            }
        });

    }

    // 用户输入字符时激发该方法
    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            // 清除ListView的过滤
            lv.clearTextFilter();
        } else {
            // 使用用户输入的内容对ListView的列表项进行过滤
            lv.setFilterText(newText);
            lv.dispatchDisplayHint(View.INVISIBLE);
        }
        return true;
    }

    // 单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String newText) {
//        // 实际应用中应该在该方法内执行实际查询
//        // 此处仅使用Toast显示用户输入的查询内容
//        Toast.makeText(this, "Your destination:" + query, Toast.LENGTH_SHORT).show();
//
//
//        Intent intent = new Intent(RoomSearching.this,  ArNavigateActivity.class);
//        startActivity(intent);
//
//
//
//
//        return false;

        if (TextUtils.isEmpty(newText)) {
            // 清除ListView的过滤
            lv.clearTextFilter();
        } else {
            // 使用用户输入的内容对ListView的列表项进行过滤
            lv.setFilterText(newText);
            lv.dispatchDisplayHint(View.INVISIBLE);
        }
        return true;
    }



    /*

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(RoomSearching.this,  ArNavigateActivity.class);
        startActivity(intent);
    }
    */
}
