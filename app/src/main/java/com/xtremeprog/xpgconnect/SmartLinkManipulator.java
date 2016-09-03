package com.xtremeprog.xpgconnect;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class SmartLinkManipulator {
    public static final int DEVICE_COUNT_MULTIPLE = -1;
    public static final int DEVICE_COUNT_ONE = 1;
    private static SmartLinkManipulator me = null;
    private int CONTENT_CHECKSUM_BEFORE_DELAY_TIME = 100;
    private int CONTENT_COUNT = 5;
    private int CONTENT_GROUP_DELAY_TIME = 500;
    private int CONTENT_PACKAGE_DELAY_TIME = 50;
    private int HEADER_CAPACITY = 76;
    private int HEADER_COUNT = 200;
    private int HEADER_PACKAGE_DELAY_TIME = 10;
    private final String RET_KEY = "smart_config";
    private String TAG = "HFdebug";
    private String broadCastIP;
    private ConnectCallBack callback;
    Runnable findThread = new Runnable() {
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
            for (int i = 0; i < 20 && SmartLinkManipulator.this.isConnecting; i++) {
                SmartLinkManipulator.this.mConfigBroadUdp.sendFindCmd();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e2) {
                }
            }
            if (SmartLinkManipulator.this.isConnecting) {
                if (SmartLinkManipulator.this.successMacSet.size() <= 0) {
                    SmartLinkManipulator.this.callback.onConnectTimeOut();
                } else if (SmartLinkManipulator.this.successMacSet.size() > 0) {
                    SmartLinkManipulator.this.callback.onConnectOk();
                }
            }
            SmartLinkManipulator.this.StopConnection();
        }
    };
    public boolean isConnecting = false;
    private ConfigUdpBroadcast mConfigBroadUdp;
    private String pswd;
    private Set<String> successEventMacSet = new HashSet();
    private Set<String> successMacSet = new HashSet();

    class ConfigUdpBroadcast {
        private DatagramPacket dataPacket;
        private InetAddress inetAddressbroadcast;
        private DatagramPacket packetToSendbroadcast;
        private int port;
        private byte[] receiveByte;
        private DatagramSocket socket;

        private ConfigUdpBroadcast() {
            this.port = 49999;
            this.receiveByte = new byte[512];
            try {
                this.inetAddressbroadcast = InetAddress.getByName(SmartLinkManipulator.this.broadCastIP);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
            }
        }

        public void open() {
            try {
                this.socket = new DatagramSocket(this.port);
                this.socket.setBroadcast(true);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        public void receive() {
            this.dataPacket = new DatagramPacket(this.receiveByte, this.receiveByte.length);
            new Thread() {
                public void run() {
                    while (SmartLinkManipulator.this.isConnecting) {
                        try {
                            if (ConfigUdpBroadcast.this.dataPacket != null && ConfigUdpBroadcast.this.socket != null) {
                                ConfigUdpBroadcast.this.socket.receive(ConfigUdpBroadcast.this.dataPacket);
                                int length = ConfigUdpBroadcast.this.dataPacket.getLength();
                                if (length > 0) {
                                    String str = new String(ConfigUdpBroadcast.this.receiveByte, 0, length, "UTF-8");
                                    if (str.contains("smart_config")) {
                                        Log.e("RECV", "smart_config");
                                        ModuleInfo moduleInfo = new ModuleInfo();
                                        moduleInfo.setMac(str.replace("smart_config", "").trim());
                                        if (!SmartLinkManipulator.this.successMacSet.contains(moduleInfo.getMac())) {
                                            SmartLinkManipulator.this.successMacSet.add(moduleInfo.getMac());
                                            Log.e("RECV", "find new Module");
                                            length = 0;
                                            while (length < 5) {
                                                try {
                                                    ConfigUdpBroadcast.this.sendHFA11Cmd();
                                                    Thread.sleep(1000);
                                                    length++;
                                                } catch (Exception e) {
                                                }
                                            }
                                        }
                                    } else {
                                        String[] split = str.split(",");
                                        if (split.length == 3) {
                                            Log.e("RECV", "get Module IP");
                                            Log.e("RECV", str);
                                            ModuleInfo moduleInfo2 = new ModuleInfo();
                                            moduleInfo2.setMac(split[1]);
                                            moduleInfo2.setModuleIP(split[0]);
                                            moduleInfo2.setMid(split[2]);
                                            if (SmartLinkManipulator.this.successMacSet.contains(moduleInfo2.getMac()) && !SmartLinkManipulator.this.successEventMacSet.contains(moduleInfo2.getMac())) {
                                                SmartLinkManipulator.this.callback.onConnect(moduleInfo2);
                                                SmartLinkManipulator.this.successEventMacSet.add(moduleInfo2.getMac());
                                            }
                                        }
                                    }
                                }
                            } else {
                                return;
                            }
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        public void send(byte[] bArr) {
            this.packetToSendbroadcast = new DatagramPacket(bArr, bArr.length, this.inetAddressbroadcast, this.port);
            try {
                if (this.packetToSendbroadcast != null && this.packetToSendbroadcast.getAddress() != null && this.socket != null) {
                    this.socket.send(this.packetToSendbroadcast);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendFindCmd() {
            System.out.println("smartlinkfind");
            this.packetToSendbroadcast = new DatagramPacket("smartlinkfind".getBytes(), "smartlinkfind".getBytes().length, this.inetAddressbroadcast, 48899);
            try {
                if (this.socket != null) {
                    this.socket.send(this.packetToSendbroadcast);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendHFA11Cmd() {
            System.out.println("smartlinkfind");
            this.packetToSendbroadcast = new DatagramPacket("HF-A11ASSISTHREAD".getBytes(), "HF-A11ASSISTHREAD".getBytes().length, this.inetAddressbroadcast, 48899);
            try {
                if (this.socket != null) {
                    this.socket.send(this.packetToSendbroadcast);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stopReceive() {
            Log.e(SmartLinkManipulator.this.TAG, "stopReceive");
            SmartLinkManipulator.this.isConnecting = false;
        }
    }

    public interface ConnectCallBack {
        void onConnect(ModuleInfo moduleInfo);

        void onConnectOk();

        void onConnectTimeOut();
    }

    private SmartLinkManipulator(Context context) {
        this.broadCastIP = getBroadCast(context);
        if (this.isConnecting) {
            this.isConnecting = false;
            this.mConfigBroadUdp.stopReceive();
        } else {
            this.mConfigBroadUdp = new ConfigUdpBroadcast();
        }
        this.mConfigBroadUdp.open();
    }

    private void connect() {
        int i;
        Log.e(this.TAG, "connect");
        byte[] bytes = getBytes(this.HEADER_CAPACITY);
        for (i = 1; i <= this.HEADER_COUNT && this.isConnecting; i++) {
            this.mConfigBroadUdp.send(bytes);
            try {
                Thread.sleep((long) this.HEADER_PACKAGE_DELAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String str = this.pswd;
        int[] iArr = new int[(str.length() + 2)];
        iArr[0] = 89;
        int i2 = 1;
        for (i = 0; i < str.length(); i++) {
            iArr[i2] = str.charAt(i) + 76;
            i2++;
        }
        iArr[iArr.length - 1] = 86;
        for (i = 1; i <= this.CONTENT_COUNT && this.isConnecting; i++) {
            i2 = 0;
            while (i2 < iArr.length) {
                int i3 = (i2 == 0 || i2 == iArr.length - 1) ? 3 : 1;
                for (int i4 = 1; i4 <= i3 && this.isConnecting; i4++) {
                    this.mConfigBroadUdp.send(getBytes(iArr[i2]));
                    if (i2 != iArr.length) {
                        try {
                            Thread.sleep((long) this.CONTENT_PACKAGE_DELAY_TIME);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                if (i2 != iArr.length) {
                    try {
                        Thread.sleep((long) this.CONTENT_PACKAGE_DELAY_TIME);
                    } catch (InterruptedException e3) {
                        e3.printStackTrace();
                    }
                }
                i2++;
            }
            try {
                Thread.sleep((long) this.CONTENT_CHECKSUM_BEFORE_DELAY_TIME);
            } catch (InterruptedException e4) {
                e4.printStackTrace();
            }
            int length = (str.length() + 256) + 76;
            for (int i3 = 1; i3 <= 3 && this.isConnecting; i3++) {
                this.mConfigBroadUdp.send(getBytes(length));
                if (i3 < 3) {
                    try {
                        Thread.sleep((long) this.CONTENT_PACKAGE_DELAY_TIME);
                    } catch (InterruptedException e42) {
                        e42.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep((long) this.CONTENT_GROUP_DELAY_TIME);
            } catch (InterruptedException e422) {
                e422.printStackTrace();
            }
        }
        Log.e(this.TAG, "connect END");
    }

    private String getBroadCast(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        return wifiManager != null ? Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway | ViewCompat.MEASURED_STATE_MASK) : null;
    }

    private byte[] getBytes(int i) {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) 5;
        }
        return bArr;
    }

    public static SmartLinkManipulator getInstence(Context context) {
        if (me == null) {
            me = new SmartLinkManipulator(context);
        }
        return me;
    }

    public void Startconnection(ConnectCallBack connectCallBack) {
        Log.e(this.TAG, "Startconnection");
        this.callback = connectCallBack;
        this.isConnecting = true;
        this.mConfigBroadUdp.receive();
        this.successMacSet.clear();
        this.successEventMacSet.clear();
        new Thread(new Runnable() {
            public void run() {
                while (SmartLinkManipulator.this.isConnecting) {
                    SmartLinkManipulator.this.connect();
                }
            }
        }).start();
        new Thread(this.findThread).start();
    }

    public void StopConnection() {
        if (me != null) {
            this.isConnecting = false;
            this.mConfigBroadUdp.stopReceive();
            this.mConfigBroadUdp.close();
            me = null;
        }
    }

    public char byteToChar(byte[] bArr) {
        return (char) (((bArr[0] & MotionEventCompat.ACTION_MASK) << 8) | (bArr[1] & MotionEventCompat.ACTION_MASK));
    }

    public void setConnection(String str, String str2) {
        Log.e(this.TAG, new StringBuilder(String.valueOf(str)).append(":").append(str2).toString());
        this.pswd = str2;
    }
}
