package com.thonetdry.note_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayAdapter<String> adapter;
    ListView item;
    AlertDialog addDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        item  = (ListView)findViewById(R.id.task_list);

        loadGroceryItem();

        findViewById(R.id.task_add).setOnClickListener(View ->{
            showAlertDialog();
        });

    }

    private void showAlertDialog() {
        if (addDialog == null ){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View v= LayoutInflater.from(this).inflate(R.layout.customdialog,(ViewGroup)findViewById(R.id.layoutDialogContainer)
            );
            builder.setView(v);

            addDialog = builder.create();
            if (addDialog.getWindow() != null){
                addDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            final EditText add_item = (EditText)v.findViewById(R.id.textMessage);

            v.findViewById(R.id.buttonYes).setOnClickListener(view ->{
                addDialog.dismiss();
                String item = String.valueOf(add_item.getText());
                dbHelper.insertNewTask(item);
                loadGroceryItem();//getItemList
            });

            v.findViewById(R.id.buttonNo).setOnClickListener(view->{
                addDialog.dismiss();
            });
        }
        addDialog.show();
    }

    private void loadGroceryItem() {
        ArrayList<String> groceryItem = dbHelper.getGroceryItem();
        if (adapter == null){
            adapter = new ArrayAdapter<String>(this,R.layout.grocery_card,R.id.textitem,groceryItem);
           item.setAdapter(adapter);
        }
        else{
            adapter.clear();
            adapter.addAll(groceryItem);
            adapter.notifyDataSetChanged();
        }
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView itemTextView = (TextView)parent.findViewById(R.id.textitem);
        Log.e("String", (String) itemTextView.getText());
        String item =String.valueOf(itemTextView.getText());
        dbHelper.deleteTask(item);
        loadGroceryItem();

    }

}