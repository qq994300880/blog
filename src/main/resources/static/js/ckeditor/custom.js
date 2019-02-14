window.onload = function () {
    //禁用复制功能
    document.oncontextmenu = new Function("event.returnValue=false");
    document.onselectstart = new Function("event.returnValue=false");
    //
    var min = document.getElementById("blog-input");
    var content = document.getElementById("editor");
    var date = min.value;
    // alert(date);
    content.innerHTML = date;
    min.remove();
    DecoupledEditor
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
            // ckfinder: {
            //     uploadUrl: 'editor-upload'
            // }
        })
        .then(function (editor) {
            //添加样式到#toolbar-container
            // const toolbarContainer = document.querySelector('#toolbar-container');
            // toolbarContainer.appendChild(editor.ui.view.toolbar.element);
            //开启只读模式
            editor.isReadOnly = true;
            // editor.updateSourceElement(edate);
        })
        .catch(function (error) {
            console.error(error);
        });
}