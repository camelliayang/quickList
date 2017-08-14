package com.example.camelliayang.quicklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static com.example.camelliayang.quicklist.R.id.updateText;

public class EditItemActivity extends AppCompatActivity {
    String itemName;
    Integer itemPos;
    EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editContent = (EditText) findViewById(updateText);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            itemName = (String) bd.get("updateItem");
            itemPos = (Integer) bd.get("updatePos");
            editContent.setText(itemName);
            editContent.setSelection(editContent.getText().length());
        }
    }

    public void onUpdateItem(View v) {
        Intent i = new Intent();
        editContent = (EditText) findViewById(updateText);
        i.putExtra("savedContent", editContent.getText().toString()); // pass arbitrary data to launched activity
        i.putExtra("savedPos", itemPos);
        setResult(RESULT_OK, i);
        finish();
    }

}
