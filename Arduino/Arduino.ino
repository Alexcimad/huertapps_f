// Andrino.ino
#include <SoftwareSerial.h>
#include "SPI.h"
#include <EEPROM.h>


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


int sensorPin = 1;
int regarPin=12;// select the input pin for the potentiometer
int humedadSuelo = 0;
int promedio = 0;

void setup() {
  BT.begin(9600);
  BT.println("B para baja humedad, N para humedad normal, A para alta humedad");
  pinMode(sensorPin,INPUT);
  pinMode(regarPin,OUTPUT);
  BT.println("Humedad Actual: "+ String(pedirHumedadSuelo()));
  //BT.println(String(pedirHumedadSuelo()));
  BT.println("0 es seco, 550 es el ideal y 999 es flotando en agua");
}

void loop() {



char comando = BT.read();
    BT.flush();

    //Baja Humedad
    if (comando == 'B'){
      BT.println("Comenzando Riego para baja humedad");
       promedio = ((secoAlto+secoBajo)/2)-1;
       while (promedio < (secoAlto+secoBajo)/2){
         digitalWrite(regarPin,HIGH);
       promedio = calcularPromedio();
      }
      digitalWrite(regarPin,LOW);
      BT.println("terminando Riego para baja humedad");
      BT.println("Humedad Actual");
      BT.println(String(pedirHumedadSuelo()));
      BT.println("0 es seco, 550 es el ideal y 999 es flotando en agua");
    }

    if (comando == 'N'){
      BT.println("Comenzando Riego para humedad normal ");
      promedio = ideal - 1;
      while ( promedio < ideal){
       digitalWrite(regarPin,HIGH);
       promedio = calcularPromedio();
      }
      digitalWrite(regarPin,LOW);
      BT.println("terminando Riego para humedad normal ");
      BT.println("Humedad Actual");
      BT.println(String(pedirHumedadSuelo()));
      BT.println("0 es seco, 550 es el ideal y 999 es flotando en agua");
    }

    if (comando == 'A'){
      BT.println("Comenzando Riego para alta humedad");
      promedio = ((mojadoAlto+mojadoBajo)/2)-1;
      while (promedio < (mojadoAlto+mojadoBajo)/2){
       digitalWrite(regarPin,HIGH);
       promedio = calcularPromedio();
      }
      digitalWrite(regarPin,LOW);
      BT.println("terminando Riego para alta humedad");
      BT.println("Humedad Actual");
      BT.println(String(pedirHumedadSuelo()));
      BT.println("0 es seco, 550 es el ideal y 999 es flotando en agua");
}
}

int pedirHumedadSuelo(){
return analogRead(sensorPin);
}

void regar(){
digitalWrite(regarPin,LOW);
delay(300);
digitalWrite(regarPin,HIGH);
delay(300);
}

int calcularPromedio(){
int medida = pedirHumedadSuelo();
delay (1000);
medida += pedirHumedadSuelo();
delay (1000);
medida += pedirHumedadSuelo();
delay (1000);
medida += pedirHumedadSuelo();
delay (1000);
medida += pedirHumedadSuelo();
return medida/ 5;
}
