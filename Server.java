package com.lucadenhez.Cucumbers;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    ServerSocket server;

    ServerSocket openLoginPort(int loginPort) {
        try {
            server = new ServerSocket(loginPort);
            server.setReuseAddress(true);
            return server;
        }
        catch (Exception ex) {
            System.out.println("[!] Failed to start login socket:\n");
            System.out.println("## " + ex.toString() + " ##");
            return null;
        }
    }

    int generatePort(ArrayList<Integer> activeConnections, int loginPort) {
        if (activeConnections.isEmpty()) {
            return loginPort + 1;
        }
        else {
            return activeConnections.get(activeConnections.size() - 1) + 1;
        }
    }
}
