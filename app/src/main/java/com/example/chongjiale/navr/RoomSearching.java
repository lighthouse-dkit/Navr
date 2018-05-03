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

    private final String[] mStrings = { "Entrance", "P1102", "P1103", "P1104", "P1105", "P1106", "P1107", "P1111", "P1119", "P1094"};

    private final double[] latitude={53.98192369,53.98125554,53.98128356,53.98122578,53.98127367,53.98132047,53.98131208,53.98133749,53.98167981,53.98126547};
    private final double[] longitude={-6.39274222,-6.39179356,-6.39164711,-6.39154307,-6.39130638,-6.39144483,-6.39148571,-6.39152689,-6.39170334,-6.39189292};
    TextView from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_searching);

        String roomName= getIntent().getStringExtra("Room_Name");

        from= (TextView)findViewById(R.id.from);
        from.setText(roomName);

        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mStrings));
        lv.setTextFilterEnabled(true);
        sv = (SearchView) findViewById(R.id.sv);
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(this);
        sv.setSubmitButtonEnabled(false);
        sv.setQueryHint("Search room");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long arg3)
            {
                String name = arg0.getItemAtPosition(position).toString();
                Intent intent = new Intent(RoomSearching.this,  PathFinding.class);
                Toast.makeText(RoomSearching.this,"destination:"+name,Toast.LENGTH_LONG).show();
                Bundle b = new Bundle();
                b.putDouble("longitude",longitude[position] );
                b.putDouble("latitude",latitude[position]);
                intent.putExtras(b);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            lv.clearTextFilter();
        } else {

            lv.setFilterText(newText);
            lv.dispatchDisplayHint(View.INVISIBLE);
        }
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String newText) {

        if (TextUtils.isEmpty(newText)) {

            lv.clearTextFilter();
        } else {

            lv.setFilterText(newText);
            lv.dispatchDisplayHint(View.INVISIBLE);
        }
        return true;
    }

}
