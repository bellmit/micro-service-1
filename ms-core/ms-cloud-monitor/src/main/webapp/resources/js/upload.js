//=======================================================================
//功能: 		上传文件的工具类[依赖于jQuery]
//author:		岳静
//e-mail: 	yuejing0129@126.com
//QQ:			503490146
//date: 		2012-07-26
//version: 	1.0
//=======================================================================
var upload = {
		/**
		 * 上传图片
		 * @param data 		: 传入的参数[为json]
		 *  data.url 		: 上传图片的完整地址[默认为'/weibo/uploadServlet']
		 *  data.fileId 	: 上传图片的file的ID名称[不传默认为'fileToUpload']
		 *  data.loading 	: 加载中图片显示的ID[不传默认为'loading']
		 *  data.param 		: 传入后台的参数的json
		 *  data.success 	: 执行成功的回调函数[回调函数内有JSON类型的值]
		 * @returns
		 */
		file : function(data) {
			if(data.fileId === undefined) data.fileId = 'fileToUpload';
			if(data.param === undefined) data.param = {};
			if(data.loading === undefined) data.loading = 'loading';
			//基于1.8之前版本的jQuery
			/*jQuery('#' + data.loading)
			.ajaxStart(function(){ confirm(1);jQuery('#' + data.loading).css('display', 'inline-block'); })
			.ajaxComplete(function(){ confirm(2);jQuery('#' + data.loading).css('display', 'none'); jQuery(this).unbind('ajaxStart'); jQuery(this).unbind('ajaxComplete'); });*/
			//基于1.8之后版本的jQuery
			$doc = $(document);
			$doc.ajaxStart(function(){ jQuery('#' + data.loading).show(); });
			$doc.ajaxComplete(function(){ 
				jQuery('#' + data.loading).hide();
				$doc.unbind('ajaxStart'); 
				$doc.unbind('ajaxComplete');
			});
			jQuery.ajaxFileUpload({
				url: data.url,
				secureuri:false,
				fileElementId: data.fileId,
				dataType: 'json',
				data: data.param,
				success: function (json, status) {
					if(json.code === 0) data.success(json);
					else if(json.code === -3) alert('请上传['+json.body.exts+']格式的文件!');
					else if(json.code === -2) alert('上传的文件不能大于' + json.body.maxSize + ',请处理后再来上传!');
					else if(json.code === -1) alert('上传文件异常!');
				},
				error: function (data, status, e) { alert(e); }
			});
			return false;
		}
};

