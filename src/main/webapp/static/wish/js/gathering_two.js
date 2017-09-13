$(function(){
    function zShow(content, title, divid,width,height){
        var dialog = artDialog({
            id: 'zShow',
            title: title,
            fixed: true,
            lock: true,
            width:width,
            height:height,
            content: content,
            yesFn: false
        });

        $(divid+" .close").click(function(){
            dialog.close();
        });
    }

    var content = $('#J-hi-content').html();

    $('#J-pop').on('click',function(){
        zShow(content,'秒收贷款       协议','#J-pop',600,200);
        $('.aui_state_focus .aui_content').css({
            'height': '400px',
            'overflow-y': 'auto'
        })
    })


})