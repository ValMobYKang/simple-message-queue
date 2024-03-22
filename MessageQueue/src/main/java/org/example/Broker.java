package org.example;

import java.util.concurrent.ArrayBlockingQueue;

public class Broker {
    private final static int MAX_SIZE = 3;
    private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(MAX_SIZE);

    public static void produce(String msg){
        if (messageQueue.offer(msg)){
           System.out.println("Messages have been consumed:" + msg + ", and the number of messages currently staged is:" + messageQueue.size());
        } else {
            System.out.println("There are no messages available for consumption within the Message Processing Centre!");
        }
        System.out.println("=========");
    }

    public static String consume() {
        String msg = messageQueue.poll();
        if (msg != null) {
            System.out.println("Messages have been consumed:" + msg + ", and the number of messages currently staged is:" + messageQueue.size());
        } else {
            System.out.println("There are no messages to consume within the message processing centre!") ;
        }
        System.out.println("=========");
        return msg;
    }
}

