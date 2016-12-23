/*
 * Linux Policy Machine (LPM) Prototype
 *   
 * Copyright (C) 2015-2016  Kirill A Belyaev
 * Colorado State University
 * Department of Computer Science,
 * Fort Collins, CO  80523-1873, USA
 *
 * E-mail contact:
 * kirillbelyaev@yahoo.com
 * kirill@cs.colostate.edu
 *   
 * This work is licensed under the Creative Commons Attribution-NonCommercial 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/ or send 
 * a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 */
package edu.csu.lpm.machine;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Remote Client
 *
 * @author maalv
 */
public class ClientMain {

    private static DataInputStream in;
    private static DataOutputStream out;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(RemoteConnect.serverAddress, RemoteConnect.PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String serverResponse = in.readUTF();
            System.out.println(serverResponse);
            System.out.println("Do you want to reply to server:(y/n)");
            String ch = br.readLine();
            if (ch.equalsIgnoreCase("y")) {
                String myResponse = br.readLine();
                out.writeUTF(myResponse);
            }
        }

    }

}
