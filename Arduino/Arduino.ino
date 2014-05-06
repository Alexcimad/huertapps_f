// Andrino.ino
#include <SoftwareSerial.h>
#include "SPI.h"
#include <EEPROM.h>
#include "DHT.h"

#define secoBajo 0
#define secoAlto 300
#define humedoBajo 301
#define humedoAlto 700
#define mojadoBajo 701
#define mojadoAlto 950
#define ideal 550
#define RxD 10
#define TxD 11
#define RST 5
#define KEY 4

SoftwareSerial BT(RxD, TxD);


int sensorPin = A0;    // select the input pin for the potentiometer
int humedadSuelo = 0;

void setup() {
  BT.begin(9600);
  BT.println("Bluetooth On please press 1 or 0 blink LED ..");
  pinMode(sensorPin,INPUT);
}

void loop() {
	
	BT.println(String(pedirHumedadSuelo()));
	delay(2000);

	// char command = BT.read();
 //    BT.flush();
 //    Serial.println(command);
    
 //    // En caso de que el caracter recibido sea "L" cambiamos
 //    // El estado del LED
 //    if (command == 'L'){
 //      Serial.println("Toggle LED");
 //      toggle(LED);
 //    }

}


int pedirHumedadSuelo(){ 
	return analogRead(sensorPin);

}





// //setTime
// //0->humedad suelo 
// //1->humedad relativa
// //2->temperatura
// //340 tomas, read write, direccion, byte 0 255
// //EEPROM 1024 bytes
// //0 255


//   // variable to store the value coming from the sensor

// void setup() {
//   // declare the ledPin as an OUTPUT:
//    Serial.begin(9600);  
// }

// void loop() {
//   // read the value from the sensor:
//   sensorValue = analogRead(sensorPin);    
//   delay(1000);          
//   Serial.print("sensor = " );                       
//   Serial.println(sensorValue);                   
// }

// //rango 0 300 seco, 300 700 humedo, 700 - 950 agua




// #include "DHT.h"

// DHT dht;

// void setup()
// {
//   Serial.begin(9600);
//   Serial.println();
//   Serial.println("Status\tHumidity (%)\tTemperature (C)\t(F)");

//   dht.setup(2); // data pin 2
// }

// void loop()
// {
//   delay(dht.getMinimumSamplingPeriod());

//   float humidity = dht.getHumidity();
//   float temperature = dht.getTemperature();

//   Serial.print(dht.getStatusString());
//   Serial.print("\t");
//   Serial.print(humidity, 1);
//   Serial.print("\t\t");
//   Serial.print(temperature, 1);
//   Serial.print("\t\t");
//   Serial.println(dht.toFahrenheit(temperature), 1);
// }