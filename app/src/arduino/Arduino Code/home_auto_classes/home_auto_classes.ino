#include <SoftwareSerial.h>
#include <Servo.h>
#include "calc_temp.h"

const int unusedPin = 255;       // an empty pin required for additional serial connections
const int servoPin = 4;          // the pin that the servo is attached to
const int tempPin = 0;           // the pin that the temperature monitor is attached to
const int ledPin = 10;           // the pin that the first LED is attached to
const int ledPinB = 8;           // the pin that the second LED is attached to
const int light_two_send = 6;    // the "send" pin for the additional serial connection for the second LED
const int light_two_receive = 7; // the "receive" pin for the additional serial connection for the second LED

SoftwareSerial serial_servo(unusedPin, servoPin);
SoftwareSerial serial_temp(unusedPin, tempPin);
SoftwareSerial light_two(light_two_send, light_two_receive);

char serialA;
char serialB;

Servo myServo;

int angle = 0;
char light_state = '0';
char light_state_B = '0';
float millivolts = 0.0;
float celsius = 0.0;

void setup()
{
  // initialize the serial communication:
  Serial.begin(9600); //baud rate - make sure it matches that of the module you got:
  serial_servo.begin(9600);
  serial_temp.begin(9600);
  light_two.begin(9600);
  // initialize the ledPin as an output:
  pinMode(ledPin, OUTPUT);
  pinMode(servoPin, OUTPUT);
  pinMode(ledPinB, OUTPUT);
  //light_state = digitalRead(ledPin);

  //myServo.attach(4);
  Serial.print("AT+NAMELightA");
  light_two.print("AT+NAMELightB");  
}


void loop() {

int volt_value = analogRead(tempPin);
float my_temp = calc_temp(volt_value);


/*
Serial.print(my_temp);
Serial.println(" degrees Celsius, ");
*/

delay(250);
 
/*
if (celsius > 24.0) {
  myServo.attach(4);
  myServo.write(130);
  delay(1000);
  myServo.write(25);
  delay(1000);
}
*/

if (Serial.available() > 0) {serialA = Serial.read(); Serial.print(serialA);}

   switch (serialA) {
    case '1':
      digitalWrite(ledPin, HIGH);
      light_state = '1';
      Serial.flush();
      break;
    case '2':
      digitalWrite(ledPin, LOW);
      light_state = '0';
      Serial.flush();
      break;
    case '3':
      Serial.print(light_state);
      //Serial.flush();
      break;
    case '4':
        Serial.print(my_temp);
        Serial.flush();
        break;
    default:

      break;
        
    Serial.flush();
   }
   serialA = ' ';
    
  if (light_two.available() > 0) {serialB = light_two.read();light_two.print(serialB);}

   switch (serialB) {
    case '1':
      digitalWrite(ledPinB, HIGH);
      light_state_B = '1';
      light_two.flush();
      break;
    case '2':
      digitalWrite(ledPinB, LOW);
      light_state_B = '0';
      light_two.flush();
      break;
    case '3':
      light_two.print(light_state_B);
      //light_two.flush();
      break;
    case '4':
        //light_two.print(my_temp);
        //light_two.flush();
        break;
    default:

      break;
      
   }
   serialB = ' ';
   
}

