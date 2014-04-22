package com.example.huertapps;

//import java.util.Set;
import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
import java.util.ArrayList;
import java.util.Arrays;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Menuactivity extends Activity {
  
  private ListView menuListView ;
  private ArrayAdapter<String> listAdapter ;
  
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
  }
  
}