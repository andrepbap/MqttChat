package com.example.mqttchat.client;

import android.content.Context;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyMqttClient {

    private final MqttAndroidClient client;

    public MyMqttClient(Context context) {
        client = new MqttAndroidClient(context, "tcp://broker.emqx.io:1883",
                "teste-tully");

        connectToMqtt();
    }

    void connectToMqtt() {
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    subscribeToMqttChannel();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    System.out.println("fail" + exception.getMessage());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    IMqttMessageListener iMqttMessageListener = new IMqttMessageListener() {
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            System.out.println("message received" + message.toString());
        }
    };

    void subscribeToMqttChannel() {
        String topic = "teste/tully";
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos, iMqttMessageListener);

            /*subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Timber.d("Mqtt channel subscribe success");
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    Timber.d("Mqtt channel subscribe error %s",exception.getMessage());
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards
                }
            });
            */
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
//        client.connectWith()
//                .willPublish()
//                .topic("italo/messages/1")
//                .payload("jihad safado".getBytes())
//                .qos(MqttQos.EXACTLY_ONCE)
//                .retain(true)
//                .applyWillPublish()
//                .send()
//                .whenComplete((connAck, throwable) -> {
//                    if (throwable != null) {
//                        // Handle failure to publish
//                        System.out.println("Deu ruim") ;
//                    } else {
//                        System.out.println("concluiu uhuu") ;
//                        // Handle successful publish, e.g. logging or incrementing a metric
//                    }
//                });
    }
}
