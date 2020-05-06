package com.company;

/*

 * Niyomukiza Mechack
 */
import java.io.*;
import java.net.*;

public class server {
    private static ServerSocket listener;
    private static Socket connSocket;

    //convert url to file and send
    private static void sendFile(File file) throws FileNotFoundException {

        try {
            FileInputStream fileInput = new FileInputStream(file);
            BufferedInputStream buff_File = new BufferedInputStream(fileInput);
            //bytes to be written to
            byte[] byteArray = new byte[8192];
            int bytesRead;

            BufferedOutputStream out = new BufferedOutputStream(connSocket.getOutputStream());
            while((bytesRead = buff_File.read(byteArray)) > 0) {
                out.write(byteArray, 0, bytesRead);
            }

            //close streams
            fileInput.close();
            buff_File.close();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: PORT number should be specified");
            return;
        }

        try {
            listener = new ServerSocket(Integer.parseInt(args[0]));
            connSocket = listener.accept();

            System.out.println("Client connected from " + connSocket.getRemoteSocketAddress().toString());
            //client input set up
            BufferedReader input = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            //server response set up
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(connSocket.getOutputStream()));
            //take input at line
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            //f9.png
            File png = new File("C:\\Users\\natha\\Downloads\\f9.png");
            //map.pdf
            File pdf = new File("C:\\Users\\natha\\Download\\map.pdf");

            String response="Please select from menu: 1. f9.png  2. map.pdf 3. bye";
            String client;
            int n = 0;

            //loop for prompt
            while(n != 3) {
                //print menu
                output.write(response, 0, response.length());
                output.newLine();
                output.flush();
                //read client input
                client = input.readLine();
                //hold client choice
                n = Integer.parseInt(client.trim());

                //switch case
                switch(n) {
                    case 1:
                        sendFile(png);
                        break;
                    case 2:
                        sendFile(pdf);
                        break;
                    case 3:
                    default:
                        output.write("bye");
                        input.close();
                        output.close();
                        stdIn.close();
                        // I left stdIn just incase you guys wanted to use it
                }
            }


        }
        catch(IOException e) {

        }


    }

}

