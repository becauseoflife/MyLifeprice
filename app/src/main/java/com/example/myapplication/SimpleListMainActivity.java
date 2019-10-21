package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;

public class SimpleListMainActivity extends AppCompatActivity {

    private ListView listViewGoods;

    private String[] goodsArray=
            new String[]{"青椒","apple","meat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_price_main);

        listViewGoods= (ListView) this.findViewById(R.id.list_view_goods);

        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1, goodsArray);

        listViewGoods.setAdapter(stringArrayAdapter);
    }
}
