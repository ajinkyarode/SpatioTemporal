package com.example.ajinkyarode.chatapp;

/**
 * Created by ajinkyarode on 8/4/15.
 */

import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Class to broadcast the messages using address, port and
 * multicast lock
 */
public class MessageBroadcast implements Runnable {

    public WifiManager.MulticastLock lock;
    private WifiManager manager;
    private int port;
    private int len = 1024;
    private Charset charset = Charset.forName("UTF-8");
    private boolean listening;
    private InetAddress m_addr;
    private static int final_time = 0;
    public static int curr_time = 0;
    private static int sel_time = 0;

    /**
     * Getters and Setters for the UDP packet information
     */
    public void sendMulticast(String msg) {
        send(m_addr, msg);
    }
    public MessageBroadcast() {
    }
    public MessageBroadcast(WifiManager manager, int port) {
        this.lock = manager.createMulticastLock("MyLock");
        this.manager = manager;
        this.port = port;
        try {
            this.m_addr = InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if WiFi connection is enabled
     *
     * @throws Exception
     */
    private void WiFiConnection() throws Exception {
        if (manager.isWifiEnabled()) {
        } else {
            throw new Exception();
        }
    }

    /**
     * Check and initialize the time set by Initiator
     */
    public void checkt() {
        MessageBroadcast mb = new MessageBroadcast();
        Thread t1 = new Thread(mb);
        int curr_time1 = getTime();
        t1.start();
        if (PeerDetails.ctr.equals("NA")) {
            sel_time = 2;
        } else {
            sel_time = Integer.parseInt(PeerDetails.ctr);
        }
        final_time = curr_time1 + sel_time;
    }

    /*
     * Starts listening to any received packets on the network
     * on the specified port number
     *
     * @param listener
     * @throws Exception
     */
    public void listen(final ReceiveListener listener) throws Exception {
        WiFiConnection();
        listening = true;
        checkt();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket datagramSocket;
                try {
                    datagramSocket = new DatagramSocket(port);
                    datagramSocket.setBroadcast(true);
                    byte[] data = new byte[len];
                    /*
                     * While in listening mode, it acquires the multicast lock when
                     * receives the packet. Upon receiving the packet, it releases
                     * the lock to receive further packets
                     */
                    while (listening) {
                        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
                        try {
                            datagramSocket.receive(datagramPacket);
                            lock.acquire();
                            String msg = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), charset);
                            new ReceiveMessage(msg, datagramPacket.getAddress(), listener).start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            lock.release();
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    /*
     * Sends UDP packets to the given address
     *
     * @param address
     * @param msg
     */
    public void send(final InetAddress address, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket s;
                try {
                    s = new DatagramSocket();
                    byte[] messageByte = msg.getBytes();
                    DatagramPacket p = new DatagramPacket(messageByte, messageByte.length, address, port);
                    s.send(p);
                    s.close();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static int getTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String currentDateTimeString = sdf.format(d);
        String[] hourMin = currentDateTimeString.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int curr_time = (hour * 60) + mins;
        return curr_time;
    }

    @Override
    public void run() {
        while (true) {
            curr_time = getTime();
            String temp = String.valueOf(curr_time);
            if (final_time <= curr_time) {
                System.exit(0);
            }
        }
    }
}