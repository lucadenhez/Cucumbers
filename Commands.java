package com.lucadenhez.Cucumbers;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

import static com.lucadenhez.Cucumbers.Main.sleep;

public class Commands {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public Commands(Socket client, BufferedReader in, PrintWriter out) {
        this.client = client;
        this.in = in;
        this.out = out;
    }

    String sendCommand(String command) {
        try {
            out.println(command);
            out.flush();
            System.out.println("## SENT COMMAND: " + command + " ##");
            System.out.println("## WAITING FOR RESPONSE ##");

            sleep(1000);

            System.out.println("## GOT RESPONSE ##");

            return in.readLine();
        }
        catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    String getInitialInformation() {
        return sendCommand("initialInformation");
    }
}
