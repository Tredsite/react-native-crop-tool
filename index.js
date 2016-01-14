var React = require('react-native');
var {
  Platform,
  NativeModules,
} = React;
var RNCropTool = NativeModules.RNCropTool;

module.exports = {
  crop: function(imagePath, hFactor, wFactor) {
    if (Platform.OS === 'android') {
      return new Promise(function(resolve, reject) {
        RNCropTool.crop(imagePath, hFactor, wFactor, function(err, data) {
          if (err) {
            reject(err);
          } else {
            resolve(data);
          }
        });
      });
    } else {
      return RNCropTool.crop(imagePath, hFactor, wFactor);
    }
  }
};

