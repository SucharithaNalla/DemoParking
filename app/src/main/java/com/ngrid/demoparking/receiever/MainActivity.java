package com.ngrid.demoparking.receiever;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ngrid.demoparking.R;
import com.ngrid.demoparking.service.MqttService;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //The URL of the Mosquitto Broker is 192.168.9.100:1883
    MqttAndroidClient client;
    String clientId = MqttClient.generateClientId();
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4;
    TextView tv_filled, tv_empty, tv_moving;
    private Toolbar toolbar;

    BroadcastReceiver receiver;
    String devicedetails_String;

    LinearLayout mSlot1;
    LinearLayout mSlot21;
    LinearLayout mSlot41;
    LinearLayout level1, level2, level3;

    RelativeLayout mSlot2, mSlot3, mSlot4, mSlot5, mSlot6, mSlot7, mSlot8, mSlot9, mSlot10;
    RelativeLayout mSlot11, mSlot12, mSlot13, mSlot14, mSlot15, mSlot16, mSlot17, mSlot18, mSlot19, mSlot20;
    RelativeLayout mSlot22, mSlot23, mSlot24, mSlot25, mSlot26, mSlot27, mSlot28, mSlot29, mSlot30;
    RelativeLayout mSlot31, mSlot32, mSlot33, mSlot34, mSlot35, mSlot36, mSlot37, mSlot38, mSlot39, mSlot40;
    RelativeLayout mSlot42, mSlot43, mSlot44, mSlot45, mSlot46, mSlot47, mSlot48, mSlot49, mSlot50;
    RelativeLayout mSlot51, mSlot52, mSlot53;

    TextView mSlot_text1, mSlot_text2, mSlot_text3, mSlot_4, mSlot_textx5, mSlot_text6, mSlot_text7, mSlot_text8, mSlot_text9, mSlot_text10;
    TextView mSlot_text11, mSlot_text12, mSlot_text13, mSlot_text14, mSlot_text15, mSlot_text16, mSlot_text17, mSlot_text18, mSlot_text19, mSlot_text20;
    TextView mSlot_text21, mSlot_text22, mSlot_text23, mSlot_24, mSlot_textx25, mSlot_text26, mSlot_text27, mSlot_text28, mSlot_text29, mSlot_text30;
    TextView mSlot_text31, mSlot_text32, mSlot_text33, mSlot_text34, mSlot_text35, mSlot_text36, mSlot_text37, mSlot_text38, mSlot_text39, mSlot_text40;
    TextView mSlot_text41, mSlot_text42, mSlot_text43, mSlot_text44, mSlot_text45, mSlot_text46, mSlot_text47, mSlot_text48, mSlot_text49, mSlot_text50;
    TextView mSlot_text51, mSlot_text52, mSlot_text53;

    ImageView mSlot_image1, mSlot_image2, mSlot_image3, mSlot_image4, mSlot_image5, mSlot_image6, mSlot_image7, mSlot_image8, mSlot_image9, mSlot_image10;
    ImageView mSlot_image11, mSlot_image12, mSlot_image13, mSlot_image14, mSlot_image15, mSlot_image16, mSlot_image17, mSlot_image18, mSlot_image19, mSlot_image20;
    ImageView mSlot_image21, mSlot_image22, mSlot_image23, mSlot_image24, mSlot_image25, mSlot_image26, mSlot_image27, mSlot_image28, mSlot_image29, mSlot_image30;
    ImageView mSlot_image31, mSlot_image32, mSlot_image33, mSlot_image34, mSlot_image35, mSlot_image36, mSlot_image37, mSlot_image38, mSlot_image39, mSlot_image40;
    ImageView mSlot_image41, mSlot_image42, mSlot_image43, mSlot_image44, mSlot_image45, mSlot_image46, mSlot_image47, mSlot_image48, mSlot_image49, mSlot_image50;
    ImageView mSlot_image51, mSlot_image52, mSlot_image53;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://183.82.122.136:1883", clientId);
        client.setCallback(new MqttCallbackHandler(client));//This is here for when a message is received

        MqttConnectOptions options = new MqttConnectOptions();
        try {
//            options.setUserName("yant");
//            options.setPassword("yolo1234".toCharArray());
            IMqttToken token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected

                    String subtopic = "ngrid/smartparking/carstatus";
                    int qos = 0;
                    try {
                        IMqttToken subToken = client.subscribe(subtopic, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                // The message was published
                                Log.i("mqtt", "subscription success");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                // The subscription could not be performed, maybe the user was not
                                // authorized to subscribe on the specified topic e.g. using wildcards
                                Log.i("mqtt", "subscription failed");

                            }
                        });


                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    Log.d("mqtt", "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("mqtt", "onFailure");

                }

            });


        } catch (MqttException e) {
            e.printStackTrace();
        }
        if (!isMyServiceRunning(MqttService.class)) {

            startService(new Intent(this, MqttService.class));

        }
        Toast.makeText(MainActivity.this, "if you want to demo for smart parking\n click floatbutton and select demo start option  ", Toast.LENGTH_SHORT).show();
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);

        level1 = (LinearLayout) findViewById(R.id.ll_levelone);
        level2 = (LinearLayout) findViewById(R.id.ll_leveltwo);
        level3 = (LinearLayout) findViewById(R.id.ll_levelthree);

        tv_filled = (TextView) findViewById(R.id.tv_filled);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        tv_moving = (TextView) findViewById(R.id.tv_moving);

        mSlot1 = (LinearLayout) findViewById(R.id.slot1);
        mSlot2 = (RelativeLayout) findViewById(R.id.slot2);
        mSlot3 = (RelativeLayout) findViewById(R.id.slot3);
        mSlot4 = (RelativeLayout) findViewById(R.id.slot4);
        mSlot5 = (RelativeLayout) findViewById(R.id.slot5);
        mSlot6 = (RelativeLayout) findViewById(R.id.slot6);
        mSlot7 = (RelativeLayout) findViewById(R.id.slot7);
        mSlot8 = (RelativeLayout) findViewById(R.id.slot8);
        mSlot9 = (RelativeLayout) findViewById(R.id.slot9);
        mSlot10 = (RelativeLayout) findViewById(R.id.slot10);
        mSlot11 = (RelativeLayout) findViewById(R.id.slot11);
        mSlot12 = (RelativeLayout) findViewById(R.id.slot12);
        mSlot13 = (RelativeLayout) findViewById(R.id.slot13);
        mSlot14 = (RelativeLayout) findViewById(R.id.slot14);
        mSlot15 = (RelativeLayout) findViewById(R.id.slot15);
        mSlot16 = (RelativeLayout) findViewById(R.id.slot16);
        mSlot17 = (RelativeLayout) findViewById(R.id.slot17);
        mSlot18 = (RelativeLayout) findViewById(R.id.slot18);
        mSlot19 = (RelativeLayout) findViewById(R.id.slot19);
        mSlot20 = (RelativeLayout) findViewById(R.id.slot20);

        mSlot_text1 = (TextView) findViewById(R.id.slot_text1);
        mSlot_text2 = (TextView) findViewById(R.id.slot_text2);
        mSlot_text3 = (TextView) findViewById(R.id.slot_text3);
        mSlot_4 = (TextView) findViewById(R.id.slot_text4);
        mSlot_textx5 = (TextView) findViewById(R.id.slot_text5);
        mSlot_text6 = (TextView) findViewById(R.id.slot_text6);
        mSlot_text7 = (TextView) findViewById(R.id.slot_text7);
        mSlot_text8 = (TextView) findViewById(R.id.slot_text8);
        mSlot_text9 = (TextView) findViewById(R.id.slot_text9);
        mSlot_text10 = (TextView) findViewById(R.id.slot_text10);
        mSlot_text11 = (TextView) findViewById(R.id.slot_text11);
        mSlot_text12 = (TextView) findViewById(R.id.slot_text12);
        mSlot_text13 = (TextView) findViewById(R.id.slot_text13);
        mSlot_text14 = (TextView) findViewById(R.id.slot_text14);
        mSlot_text15 = (TextView) findViewById(R.id.slot_text15);
        mSlot_text16 = (TextView) findViewById(R.id.slot_text16);
        mSlot_text17 = (TextView) findViewById(R.id.slot_text17);
        mSlot_text18 = (TextView) findViewById(R.id.slot_text18);
        mSlot_text19 = (TextView) findViewById(R.id.slot_text19);
        mSlot_text20 = (TextView) findViewById(R.id.slot_text20);

        mSlot_image1 = (ImageView) findViewById(R.id.slot_image1);
        mSlot_image2 = (ImageView) findViewById(R.id.slot_image2);
        mSlot_image3 = (ImageView) findViewById(R.id.slot_image3);
        mSlot_image4 = (ImageView) findViewById(R.id.slot_image4);
        mSlot_image5 = (ImageView) findViewById(R.id.slot_image5);
        mSlot_image6 = (ImageView) findViewById(R.id.slot_image6);
        mSlot_image7 = (ImageView) findViewById(R.id.slot_image7);
        mSlot_image8 = (ImageView) findViewById(R.id.slot_image8);
        mSlot_image9 = (ImageView) findViewById(R.id.slot_image9);
        mSlot_image10 = (ImageView) findViewById(R.id.slot_image10);
        mSlot_image11 = (ImageView) findViewById(R.id.slot_image11);
        mSlot_image12 = (ImageView) findViewById(R.id.slot_image12);
        mSlot_image13 = (ImageView) findViewById(R.id.slot_image13);
        mSlot_image14 = (ImageView) findViewById(R.id.slot_image14);
        mSlot_image15 = (ImageView) findViewById(R.id.slot_image15);
        mSlot_image16 = (ImageView) findViewById(R.id.slot_image16);
        mSlot_image17 = (ImageView) findViewById(R.id.slot_image17);
        mSlot_image18 = (ImageView) findViewById(R.id.slot_image18);
        mSlot_image19 = (ImageView) findViewById(R.id.slot_image19);
        mSlot_image20 = (ImageView) findViewById(R.id.slot_image20);

        mSlot21 = (LinearLayout) findViewById(R.id.slot21);
        mSlot22 = (RelativeLayout) findViewById(R.id.slot22);
        mSlot23 = (RelativeLayout) findViewById(R.id.slot23);
        mSlot24 = (RelativeLayout) findViewById(R.id.slot24);
        mSlot25 = (RelativeLayout) findViewById(R.id.slot25);
        mSlot26 = (RelativeLayout) findViewById(R.id.slot26);
        mSlot27 = (RelativeLayout) findViewById(R.id.slot27);
        mSlot28 = (RelativeLayout) findViewById(R.id.slot28);
        mSlot29 = (RelativeLayout) findViewById(R.id.slot29);
        mSlot30 = (RelativeLayout) findViewById(R.id.slot30);
        mSlot31 = (RelativeLayout) findViewById(R.id.slot31);
        mSlot32 = (RelativeLayout) findViewById(R.id.slot32);
        mSlot33 = (RelativeLayout) findViewById(R.id.slot33);
        mSlot34 = (RelativeLayout) findViewById(R.id.slot34);
        mSlot35 = (RelativeLayout) findViewById(R.id.slot35);
        mSlot36 = (RelativeLayout) findViewById(R.id.slot36);
        mSlot37 = (RelativeLayout) findViewById(R.id.slot37);
        mSlot38 = (RelativeLayout) findViewById(R.id.slot38);
        mSlot39 = (RelativeLayout) findViewById(R.id.slot39);
        mSlot40 = (RelativeLayout) findViewById(R.id.slot40);

        mSlot_text21 = (TextView) findViewById(R.id.slot_text21);
        mSlot_text22 = (TextView) findViewById(R.id.slot_text22);
        mSlot_text23 = (TextView) findViewById(R.id.slot_text23);
        mSlot_24 = (TextView) findViewById(R.id.slot_text24);
        mSlot_textx25 = (TextView) findViewById(R.id.slot_text25);
        mSlot_text26 = (TextView) findViewById(R.id.slot_text26);
        mSlot_text27 = (TextView) findViewById(R.id.slot_text27);
        mSlot_text28 = (TextView) findViewById(R.id.slot_text28);
        mSlot_text29 = (TextView) findViewById(R.id.slot_text29);
        mSlot_text30 = (TextView) findViewById(R.id.slot_text30);
        mSlot_text31 = (TextView) findViewById(R.id.slot_text31);
        mSlot_text32 = (TextView) findViewById(R.id.slot_text32);
        mSlot_text33 = (TextView) findViewById(R.id.slot_text33);
        mSlot_text34 = (TextView) findViewById(R.id.slot_text34);
        mSlot_text35 = (TextView) findViewById(R.id.slot_text35);
        mSlot_text36 = (TextView) findViewById(R.id.slot_text36);
        mSlot_text37 = (TextView) findViewById(R.id.slot_text37);
        mSlot_text38 = (TextView) findViewById(R.id.slot_text38);
        mSlot_text39 = (TextView) findViewById(R.id.slot_text39);
        mSlot_text40 = (TextView) findViewById(R.id.slot_text40);

        mSlot_image21 = (ImageView) findViewById(R.id.slot_image21);
        mSlot_image22 = (ImageView) findViewById(R.id.slot_image22);
        mSlot_image23 = (ImageView) findViewById(R.id.slot_image23);
        mSlot_image24 = (ImageView) findViewById(R.id.slot_image24);
        mSlot_image25 = (ImageView) findViewById(R.id.slot_image25);
        mSlot_image26 = (ImageView) findViewById(R.id.slot_image26);
        mSlot_image27 = (ImageView) findViewById(R.id.slot_image27);
        mSlot_image28 = (ImageView) findViewById(R.id.slot_image28);
        mSlot_image29 = (ImageView) findViewById(R.id.slot_image29);
        mSlot_image30 = (ImageView) findViewById(R.id.slot_image30);
        mSlot_image31 = (ImageView) findViewById(R.id.slot_image31);
        mSlot_image32 = (ImageView) findViewById(R.id.slot_image32);
        mSlot_image33 = (ImageView) findViewById(R.id.slot_image33);
        mSlot_image34 = (ImageView) findViewById(R.id.slot_image34);
        mSlot_image35 = (ImageView) findViewById(R.id.slot_image35);
        mSlot_image36 = (ImageView) findViewById(R.id.slot_image36);
        mSlot_image37 = (ImageView) findViewById(R.id.slot_image37);
        mSlot_image38 = (ImageView) findViewById(R.id.slot_image38);
        mSlot_image39 = (ImageView) findViewById(R.id.slot_image39);
        mSlot_image40 = (ImageView) findViewById(R.id.slot_image40);

        mSlot41 = (LinearLayout) findViewById(R.id.slot41);
        mSlot42 = (RelativeLayout) findViewById(R.id.slot42);
        mSlot43 = (RelativeLayout) findViewById(R.id.slot43);
        mSlot44 = (RelativeLayout) findViewById(R.id.slot44);
        mSlot45 = (RelativeLayout) findViewById(R.id.slot45);
        mSlot46 = (RelativeLayout) findViewById(R.id.slot46);
        mSlot47 = (RelativeLayout) findViewById(R.id.slot47);
        mSlot48 = (RelativeLayout) findViewById(R.id.slot48);
        mSlot49 = (RelativeLayout) findViewById(R.id.slot49);
        mSlot50 = (RelativeLayout) findViewById(R.id.slot50);
        mSlot51 = (RelativeLayout) findViewById(R.id.slot51);
        mSlot52 = (RelativeLayout) findViewById(R.id.slot52);
        mSlot53 = (RelativeLayout) findViewById(R.id.slot53);

        mSlot_text41 = (TextView) findViewById(R.id.slot_text41);
        mSlot_text42 = (TextView) findViewById(R.id.slot_text42);
        mSlot_text43 = (TextView) findViewById(R.id.slot_text43);
        mSlot_text44 = (TextView) findViewById(R.id.slot_text44);
        mSlot_text45 = (TextView) findViewById(R.id.slot_text45);
        mSlot_text46 = (TextView) findViewById(R.id.slot_text46);
        mSlot_text47 = (TextView) findViewById(R.id.slot_text47);
        mSlot_text48 = (TextView) findViewById(R.id.slot_text48);
        mSlot_text49 = (TextView) findViewById(R.id.slot_text49);
        mSlot_text50 = (TextView) findViewById(R.id.slot_text50);
        mSlot_text51 = (TextView) findViewById(R.id.slot_text51);
        mSlot_text52 = (TextView) findViewById(R.id.slot_text52);
        mSlot_text53 = (TextView) findViewById(R.id.slot_text53);

        mSlot_image41 = (ImageView) findViewById(R.id.slot_image41);
        mSlot_image42 = (ImageView) findViewById(R.id.slot_image42);
        mSlot_image43 = (ImageView) findViewById(R.id.slot_image43);
        mSlot_image44 = (ImageView) findViewById(R.id.slot_image44);
        mSlot_image45 = (ImageView) findViewById(R.id.slot_image45);
        mSlot_image46 = (ImageView) findViewById(R.id.slot_image46);
        mSlot_image47 = (ImageView) findViewById(R.id.slot_image47);
        mSlot_image48 = (ImageView) findViewById(R.id.slot_image48);
        mSlot_image49 = (ImageView) findViewById(R.id.slot_image49);
        mSlot_image50 = (ImageView) findViewById(R.id.slot_image50);
        mSlot_image51 = (ImageView) findViewById(R.id.slot_image51);
        mSlot_image52 = (ImageView) findViewById(R.id.slot_image52);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                level1.setVisibility(View.VISIBLE);
                level2.setVisibility(View.GONE);
                level3.setVisibility(View.GONE);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                level1.setVisibility(View.GONE);
                level2.setVisibility(View.VISIBLE);
                level3.setVisibility(View.GONE);
            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                level1.setVisibility(View.GONE);
                level3.setVisibility(View.VISIBLE);
                level2.setVisibility(View.GONE);
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                if (!isNetworkAvailable()) {
                    Toast.makeText(MainActivity.this, "Please check your network connection!", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    //PUBLISH THE MESSAGE
                    MqttMessage message = new MqttMessage("startdemo".getBytes());
                    message.setQos(0);
                    message.setRetained(false);
                    String topic = "ngrid/smartparking/carstatus_demo";
                    client.publish(topic, message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "sucessfully to send demo start: ", Toast.LENGTH_SHORT).show();
            }
        });

        receiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context context, Intent intent) {
                devicedetails_String = intent.getStringExtra(MqttService.COPA_MESSAGE);
                JSONObject jsonObject = null;
                //   JSONArray jsonArray = null;

                try {
                    jsonObject = new JSONObject(devicedetails_String);
                    if(jsonObject != null) {
                        //Get the instance of JSONArray that contains JSONObjects
                        JSONArray jsonArray = jsonObject.optJSONArray("car_status");
                        String json_filled = jsonObject.optString("Filled");
                        String json_empty = jsonObject.optString("Empty");
                        String json_moving = jsonObject.optString("Moving+Parked_cars");
                        if (json_filled != null) {
                            tv_filled.setText(json_filled + "\nFilled");
                        }
                        if (json_empty != null) {
                            tv_empty.setText(json_empty + "\nEmpty");
                        }
                        if (json_moving != null) {
                            tv_moving.setText(json_moving + "\nMoving");
                        }

                        //                        // Deal with the case of a non-array value.
                        //                        if (jsonArray == null) { /*...*/ }

                        // Create an int array to accomodate the numbers.
                        int[] numbers = new int[jsonArray.length()];

                        // Extract numbers from JSON array.
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            numbers[i] = jsonArray.optInt(i);
                            // Toast.makeText(MainActivity.this, String.valueOf(numbers[i]), Toast.LENGTH_SHORT).show();
                            if (i == 0) {
                                if (numbers[i] == 1) {
                                    mSlot1.setBackgroundColor(Color.WHITE);
                                    mSlot_image1.setVisibility(View.VISIBLE);
                                    mSlot_text1.setVisibility(View.GONE);
                                } else {
                                    mSlot1.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image1.setVisibility(View.GONE);
                                    mSlot_text1.setVisibility(View.VISIBLE);
                                }

                            }
                            if (i == 1) {
                                if (numbers[i] == 1) {
                                    mSlot2.setBackgroundColor(Color.WHITE);
                                    mSlot_image2.setVisibility(View.VISIBLE);
                                    mSlot_text2.setVisibility(View.GONE);
                                } else {
                                    mSlot2.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image2.setVisibility(View.GONE);
                                    mSlot_text2.setVisibility(View.VISIBLE);
                                }

                            }
                            if (i == 2) {
                                if (numbers[i] == 1) {
                                    mSlot3.setBackgroundColor(Color.WHITE);
                                    mSlot_image3.setVisibility(View.VISIBLE);
                                    mSlot_text3.setVisibility(View.GONE);
                                } else {
                                    mSlot3.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image3.setVisibility(View.GONE);
                                    mSlot_text3.setVisibility(View.VISIBLE);
                                }

                            }
                            if (i == 3) {
                                if (numbers[i] == 1) {
                                    mSlot4.setBackgroundColor(Color.WHITE);
                                    mSlot_image4.setVisibility(View.VISIBLE);
                                    mSlot_4.setVisibility(View.GONE);
                                } else {
                                    mSlot4.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image4.setVisibility(View.GONE);
                                    mSlot_4.setVisibility(View.VISIBLE);
                                }

                            }
                            if (i == 4) {
                                if (numbers[i] == 1) {
                                    mSlot5.setBackgroundColor(Color.WHITE);
                                    mSlot_image5.setVisibility(View.VISIBLE);
                                    mSlot_textx5.setVisibility(View.GONE);
                                } else {
                                    mSlot5.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image5.setVisibility(View.GONE);
                                    mSlot_textx5.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 5) {
                                if (numbers[i] == 1) {
                                    mSlot6.setBackgroundColor(Color.WHITE);
                                    mSlot_image6.setVisibility(View.VISIBLE);
                                    mSlot_text6.setVisibility(View.GONE);
                                } else {
                                    mSlot6.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image6.setVisibility(View.GONE);
                                    mSlot_text6.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 6) {
                                if (numbers[i] == 1) {
                                    mSlot7.setBackgroundColor(Color.WHITE);
                                    mSlot_image7.setVisibility(View.VISIBLE);
                                    mSlot_text7.setVisibility(View.GONE);
                                } else {
                                    mSlot7.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image7.setVisibility(View.GONE);
                                    mSlot_text7.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 7) {
                                if (numbers[i] == 1) {
                                    mSlot8.setBackgroundColor(Color.WHITE);
                                    mSlot_image8.setVisibility(View.VISIBLE);
                                    mSlot_text8.setVisibility(View.GONE);
                                } else {
                                    mSlot8.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image8.setVisibility(View.GONE);
                                    mSlot_text8.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 8) {
                                if (numbers[i] == 1) {
                                    mSlot9.setBackgroundColor(Color.WHITE);
                                    mSlot_image9.setVisibility(View.VISIBLE);
                                    mSlot_text9.setVisibility(View.GONE);
                                } else {
                                    mSlot9.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image9.setVisibility(View.GONE);
                                    mSlot_text9.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 9) {
                                if (numbers[i] == 1) {
                                    mSlot10.setBackgroundColor(Color.WHITE);
                                    mSlot_image10.setVisibility(View.VISIBLE);
                                    mSlot_text10.setVisibility(View.GONE);
                                } else {
                                    mSlot10.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image10.setVisibility(View.GONE);
                                    mSlot_text10.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 10) {
                                if (numbers[i] == 1) {
                                    mSlot11.setBackgroundColor(Color.WHITE);
                                    mSlot_image11.setVisibility(View.VISIBLE);
                                    mSlot_text11.setVisibility(View.GONE);
                                } else {
                                    mSlot11.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image11.setVisibility(View.GONE);
                                    mSlot_text11.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 11) {
                                if (numbers[i] == 1) {
                                    mSlot12.setBackgroundColor(Color.WHITE);
                                    mSlot_image12.setVisibility(View.VISIBLE);
                                    mSlot_text12.setVisibility(View.GONE);
                                } else {
                                    mSlot12.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image12.setVisibility(View.GONE);
                                    mSlot_text12.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 12) {
                                if (numbers[i] == 1) {
                                    mSlot13.setBackgroundColor(Color.WHITE);
                                    mSlot_image13.setVisibility(View.VISIBLE);
                                    mSlot_text13.setVisibility(View.GONE);
                                } else {
                                    mSlot13.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image13.setVisibility(View.GONE);
                                    mSlot_text13.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 13) {
                                if (numbers[i] == 1) {
                                    mSlot14.setBackgroundColor(Color.WHITE);
                                    mSlot_image14.setVisibility(View.VISIBLE);
                                    mSlot_text14.setVisibility(View.GONE);
                                } else {
                                    mSlot14.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image14.setVisibility(View.GONE);
                                    mSlot_text14.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 14) {
                                if (numbers[i] == 1) {
                                    mSlot15.setBackgroundColor(Color.WHITE);
                                    mSlot_image15.setVisibility(View.VISIBLE);
                                    mSlot_text15.setVisibility(View.GONE);
                                } else {
                                    mSlot15.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image15.setVisibility(View.GONE);
                                    mSlot_text15.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 15) {
                                if (numbers[i] == 1) {
                                    mSlot16.setBackgroundColor(Color.WHITE);
                                    mSlot_image16.setVisibility(View.VISIBLE);
                                    mSlot_text16.setVisibility(View.GONE);
                                } else {
                                    mSlot16.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image16.setVisibility(View.GONE);
                                    mSlot_text16.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 16) {
                                if (numbers[i] == 1) {
                                    mSlot17.setBackgroundColor(Color.WHITE);
                                    mSlot_image17.setVisibility(View.VISIBLE);
                                    mSlot_text17.setVisibility(View.GONE);
                                } else {
                                    mSlot17.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image17.setVisibility(View.GONE);
                                    mSlot_text17.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 17) {
                                if (numbers[i] == 1) {
                                    mSlot18.setBackgroundColor(Color.WHITE);
                                    mSlot_image18.setVisibility(View.VISIBLE);
                                    mSlot_text18.setVisibility(View.GONE);
                                } else {
                                    mSlot18.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image18.setVisibility(View.GONE);
                                    mSlot_text18.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 18) {
                                if (numbers[i] == 1) {
                                    mSlot19.setBackgroundColor(Color.WHITE);
                                    mSlot_image19.setVisibility(View.VISIBLE);
                                    mSlot_text19.setVisibility(View.GONE);
                                } else {
                                    mSlot19.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image19.setVisibility(View.GONE);
                                    mSlot_text19.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 19) {
                                if (numbers[i] == 1) {
                                    mSlot20.setBackgroundColor(Color.WHITE);
                                    mSlot_image20.setVisibility(View.VISIBLE);
                                    mSlot_text20.setVisibility(View.GONE);
                                } else {
                                    mSlot20.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image20.setVisibility(View.GONE);
                                    mSlot_text20.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 20) {
                                if (numbers[i] == 1) {
                                    mSlot21.setBackgroundColor(Color.WHITE);
                                    mSlot_image21.setVisibility(View.VISIBLE);
                                    mSlot_text1.setVisibility(View.GONE);
                                } else {
                                    mSlot21.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image21.setVisibility(View.GONE);
                                    mSlot_text21.setVisibility(View.VISIBLE);
                                }

                            }
                            if (i == 21) {
                                if (numbers[i] == 1) {
                                    mSlot22.setBackgroundColor(Color.WHITE);
                                    mSlot_image22.setVisibility(View.VISIBLE);
                                    mSlot_text22.setVisibility(View.GONE);
                                } else {
                                    mSlot22.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image22.setVisibility(View.GONE);
                                    mSlot_text22.setVisibility(View.VISIBLE);
                                }

                            }
                            if (i == 22) {
                                if (numbers[i] == 1) {
                                    mSlot23.setBackgroundColor(Color.WHITE);
                                    mSlot_image23.setVisibility(View.VISIBLE);
                                    mSlot_text23.setVisibility(View.GONE);
                                } else {
                                    mSlot23.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image23.setVisibility(View.GONE);
                                    mSlot_text23.setVisibility(View.VISIBLE);
                                }

                            }
                            if (i == 23) {
                                if (numbers[i] == 1) {
                                    mSlot24.setBackgroundColor(Color.WHITE);
                                    mSlot_image24.setVisibility(View.VISIBLE);
                                    mSlot_24.setVisibility(View.GONE);
                                } else {
                                    mSlot24.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image24.setVisibility(View.GONE);
                                    mSlot_24.setVisibility(View.VISIBLE);
                                }

                            }
                            if (i == 24) {
                                if (numbers[i] == 1) {
                                    mSlot25.setBackgroundColor(Color.WHITE);
                                    mSlot_image25.setVisibility(View.VISIBLE);
                                    mSlot_textx25.setVisibility(View.GONE);
                                } else {
                                    mSlot25.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image25.setVisibility(View.GONE);
                                    mSlot_textx25.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 25) {
                                if (numbers[i] == 1) {
                                    mSlot26.setBackgroundColor(Color.WHITE);
                                    mSlot_image26.setVisibility(View.VISIBLE);
                                    mSlot_text26.setVisibility(View.GONE);
                                } else {
                                    mSlot26.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image26.setVisibility(View.GONE);
                                    mSlot_text26.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 26) {
                                if (numbers[i] == 1) {
                                    mSlot27.setBackgroundColor(Color.WHITE);
                                    mSlot_image27.setVisibility(View.VISIBLE);
                                    mSlot_text27.setVisibility(View.GONE);
                                } else {
                                    mSlot27.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image27.setVisibility(View.GONE);
                                    mSlot_text27.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 27) {
                                if (numbers[i] == 1) {
                                    mSlot28.setBackgroundColor(Color.WHITE);
                                    mSlot_image28.setVisibility(View.VISIBLE);
                                    mSlot_text28.setVisibility(View.GONE);
                                } else {
                                    mSlot28.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image28.setVisibility(View.GONE);
                                    mSlot_text28.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 28) {
                                if (numbers[i] == 1) {
                                    mSlot29.setBackgroundColor(Color.WHITE);
                                    mSlot_image29.setVisibility(View.VISIBLE);
                                    mSlot_text29.setVisibility(View.GONE);
                                } else {
                                    mSlot29.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image29.setVisibility(View.GONE);
                                    mSlot_text29.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 29) {
                                if (numbers[i] == 1) {
                                    mSlot30.setBackgroundColor(Color.WHITE);
                                    mSlot_image30.setVisibility(View.VISIBLE);
                                    mSlot_text30.setVisibility(View.GONE);
                                } else {
                                    mSlot30.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image30.setVisibility(View.GONE);
                                    mSlot_text30.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 30) {
                                if (numbers[i] == 1) {
                                    mSlot31.setBackgroundColor(Color.WHITE);
                                    mSlot_image31.setVisibility(View.VISIBLE);
                                    mSlot_text31.setVisibility(View.GONE);
                                } else {
                                    mSlot31.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image31.setVisibility(View.GONE);
                                    mSlot_text31.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 31) {
                                if (numbers[i] == 1) {
                                    mSlot32.setBackgroundColor(Color.WHITE);
                                    mSlot_image32.setVisibility(View.VISIBLE);
                                    mSlot_text32.setVisibility(View.GONE);
                                } else {
                                    mSlot32.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image32.setVisibility(View.GONE);
                                    mSlot_text32.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 32) {
                                if (numbers[i] == 1) {
                                    mSlot33.setBackgroundColor(Color.WHITE);
                                    mSlot_image33.setVisibility(View.VISIBLE);
                                    mSlot_text33.setVisibility(View.GONE);
                                } else {
                                    mSlot33.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image33.setVisibility(View.GONE);
                                    mSlot_text33.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 33) {
                                if (numbers[i] == 1) {
                                    mSlot34.setBackgroundColor(Color.WHITE);
                                    mSlot_image34.setVisibility(View.VISIBLE);
                                    mSlot_text34.setVisibility(View.GONE);
                                } else {
                                    mSlot34.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image34.setVisibility(View.GONE);
                                    mSlot_text34.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 34) {
                                if (numbers[i] == 1) {
                                    mSlot35.setBackgroundColor(Color.WHITE);
                                    mSlot_image35.setVisibility(View.VISIBLE);
                                    mSlot_text35.setVisibility(View.GONE);
                                } else {
                                    mSlot35.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image35.setVisibility(View.GONE);
                                    mSlot_text35.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 35) {
                                if (numbers[i] == 1) {
                                    mSlot36.setBackgroundColor(Color.WHITE);
                                    mSlot_image36.setVisibility(View.VISIBLE);
                                    mSlot_text36.setVisibility(View.GONE);
                                } else {
                                    mSlot36.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image36.setVisibility(View.GONE);
                                    mSlot_text36.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 36) {
                                if (numbers[i] == 1) {
                                    mSlot37.setBackgroundColor(Color.WHITE);
                                    mSlot_image37.setVisibility(View.VISIBLE);
                                    mSlot_text37.setVisibility(View.GONE);
                                } else {
                                    mSlot37.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image37.setVisibility(View.GONE);
                                    mSlot_text37.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 37) {
                                if (numbers[i] == 1) {
                                    mSlot38.setBackgroundColor(Color.WHITE);
                                    mSlot_image38.setVisibility(View.VISIBLE);
                                    mSlot_text38.setVisibility(View.GONE);
                                } else {
                                    mSlot38.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image38.setVisibility(View.GONE);
                                    mSlot_text38.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 38) {
                                if (numbers[i] == 1) {
                                    mSlot39.setBackgroundColor(Color.WHITE);
                                    mSlot_image39.setVisibility(View.VISIBLE);
                                    mSlot_text39.setVisibility(View.GONE);
                                } else {
                                    mSlot39.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image39.setVisibility(View.GONE);
                                    mSlot_text39.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 39) {
                                if (numbers[i] == 1) {
                                    mSlot40.setBackgroundColor(Color.WHITE);
                                    mSlot_image40.setVisibility(View.VISIBLE);
                                    mSlot_text40.setVisibility(View.GONE);
                                } else {
                                    mSlot40.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image40.setVisibility(View.GONE);
                                    mSlot_text40.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 40) {
                                if (numbers[i] == 1) {
                                    mSlot41.setBackgroundColor(Color.WHITE);
                                    mSlot_image41.setVisibility(View.VISIBLE);
                                    mSlot_text41.setVisibility(View.GONE);
                                } else {
                                    mSlot41.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image41.setVisibility(View.GONE);
                                    mSlot_text41.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 41) {
                                if (numbers[i] == 1) {
                                    mSlot42.setBackgroundColor(Color.WHITE);
                                    mSlot_image42.setVisibility(View.VISIBLE);
                                    mSlot_text42.setVisibility(View.GONE);
                                } else {
                                    mSlot42.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image42.setVisibility(View.GONE);
                                    mSlot_text42.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 42) {
                                if (numbers[i] == 1) {
                                    mSlot43.setBackgroundColor(Color.WHITE);
                                    mSlot_image43.setVisibility(View.VISIBLE);
                                    mSlot_text43.setVisibility(View.GONE);
                                } else {
                                    mSlot43.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image43.setVisibility(View.GONE);
                                    mSlot_text43.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 43) {
                                if (numbers[i] == 1) {
                                    mSlot44.setBackgroundColor(Color.WHITE);
                                    mSlot_image44.setVisibility(View.VISIBLE);
                                    mSlot_text44.setVisibility(View.GONE);
                                } else {
                                    mSlot44.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image44.setVisibility(View.GONE);
                                    mSlot_text44.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 44) {
                                if (numbers[i] == 1) {
                                    mSlot45.setBackgroundColor(Color.WHITE);
                                    mSlot_image45.setVisibility(View.VISIBLE);
                                    mSlot_text45.setVisibility(View.GONE);
                                } else {
                                    mSlot45.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image45.setVisibility(View.GONE);
                                    mSlot_text45.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 45) {
                                if (numbers[i] == 1) {
                                    mSlot46.setBackgroundColor(Color.WHITE);
                                    mSlot_image46.setVisibility(View.VISIBLE);
                                    mSlot_text46.setVisibility(View.GONE);
                                } else {
                                    mSlot46.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image46.setVisibility(View.GONE);
                                    mSlot_text46.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 46) {
                                if (numbers[i] == 1) {
                                    mSlot47.setBackgroundColor(Color.WHITE);
                                    mSlot_image47.setVisibility(View.VISIBLE);
                                    mSlot_text47.setVisibility(View.GONE);
                                } else {
                                    mSlot47.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image47.setVisibility(View.GONE);
                                    mSlot_text47.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 47) {
                                if (numbers[i] == 1) {
                                    mSlot48.setBackgroundColor(Color.WHITE);
                                    mSlot_image48.setVisibility(View.VISIBLE);
                                    mSlot_text48.setVisibility(View.GONE);
                                } else {
                                    mSlot48.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image48.setVisibility(View.GONE);
                                    mSlot_text48.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 48) {
                                if (numbers[i] == 1) {
                                    mSlot49.setBackgroundColor(Color.WHITE);
                                    mSlot_image49.setVisibility(View.VISIBLE);
                                    mSlot_text49.setVisibility(View.GONE);
                                } else {
                                    mSlot49.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image49.setVisibility(View.GONE);
                                    mSlot_text49.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 49) {
                                if (numbers[i] == 1) {
                                    mSlot50.setBackgroundColor(Color.WHITE);
                                    mSlot_image50.setVisibility(View.VISIBLE);
                                    mSlot_text50.setVisibility(View.GONE);
                                } else {
                                    mSlot50.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image50.setVisibility(View.GONE);
                                    mSlot_text50.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 50) {
                                if (numbers[i] == 1) {
                                    mSlot51.setBackgroundColor(Color.WHITE);
                                    mSlot_image51.setVisibility(View.VISIBLE);
                                    mSlot_text51.setVisibility(View.GONE);
                                } else {
                                    mSlot51.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image51.setVisibility(View.GONE);
                                    mSlot_text51.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 51) {
                                if (numbers[i] == 1) {
                                    mSlot52.setBackgroundColor(Color.WHITE);
                                    mSlot_image52.setVisibility(View.VISIBLE);
                                    mSlot_text52.setVisibility(View.GONE);
                                } else {
                                    mSlot52.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image52.setVisibility(View.GONE);
                                    mSlot_text52.setVisibility(View.VISIBLE);
                                }
                            }
                            if (i == 52) {
                                if (numbers[i] == 1) {
                                    mSlot53.setBackgroundColor(Color.WHITE);
                                    mSlot_image53.setVisibility(View.VISIBLE);
                                    mSlot_text53.setVisibility(View.GONE);
                                } else {
                                    mSlot53.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
                                    mSlot_image53.setVisibility(View.GONE);
                                    mSlot_text53.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }


                    // String slot = jsonObject.getString("slot_number");
                    // String value_status = jsonObject.getString("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    /*if the status reply is 0 then it represents the slot is available
                    * if 1 then it indicates that the car came to the slot and the slot is not available*/
//                    if (devicedetails_String.equals("0") && devicedetails_String != null) {
//                        mSlot1.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_green_dark));
//                        mSlot_image1.setVisibility(View.GONE);
//                        mSlot_text1.setVisibility(View.VISIBLE);
//                    } else if (devicedetails_String.equals("1") && devicedetails_String != null) {
//                        mSlot1.setBackgroundColor(Color.WHITE);
//                        mSlot_image1.setVisibility(View.VISIBLE);
//                        mSlot_text1.setVisibility(View.GONE);
            }
        };

    }

    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MqttService.COPA_RESULT)
        );
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        return nf != null && nf.isConnected();
    }
}

