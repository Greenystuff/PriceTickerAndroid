package com.greedythings.priceticker;;

import android.os.AsyncTask;
import android.util.Log;


import com.greedythings.priceticker.MainContract;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient implements MainContract.Presenter {


    public static final String TAG = TcpClient.class.getSimpleName();
    // message to send to the server
    private String mServerMessage;
    // sends message received notifications
    private OnMessageReceived mMessageListener = null;
    // while this is true, the server will continue running
    private boolean mRun = false;
    // used to send messages
    private PrintWriter mBufferOut;
    // used to read messages from the server
    private BufferedReader mBufferIn;

    private MainContract.View view;

    TcpClient clientTcp;
    private String ipAdress = "";
    private String port = "";
    private static TcpClient tcpClientInstance;

    public static TcpClient Init(MainContract.View view) throws Exception {
        if (tcpClientInstance != null){ //if there is no instance available... create new one
            throw new Exception("Déja initialisé frérot !");
        }
        tcpClientInstance = new TcpClient(view);
        return tcpClientInstance;
    }

    public static TcpClient getInstance() throws Exception {
        if (tcpClientInstance == null){ //if there is no instance available... create new one
            throw new Exception("Tu dois initailiser la fonction Init()");
       }
        return tcpClientInstance;
    }

    private TcpClient(MainContract.View view) {
        this.view = view;
    }

    public void sendMessage(final String message) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mBufferOut != null) {
                    Log.e(TAG, "Envoi du message : " + message);
                    mBufferOut.println(message);
                    mBufferOut.flush();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void stopClient() {

        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }

    public void run(String Ip, int Port) {

        mRun = true;

        try {
            InetAddress serverAddr = InetAddress.getByName(Ip);
            Log.e("TCP Client", "C: Connection...");
            Socket socket = new Socket(serverAddr, Port);
            if (view != null) {
                view.ConnexionStatus(true);
            }
            try {

                mBufferOut = new PrintWriter(socket.getOutputStream());
                Log.e("TCP Client", "C: Connecté !");
                mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                int charsRead = 0;
                char[] buffer = new char[1024]; //choose your buffer size if you need other than 1024
                while (mRun) {
                    charsRead = mBufferIn.read(buffer,0, buffer.length);
                    mServerMessage = new String(buffer).substring(0, charsRead);
                    if (mServerMessage != null && mMessageListener != null) {
                        mMessageListener.messageReceived(mServerMessage);
                    }
                    //Log.e("MESSAGE MAINTEST SERVER", mServerMessage);
                    mServerMessage = null;
                }
                Log.d("Reponse du server", mServerMessage);
            } catch (Exception e) {
                if(e.getMessage().equals("Connection reset")) {
                    Log.e("TCP Client", "S: Le server a été fermé");
                    view.ServerClosed("Le server a été fermé, veuillez vous reconnecter");
                }else {
                    Log.e("TCP Client", "Déconnexion volontaire du client");
                    view.ServerClosed("Vous êtes déconnecté");
                }
            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }
        } catch (Exception e) {
            Log.e("TCP", "C: Server Injoignable", e);
            view.ConnexionStatus(false);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void RunServer(String AdresseIp, String Port) {
        ipAdress = AdresseIp;
        port = Port;
        new ConnectTask().execute("");
    }

    private class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {
            try {
                clientTcp = TcpClient.getInstance();
                clientTcp.run(ipAdress, Integer.parseInt(port));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void AttachListener(OnMessageReceived listener) {
        mMessageListener = listener;
    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the Activity
    //class at on AsyncTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
