function ValidateInput(event) {
  var char = event.which || event.keyCode;
  // Allow only alphabets and space
  if (!(char >= 65 && char <= 90) && // A-Z
      !(char >= 97 && char <= 122) && // a-z
      char !== 32) { // space
    event.preventDefault();
    return false;
  }
}