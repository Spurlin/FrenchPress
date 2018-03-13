#include <Servo.h>
//setting up pins and servos
Servo mServo;
const int servoPin = 11;
const int temPin = A0;
const int groundsPin = A1;


void setup() {
  //Set the same baudrate as on RPi
  Serial.begin(9600);
  //connect the servo and set default postion to 0
  mServo.attach(servoPin);
  mServo.write(0);
  
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
        
        
  }
  Serial.flush();
}
