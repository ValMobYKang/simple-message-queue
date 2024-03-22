package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//使用接口来实现多线程的方式
public class BrokerServer implements Runnable {

    public static int SERVICE_PORT = 9999;
    private final Socket socket;

    BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
        ) {
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    continue;
                }
                System.out.println("Receive raw message: " + str);

                if (str.equals("CONSUME")) {
                    String message = Broker.consume();
                    out.println(message);
                    out.flush();
                } else if (str.contains("SEND:")) {
                    Broker.produce(str);
                } else {
                    // The CONSUME/SEND: can be understood as a special protocol.
                    System.out.println("Raw data: " + str + " does not follow the protocal and dase not provide the related service");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(SERVICE_PORT);
        while (true) {
            BrokerServer brokerServer = new BrokerServer(server.accept());
            new Thread((brokerServer)).start();
        }
    }
}
