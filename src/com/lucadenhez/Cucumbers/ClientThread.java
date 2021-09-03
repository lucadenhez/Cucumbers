/* TO DO

- Troubleshoot timing/sleep delays, to speed up things!
- Make messages from threads identifiable, not just ALL CAPS, but colors too.

 */

package com.lucadenhez.Cucumbers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class ClientThread extends Thread {
    private final Integer port;
    private ServerSocket server;
    private Socket client;
    private BufferedReader in;
    private BufferedReader stdIn;
    private PrintWriter out;
    public String initialInformation;

    public ClientThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("\n## NEW THREAD INITIALIZED! # " + port.toString() + " ##\n");
        Main.commandQueue.add("Bruv");
        try {
            while (true) {
                System.out.println("## SOCKET ON THREAD #" + port.toString() + " IS BACK ONLINE ##");

                sleep(100);
                server = new ServerSocket(port);

                try {
                    client = server.accept();
                }
                catch (Exception ex) {
                    return;
                }

                System.out.println("## CLIENT JOINED SOCKET #" + port.toString() + " ##");

                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                stdIn = new BufferedReader(new InputStreamReader(System.in));

                Commands commands = new Commands(client, in, out);

                while (Main.commandQueue.isEmpty()) { sleep(100); }

                if (Main.commandQueue.contains("initialInformation")) {
                    System.out.println("Attempting to retrieve initial information from client...\n");
                    initialInformation = commands.getInitialInformation();
                    System.out.println("\nInitial Information:\n" + initialInformation);
                }

                Main.commandQueue.poll();
                server.close();
            }
        }
        catch (Exception ex) {
            System.out.println("## EXCEPTION OCCURED ON THREAD #" + port.toString() + " ##");
            System.out.println("## TEMPORARILY IGNORNING... ##");
        }
    }
}
