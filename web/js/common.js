/**
 * Created by zz on 2018/5/18.
 */
/**
 * 拼接提示html
 * @param msg
 * @returns {string}
 */
function showHintMsg(msg) {
    // $("#row-hint").html('<div class="alert alert-warning" id="tv-hint"> <a href="#" class="close" data-dismiss="alert"> &times;</a><label id="tv-hint-content">' + msg + '</label></div>');
    // window.setTimeout("clearHint()", 1500);//使用字符串执行方法

    $.notify(msg, {type: "info", delay: 1500});
}
/**
 * 拼接成功html
 * @param msg
 * @returns {string}
 */
function showOkMsg(msg) {
    // $("#row-hint").html('<div class="alert alert-success" id="tv-hint"> <a href="#" class="close" data-dismiss="alert"> &times;</a><label id="tv-hint-content">' + msg + '</label></div>');
    // window.setTimeout("clearHint()", 1500);//使用字符串执行方法
    $.notify(msg, {type: "success", delay: 1500});
}