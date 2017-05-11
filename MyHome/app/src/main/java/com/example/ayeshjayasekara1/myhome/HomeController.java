package com.example.ayeshjayasekara1.myhome;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

/**
 * Created by ayeshjayasekara1 on 4/15/17.
 */

public final class HomeController {
    private int Shifter[];
    private  int totalPins;
    private PNConfiguration pnConfiguration;
    private String Chennel;
    private final PubNub Connect;

    public void Modify (int pin, int value){
        Shifter[pin]=value;
    }

    private void Send(String msg){
        Connect.publish()
                .message(msg)
                .channel(Chennel)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        // handle publish result, status always present, result if successful
                        // status.isError to see if error happened
                    }
                });
    }

    private void Commit(){
        Send(ConverArraytoString());
    }

    private String ConverArraytoString(){
        String temp = new String();
        temp="";
        for (int t : Shifter)
            temp +=t;
        return temp;
    }



    public HomeController(String pubKey, String subKey, String Channel , int pins) {
        Shifter = new int[24];
        this.Chennel=Channel;
        totalPins = pins;
        //sub-c-764a7b9e-16a3-11e7-bb8a-0619f8945a4f
        //pub-c-784c01b6-34fa-46e2-aba7-5eebb8efac0e
        pnConfiguration= new PNConfiguration();
        pnConfiguration.setSubscribeKey(subKey);
        pnConfiguration.setPublishKey(pubKey);
        pnConfiguration.setSecure(false);
        Connect = new PubNub(pnConfiguration);
        Connect.subscribe().channels(Arrays.asList(Chennel)).execute();
        SetupPub();

    }

    public void setAllOff(){

        for(int i = 0; i < totalPins; i++ )
            Modify(i,0);
        Commit();
    }

    public void setAllOn(){

        for(int i = 0; i < totalPins; i++ )
            Modify(i,1);
        Commit();
    }

    private void TestAll(){
        setAllOff();
        setAllOn();
        setAllOff();
    }


    private void SetupPub(){
        Connect.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {


                if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    // This event happens when radio / connectivity is lost
                }

                else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {

                    // Connect event. You can do stuff like publish, and know you'll get it.
                    // Or just use the connected event to confirm you are subscribed for
                    // UI / internal notifications, etc

                    if (status.getCategory() == PNStatusCategory.PNConnectedCategory){
                        pubnub.publish().channel(Chennel).message("Connected").async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                // Check whether request successfully completed or not.
                                if (!status.isError()) {

                                    // Message successfully published to specified channel.
                                }
                                // Request processing failed.
                                else {

                                    // Handle message publish error. Check 'category' property to find out possible issue
                                    // because of which request did fail.
                                    //
                                    // Request can be resent using: [status retry];
                                }
                            }
                        });
                    }
                }
                else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {

                    // Happens as part of our regular operation. This event happens when
                    // radio / connectivity is lost, then regained.
                }
                else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {

                    // Handle messsage decryption error. Probably client configured to
                    // encrypt messages and on live data feed it received plain text.
                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                // Handle new message stored in message.message
                if (message.getChannel() != null) {
                    // Message has been received on channel group stored in
                    // message.getChannel()

                }
                else {
                    // Message has been received on channel stored in
                    // message.getSubscription()
                }

            /*
                log the following items with your favorite logger
                    - message.getMessage()
                    - message.getSubscription()
                    - message.getTimetoken()
            */
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
            }
        });
    }
}
