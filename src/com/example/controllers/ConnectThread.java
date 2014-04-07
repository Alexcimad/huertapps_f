package com.example.controllers;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;


public class ConnectThread extends Thread{
	
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
	private final BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;
	public BluetoothAdapter BA;

	   public ConnectThread(BluetoothDevice device) {
	       BluetoothSocket tmp = null;
	       mmDevice = device;
	       try {
	           tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
	       } catch (IOException e) { }
	       mmSocket = tmp;
	   }

	   public void run() {
		   BA=BluetoothAdapter.getDefaultAdapter();
		   BA.cancelDiscovery();
		   
		  
		   
	       try {
	           mmSocket.connect();
	           
	       } catch (IOException connectException) {
	           try {
	               mmSocket.close();
	               
	           } catch (IOException closeException) { }
	           return;
	       }
	     
	   }

	   public void cancel() {
	       try {
	           mmSocket.close();
	       } catch (IOException e) { }
	   }
	
}
