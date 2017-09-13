/**
 * 警告
 * @param	{String}	消息内容
 */
artDialog.alert = function (content, closeFn) {
	return artDialog({
		title:$.i18n.prop("提示"),
		yesText:$.i18n.prop('gb'),
		id: 'Alert',
		icon: 'warning',
		fixed: true,
		lock: true,
		content: content,
		yesFn: true,
//		title:"title",
		closeFn: closeFn
	});
};

artDialog.error = function (content, closeFn) {
	return artDialog({
		title:$.i18n.prop("提示"),
		yesText:$.i18n.prop('gb'),
		id: 'Alert',
		icon: 'error',
		fixed: true,
		lock: true,
		content: content,
		yesFn: true,
		closeFn: closeFn
	}).shake();
};

artDialog.succeed = function (content, closeFn) {
	return artDialog({
		title:$.i18n.prop("提示"),
		yesText:$.i18n.prop('gb'),
		id: 'Alert',
		icon: 'succeed',
		fixed: true,
		lock: true,
		content: content,
		yesFn: true,
		closeFn: closeFn
	});
};

/**
 * 确认
 * @param	{String}	消息内容
 * @param	{Function}	确定按钮回调函数
 * @param	{Function}	取消按钮回调函数
 */
artDialog.confirm = function (content, yes, no) {
	return artDialog({
		title:$.i18n.prop("提示"),
		yesText:$.i18n.prop('qd'),
		noText:$.i18n.prop('qx'),
		id: 'Confirm',
		icon: 'question',
		fixed: true,
		lock: true,
		opacity: .1,
		content: content,
		yesFn: function (here) {
			return yes.call(this, here);
		},
		noFn: function (here) {
			return no && no.call(this, here);
		}
	});
};


/**
 * 提问
 * @param	{String}	提问内容
 * @param	{Function}	回调函数. 接收参数：输入值
 * @param	{String}	默认值
 */
artDialog.prompt = function (content, yes, value) {
	value = value || '';
	var input;
	
	return artDialog({
		title:$.i18n.prop("提示"),
		yesText:$.i18n.prop('qd'),
		noText:$.i18n.prop('qx'), 
		id: 'Prompt',
		icon: 'question',
		fixed: true,
		lock: true,
		opacity: .1,
		content: [
			'<div style="margin-bottom:5px;font-size:12px">',
				content,
			'</div>',
			'<div>',
				'<input value="',
					value,
				'" style="width:18em;padding:6px 4px" />',
			'</div>'
			].join(''),
		initFn: function () {
			input = this.DOM.content.find('input')[0];
			input.select();
			input.focus();
		},
		yesFn: function (here) {
			return yes && yes.call(this, input.value, here);
		},
		noFn: true
	});
};

artDialog.waiting=function(msg) {
	if (!msg) msg = $.i18n.prop("qsd2");
	return artDialog({
		title:$.i18n.prop("提示"),
		yesText:$.i18n.prop('qd'),
		noText:$.i18n.prop('qx'), 
		id: 'Waiting',
		fixed: true,
		lock: true,
		content: "<div class='waiting'><label></label><span>"+msg+"</span></div>",
		noFn:false
	});
};

/**
 * 短暂提示
 * @param	{String}	提示内容
 * @param	{Number}	显示时间 (默认1.5秒)
 */
artDialog.tips = function (content, time) {
	return artDialog({
		title:$.i18n.prop("提示"),
		yesText:$.i18n.prop('qd'),
		noText:$.i18n.prop('qx'), 
		id: 'Tips',
		title: false,
        noFn: false,
		fixed: true
	})
    .content('<div style="padding: 0 1em;">' + content + '</div>')
	.position('50%', 'goldenRatio')
	.time(time || 1.5);
};

artDialog.fn.shake = function (){
    var style = this.DOM.wrap[0].style,
        p = [4, 8, 4, 0, -4, -8, -4, 0],
        fx = function () {
            style.marginLeft = p.shift() + 'px';
            if (p.length <= 0) {
                style.marginLeft = 0;
                clearInterval(timerId);
            };
        };
    p = p.concat(p.concat(p));
    timerId = setInterval(fx, 13);
    return this;
    
};
