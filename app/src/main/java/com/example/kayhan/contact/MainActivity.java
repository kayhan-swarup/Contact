package com.example.kayhan.contact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    ListView contactList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = (ListView) findViewById(R.id.contact_listView);
        MySqliteOpenHelper dbHelper = new MySqliteOpenHelper(this);
        Cursor cursor = dbHelper.getAllContacts();
        final MyAdapter adapter = new MyAdapter(this,cursor);
        contactList.setAdapter(adapter);
        dbHelper.close();
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final View view1 = view;
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MySqliteOpenHelper db = new MySqliteOpenHelper(getBaseContext());
                                TextView textView = (TextView) view1.findViewById(R.id.name_textView);
                                db.deleteContact(textView.getText().toString());
                                Cursor cursor = db.getAllContacts();
                                adapter.changeCursor(cursor);
                            }
                        });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final AlertDialog dialog = builder.create();

                dialog.show();


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
