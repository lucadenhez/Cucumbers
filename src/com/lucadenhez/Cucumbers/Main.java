package com.lucadenhez.Cucumbers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    static final int loginPort = 6969;
    static final int dashboardPort = 5050;

    static ArrayList<Integer> activeConnections = new ArrayList<>();
    public static BlockingQueue<String> commandQueue = new LinkedBlockingQueue<String>();

    static ServerSocket ss;
    static Socket client;

    public static void main(String[] args) throws IOException {
        int threadsAvailable = Runtime.getRuntime().availableProcessors();

        System.out.println("Available Threads: " + threadsAvailable + " (" + (threadsAvailable - 2) + " for connected computers)");

        DashboardHandler dashboardHandler = new DashboardHandler(dashboardPort);
        dashboardHandler.start();

        System.out.println("\nStarted website handler!");

        while (true) {
            sleep(1000);

            Server server = new Server();
            ss = server.openLoginPort(loginPort);

            if (ss != null) {
                System.out.println("Login socket successfully started!");
                System.out.println("\nWaiting for client to join login socket on port " + loginPort + "...");

                sleep(100);

                try {
                    client = ss.accept();
                } catch (Exception ex) {
                    System.out.println("[!] Failed to obtain connection from client:\n");
                    System.out.println("## " + ex.toString() + " ##\n");
                    System.out.println("Restarting socket...\n");
                    continue;
                }

                sleep(300);
                int targetPort = server.generatePort(activeConnections, loginPort);
                System.out.println("Client joined!");
                System.out.println("Generated target port: " + targetPort);

                try {
                    PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
                    pw.println(targetPort);
                    System.out.println("\nSuccessfully sent target port to client.");
                } catch (Exception ex) {
                    System.out.println("[!] Failed to get client's output stream:\n");
                    System.out.println("## " + ex.toString() + " ##\n");
                    System.out.println("Restarting socket...\n");
                    continue;
                }

                while (client.getInputStream().read() != -1) {
                    sleep(100);
                }

                ss.close();

                activeConnections.add(targetPort);

                System.out.println("Client received port and disconnected!");
                System.out.println("Opening socket with port and restarting login socket...");

                // START NEW THREAD FOR TARGET

                ClientThread clientThread = new ClientThread(targetPort);
                clientThread.start();
            }
        }
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (Exception ex) { }
    }
}
