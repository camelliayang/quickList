package com.example.camelliayang.quicklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.camelliayang.quicklist.Model.QListItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<QListItem> toDoItems;
    ArrayAdapter<QListItem> aToDoAdapter;
    ListView lvItems;
    EditText editItemText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.listViewItems);
        lvItems.setAdapter(aToDoAdapter);
        editItemText = (EditText) findViewById(R.id.editItemText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                toDoItems.get(position).delete();
                toDoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                launchComposeView(position, toDoItems.get(position).toString());
                aToDoAdapter.notifyDataSetChanged();
            }
        });
    }

    public void populateArrayItems() {
        toDoItems = SQLite.select().
                from(QListItem.class).queryList();
        aToDoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoItems);
    }

    public void onAddItem(View view) {
        QListItem listItem = new QListItem();
        String addedItem = editItemText.getText().toString();
        if (toDoItems.isEmpty()) {
            listItem.id = 1;
        }
        else {
            listItem.id = toDoItems.size();
        }
        listItem.setDetail(addedItem);
        aToDoAdapter.add(listItem);
        editItemText.setText("");
        listItem.save();
    }

    public void launchComposeView(final int position, String oldItem) {
        Intent i = new Intent(ListActivity.this, EditItemActivity.class);

        i.putExtra("updateItem", oldItem);
        i.putExtra("updatePos", position);

        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String updatedContent = data.getExtras().getString("savedContent");
            int pos = data.getExtras().getInt("savedPos");

            QListItem item = new QListItem();
            item.setDetail(updatedContent);
            toDoItems.set(pos, item);
            aToDoAdapter.notifyDataSetChanged();
            item.save();
        }
    }
}
