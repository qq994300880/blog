var picFile = document.getElementById("picFile");
var adr = picFile.getAttribute("addr");
var noP = picFile.getAttribute("noPic");
var moreSi = picFile.getAttribute("moreSize");
var failed = picFile.getAttribute("upFailed");

picFile.onchange = function () {
    //获取文件对象
    var file = picFile.files[0];
    //打印文件对象
    // console.log(file);
    //文件初始名
    // console.log(file.name);
    //读取到的是字节数byte
    // console.log(file.size);
    //文件类型
    // console.log(file.type);
    //网络工具包相对路径
    // console.log(file.webkitRelativePath);

    //先判断文件是否为图片类型
    //验证正则表达式
    var regex = /^image\/(gif|jpg|jpeg|png|gif|jpg|png)$/;
    if (!regex.test(file.type)) {
        alert(noP);
    } else {
        //验证图片大小:限制2M
        if (file.size > 1024 * 1024) {
            alert(moreSi);
        } else {
            $.ajax({
                url: adr,
                type: "POST",
                cache: false,
                data: new FormData($("#picForm")[0]),
                processData: false,
                contentType: false,
                // dataType: "json",
                success: function (data) {
                    if (null != data) {
                        $("#topic-picSrc").val(data);
                        $("#topic-url").attr("src", data);
                    } else {
                        alert(failed);
                    }
                }
            });
        }
    }
};