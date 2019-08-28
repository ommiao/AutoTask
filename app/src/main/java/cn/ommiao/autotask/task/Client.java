package cn.ommiao.autotask.task;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

    public static final String HEART_BEAT = "@heartbeat";
    public static final String OK = "@ok";
    public static final String ALIVE = "@alive";
    public static final String RUN_TEST = "am instrument -w -r -e class cn.ommiao.autotaskexecutor.AutoTaskTest cn.ommiao.autotaskexecutor.test/androidx.test.runner.AndroidJUnitRunner";

    private DatagramSocket datagramSocket;
    private InetAddress address;
    private ReceiveThread receiveThread;

    public Client(Callback callback){
        try {
            datagramSocket = new DatagramSocket(2696);
            datagramSocket.setSoTimeout(3000);
            address = InetAddress.getByName("127.0.0.1");
            receiveThread = new ReceiveThread(datagramSocket, callback);
            receiveThread.start();
        } catch (SocketException | UnknownHostException e){
            e.printStackTrace();
        }
    }

    public void send(final String message){
        new Thread(() -> {
            byte[] messageByte = message.getBytes();
            DatagramPacket packet = new DatagramPacket(messageByte, 0, messageByte.length, address, 2692);
            try {
                datagramSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void close(){
        datagramSocket.close();
        receiveThread.setRun(false);
    }

    class ReceiveThread extends Thread{

        DatagramSocket socket;
        DatagramPacket packet;
        private Callback callback;
        private boolean run = true;

        public void setRun(boolean run) {
            this.run = run;
        }

        public ReceiveThread(DatagramSocket socket, Callback callback) {
            this.socket = socket;
            this.callback = callback;
        }

        @Override
        public void run() {
            byte[] buff = new byte[1024];
            packet = new DatagramPacket(buff, 0, buff.length);
            while (run){
                try {
                    socket.receive(packet);
                    String messageRec = new String(packet.getData(), 0, packet.getLength());
                    if (callback != null){
                        callback.onReceiveMessage(messageRec);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public interface Callback{
        void onReceiveMessage(String message);
    }

}
