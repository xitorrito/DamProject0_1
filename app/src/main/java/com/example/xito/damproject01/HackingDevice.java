package com.example.xito.damproject01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HackingDevice extends AppCompatActivity {
    private TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hacking_device);
        tl =(TableLayout)findViewById(R.id.tablelayout);
        tl.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TableRow row=new TableRow(this);
        TextView textView = new TextView(this);
        for (int i = 0; i < 10 ; i++) {
            textView.setText(i);
            row.addView( textView);
            tl.addView(row);
        }
    }
}
