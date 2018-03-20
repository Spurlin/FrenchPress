#include <Servo.h>
#include <HX711.h>

//setting up pins and servos
Servo mServo;
const int servoPin = 11;
const int temPin = A0;
const int groundsPin = A1;
const int dout = 4;
const int clk = 2;
const int ledPin = 13;
float calibration_factor = -96650;
HX711 scale(dout,clk);
void setup() {
  //Set the same baudrate as on RPi
  Serial.begin(9600);
  //connect the servo and set default postion to 0
  mServo.attach(servoPin);
  mServo.write(0);
  pinMode(ledPin,OUTPUT);
  scale.set_scale(calibration_factor);
  scale.tare();
  
}
//used to read the light value for the grounds
void grounds(){
      int groundsValue = analogRead(groundsPin);
      Serial.print(groundsValue);
}
//used to read the temperture value for the water
void temp(){
  int sensorVal = analogRead(temPin);
  float voltage = (sensorVal/1024.0) * 5.0;
  float tempC = (voltage -.5) *100;
  float tempF = tempC * (9.0/5.0) + 32;
  
  Serial.print(tempF,0);
}

//used to dispense grounds one scoop at a time
void servo(){
  mServo.write(0);
  delay(500);
  mServo.write(180);
  delay(500);
  mServo.write(0);
}

void loadcell(){
  Serial.print(scale.get_units(),2);
}

void ledOn(){
  digitalWrite(ledPin,HIGH);
}

void ledOff(){
  digitalWrite(ledPin, LOW);
}

void loop() {
  //hold while there is nothing on serial
 
  while (Serial.available() == 0);
  //read data from UART
  int data = Serial.read();
   //switch case to handle incoming message
  switch(data) {
    case 1: 
        temp();
        break;
    case 2:
        grounds();
        break;
    case 3:
        servo();
        break;
    case 4:
        loadcell();
        break;
    case 5:
        ledOn();
        break;
    case 6:
        ledOff();
        break;
        
        
  }
  Serial.flush();
}
