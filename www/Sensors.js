/*global cordova, module*/

module.exports = {
    getState: function(successCallback) {
        cordova.exec(successCallback, null, "Sensors", "getState", []);
    },

    /**
     *  Enable the proximity sensor. Needs to be called before getting the proximity state.
     */
    enableSensor: function(TYPE_SENSOR) {
        cordova.exec(null, null, "Sensors", "start", [TYPE_SENSOR]);
    },

    /**
     *  Disable the proximity sensor.
     */
    disableSensor: function() {
        cordova.exec(null, null, "Sensors", "stop", []);
    }
};


/*
DOC:

"PROXIMITY"
"ACCELEROMETER"
"GRAVITY"
"GYROSCOPE"
"GYROSCOPE_UNCALIBRATED"
"LINEAR_ACCELERATION"
"ROTATION_VECTOR"
"SIGNIFICANT_MOTION"
"STEP_COUNTER"
"STEP_DETECTOR"
"GAME_ROTATION_VECTOR"
"GEOMAGNETIC_ROTATION_VECTOR"
"MAGNETIC_FIELD"
"MAGNETIC_FIELD_UNCALIBRATED"
"ORIENTATION"
"AMBIENT_TEMPERATURE"
"LIGHT"
"PRESSURE"
"RELATIVE_HUMIDITY"
"TEMPERATURE"
*/