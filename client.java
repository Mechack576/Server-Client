package com.company;
/*
 * Niyomukiza Mechack
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class client{
    private static ServerSocket listener;
    private static Socket socket;

    public static void main(String[] args)throws IOException{

        int n = 0;
        long index = 0;
        try {
            socket =  new Socket(args[0], Integer.parseInt(args[1]));

            BufferedReader input = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(client.socket.getOutputStream()));
            //InputStream output = connSocket.getInputStream();
            String fromServer;

            while (true) {
                fromServer = input.readLine();
                if (fromServer == null) {
                    break;
                }
                Scanner scan = new Scanner(System.in);
                System.out.println(index + " From Server: " + fromServer);
                System.out.println("Which number from the Menu would you like: ");
                //User Inputs which file they would like to recieve
                String response = scan.nextLine();
                n = Integer.parseInt(response);
                if(Integer.parseInt(response)==3) {
                    scan.close();
                    break;
                }
                output.write(response, 0, response.length());
                output.newLine();
                output.flush();
                index++;
            }
            input.close();
            output.close();
            client.socket.close();


        }catch (IOException e){
            System.out.println("Client Error: " + e.getMessage());
        }
        if(n != 3) {
            // saves the file from server with current time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String fileName = new String(dateFormat.format(date));
            fileName += ".png";
            //Write to filename
            FileOutputStream myFileOutput = new FileOutputStream(fileName);
            BufferedOutputStream bufferOutput = new BufferedOutputStream(myFileOutput);
            byte byteArray[] = new byte[1024];
            BufferedInputStream Fileinput = new BufferedInputStream(socket.getInputStream());
            int bytesRead;

            do {
                bytesRead = Fileinput.read(byteArray, 0, byteArray.length);
                if(bytesRead > 0) {
                    bufferOutput.write(byteArray, 0, bytesRead);
                    bufferOutput.flush();
                }
            } while(bytesRead != -1);

            System.out.println("Writing complete......");


            bufferOutput.close();
            Fileinput.close();
            myFileOutput.close();
            socket.close();
        }

    }

}


