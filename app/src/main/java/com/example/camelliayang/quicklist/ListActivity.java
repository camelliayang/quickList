package com.example.camelliayang.quicklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ArrayList<String> toDoItems;
    ArrayAdapter<String> aToDoAdapter;
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
                toDoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                launchComposeView(position, toDoItems.get(position));
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
            }
        });

    }

    public void populateArrayItems() {
        toDoItems = new ArrayList<String>();
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
    }

    private void readItems() {
        File filesDr = getFilesDir();
        File file = new File(filesDr, "todo.txt");
        try {
            toDoItems = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch (IOException e) {

        }
    }

    private void writeItems() {
        File filesDr = getFilesDir();
        File file = new File(filesDr, "todo.txt");
        try {
            FileUtils.writeLines(file, toDoItems);
        }
        catch (IOException e) {

        }
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(editItemText.getText().toString());
        editItemText.setText("");
        writeItems();
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
            // Extract name value from result extras
            String updatedContent = data.getExtras().getString("savedContent");
            int pos = data.getExtras().getInt("savedPos");
            toDoItems.set(pos, updatedContent);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
