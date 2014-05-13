package com.example.huertapps;

//import java.util.Set;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.service.BluetoothService;
/*import com.example.service.ConnectThread;
import com.example.service.ConnectedThread;*/

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Menuactivity extends Activity {
	
	private static final String TAG = "BluetoothChat";
    private static final boolean D = true;
	public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    private BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
  private ListView menuListView ;
  private ArrayAdapter<String> listAdapter ;
  private BluetoothService mBTservice;
  private Handler mHandler;
  private Intent mdata;
  private boolean exist=false;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    
    setContentView(R.layout.menu);
    Toast.makeText(getApplicationContext(), "APLICACION_CONECTADA", Toast.LENGTH_LONG).show();
   
    //setContentView(R.layout.menu);
	  //super.onCreate(savedInstanceState);
	  //Toast.makeText(getApplicationContext(), "Aplicacion conectada", Toast.LENGTH_SHORT).show(); 			
    
    // Encontrar el ListView
    menuListView = (ListView) findViewById( R.id.menuListView );

    // Crea la lista con los nombres
    String[] planets = new String[] { "Regar", "Fertilizar", "Programar", "Estadisticas",
                                      "Automatizar"};  
    ArrayList<String> planetList = new ArrayList<String>();
    planetList.addAll( Arrays.asList(planets) );
    
    // Create ArrayAdapter usando la lista anterior
    listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
    
    
    // Set the ArrayAdapter as the ListView's adapter.
    menuListView.setAdapter( listAdapter );      
    menuListView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2,
				long arg3) {
			int msg=1;
			sendMessage(msg);
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
			
			
          
			//Log.d(TAG, "msg send");
			
		}
    });
    
    
		}
  
  public void onStart() {
      super.onStart();
      Intent data_receive=this.getIntent();
     
      if(D) Log.e(TAG, "++ ON START ++");

      // If BT is not on, request that it be enabled.
      // setupChat() will then be called during onActivityResult
      if (!mBluetoothAdapter.isEnabled()) {
          Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
          startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
      // Otherwise, setup the chat session
      } else {
          if (mBTservice == null) {
        	  //setupChat();
        
        	setupChat();
        	
            String address = data_receive.getExtras()
                    .getString(BluetoothActivity.EXTRA_DEVICE_ADDRESS);
            /*if (address.length()>0){
          	  exist=true;
          	if(exist){*/
            	
          		if ((mBTservice.getState())!=3){
          			Log.d(TAG,"tentative_connexion");
          			connectDevice(data_receive);
          			
          		}
          	//}
        	//}
        	  
          }
      }
  }
      
      
  
  public synchronized void onResume() {
      super.onResume();
      boolean exist=false; 
      if(D) Log.e(TAG, "+ ON RESUME +");

      // Performing this check in onResume() covers the case in which BT was
      // not enabled during onStart(), so we were paused to enable it...
      // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
      if (mBTservice != null) {
          // Only if the state is STATE_NONE, do we know that we haven't started already
          if (mBTservice.getState() == BluetoothService.STATE_NONE) {
            // Start the Bluetooth chat services
            mBTservice.start();
            
            
            
                
          }
      }
  }
  public synchronized void onPause() {
      super.onPause();
      if(D) Log.e(TAG, "- ON PAUSE -");
  }

  @Override
  public void onStop() {
      super.onStop();
      if(D) Log.e(TAG, "-- ON STOP --");
  }

  private void setupChat() {
      Log.d(TAG, "setupChat()");

      // Initialize the array adapter for the conversation thread
     /* mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
      mConversationView = (ListView) findViewById(R.id.in);
      mConversationView.setAdapter(mConversationArrayAdapter);

      // Initialize the compose field with a listener for the return key
      mOutEditText = (EditText) findViewById(R.id.edit_text_out);
      mOutEditText.setOnEditorActionListener(mWriteListener);

      // Initialize the send button with a listener that for click events
      mSendButton = (Button) findViewById(R.id.button_send);
      mSendButton.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
              // Send a message using content of the edit text widget
              TextView view = (TextView) findViewById(R.id.edit_text_out);
              String message = view.getText().toString();
              sendMessage(message);
          }
      });*/

      // Initialize the BluetoothChatService to perform bluetooth connections
      mBTservice = new BluetoothService(this);
      
      // Initialize the buffer for outgoing messages
      //mOutStringBuffer = new StringBuffer("");
  }

  private void sendMessage(int flag) {
      // Check that we're actually connected before trying anything
      if (mBTservice.getState() != BluetoothService.STATE_CONNECTED) {
          //Toast.makeText(getApplicationContext(), "fuck", Toast.LENGTH_SHORT).show();
         
      }
  
      // Check that there's actually something to send
      
          // Get the message bytes and tell the BluetoothChatService to write
          //byte[] send = msg.getBytes();
          mBTservice.write(flag);

          // Reset out string buffer to zero and clear the edit text field
          /*mOutStringBuffer.setLength(0);
          mOutEditText.setText(mOutStringBuffer);*/
      
  }
  private void connectDevice(Intent data) {
      // Get the device MAC address
      String address = data.getExtras()
          .getString(BluetoothActivity.EXTRA_DEVICE_ADDRESS);
      // Get the BluetoothDevice object
      BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
      // Attempt to connect to the device
      mBTservice.connect(device);
  }
  /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if(D) Log.d(TAG, "onActivityResult " + resultCode);
      switch (requestCode) {
      case REQUEST_CONNECT_DEVICE_SECURE:
          // When DeviceListActivity returns with a device to connect
          if (resultCode == Activity.RESULT_OK) {
              connectDevice(data);
          }
          break;
      case REQUEST_CONNECT_DEVICE_INSECURE:
          // When DeviceListActivity returns with a device to connect
          if (resultCode == Activity.RESULT_OK) {
              connectDevice(data, false);
          }
          break;
      case REQUEST_ENABLE_BT:
          // When the request to enable Bluetooth returns
          if (resultCode == Activity.RESULT_OK) {
              // Bluetooth is now enabled, so set up a chat session
             // setupChat();
          } else {
              // User did not enable Bluetooth or an error occurred
              Log.d(TAG, "BT not enabled");
              Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
              finish();
          }
      }
  }*/
  
  
 
}