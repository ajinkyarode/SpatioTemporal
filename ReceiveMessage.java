package com.example.ajinkyarode.chatapp;

/**
 * Created by ajinkyarode on 8/4/15.
 */

import java.net.InetAddress;

/*
 * Structure for receiving message with message, address
 * and ReceiveListener to capture the messages
 */
public class ReceiveMessage extends Thread {

    private String broadcast_msg;
    private InetAddress address;
    private ReceiveListener listener;

    public ReceiveMessage(String b_msg, InetAddress addr, ReceiveListener listener) {
        this.broadcast_msg = b_msg;
        this.address = addr;
        this.listener = listener;
    }

    /*
     * Receive message method
     */
    @Override
    public void run() {
        listener.receive(address, broadcast_msg);
    }
}