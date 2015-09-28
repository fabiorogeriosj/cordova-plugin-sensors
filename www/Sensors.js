function sensors() {
}

sensors.prototype.get = function (options, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "AndroidSensors", "get", [options]);
};

sensors.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.sensors = new sensors();

  return window.plugins.sensors;
};

cordova.addConstructor(sensors.install);