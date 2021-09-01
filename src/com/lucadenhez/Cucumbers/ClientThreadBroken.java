package com.lucadenhez.Cucumbers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThreadBroken extends Thread {
    private final Integer port;
    private final Integer controlPort;
    private ServerSocket server;
    private Socket client;
    private BufferedReader in;
    private BufferedReader stdIn;
    private PrintWriter out;

    public ClientThreadBroken(int port, int controlPort) throws IOException {
        this.port = port;
        this.controlPort = controlPort;
        this.server = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            try {
                client = server.accept();
            }
            catch (Exception ex) {
                return;
            }

            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\n## New thread initialized! | Client: " + port.toString() + " ##\n");

            try {
                Thread.sleep(500);
            }
            catch (Exception ignored) { }

            //Commands commands = new Commands();
            System.out.println("Attempting to retrieve initial information from client...\n");

            DataInputStream inStream = new DataInputStream(client.getInputStream());
            String clientMessage = "";

            do {
                clientMessage = inStream.readUTF();
            } while (clientMessage.equals(""));

            System.out.println("Here we go: " + clientMessage);

            //String initialInformation = commands.getInitialInformation(client, in, out);

            //out.println("initialInformation");
            //sleep(1000);
            //String initialInformation = in.readLine();
            //String initialInformation = in.readLine();

            //String line;

            //ystem.out.println("Here");

            //while((line = in.readLine()) != null)
            //{
                //System.out.println(line);
            //}

            //nputStream inputStream = client.getInputStream();
            // create a DataInputStream so we can read data from it.
            //DataInputStream dataInputStream = new DataInputStream(inputStream);

            // read the message from the socket
            //String message = dataInputStream.readUTF();

            //System.out.println("Got initial information!\n");
            //System.out.println(message);

            //System.out.println("Bruv");

            //System.in.read();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
