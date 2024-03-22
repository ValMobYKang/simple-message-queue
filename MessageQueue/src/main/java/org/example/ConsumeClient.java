package org.example;

public class ConsumeClient {
    public static void main(String[] args) throws Exception {
        String message = MyClient.consume();
        System.out.println("Get message from broker: " + message);
    }
}
