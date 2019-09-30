package cn.ommiao.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class AutoTaskServer {

    private static final int PORT = 2692;

    public static void main(String[] args){
        new AutoTaskServer();
    }

    private DatagramPacket datagramPacket;
    private DatagramSocket datagramSocket;

    private byte[] messageRec = new byte[1024];

    private AutoTaskServer() {
        try {
            datagramSocket = new DatagramSocket(PORT);
            datagramPacket = new DatagramPacket(messageRec, messageRec.length);
            while (true){
                System.out.println("@waiting");
                datagramSocket.receive(datagramPacket);
                String command = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                if(command.startsWith("@heartbeat")){
                    reply("@alive");
                    continue;
                }
                if(command.startsWith("@stop")){
                    reply("@bye");
                    break;
                }
                new ExecuteThread(command).start();
                reply("@ok");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null){
                datagramSocket.close();
            }
        }
    }

    private void reply(String message){
        byte[] messageByte = message.getBytes();
        DatagramPacket packet = new DatagramPacket(messageByte, messageByte.length, datagramPacket.getAddress(), datagramPacket.getPort());
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ExecuteThread extends Thread{

        private String command;

        private ExecuteThread(String command){
            this.command = command;
        }

        @Override
        public void run() {
            try {
                Process process = Runtime.getRuntime().exec(command.trim());
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null){
                    System.out.println(line);
                }
                process.destroy();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
