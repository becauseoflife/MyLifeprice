package com.example.myapplication;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.FileDataSource;
import com.example.myapplication.data.model.Good;

import java.util.ArrayList;
import java.util.List;

public class LifePriceMainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_ITEM_NEW = 1;
    public static final int CONTEXT_MENU_ITEM_UPDATE= CONTEXT_MENU_ITEM_NEW+1;
    public static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE+1;
    public static final int CONTEXT_MENU_ITEM_ABOUT = CONTEXT_MENU_ITEM_DELETE+1;
    public static final int REQUEST_CODE_NEW_GOOD = 901;
    public static final int REQUEST_CODE_UPDATE_GOOD = 902;
    private ArrayList<Good> theGoods;
    private FileDataSource fileDataSource;
    private ListView listViewSuper;
    private GoodsArrayAdapter theAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_price_main);

        InitData();

        listViewSuper= (ListView) this.findViewById(R.id.list_view_goods);
        theAdaper=new GoodsArrayAdapter(this,R.layout.list_item_good,theGoods);
        listViewSuper.setAdapter(theAdaper);

        this.registerForContextMenu(listViewSuper);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==listViewSuper) {
            int itemPosition=((AdapterView.AdapterContextMenuInfo)menuInfo).position;
            menu.setHeaderTitle(theGoods.get(itemPosition).getName());
            menu.add(0, CONTEXT_MENU_ITEM_NEW, 0, "新建");
            menu.add(0, CONTEXT_MENU_ITEM_UPDATE, 0, "修改");
            menu.add(0, CONTEXT_MENU_ITEM_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_ITEM_ABOUT, 0, "关于...");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE_NEW_GOOD:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("good_name");
                    double price =data.getDoubleExtra("good_price",0);
                    theGoods.add(position, new Good(name,price,R.drawable.a4));
                    theAdaper.notifyDataSetChanged();

                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_UPDATE_GOOD:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("good_name");
                    double price =data.getDoubleExtra("good_price",0);

                    Good good=theGoods.get(position);
                    good.setName(name);
                    good.setPrice(price);
                    theAdaper.notifyDataSetChanged();

                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case CONTEXT_MENU_ITEM_NEW: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                Intent intent = new Intent(LifePriceMainActivity.this,GoodEditActivity.class);
                intent.putExtra("edit_position",menuInfo.position);
                startActivityForResult(intent, REQUEST_CODE_NEW_GOOD);
                break;
            }
            case CONTEXT_MENU_ITEM_UPDATE: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                Good good=theGoods.get(menuInfo.position);

                Intent intent = new Intent(LifePriceMainActivity.this,GoodEditActivity.class);
                intent.putExtra("edit_position",menuInfo.position);
                intent.putExtra("good_name",good.getName());
                intent.putExtra("good_price",good.getPrice());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_GOOD);
                break;
            }
            case CONTEXT_MENU_ITEM_DELETE: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final int itemPosition=menuInfo.position;
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("询问")
                        .setMessage("你确定要删除这条吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                theGoods.remove(itemPosition);
                                theAdaper.notifyDataSetChanged();
                                Toast.makeText(LifePriceMainActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
                break;
            }
            case CONTEXT_MENU_ITEM_ABOUT:
                Toast.makeText(this, "版权所有by shpchen!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileDataSource.save();
    }

    private void InitData() {
        fileDataSource=new FileDataSource(this);
        theGoods=fileDataSource.load();
        if(theGoods.size()==0) {
            theGoods.add(new Good("目前没有鱼", 1, R.drawable.a1));
        }
    }

    protected class GoodsArrayAdapter extends ArrayAdapter<Good>
    {
        private  int resourceId;
        public GoodsArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Good> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.image_view_good);
            TextView name = (TextView)item.findViewById(R.id.text_view_name);
            TextView price = (TextView)item.findViewById(R.id.text_view_price);

            Good good_item= this.getItem(position);
            img.setImageResource(good_item.getPictureId());
            name.setText("商品："+good_item.getName());
            price.setText("价格："+good_item.getPrice()+"元");

            return item;
        }
    }
}