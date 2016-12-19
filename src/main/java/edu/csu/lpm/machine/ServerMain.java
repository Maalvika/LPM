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

import edu.csu.lpm.DB.interfaces.DB_Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This provide remote connectivity so it will require remote client to connect
 * @author maalv
 */
public class ServerMain {

    

    /**
     * The set of all the print writers for all the client. This set is kept so
     * we can easily broadcast messages.
     */
    private static Map<String/*IP address*/, PrintWriter> client = new HashMap<String, PrintWriter>();
    
    private String clientIP;
    private PrintWriter clientOut;

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(RemoteConnect.PORT);
        try {
            while (true) {
                if(client.size() == 0) {
                    new Handler(listener.accept()).start();
                } else {
                    System.out.println("Other client trying to connect");
                }
            }
        } finally {
            listener.close();
        }
    }

    private static class Handler extends Thread {

        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                File db = new File(DB_Constants.DB_NAME);
                PM_Shell sh = new PM_Shell(out, in);

                if (db.exists()) {
                    sh.verify();

                } else {

                    out.println("PM Database " + DB_Constants.DB_NAME + " does not exist! Exiting.");
                }

                client.put(socket.getRemoteSocketAddress().toString(), out);

            } catch (IOException ex) {
                Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
