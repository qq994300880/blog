var uin = document.getElementById("login-username");
var pin = document.getElementById("login-password");
var umsg = document.getElementById("umsg");
var pmsg = document.getElementById("pmsg");
uin.onchange = function () {
    if (null != umsg) {
        umsg.innerHTML = "";
    }
};
pin.onfocus = function () {
    if (null != pmsg) {
        pmsg.innerHTML = "";
    }
};