window.onload = function () {
    var oneDate = document.getElementById("art-content").value;
    var editorContent = document.getElementById("editor");
    if (null != oneDate && "" !== oneDate) {
        editorContent.innerHTML = oneDate;
    }
    ;
    let one;
    BalloonEditor
        .create(document.querySelector('#editor'), {
            language: 'zh-cn',//设置语言
            //设置自定义UI
            // toolbar: ['heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote'],
            // heading: {
            //     options: [
            //         {model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph'},
            //         {model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1'},
            //         {model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2'}
            //     ]
            // }
            //图片上传路径
            ckfinder: {
                uploadUrl: "http://www.ydblog.com/blog/manager/ck/upload"
            }
        })
        .then(function (editor) {
            //添加样式到#toolbar-container
            const toolbarContainer = document.querySelector('#toolbar-container');
            toolbarContainer.appendChild(editor.ui.view.toolbar.element);
            //开启只读模式
            // editor.isReadOnly = true;
            // editor.updateSourceElement(edate);
            one = editor;
        })
        .catch(function (error) {
            console.error(error);
        });
    //测试获取数据
    // var s = document.getElementById("submit");
    // s.onclick = function () {
    //     const data = one.getData();
    //     console.info(data);
    // }

    //文章名输入框失去焦点后事件
    var titleIn = document.getElementById("art-title");
    var titleView = document.getElementById("art-view");
    titleIn.onchange = function () {
        var tit = titleIn.value;
        var tit2 = tit.trim(" ");
        if (null != tit && "" !== tit2)
            titleView.innerText = tit;
    };
    //选项框被点击，则去除selected属性
    // var topSel = document.getElementById("topic-select");
    // var topOpts = document.getElementsByClassName("topic-option");
    // topSel.onfocus = function () {
    //     for (var i = 0; i < topOpts.length; i++) {
    //         var topOpt = topOpts[i];
    //         topOpt.removeAttribute("selected");
    //         topOpt.onclick = function () {
    //             topOpt.setAttribute("selected", "selected");
    //         }
    //     }
    // };
    //文本框失去焦点保存
    var editor = document.getElementById("editor");
    var artContent = document.getElementById("art-content");
    editor.onblur = function () {
        const data = one.getData();
        // console.info(data);
        // alert(date);
        var data2 = data.trim(" ");
        if (null != data2 && "" !== data2) {
            artContent.value = data;
        }
    };
    //编辑框失去焦点保存
    var toolbarContainer = document.getElementById("toolbar-container");
    toolbarContainer.onblur = function () {
        const data = one.getData();
        var data2 = data.trim(" ");
        if (null != data2 && "" !== data2) {
            artContent.value = data;
        }
    };
    //提交表单,判断
    var bSubmit = document.getElementById("btn-submit");
    var bInfo = document.getElementById("art-info");
    var sForm = document.getElementById("art-form");
    bSubmit.onclick = function () {
        //最后一次确认值传给Hidden
        const contentData = one.getData();
        artContent.value = contentData;
        //判断标题是否为空
        var titleData = titleIn.value;
        var titleData2 = titleData.trim(" ");
        // console.info("文章名:" + titleData2);
        titleIn.value = titleData2;
        if (null == titleData2 || "" === titleData2) {
            alert("文章名称不能为空!");
        } else {
            //判断介绍信息是否为空
            var infoData = bInfo.value;
            var infoData2 = infoData.trim(" ");
            // console.info("文章介绍:" + infoData2);
            bInfo.value = infoData2;
            if (null == infoData2 || "" === infoData2) {
                alert("文章介绍不能为空!");
            } else {
                var length = contentData.length;
                if (length < 500) {
                    if (confirm("内容过少,确定要保存吗")) {
                        sForm.submit();
                    }
                } else {
                    sForm.submit();
                }
            }
        }
    }
};