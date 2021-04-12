package com.example.mqttchat.client;

import android.content.Context;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyMqttClient {

    private final MqttAndroidClient client;
    String topic = "teste/tully";

    public MyMqttClient(Context context) {
        client = new MqttAndroidClient(context, "tcp://broker.emqx.io:1883",
                "teste-tully");
        connectToMqtt(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            }
        });
    }

    public void connectToMqtt(IMqttActionListener iMqttActionListener) {
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    System.out.println("Conexao realizada com sucesso");
                    iMqttActionListener.onSuccess(asyncActionToken);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    System.out.println("fail" + exception.getMessage());
                    iMqttActionListener.onFailure(asyncActionToken, exception);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribeToMqttChannel(IMqttMessageListener iMqttMessageListener) {
        String topic = "teste/tully";
        int qos = 1;
        try {
            client.subscribe(topic, qos, iMqttMessageListener);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {

        try {
            int qos = 1;
            boolean retained = false;
            IMqttDeliveryToken subToken = client.publish(topic, new MqttMessage(message.getBytes()));

            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    System.out.println("mensagem enviada com sucesso " + asyncActionToken.getResponse().toString());
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    System.out.println(exception);
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
