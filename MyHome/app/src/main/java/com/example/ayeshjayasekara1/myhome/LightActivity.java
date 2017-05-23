package com.example.ayeshjayasekara1.myhome;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class LightActivity extends AppCompatActivity {
    int l1,l2;
    private StatusCommit sender;
    public int[] ControllerArray;
    private StatusRetrieve reciever;
    private String username;
    private ToggleButton Light1,Light2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ControllerArray = new int[17];
        Bundle b2 = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        if(b2 != null)
        {
            l1 = b2.getInt("light1");
            l2 = b2.getInt("light2");
            username = b2.getString("username");
        }


        Light1 = (ToggleButton) findViewById(R.id.but1);
        Light2 = (ToggleButton) findViewById(R.id.but2);
        if(l1==1){
            Light1.setChecked(true);
        }
        else
            Light1.setChecked(false);
        if(l2==1){
            Light2.setChecked(true);
        }
        else
            Light2.setChecked(false);
        LightActivity.RetrieveTask t = new LightActivity.RetrieveTask();
        t.execute((Void) null);

        Light1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ControllerArray[0]=1;
                    Light1.setChecked(true);
                    // The toggle is enabled
                } else {
                    ControllerArray[0]=0;
                    // The toggle is disabled
                    Light1.setChecked(false);
                }
                LightActivity.CommitChanges r= new LightActivity.CommitChanges();
                r.execute((Void) null);
            }


        });

        Light2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ControllerArray[1]=1;
                    Light2.setChecked(true);
                    // The toggle is enabled
                } else {
                    ControllerArray[1]=0;
                    // The toggle is disabled
                    Light2.setChecked(false);
                }
                LightActivity.CommitChanges r= new LightActivity.CommitChanges();
                r.execute((Void) null);
            }


        });

    }
    public class CommitChanges extends AsyncTask<Void,Void,Boolean> {

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
           // textView.setText("Done!");
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

                if(ControllerArray[0]==0)
                    Light1.setChecked(false);
                else
                    Light1.setChecked(true);
                if(ControllerArray[1]==0)
                    Light2.setChecked(false);
                else
                    Light2.setChecked(true);

            }
        }
    }
}
