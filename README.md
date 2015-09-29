# Cordova Sensors Plugin

The sensors are capable of providing raw data with high precision and accuracy, and are useful if you want to monitor three-dimensional device movement or positioning, or you want to monitor changes in the ambient environment near a device. For example, a game might track readings from a device's gravity sensor to infer complex user gestures and motions, such as tilt, shake, rotation, or swing. Likewise, a weather application might use a device's temperature sensor and humidity sensor to calculate and report the dewpoint, or a travel application might use the geomagnetic field sensor and accelerometer to report a compass bearing.

At this moment this plugin is implemented only for Android!

## Demos

See in https://github.com/fabiorogeriosj/cordova-plugin-sensors-demo

## Install

    $ cordova plugin add https://github.com/fabiorogeriosj/cordova-plugin-sensors.git

## Methods    

#### sensors.enableSensor("TYPE_SENSOR")

Enable sensor.

#### sensors.disableSensor()

Disable sensor.

#### sensors.getState(sucessCallBack)

Get values sensor.

## Using in Ionic

```js
  APP.controller("indexController", function ($scope, $interval){

      function onSuccess(values) {
          $scope.state = values[0];
      };

      document.addEventListener("deviceready", function () {
        
        sensors.enableSensor("PROXIMITY");

        $interval(function(){
          sensors.getState(onSuccess);
        }, 100);


      }, false);

  });
```

## Type sensors

**PROXIMITY** - Measures the proximity of an object in cm relative to the view screen of a device.

**ACCELEROMETER** - Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), including the force of gravity.

**GRAVITY** - Measures the force of gravity in m/s2 that is applied to a device on all three physical axes (x, y, z).

**GYROSCOPE** - Measures a device's rate of rotation in rad/s around each of the three physical axes (x, y, and z).

**GYROSCOPE_UNCALIBRATED** - Rate of rotation (without drift compensation) around the x axis.

**LINEAR_ACCELERATION** - Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), excluding the force of gravity.

**ROTATION_VECTOR** - Measures the orientation of a device by providing the three elements of the device's rotation vector.

**STEP_COUNTER** - Number of steps taken by the user since the last reboot while the sensor was activated.

**GAME_ROTATION_VECTOR** - Rotation vector component along the x axis (x * sin(θ/2)).

**GEOMAGNETIC_ROTATION_VECTOR** - Rotation vector component along the x axis (x * sin(θ/2)).

**MAGNETIC_FIELD** - Measures the ambient geomagnetic field for all three physical axes (x, y, z) in μT.

**MAGNETIC_FIELD_UNCALIBRATED** - Geomagnetic field strength (without hard iron calibration) along the x axis.

**ORIENTATION** - Measures degrees of rotation that a device makes around all three physical axes (x, y, z).

**AMBIENT_TEMPERATURE** - Measures the ambient room temperature in degrees Celsius (°C). See note below.

**LIGHT** - Measures the ambient light level (illumination) in lx.

**PRESSURE** - Measures the ambient air pressure in hPa or mbar.

**RELATIVE_HUMIDITY** - Measures the relative ambient humidity in percent (%).

**TEMPERATURE** - Measures the temperature of the device in degrees Celsius (°C). 



For more information about sensors **Android** see [Android Sensors Overview](http://developer.android.com/guide/topics/sensors/sensors_overview.html)