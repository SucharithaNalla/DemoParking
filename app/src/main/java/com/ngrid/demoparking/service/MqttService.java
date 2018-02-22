package com.ngrid.demoparking.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.util.Timer;
import java.util.TimerTask;


public class MqttService extends Service implements MqttCallback,
        IMqttActionListener {
    String phone_number;
    public static final String CLIENT_ID = "Client id";
    static final String TAG = "ChatActivity";

    public static String Device_Connected_Details;
    public static String videoresdetails;
    public static String mPieChart_String;

    private MqttAndroidClient mClient;
    private boolean mIsConnecting;

    LocalBroadcastManager broadcaster;
    int i = 7;

    SharedPreferences sharedpreferences;

    static final public String COPA_RESULT = "com.controlj.copame.backend.COPAService.REQUEST_PROCESSED";

    static final public String COPA_MESSAGE = "com.controlj.copame.backend.COPAService.COPA_MSG";
    static final public String COPA_MESSAGE2 = "com.controlj.copame.backend.COPAService.COPA_MSG";

    public MqttService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        broadcaster = LocalBroadcastManager.getInstance(this);
        try {
            connect();
        } catch (MqttException e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //Device_Connected_Details = sharedpreferences.getString("Device_Details", null);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };

        Timer t = new Timer();
        t.schedule(timerTask, 0, 3600);

        return START_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void connect() throws MqttException {
        String clientId = MqttClient.generateClientId();
        String server = "183.82.122.136";
        String port = "1883";

        String uri = "tcp://" + server + ":" + port;

        mClient = new MqttAndroidClient(this, uri, clientId);
        MqttConnectOptions conOpt = new MqttConnectOptions();

//        conOpt.setUserName("918757151417");
//        conOpt.setPassword("4321".toCharArray());

        mClient.setCallback(this);

        mIsConnecting = true;
        IMqttToken iMqttToken = mClient.connect(conOpt, null, this);
        iMqttToken.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                // We are connected
                Log.d("", "Connected");
                Toast.makeText(MqttService.this, "Connected", Toast.LENGTH_SHORT).show();
                subscribe();
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                // Something went wrong e.g. connection timeout or firewall problems
            }
        });
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        if (mIsConnecting) {
            mIsConnecting = false;
            subscribe();
            //  publishToTopic();
        }
    }

    private void subscribe() {
        String topic = "ngrid/smartparking/#";
        int qos = 2;

        try {
            String[] topics = new String[1];
            topics[0] = topic;
            getClient().subscribe(topic, qos, null, this);
        } catch (MqttSecurityException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.e(MqttService.TAG, "something went wrong: " + exception.toString());
    }

    @Override
    public void connectionLost(Throwable throwable) {
        Log.i(TAG, "Connection lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.i("abc", message.toString());
        if (topic.endsWith("/carstatus")) {
            Device_Connected_Details = message.toString();
            sendResult(Device_Connected_Details);
        }
    }

    public void sendResult(String message) {
        Intent intent = new Intent(COPA_RESULT);
        if (message != null)
            intent.putExtra(COPA_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }

    public void sendResult_(String message) {
        Intent intent = new Intent(COPA_RESULT);
        if (message != null)
            intent.putExtra(COPA_MESSAGE2, message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public MqttAndroidClient getClient() {
        return mClient;
    }
}
