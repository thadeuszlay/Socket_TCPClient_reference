package com.example.socket_tcpclient_reference;
/*
import android.util.Log;
import java.io.*;
import java.net.*;

public class TCPClient {
	private String serverMessage;
	public static final String SERVERIP="192.168.173.1";
	public static final int SERVERPORT=4444;
	private OnMessageReceived mMessageListener=null;
	private boolean mRun=false;
	PrintWriter out;
	BufferedReader in;
	
	public TCPClient(OnMessageReceived listener){
		mMessageListener=listener;
	}
	
	public void sendMessage(String message){
		if (out!=null && !out.checkError()){
			out.println(message);
			out.flush();
		}
	}
	
	public void stopClient(){
		mRun=false;
	}
	
	public void run(){
		mRun=true;
		try{
			InetAddress serverAddr=InetAddress.getByName(SERVERIP);
			Log.e("TCP Client", "C: Connecting....");
			Socket socket=new Socket(serverAddr, SERVERPORT);
			try{
				out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				Log.e("TCP Client", "C: Sent.");
				Log.e("TCP Client", "C: Done.");
				in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while(mRun){
					serverMessage=in.readLine();
					if (serverMessage!=null && mMessageListener!=null){
						mMessageListener.messageReceived(serverMessage);
					}
					serverMessage=null;
				}
				Log.e("RESPONSE FROM SRVER", "S: Received Message: '"+serverMessage+"'");			
				
			}catch (Exception e){
				Log.e("TCP", "S: Error", e);
			}finally{
				//the socket must be closed. It is not possible to reconnect to this socket
				//after it is closed, which means a new socket instance has to be created.
				socket.close();
			} 
			}catch(Exception e){
				Log.e("TCP", "C:Error",e);
			}
		}
	//Declare the interface. The method messageRecheived(String message) must be implemented in the MyActivity
	//class at on AsyncTask doInBackground
	public interface OnMessageReceived{
		public void messageReceived(String message);
	}
}
*/

//you just could "import java.io.*" or "import java.net.*"
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

public class TCPClient {
	private String serverMessage;
	public static final String SERVERIP="192.168.173.1";// to be replaced with your IP-address
	public static final int SERVERPORT=4444;
	private OnMessageReceived mMessageListener=null;
	private boolean mRun=false;
	PrintWriter out;
	BufferedReader in;
	
	//constructor
	public TCPClient(OnMessageReceived listener){
		mMessageListener=listener;
	}
	
	public void sendMessage(String message){
		if(out!=null && !out.checkError()){
			out.println(message);
			out.flush();
		}
	}
	
	public void stopClient(){
		mRun=false;
	}
	
	public void run(){
		mRun=true;
		try{
			InetAddress serverAddr=InetAddress.getByName(SERVERIP);
			Log.e("TCP Client", "Client: Connecting....");
			Socket socket=new Socket(serverAddr, SERVERPORT);
			try{
				out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				
				Log.e("TCP Client", "Client: Sent.");
				Log.e("TCP Client", "Client: Done.");
				
				in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				while(mRun){
					serverMessage=in.readLine();
					if(serverMessage!=null && mMessageListener!=null){
						mMessageListener.messageReceived(serverMessage);
					}
					serverMessage=null;
				}
				Log.e("RESPONSE FROM SERVER", "Server: Received message: '"+serverMessage+"'");
			}catch(Exception ex){
				Log.e("TCP", "Client: Error",ex);
			}finally{
				//Closing the socket is important!!!
				socket.close();
			}
		}catch(Exception ex){
			Log.e("TCP", "Client: Error",ex);
		}
	}
	
	public interface OnMessageReceived{
		public void messageReceived(String message);
	}
}