window.onload = function () {
    var delFrom = document.getElementById("delete-form");
    var deleteList = document.getElementsByClassName("list-delete");
    for (var i = 0; i < deleteList.length; i++) {
        deleteList[i].onclick = function () {
            delFrom.setAttribute("action", this.getAttribute("address"));
        }
    }
};