jQuery.extend({
	createUploadIframe: function(id, uri) {
		//create frame
		var frameId = 'jUploadFrame' + id;
		var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
		if(window.ActiveXObject) {
			if(typeof uri== 'boolean'){
				iframeHtml += ' src="' + 'javascript:false' + '"';
			}
			else if(typeof uri== 'string'){
				iframeHtml += ' src="' + uri + '"';
			}
		}
		iframeHtml += ' />';
		jQuery(iframeHtml).appendTo(document.body);
		return jQuery('#' + frameId).get(0);
	},
	createUploadForm: function(id, fileElementId, data) {
		//create form
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = jQuery('<form action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');
		if(data) {
			for(var i in data) {
				jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
			}
		}
		var oldElement = jQuery('#' + fileElementId);
		var newElement = jQuery(oldElement).clone();
		jQuery(oldElement).attr('id', fileId);
		jQuery(oldElement).before(newElement);
		jQuery(oldElement).appendTo(form);
		//set attributes
		jQuery(form).css('position', 'absolute');
		jQuery(form).css('top', '-1200px');
		jQuery(form).css('left', '-1200px');
		jQuery(form).appendTo('body');
		return form;
	},
	ajaxFileUpload: function(s) {
		// TODO introduce global settings, allowing the client to modify them for all requests, not only timeout
		s = jQuery.extend({}, jQuery.ajaxSettings, s);
		var id = new Date().getTime()
		var form = jQuery.createUploadForm(id, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));
		var io = jQuery.createUploadIframe(id, s.secureuri);
		var frameId = 'jUploadFrame' + id;
		var formId = 'jUploadForm' + id;
		// Watch for a new set of requests
		if ( s.global && ! jQuery.active++ ) {
			jQuery.event.trigger( "ajaxStart" );
		}
		var requestDone = false;
		// Create the request object
		var xml = {}
		if ( s.global ) jQuery.event.trigger("ajaxSend", [xml, s]);
		// Wait for a response to come back
		var uploadCallback = function(isTimeout) {
			var io = document.getElementById(frameId);
			try {
				if(io.contentWindow) {
					xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
					xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
				}else if(io.contentDocument) {
					xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
					xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
				}
			} catch(e) {
				jQuery.handleError(s, xml, null, e);
			}
			if ( xml || isTimeout == "timeout") {
				requestDone = true;
				var status;
				try {
					status = isTimeout != "timeout" ? "success" : "error";
					// Make sure that the request was successful or notmodified
					if ( status != "error" ) {
						// process the data (runs the xml through httpData regardless of callback)
						var data = jQuery.uploadHttpData( xml, s.dataType );
						
						// If a local callback was specified, fire it and pass it the data
						if ( s.success ) s.success( data, status );
						// Fire the global callback
						if( s.global ) jQuery.event.trigger( "ajaxSuccess", [xml, s] );
					} else
						jQuery.handleError(s, xml, status);
				} catch(e) {
					status = "error";
					jQuery.handleError(s, xml, status, e);
				}
				// The request was completed
				if( s.global ) jQuery.event.trigger( "ajaxComplete", [xml, s] );
				// Handle the global AJAX counter
				if ( s.global && ! --jQuery.active ) jQuery.event.trigger( "ajaxStop" );
				// Process result
				if ( s.complete ) s.complete(xml, status);
				jQuery(io).unbind()
				setTimeout(function() {
					try {
						jQuery(io).remove();
						jQuery(form).remove();
					} catch(e) {
						jQuery.handleError(s, xml, null, e);
					}
				}, 100)
				xml = null
			}
		}
		// Timeout checker
		if ( s.timeout > 0 ) {
			setTimeout(function() {
				// Check to see if the request is still happening
				if( !requestDone ) uploadCallback( "timeout" );
			}, s.timeout);
		}
		try {
			var form = jQuery('#' + formId);
			jQuery(form).attr('action', s.url);
			jQuery(form).attr('method', 'POST');
			jQuery(form).attr('target', frameId);
			if(form.encoding) {
				jQuery(form).attr('encoding', 'multipart/form-data');
			}
			else {
				jQuery(form).attr('enctype', 'multipart/form-data');
			}
			jQuery(form).submit();

		} catch(e) {
			jQuery.handleError(s, xml, null, e);
		}
		jQuery('#' + frameId).load(uploadCallback);
		return {abort: function () {}};
	},
	uploadHttpData: function( r, type ) {
		var _data = undefined;
		var _resp = !type;
		_resp = type == "xml" || _resp ? r.responseXML : r.responseText;
		//处理google浏览器的<pre>的标签
		//if(data.indexOf('<') != -1) data = data.substring(data.indexOf('{'), data.lastIndexOf('}') + 1);
		// If the type is "script", eval it in global context
		if ( type == "script" ) jQuery.globalEval( _resp );
		// Get the JavaScript object, if JSON is used.
		else if ( type == "json" ) {
			_data = eval('(' + _resp + ')');
			/*_data = {};
			_resp = _resp.substring(_resp.indexOf('{'));
			_resp = _resp.substring(0, _resp.indexOf('}') + 1);
			_resp = '"' + _resp + '"';
			try {
				//_data = $.parseJSON( $.parseJSON( _resp ) );
				//_data = eval('(' + _resp + ')');
			} catch(e) {
				alert('相应结果转为对象异常: '+e);
			}*/
		}
		// evaluate scripts within html
		else if ( type == "html" ) jQuery("<div>").html(_resp).evalScripts();
		return _data;
	}
});