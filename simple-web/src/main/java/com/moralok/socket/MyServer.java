package com.moralok.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * write your description here
 *
 * @author moralok
 * @date 2022/3/14 10:30 上午
 */
public class MyServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String s = "", s2 = "";
            while (!"stop".equals(s)) {
                s = dataInputStream.readUTF();
                System.out.println("Client says: " + s);
                s2 = bufferedReader.readLine();
                dataOutputStream.writeUTF(s2);
                dataOutputStream.flush();
            }
            dataInputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
