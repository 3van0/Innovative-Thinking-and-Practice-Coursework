package com.evan.holder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button mbuttonCamActivity;
    private Button mbuttonGotoHorizontal;
    private Button mbuttonGotoVertical;
    private Button mbuttonSave;
    private Button mbuttonRestore;
    private Button mbuttonUp;
    private Button mbuttonLeft;
    private Button mbuttonRight;
    private Button mbuttonDown;
    private Button mbuttonBTConnect;
    private Button mbuttonBTCMD1;

    private String[] strSavings = new String[0];

    BluetoothArduino mBlue = BluetoothArduino.getInstance("G8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbuttonCamActivity = findViewById(R.id.buttonCamActivity);
        mbuttonGotoHorizontal = findViewById(R.id.buttonGoToHorizontal);
        mbuttonGotoVertical =  findViewById(R.id.buttonGoToVertical);
        mbuttonSave =  findViewById(R.id.buttonSave);
        mbuttonRestore =  findViewById(R.id.buttonRestore);
        mbuttonUp =  findViewById(R.id.buttonUp);
        mbuttonLeft =  findViewById(R.id.buttonLeft);
        mbuttonRight =  findViewById(R.id.buttonRight);
        mbuttonDown =  findViewById(R.id.buttonDown);
        mbuttonBTConnect =  findViewById(R.id.buttonBTConnect);
        mbuttonBTCMD1 =  findViewById(R.id.buttonBTCMD1);

        setListeners();


    }

    private void setListeners() {
        OnClick onclick = new OnClick();
        mbuttonCamActivity.setOnClickListener(onclick);
        mbuttonGotoHorizontal.setOnClickListener(onclick);
        mbuttonGotoVertical.setOnClickListener(onclick);
        mbuttonSave.setOnClickListener(onclick);
        mbuttonRestore.setOnClickListener(onclick);
        mbuttonUp.setOnClickListener(onclick);
        mbuttonLeft.setOnClickListener(onclick);
        mbuttonRight.setOnClickListener(onclick);
        mbuttonDown.setOnClickListener(onclick);
        mbuttonBTConnect.setOnClickListener(onclick);
        mbuttonBTCMD1.setOnClickListener(onclick);
    }

    public String[] addDataToArray(String[] s, String newData) {
        s = Arrays.copyOf(s, s.length + 1);
        s[s.length - 1] = newData;
        return s;
    }

    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonCamActivity:
                    Intent intent = new Intent(MainActivity.this, CamActivity.class);
                    startActivity(intent);
                    break;
                case R.id.buttonGoToHorizontal:
                    break;
                case R.id.buttonGoToVertical:
                    break;
                case R.id.buttonSave:
                    final EditText et = new EditText(MainActivity.this);
                    new AlertDialog.Builder(MainActivity.this).setTitle("Save ID")
                            .setView(et)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
                                    strSavings = addDataToArray(strSavings, et.getText().toString());
                                }
                            }).setNegativeButton("Cancel", null).show();
                    break;
                case R.id.buttonRestore:
                    if (strSavings.length == 0) {
                        break;
                    } else {
                        new AlertDialog.Builder(MainActivity.this).setTitle("Restore Pos")
                                .setItems(strSavings, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getApplicationContext(), strSavings[i], Toast.LENGTH_LONG).show();
                                    }
                                }).show();
                        break;
                    }
                case R.id.buttonUp:
                    break;
                case R.id.buttonLeft:
                    break;
                case R.id.buttonRight:
                    break;
                case R.id.buttonDown:
                    break;
                case R.id.buttonBTConnect:
                    mBlue.Connect();
                    mBlue.SendMessage("connected");
                    break;
                case R.id.buttonBTCMD1:
                    mBlue.SendMessage("CMD01");
                    break;

            }
        }
    }
}