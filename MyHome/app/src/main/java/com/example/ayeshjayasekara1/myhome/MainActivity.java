package com.example.ayeshjayasekara1.myhome;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    public int[] ControllerArray;
    private StatusRetrieve reciever;
    private String username;
    private ToggleButton AC,CURTAIN,DOOR;
    private Button LIGHTS;
    private StatusCommit sender;
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ControllerArray = new int[17];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b2 = getIntent().getExtras();
        if(b2 != null)
        {
            username = b2.getString("user");
        }
        textView = (TextView) findViewById(R.id.textView2);
        AC = (ToggleButton) findViewById(R.id.ac);
        LIGHTS = (Button) findViewById(R.id.Lights);
        CURTAIN = (ToggleButton) findViewById(R.id.curtain);
        DOOR = (ToggleButton) findViewById(R.id.door);


        RetrieveTask t = new RetrieveTask();
        t.execute((Void) null);

        DOOR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ControllerArray[8]=1;
                    DOOR.setChecked(true);
                    // The toggle is enabled
                } else {
                    ControllerArray[8]=0;
                    // The toggle is disabled
                    DOOR.setChecked(false);
                }
                CommitChanges r= new CommitChanges();
                r.execute((Void) null);
            }


        });

        CURTAIN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ControllerArray[7]=1;
                    CURTAIN.setChecked(true);
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                    ControllerArray[7]=0;
                    CURTAIN.setChecked(false);
                }
                CommitChanges r= new CommitChanges();
                r.execute((Void) null);
            }

        });

        AC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ControllerArray[9]=1;
                    AC.setChecked(true);
                    // The toggle is enabled
                } else {
                    ControllerArray[9]=0;
                    AC.setChecked(false);
                    // The toggle is disabled
                }
                CommitChanges r= new CommitChanges();
                r.execute((Void) null);
            }
        });

//
       // textView.setText("sfdsfs+ "+fg);
    }

    public class CommitChanges extends  AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            //Do Commits Here
            sender = new StatusCommit(username);
            return sender.Commit(ControllerArray);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
           // RetrieveTask t = new RetrieveTask();
            //t.execute((Void) null);
            textView.setText("Done!");
        }

    }

    public class RetrieveTask extends AsyncTask<Void, Void, Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            reciever = new StatusRetrieve(username);
            String fg = reciever.Communicate();
            ControllerArray = reciever.decodeArray(fg);
            //textView.setText("sfdsfs+ "+ControllerArray[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){

                if(ControllerArray[9]==0)
                    AC.setChecked(false);
                else
                    AC.setChecked(true);
                if(ControllerArray[8]==0)
                    DOOR.setChecked(false);
                else
                    DOOR.setChecked(true);
                if(ControllerArray[7]==0)
                    CURTAIN.setChecked(false);
                else
                    CURTAIN.setChecked(true);
            }
        }
    }
}
