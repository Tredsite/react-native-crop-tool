var RNCropTool = require('react-native').NativeModules.RNCropTool;

module.exports = {
  crop: function(imagePath) {
    return RNCropTool.crop(imagePath);
  }
};
