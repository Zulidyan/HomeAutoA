//Calc_Temp function
//A library to calculate the temperature in degrees Celsius as read by the temperature sensor

#include "Arduino.h"
#include "calc_temp.h"

float calc_temp(int voltage) {
  float millivolts = (voltage / 1024.0) * 5000;
  float celsius = millivolts / 10;
  return celsius;
}