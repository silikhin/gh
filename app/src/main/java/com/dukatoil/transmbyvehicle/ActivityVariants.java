package com.dukatoil.transmbyvehicle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityVariants extends AppCompatActivity {

    Toolbar toolbar;
    final Context secondActivity = this;
    String extra; // extra from MainActivity (type "model_variant"
    int connector, connector1; // resource id for transmission variants
    String[] variants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variants);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        extra = intent.getStringExtra("version").toLowerCase();
        connector = this.getResources().getIdentifier(extra, "array", this.getPackageName());
        variants = getResources().getStringArray(connector);

        final ListView lvMain = (ListView) findViewById(R.id.lvMain);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_transmission_variants, variants);

        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String extra_transm = lvMain.getItemAtPosition(position).toString();
                Intent intent1 = new Intent(view.getContext(), ActivityTransmission.class);
                intent1.putExtra("transmission", extra_transm);
                startActivity(intent1);
            }
        });

        if (variants.length>1){
            final String description = extra + "_descr";
            final FloatingActionButton fabButton1 = new FloatingActionButton.Builder(this)
                    .withDrawable(getResources().getDrawable(R.drawable.ic_help_outline_white_24dp))
                    .withButtonColor(Color.RED)
                    .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                    .withMargins(0, 0, 16, 16)
                    .create();
            fabButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fabButton1.hideFloatingActionButton();
                    AlertDialog.Builder builder = new AlertDialog.Builder(secondActivity);
                    connector1 = secondActivity.getResources().getIdentifier(description, "string", getPackageName());
                    builder.setMessage(getResources().getString(connector1))
                        .setTitle(R.string.text_dialog_title)
                        .setCancelable(false)
                        .setNegativeButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                fabButton1.showFloatingActionButton();
                            }
                        });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.faq:
                intent = new Intent(secondActivity, ActivityFaq.class);
                startActivity(intent);
                return true;
            case R.id.problems:
                intent = new Intent(secondActivity, ActivityProblems.class);
                startActivity(intent);
                return true;
            case R.id.contacts:
                AlertDialog.Builder builder = new AlertDialog.Builder(secondActivity);
                builder.setMessage(getResources().getString(R.string.contacts))
                        .setTitle(R.string.menuTitleContacts)
                        .setCancelable(true)
                        .setNegativeButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
