function Sensors() {
}

Sensors.prototype.get = function (options, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "AndroidSensors", "get", [options]);
};

Sensors.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.sensors = new Sensors();

  return window.plugins.sensors;
};

cordova.addConstructor(Sensors.install);