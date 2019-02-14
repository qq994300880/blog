window.onload = function () {
    var delFrom = document.getElementById("delete-form");
    var tle = document.getElementById("art-title");
    var inf = document.getElementById("art-info");
    var tim = document.getElementById("art-time");
    var cou = document.getElementById("art-count");
    var deleteList = document.getElementsByClassName("list-delete");
    for (var i = 0; i < deleteList.length; i++) {
        deleteList[i].onclick = function () {
            delFrom.setAttribute("action", this.getAttribute("address"));
            tle.innerText = this.getAttribute("title");
            inf.innerText = this.getAttribute("info");
            tim.innerText = this.getAttribute("time");
            cou.innerText = this.getAttribute("count");
        }
    }
};