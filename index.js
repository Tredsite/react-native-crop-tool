var RNCropTool = require('react-native').NativeModules.RNCropTool;

module.exports = {
  crop: function(imagePath, hFactor, wFactor) {
    return RNCropTool.crop(imagePath, hFactor, wFactor);
  }
};
