package nl.xservices.plugins.sensors;

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
public class Sensors extends CordovaPlugin implements SensorEventListener {

  private SensorManager mSensorManager;
  private Sensor mPressure;
  private CallbackContext _callbackContext; 

  public Sensors() {
    super();
  }

  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    _callbackContext = callbackContext;
    Context ctx = this.cordova.getActivity();
    mSensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);

    mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    /*
    if ("get".equals(action)) {
      JSONObject options = args.optJSONObject(0);

      
      String title = options.optString("title");
      int theme = options.optInt("androidTheme", 1);
      JSONArray buttons = options.optJSONArray("buttonLabels");

      boolean androidEnableCancelButton = options.optBoolean("androidEnableCancelButton", false);

      String addCancelButtonWithLabel = options.optString("addCancelButtonWithLabel");
      String addDestructiveButtonWithLabel = options.optString("addDestructiveButtonWithLabel");

      this.show(title, buttons, addCancelButtonWithLabel,
          androidEnableCancelButton, addDestructiveButtonWithLabel,
          theme,
          callbackContext);
      // need to return as this call is async.
      return true;
    } else if ("hide".equals(action)) {
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, -1));
      }
      
      Log.v("sensors", "exec ok!!!!!!");

      return true;
    }
    */

    return true;
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
      float value = event.values[0];
      Log.v("teste", "value: "+value);
      callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, value));
      // Do something with this sensor data.
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
      // Do something here if sensor accuracy changes.
  }

  public synchronized void get(final String sensor, final CallbackContext callbackContext) {

    final CordovaInterface cordova = this.cordova;
    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, 0));

  }
}