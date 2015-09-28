package com.fabiorogeriosj.plugins;

import android.os.Build;
import android.text.TextUtils;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import android.content.Context;
import android.hardware.*;


import java.util.ArrayList;
import java.util.List;

/**
 * @author fabiorogeriosj
 */
public class AndroidSensors extends CordovaPlugin { //implements SensorEventListener 

  // private SensorManager mSensorManager;
  // private Sensor mPressure;
  // private CallbackContext _callbackContext; 



  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    Context context = cordova.getActivity().getApplicationContext();

    Log.v("sensors", "exec ok!!!!!!");
    return true;
  }
  
}