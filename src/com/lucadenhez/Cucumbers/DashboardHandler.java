package com.lucadenhez.Cucumbers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class DashboardHandler extends Thread {
    private ServerSocket ss;
    private Socket website;
    private int port;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public DashboardHandler(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ss = new ServerSocket(port);
                website = ss.accept();

                out = new PrintWriter(website.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(website.getInputStream()));
                stdIn = new BufferedReader(new InputStreamReader(System.in));

                while (website.getInputStream().read() != -1) {
                    sleep(100);
                }

                String command = in.readLine();

                System.out.println("() Recieved command from website!");
                System.out.println("(!) Command: " + command);

                if (command.equals("initialInformation")) {
                    Main.commandQueue.add("initialInformation");
                }

            }
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }
}


