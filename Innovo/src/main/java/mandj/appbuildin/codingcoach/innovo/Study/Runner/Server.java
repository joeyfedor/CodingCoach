package mandj.appbuildin.codingcoach.innovo.Study.Runner;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Joey on 6/21/2014.
 */

public class Server {

    public static String send(String code, String methodHeader, String serverIP, Context context) {

        final String TAG = "Server Communication";

        String changeText;
        try {

            //Connects the client to the server
            Socket skt = new Socket(serverIP, 13254);

            //Opens an output stream with the server and hands the clients Ip address to it
            PrintWriter ipOut = new PrintWriter(skt.getOutputStream(), true);
            //gets the name of the user
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String name = preferences.getString("example_text", "");

            ipOut.println("user:" + name);
            ipOut.println("Client[IP:" + "iphere" + "]");
            Log.d(TAG, "Client -> Server (\"Username:" + name + "]\") ");

            //Opens an output stream with the server and hands the users code to the server
            ipOut.println(methodHeader +
                    code +
                    "\n//stop//");

            Log.d(TAG, "Client -> Server (" + methodHeader + "\n" +
                    code + "\n" +
                    "//stop//)");

            //reads the servers output and prints it to the output pane
            BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));

            //checks to see if server is online
            changeText = "Server: " + in.readLine();

            //Reads us the connection number
            changeText += "\n" + in.readLine();

            //Reads us the compiler status
            changeText += "\n" + in.readLine();

            String testNxtLn = in.readLine();
            //tests for another line, first tells us if the statement compiled
            while (testNxtLn != null) {
                changeText += "\n" + testNxtLn;
                testNxtLn = in.readLine();
            }

            //closes the streams
            skt.close();
            ipOut.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
            changeText = "Error! It appears that you don't have a connection to the internet\n";
        }

        Log.d(TAG, "Server -> Client (" + changeText + ") ");
        return changeText;
    }

}