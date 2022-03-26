package com.moralok.socket;

import java.io.*;
import java.net.Socket;

/**
 * write your description here
 *
 * @author moralok
 * @date 2022/3/14 10:34 上午
 */
public class MyClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6666);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String s = "", s2 = "";
            while (!"stop".equals(s)) {
                s = bufferedReader.readLine();
                dataOutputStream.writeUTF(s);
                dataOutputStream.flush();
                s2 = dataInputStream.readUTF();
                System.out.println("Server says: " + s2);
            }
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
