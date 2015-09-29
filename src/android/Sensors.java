package com.fabiorogeriosj.plugin;

import java.util.List;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.hardware.*;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.ArrayList;

public class Sensors extends CordovaPlugin implements SensorEventListener {

    public static int STOPPED = 0;
    public static int STARTING = 1;
    public static int RUNNING = 2;
    public static int ERROR_FAILED_TO_START = 3;
    
    // sensor result 
    
    public long TIMEOUT = 30000;        // Timeout in msec to shut off listener

    int status;                         // status of listener
    long timeStamp;                     // time of most recent value
    long lastAccessTime;                // time the value was last retrieved

    JSONArray value;
    String TYPE_SENSOR;

    private SensorManager sensorManager;// Sensor manager
    Sensor mSensor;                     // Compass sensor returned by sensor manager

    private CallbackContext callbackContext;

    /**
     * Constructor.
     */
    public Sensors() {
        this.value = new JSONArray();
        this.TYPE_SENSOR = "";
        this.timeStamp = 0;
        this.setStatus(Sensors.STOPPED);
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.sensorManager = (SensorManager) cordova.getActivity().getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action                The action to execute.
     * @param args                  JSONArry of arguments for the plugin.
     * @param callbackS=Context     The callback id used when calling back into JavaScript.
     * @return                      True if the action was valid.
     * @throws JSONException 
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        
        if (action.equals("start")) {
            this.TYPE_SENSOR = args.getString(0);
            this.start();
        }
        else if (action.equals("stop")) {
            this.stop();
        }
        else if (action.equals("getState")) {
            // If not running, then this is an async call, so don't worry about waiting
            if (this.status != Sensors.RUNNING) {
                int r = this.start();
                if (r == Sensors.ERROR_FAILED_TO_START) {
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.IO_EXCEPTION, Sensors.ERROR_FAILED_TO_START));
                    return true;
                }
                // Set a timeout callback on the main thread.
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Sensors.this.timeout();
                    }
                }, 2000);
            }
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, getValue()));
        } else {
            // Unsupported action
            return false;
        }
        return true;
    }

    /**
     * Called when listener is to be shut down and object is being destroyed.
     */
    public void onDestroy() {
        this.stop();
    }

    /**
     * Called when app has navigated and JS listeners have been destroyed.
     */
    public void onReset() {
        this.stop();
    }

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------

    /**
     * Start listening for compass sensor.
     *
     * @return          status of listener
     */
    public int start() {

        // If already starting or running, then just return
        if ((this.status == Sensors.RUNNING) || (this.status == Sensors.STARTING)) {
            return this.status;
        }

        // Get proximity sensor from sensor manager
        @SuppressWarnings("deprecation")
        List<Sensor> list = new ArrayList<Sensor>();
        if(this.TYPE_SENSOR.equals("PROXIMITY")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
        } else if(this.TYPE_SENSOR.equals("ACCELEROMETER")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        } else if(this.TYPE_SENSOR.equals("GRAVITY")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_GRAVITY);
        } else if(this.TYPE_SENSOR.equals("GYROSCOPE")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        } else if(this.TYPE_SENSOR.equals("GYROSCOPE_UNCALIBRATED")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        } else if(this.TYPE_SENSOR.equals("LINEAR_ACCELERATION")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
        } else if(this.TYPE_SENSOR.equals("ROTATION_VECTOR")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_ROTATION_VECTOR);
        } else if(this.TYPE_SENSOR.equals("SIGNIFICANT_MOTION")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_SIGNIFICANT_MOTION);
        } else if(this.TYPE_SENSOR.equals("STEP_COUNTER")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER);
        } else if(this.TYPE_SENSOR.equals("STEP_DETECTOR")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_STEP_DETECTOR);
        } else if(this.TYPE_SENSOR.equals("GAME_ROTATION_VECTOR")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_GAME_ROTATION_VECTOR);
        } else if(this.TYPE_SENSOR.equals("GEOMAGNETIC_ROTATION_VECTOR")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        } else if(this.TYPE_SENSOR.equals("MAGNETIC_FIELD")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        } else if(this.TYPE_SENSOR.equals("MAGNETIC_FIELD_UNCALIBRATED")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        } else if(this.TYPE_SENSOR.equals("ORIENTATION")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        } else if(this.TYPE_SENSOR.equals("AMBIENT_TEMPERATURE")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE);
        } else if(this.TYPE_SENSOR.equals("LIGHT")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_LIGHT);
        } else if(this.TYPE_SENSOR.equals("PRESSURE")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_PRESSURE);
        } else if(this.TYPE_SENSOR.equals("RELATIVE_HUMIDITY")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_RELATIVE_HUMIDITY);
        } else if(this.TYPE_SENSOR.equals("TEMPERATURE")){
            list = this.sensorManager.getSensorList(Sensor.TYPE_TEMPERATURE);
        }

        // If found, then register as listener
        if (list != null && list.size() > 0) {
            this.mSensor = list.get(0);
            this.sensorManager.registerListener(this, this.mSensor, SensorManager.SENSOR_DELAY_NORMAL);
            this.lastAccessTime = System.currentTimeMillis();
            this.setStatus(Sensors.STARTING);
        } else {
            this.setStatus(Sensors.ERROR_FAILED_TO_START);
        }

        return this.status;
    }

    /**
     * Stop listening to compass sensor.
     */
    public void stop() {
        if (this.status != Sensors.STOPPED) {
            this.sensorManager.unregisterListener(this);
        }
        this.setStatus(Sensors.STOPPED);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    /**
     * Called after a delay to time out if the listener has not attached fast enough.
     */
    private void timeout() {
        if (this.status == Sensors.STARTING) {
            this.setStatus(Sensors.ERROR_FAILED_TO_START);
            if (this.callbackContext != null) {
                this.callbackContext.error("Compass listener failed to start.");
            }
        }
    }

    /**
     * Sensor listener event.
     *
     * @param SensorEvent event
     */
    public void onSensorChanged(SensorEvent event) {
        try {
            JSONArray value = new JSONArray();
            for(int i=0;i<event.values.length;i++){
                
                    value.put(Float.parseFloat(event.values[i]+""));
                
            }

            this.timeStamp = System.currentTimeMillis();
            this.value = value;
            this.setStatus(Sensors.RUNNING);

            // If proximity hasn't been read for TIMEOUT time, then turn off sensor to save power 
            if ((this.timeStamp - this.lastAccessTime) > this.TIMEOUT) {
                this.stop();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get status of sensor.
     *
     * @return          status
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Get the most recent distance. 
     *
     * @return          distance 
     */
    public JSONArray getValue() {
        this.lastAccessTime = System.currentTimeMillis();
        return this.value;
    }


    /**
     * Set the timeout to turn off sensor if getValue() hasn't been called.
     *
     * @param timeout       Timeout in msec.
     */
    public void setTimeout(long timeout) {
        this.TIMEOUT = timeout;
    }

    /**
     * Get the timeout to turn off sensor if getValue() hasn't been called.
     *
     * @return timeout in msec
     */
    public long getTimeout() {
        return this.TIMEOUT;
    }

    /**
     * Set the status and send it to JavaScript.
     * @param status
     */
    private void setStatus(int status) {
        this.status = status;
    }
}
