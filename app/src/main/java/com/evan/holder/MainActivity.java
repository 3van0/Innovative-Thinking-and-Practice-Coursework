package com.evan.holder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button mbuttonCamActivity;
    private Button mbuttonGotoHorizontal;
    private Button mbuttonGotoVertical;
    private Button mbuttonSave;
    private Button mbuttonRestore;
    private Button mbuttonDelete;
    private Button mbuttonUp;
    private Button mbuttonLeft;
    private Button mbuttonRight;
    private Button mbuttonDown;
    private Button mbuttonBTConnect;
    private Button mbuttonBTCMD1;
    private TextView mtextHorizontalAngle;
    private TextView mtextVerticalAngle;

    //private String[] strSavings = new String[0];

    BluetoothArduino mBlue = BluetoothArduino.getInstance("G8");

    //DBHelper mDBHelper = new DBHelper(this, "test_db", null, 1);

    //DBHelper mDBHelper;
    SQLiteDatabase dB;

    private int currentServoPos1 = 105;
    private int currentServoPos2 = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbuttonCamActivity = findViewById(R.id.buttonCamActivity);
        mbuttonGotoHorizontal = findViewById(R.id.buttonGoToHorizontal);
        mbuttonGotoVertical = findViewById(R.id.buttonGoToVertical);
        mbuttonSave = findViewById(R.id.buttonSave);
        mbuttonRestore = findViewById(R.id.buttonRestore);
        mbuttonDelete = findViewById(R.id.buttonDelete);
        mbuttonUp = findViewById(R.id.buttonUp);
        mbuttonLeft = findViewById(R.id.buttonLeft);
        mbuttonRight = findViewById(R.id.buttonRight);
        mbuttonDown = findViewById(R.id.buttonDown);
        mbuttonBTConnect = findViewById(R.id.buttonBTConnect);
        mbuttonBTCMD1 = findViewById(R.id.buttonBTCMD1);
        mtextHorizontalAngle = findViewById(R.id.textHorizontalAngle);
        mtextVerticalAngle = findViewById(R.id.textVerticalAngle);


        // mDBHelper = DBHelper.getInstance().getHelper();
        dB = DBHelper.getInstance(this);
        setListeners();


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        //while quit from CamActivity, or restart process, retrieve current pos from db
        Cursor newcursor = dB.query("tableTrans", null, "Name=?", new String[]{"CamSave"}, null, null, null);
        if (newcursor.moveToFirst()) {
            currentServoPos1 = newcursor.getInt(newcursor.getColumnIndex("ServoPos1"));
            currentServoPos2 = newcursor.getInt(newcursor.getColumnIndex("ServoPos2"));
        }
        newcursor.close();
        /*
        Toast.makeText(this, currentServoPos1 + "", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, currentServoPos2 + "", Toast.LENGTH_SHORT).show();
         */

    }

    @Override
    protected void onStop() {
        Cursor cursor = dB.query("tableTrans", null, "Name=?", new String[]{"CamSave"}, null, null, null);
        ContentValues values = new ContentValues();
        values.put("Name", "CamSave");
        values.put("ServoPos1", currentServoPos1);
        values.put("ServoPos2", currentServoPos2);
        if (cursor.moveToFirst()) {
            dB.update("tableTrans", values, "Name=?", new String[]{"CamSave"});
        } else {
            dB.insert("tableTrans", null, values);
        }
        cursor.close();
        super.onStop();
    }


    private void setListeners() {
        OnClick onclick = new OnClick();
        mbuttonCamActivity.setOnClickListener(onclick);
        mbuttonGotoHorizontal.setOnClickListener(onclick);
        mbuttonGotoVertical.setOnClickListener(onclick);
        mbuttonSave.setOnClickListener(onclick);
        mbuttonRestore.setOnClickListener(onclick);
        mbuttonDelete.setOnClickListener(onclick);
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

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            String[] strSavings = new String[0];
            //创建游标对象
            final SQLiteDatabase db = dB;

            Cursor cursor = db.query("tableSave", new String[]{"Name"}, null, null, null, null, null);

            switch (view.getId()) {
                case R.id.buttonCamActivity:
                    if (mBlue.connected) {
                        mBlue.SendMessage("f");
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluetooth disconnected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Cursor newcursor = db.query("tableTrans", null, "Name=?", new String[]{"CamSave"}, null, null, null);
                    ContentValues values = new ContentValues();
                    values.put("Name", "CamSave");
                    values.put("ServoPos1", currentServoPos1);
                    values.put("ServoPos2", currentServoPos2);
                    if (newcursor.moveToFirst()) {
                        db.update("tableTrans", values, "Name=?", new String[]{"CamSave"});
                    } else {
                        db.insert("tableTrans", null, values);
                    }
                    newcursor.close();
                    Intent intent = new Intent(MainActivity.this, CamActivity.class);
                    startActivity(intent);
                    break;
                case R.id.buttonGoToHorizontal:
                    //BT write;
                    //update currentServoPos1, currentServoPos2
                    if (mBlue.connected) {
                        if (!mtextHorizontalAngle.getText().toString().equals("")) {
                            currentServoPos1 = Integer.parseInt(mtextHorizontalAngle.getText().toString());
                            mBlue.SendMessage("h" + currentServoPos1);
                        } else {
                            Toast.makeText(getApplicationContext(), "Empty input!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluetooth disconnected", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonGoToVertical:
                    //BT write;
                    //update currentServoPos1, currentServoPos2
                    if (mBlue.connected) {
                        if (mtextVerticalAngle.getText().toString().equals("")){
                            currentServoPos2 = Integer.parseInt(mtextVerticalAngle.getText().toString());
                            mBlue.SendMessage("v" + currentServoPos2);
                        } else {
                            Toast.makeText(getApplicationContext(), "Empty input!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluetooth disconnected", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonSave:
                    final EditText et = new EditText(MainActivity.this);
                    new AlertDialog.Builder(MainActivity.this).setTitle("Save ID")
                            .setView(et)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_SHORT).show();
                                    //SQLiteDatabase db = mDBHelper.getWritableDatabase();
                                    //strSavings = addDataToArray(strSavings, et.getText().toString());
                                    ContentValues values = new ContentValues();
                                    values.put("Name", et.getText().toString());
                                    values.put("ServoPos1", currentServoPos1);
                                    values.put("ServoPos2", currentServoPos2);
                                    db.insert("tableSave", null, values);
                                }
                            }).setNegativeButton("Delete", null).show();
                    break;
                case R.id.buttonDelete:
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("Name"));
                        strSavings = addDataToArray(strSavings, name);
                    }
                    cursor.close();
                    if (strSavings.length == 0) {
                        break;
                    } else {
                        final String[] finalStrSavings = strSavings;
                        new AlertDialog.Builder(MainActivity.this).setTitle("Delete Pos")
                                .setItems(strSavings, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getApplicationContext(), finalStrSavings[i], Toast.LENGTH_SHORT).show();
                                        db.delete("tableSave", "Name=?", new String[]{finalStrSavings[i]});
                                    }
                                }).show();
                    }
                    break;

                case R.id.buttonRestore:
                    if (mBlue.connected) {
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(cursor.getColumnIndex("Name"));
                            strSavings = addDataToArray(strSavings, name);
                        }
                        cursor.close();
                        if (strSavings.length == 0) {
                            break;
                        } else {
                            final String[] finalStrSavings = strSavings;
                            new AlertDialog.Builder(MainActivity.this).setTitle("Restore Pos")
                                    .setItems(strSavings, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(getApplicationContext(), finalStrSavings[i], Toast.LENGTH_SHORT).show();
                                            Cursor newcursor = db.query("tableSave", null, "Name=?", new String[]{finalStrSavings[i]}, null, null, null);
                                            newcursor.moveToFirst();
                                            currentServoPos1 = newcursor.getInt(newcursor.getColumnIndex("ServoPos1"));
                                            currentServoPos2 = newcursor.getInt(newcursor.getColumnIndex("ServoPos2"));
                                            newcursor.close();
                                            //蓝牙传输两个位置数据
                                            mBlue.SendMessage("sh" + currentServoPos1 + "v" + currentServoPos2);
                                            Toast.makeText(getApplicationContext(), currentServoPos1 + "", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplicationContext(), currentServoPos2 + "", Toast.LENGTH_SHORT).show();
                                        }
                                    }).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluetooth disconnected", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonUp:
                    //BT write;
                    //update currentServoPos1, currentServoPos2
                    if (mBlue.connected) {
                        currentServoPos2 += 5;
                        mBlue.SendMessage("u");
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluetooth disconnected", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonLeft:
                    //BT write;
                    //update currentServoPos1, currentServoPos2
                    if (mBlue.connected) {
                        currentServoPos1 -= 5;
                        mBlue.SendMessage("l");
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluetooth disconnected", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonRight:
                    //BT write;
                    //update currentServoPos1, currentServoPos2
                    if (mBlue.connected) {
                        currentServoPos1 += 5;
                        mBlue.SendMessage("r");
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluetooth disconnected", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonDown:
                    //BT write;
                    //update currentServoPos1, currentServoPos2
                    if (mBlue.connected) {
                        currentServoPos2 -= 5;
                        mBlue.SendMessage("d");
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluetooth disconnected", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonBTConnect:
                    if (mBlue.Connect()) {
                        mBlue.SendMessage("e");
                    } else {
                        Toast.makeText(getApplicationContext(), "No Bluetooth called 'G8' found", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.buttonBTCMD1:
                    //quit from cam activity, notify arduino (just in case)
                    //currentServoPos1 += 5;
                    mBlue.SendMessage("t");
                    break;
            }
        }
    }
}