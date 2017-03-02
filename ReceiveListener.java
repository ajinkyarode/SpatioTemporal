package com.example.ajinkyarode.chatapp;

/**
 * Created by ajinkyarode on 8/4/15.
 */

import java.net.InetAddress;

/*
 * Interface with receive method for receiving
 * datagram packets on respective address
 */
public interface ReceiveListener {
    void receive(InetAddress address, String msg);
}
