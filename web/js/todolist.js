$(document).ready(function () {
    //还原用户信息
    var username = localStorage.getItem("username");
    var userId = localStorage.getItem("userId");
    var pic = localStorage.getItem("userPic");
    var copyContent;
    if (userId === null || userId === "") {
        doExitLogin();
    } else {
        $("#tv-user-name").text(username);
        $("#box-user-info").show();
        $("#form-login").hide();
        getQrcodeList(userId, 1);
    }
    $('#et-pswd-edit').password()
        .password('focus')
        .on('show.bs.password', function (e) {
            $('#eventLog').text('On show event');
            $('#methods').prop('checked', true);
        }).on('hide.bs.password', function (e) {
        $('#eventLog').text('On hide event');
        $('#methods').prop('checked', false);
    });
    $('#et-new-pswd-edit').password()
        .password('focus')
        .on('show.bs.password', function (e) {
            $('#eventLog').text('On show event');
            $('#methods').prop('checked', true);
        }).on('hide.bs.password', function (e) {
        $('#eventLog').text('On hide event');
        $('#methods').prop('checked', false);
    });
    $('#et-pswd').password()
        .password('focus')
        .on('show.bs.password', function (e) {
            $('#eventLog').text('On show event');
            $('#methods').prop('checked', true);
        }).on('hide.bs.password', function (e) {
        $('#eventLog').text('On hide event');
        $('#methods').prop('checked', false);
    });
    //编辑按钮
    $(document).on("click", ".btn-edit-todo", function () {
        var colDbId = $(this).parent().parent().find(".db-id");
        localStorage.setItem("edit-todo-id", colDbId.text());
        var content = $(this).parent().parent().find(".content").text();
        var note = $(this).parent().parent().find(".note").text();
        var handlePersonId = $(this).parent().parent().find(".handle-person-id").text();
        var isFinish = $(this).parent().parent().find(".is-finish").text();
        var pb = $(this).parent().parent().find(".pb-value").text();
        var progress = parseInt(pb) / 10;
        $("#et-todo-content-edit").val(content);
        $("#et-note-edit").val(note);
        $("#cb-finish-edit").prop("checked", isFinish === "true");
        $("#select-progress-edit").selectpicker('val', progress + "");
        getUserListEdit(userId, handlePersonId);
    });
    //发送邮件按钮
    $(document).on("click", ".btn-email-todo", function () {
        var colDbId = $(this).parent().parent().find(".db-id").text();
        localStorage.setItem("send-email-todo-id", colDbId);
    });
    //确认发送邮件按钮
    $("#btn-email-ok").click(function () {
        var emailId = localStorage.getItem("send-email-todo-id");
        sendEmail(emailId);
    });

    //复制今天待办功能
    var clipboardToday = new ClipboardJS('.btn-copy-today-todo', {
        text: function (trigger) {
            return getTodayTodoList();
        }
    });
    clipboardToday.on('success', function (e) {
        showOkMsg("复制成功！")
    });
    clipboardToday.on('error', function (e) {
        showHintMsg("复制失败！")
    });
    //复制功能
    var clipboard = new ClipboardJS('.btn-copy-todo', {
        text: function (trigger) {
            var content = $(trigger).parent().parent().find(".content").text();
            var note = $(trigger).parent().parent().find(".note").text();
            var pb = $(trigger).parent().parent().find(".pb-value").text();
            return content + ":" + note + "(完成进度:" + pb + "%)";
        }
    });
    clipboard.on('success', function (e) {
        showOkMsg("复制成功！")
    });
    clipboard.on('error', function (e) {
        showHintMsg("复制失败！")
    });

    //登录按钮点击
    $("#btn-login").click(function () {
        doLogin();
    });
    //注销按钮点击
    $("#btn-unregister").click(function () {
        doExitLogin();
    });
    //刷新按钮点击
    $("#btn-refresh").click(function () {
        var userId = localStorage.getItem("userId");
        getQrcodeList(userId, 1);
    });
    //删除按钮点击
    $("#btn-delete").click(function () {
        doDelete();
    });
    //全选监听
    $("#checkbox-all").change(function () {
        var isCheck = $(this).is(':checked');
        $(".styled").prop("checked", isCheck);
    });

    //添加按钮点击
    $("#btn-add-qrcode").click(function () {
        $("#et-todo-content-add").val("");
        $("#et-note-add").val("");
        getUserListAdd(userId);
    });

    //添加对话框保存按钮
    $("#btn-add-save").click(function () {
        addTodoList();
    });
    //编辑对话框保存按钮
    $("#btn-edit-save").click(function () {
        var todoId = localStorage.getItem("edit-todo-id");
        editTodoList(todoId);
    });

    //用户名点击
    $("#tv-user-name").click(function () {
        var username = localStorage.getItem("username");
        var userId = localStorage.getItem("userId");
        var phone = localStorage.getItem("phone");
        var email = localStorage.getItem("email");
        var sex = localStorage.getItem("sex");

        $("#et-user-name-edit").val(username);
        $("#et-phone-edit").val(phone);
        $("#et-pswd-edit").val("");
        $("#et-new-pswd-edit").val("");
        $("#et-email-edit").val(email);
        $("#select-sex-edit").selectpicker('val', sex);
    });

    //修改用户信息
    $("#btn-user-ok").click(function () {
        updateUserInfo();
    });

});

function updateUserInfo() {
    var userId = localStorage.getItem("userId");
    var username = $("#et-user-name-edit").val();
    var phone = $("#et-phone-edit").val();
    var pswd = $("#et-pswd-edit").val();
    var pswdNew = $("#et-new-pswd-edit").val();
    var email = $("#et-email-edit").val();
    var sex = $("#select-sex-edit").val();
    $.post("/ZzApiDoc/v1/user/updateUserInfo", {
            userId: userId,
            phone: phone,
            oldPassword: pswd,
            password: pswdNew,
            name: username,
            sex: sex,
            email: email
        },
        function (data, status) {
            if (status === 'success') {
                if (data.code === 0) {
                    //error msg
                    showHintMsg(data.msg);
                } else {
                    showOkMsg(data.msg);
                    //fill data
                    doExitLogin();
                }
            }

        });
}

/*用户登录*/
function doLogin() {
    var username = $("#et-username").val();
    var password = $("#et-password").val();
    if (username === "" || password === "") {
        //error msg
        showHintMsg("用户名或密码不能为空");
        return;
    }
    $.post("/ZzApiDoc/v1/user/userLogin", {
            phone: username,
            password: password
        },
        function (data, status) {
            if (status === 'success') {
                if (data.code === 0) {
                    //error msg
                    showHintMsg(data.msg);
                } else {
                    showOkMsg(data.msg);
                    //fill data
                    //隐藏登录框
                    $("#form-login").hide();
                    //显示用户名
                    $("#tv-user-name").text(data.data.name);
                    $("#box-user-info").show();
                    //保存用户信息
                    localStorage.setItem("username", data.data.name);
                    localStorage.setItem("userId", data.data.id);
                    localStorage.setItem("userPic", data.data.pic);
                    localStorage.setItem("phone", data.data.phone);
                    localStorage.setItem("email", data.data.email);
                    localStorage.setItem("sex", data.data.sex);
                    getQrcodeList(data.data.id, 1);
                }
            }

        });
}

/*注销*/
function doExitLogin() {
    localStorage.clear();
    location.href = "home";
}

/*获取项目列表*/
function getQrcodeList(userId, index) {
    if (userId === null || userId.length === 0) {
        $("#box-user-info").hide();
        $("#form-login").show();
        showHintMsg("登录已过期，请重新登录");
        return;
    }
    $.get("/ZzApiDoc/v1/todoList/getAllMyTodoListWeb?userId=" + userId + "&page=" + index,
        function (data, status) {
            if (status === 'success') {
                //填充表格
                var c = "";
                $.each(data.rows, function (n, value) {
                    c += '<tr>' +
                        '<td><div class="checkbox"><input type="checkbox" id="checkbox' + n + '" class="styled"><label for="checkbox' + n + '">选择</label></div></td>' +
                        '<td class="db-id hide">' + value.id + '</td>' +
                        '<td class="is-finish hide">' + value.isFinish + '</td>' +
                        '<td class="pb-value hide">' + value.progress + '</td>' +
                        '<td class="handle-person-id hide">' + value.handlePersonId + '</td>' +
                        '<td class="content" '+((value.handlePersonId === userId && value.isFinish === "false") ? 'bgcolor="#5cb85c"':'')+'>' + value.content + '</td>' +
                        '<td class="note" '+((value.handlePersonId === userId && value.isFinish === "false") ? 'bgcolor="#5cb85c"':'')+'>' + value.note + '</td>' +
                        '<td class="handle-person">' + value.handlePersonName + '</td>' +
                        '<td>' + '<div class="progress" style="width: 150px;"><div class="progress-bar progress-bar-success progress-bar-striped active"' +
                        ' role="progressbar"aria-valuenow="' + value.progress + '" aria-valuemin="0" aria-valuemax="100" style="width: ' + value.progress + '%"> ' +
                        '<span class="sr-only">' + value.progress + '% Complete (success)</span> </div> </div>' + '</td>' +
                        '<td>' + (value.isFinish === "true" ? '是' : '否') + '</td>' +
                        '<td>' + value.createUserName + '</td>' +
                        '<td>' + value.createTime + '</td>' +
                        '<td><button type="button" class="btn-edit-todo btn btn-primary"  data-toggle="modal" data-target="#editModel">' +
                        '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑</button></td>' +
                        '<td><button type="button" class="btn-copy-todo btn btn-primary">' +
                        '<span class="glyphicon glyphicon-copy" aria-hidden="true"></span> 复制</button></td>' +
                        '<td><button type="button" class="btn-email-todo btn btn-primary" data-toggle="modal" data-target="#emailModal">' +
                        '<span class="glyphicon glyphicon-send" aria-hidden="true"></span> 发送</button></td>' +
                        '</tr>';
                });
                $("#todo-list").html(c);

                // if($("#page-indicator").data("twbs-pagination")){
                //     $("#page-indicator").twbsPagination("destroy");
                // }
                var indicator = $("#page-indicator");
                indicator.twbsPagination("destroy");
                //分页绑定
                indicator.twbsPagination({
                    totalPages: data.totalPage,
                    visiblePages: 10,
                    onPageClick: function (event, page) {
                        //全选取消
                        $("#checkbox-all").prop("checked", false);
                        //重载数据
                        var mid = localStorage.getItem("userId");
                        justUpdateList(mid, page);
                    }
                });
            }

        });
    return false;
}

/*获取项目列表*/
function justUpdateList(userId, index) {
    if (userId === null || userId.length === 0) {
        $("#box-user-info").hide();
        $("#form-login").show();
        showHintMsg("登录已过期，请重新登录");
        return;
    }
    $.get("/ZzApiDoc/v1/todoList/getAllMyTodoListWeb?userId=" + userId + "&page=" + index,
        function (data, status) {
            if (status === 'success') {
                //填充表格
                var c = "";
                $.each(data.rows, function (n, value) {
                    c += '<tr>' +
                        '<td><div class="checkbox"><input type="checkbox" id="checkbox' + n + '" class="styled"><label for="checkbox' + n + '">选择</label></div></td>' +
                        '<td class="db-id hide">' + value.id + '</td>' +
                        '<td class="is-finish hide">' + value.isFinish + '</td>' +
                        '<td class="pb-value hide">' + value.progress + '</td>' +
                        '<td class="handle-person-id hide">' + value.handlePersonId + '</td>' +
                        '<td class="content" '+((value.handlePersonId === userId && value.isFinish === "false") ? 'bgcolor="#5cb85c"':'')+'>' + value.content + '</td>' +
                        '<td class="note" '+((value.handlePersonId === userId && value.isFinish === "false") ? 'bgcolor="#5cb85c"':'')+'>' + value.note + '</td>' +
                        '<td class="handle-person">' + value.handlePersonName + '</td>' +
                        '<td>' + '<div class="progress" style="width: 150px;"><div class="progress-bar progress-bar-success progress-bar-striped active"' +
                        ' role="progressbar"aria-valuenow="' + value.progress + '" aria-valuemin="0" aria-valuemax="100" style="width: ' + value.progress + '%"> ' +
                        '<span class="sr-only">' + value.progress + '% Complete (success)</span> </div> </div>' + '</td>' +
                        '<td>' + (value.isFinish === "true" ? '是' : '否') + '</td>' +
                        '<td>' + value.createUserName + '</td>' +
                        '<td>' + value.createTime + '</td>' +
                        '<td><button type="button" class="btn-edit-todo btn btn-primary"  data-toggle="modal" data-target="#editModel">' +
                        '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑</button></td>' +
                        '<td><button type="button" class="btn-copy-todo btn btn-primary">' +
                        '<span class="glyphicon glyphicon-copy" aria-hidden="true"></span> 复制</button></td>' +
                        '<td><button type="button" class="btn-email-todo btn btn-primary" data-toggle="modal" data-target="#emailModal">' +
                        '<span class="glyphicon glyphicon-send" aria-hidden="true"></span> 发送</button></td>' +
                        '</tr>';
                });
                $("#todo-list").html(c);
            }

        });
    return false;
}

/*获取选中的行数*/
function getChooseRowsCount() {
    var count = 0;
    var trs = $("#todo-list").find("tr");
    for (var i = 0; i < trs.length; i++) {
        var row = trs.eq(i);
        var cb = row.find(":checkbox");
        var isCheck = cb.is(':checked');
        if (isCheck) {
            count++;
        }
    }
    return count;
}
/*获取选中行的数据库id，多个用逗号隔开*/
function getChooseRowsDbIds() {
    var ids = "";
    var trs = $("#todo-list").find("tr");
    for (var i = 0; i < trs.length; i++) {
        var row = trs.eq(i);
        var cb = row.find(":checkbox");
        var isCheck = cb.is(':checked');
        if (isCheck) {
            var colDbId = row.find("td.db-id");
            ids += colDbId.text();
            ids += ",";
        }
    }
    if (getChooseRowsCount() > 0) {
        ids = ids.substr(0, ids.length - 1);
    }
    return ids;
}
/**
 * 发送邮件
 */
function sendEmail(todoId) {
    var userId = localStorage.getItem("userId");
    if (userId === null || userId.length === 0) {
        $("#box-user-info").hide();
        $("#form-login").show();
        showHintMsg("登录已过期，请重新登录");
        return;
    }
    $.post("/ZzApiDoc/v1/todoList/sendEmail", {
            userId: userId,
            todoId: todoId
        },
        function (data, status) {
            if (status === 'success') {
                if (data.code === 0) {
                    //error msg
                    showHintMsg(data.msg);
                } else {
                    showOkMsg(data.msg);
                }
            }

        });
}

/**
 * 删除选中行
 */
function doDelete() {
    var userId = localStorage.getItem("userId");
    if (userId === null || userId.length === 0) {
        $("#box-user-info").hide();
        $("#form-login").show();
        showHintMsg("登录已过期，请重新登录");
        return;
    }
    $.post("/ZzApiDoc/v1/todoList/deleteTodoListWeb", {
            userId: userId,
            ids: getChooseRowsDbIds()
        },
        function (data, status) {
            if (status === 'success') {
                if (data.code === 0) {
                    //error msg
                    showHintMsg(data.msg);
                } else {
                    showOkMsg(data.msg);
                    //重新加载数据
                    getQrcodeList(userId, 1);
                }
            }

        });
}

/**
 * 编辑
 */
function editTodoList(todoId) {
    var handlePersonId = $("#select-handle-person-edit").val();
    var content = $("#et-todo-content-edit").val();
    var note = $("#et-note-edit").val();
    var isFinish = $("#cb-finish-edit").is(':checked');
    var progress = $("#select-progress-edit").val();
    var progressInt = parseInt(progress) * 10;
    var userId = localStorage.getItem("userId");
    $.post("/ZzApiDoc/v1/todoList/updateTodoList", {
            todoId: todoId,
            userId: userId,
            handlePersonId: handlePersonId,
            content: content,
            note: note,
            progress: progressInt,
            isFinish: (isFinish ? 1 : 0)
        },
        function (data, status) {
            if (status === 'success') {
                if (data.code === 0) {
                    //error msg
                    showHintMsg(data.msg);
                } else {
                    showOkMsg(data.msg);
                    //重新加载数据
                    getQrcodeList(userId, 1);
                }
            }

        });
}

/**
 * 添加
 */
function addTodoList() {
    var handlePersonId = $("#select-handle-person-add").val();
    var content = $("#et-todo-content-add").val();
    var note = $("#et-note-add").val();
    var progress = $("#select-progress-add").val();
    var progressInt = parseInt(progress) * 10;
    var isFinish = $("#cb-finish-add").is(':checked');
    var userId = localStorage.getItem("userId");
    $.post("/ZzApiDoc/v1/todoList/addTodoList", {
            userId: userId,
            handlePersonId: handlePersonId,
            content: content,
            note: note,
            progress: progressInt,
            isFinish: (isFinish ? 1 : 0)
        },
        function (data, status) {
            if (status === 'success') {
                if (data.code === 0) {
                    //error msg
                    showHintMsg(data.msg);
                } else {
                    showOkMsg(data.msg);
                    //重新加载数据
                    getQrcodeList(userId, 1);
                }
            }

        });
}


function getUserListAdd(userId) {
    $.get("/ZzApiDoc/v1/user/getUserList?userId=" + userId,
        function (data, status) {
            if (status === 'success') {
                if (data.code === 0) {
                    showHintMsg(data.msg);
                } else {
                    //填充选项
                    var sel = $("#select-handle-person-add");
                    sel.empty();
                    var firstOne = "";
                    $.each(data.data, function (n, value) {
                        sel.append(
                            "<option value='" + value.id + "'>" + value.name + "</option>"
                        );
                        if (n === 0) {
                            firstOne = value.id;
                        }
                    });
                    sel.selectpicker('val', firstOne);
                    sel.selectpicker('refresh');
                }
            }

        });
}

function getUserListEdit(userId, defValue) {
    $.get("/ZzApiDoc/v1/user/getUserList?userId=" + userId,
        function (data, status) {
            if (status === 'success') {
                if (data.code === 0) {
                    showHintMsg(data.msg);
                } else {
                    //填充选项
                    var sel = $("#select-handle-person-edit");
                    sel.empty();
                    var firstOne = "";
                    var sameOne = "";
                    $.each(data.data, function (n, value) {
                        sel.append(
                            "<option value='" + value.id + "'>" + value.name + "</option>"
                        );
                        if (n === 0) {
                            firstOne = value.id;
                        }
                        if (defValue === value.id) {
                            sameOne = value.id;
                        }
                    });
                    if (sameOne === null || sameOne === "") {
                        sel.selectpicker('val', firstOne);
                    } else {
                        sel.selectpicker('val', sameOne);
                    }
                    sel.selectpicker('refresh');
                }
            }

        });
}

function getTodayTodoList() {
    var userId = localStorage.getItem("userId");
    if (userId === null || userId.length === 0) {
        $("#box-user-info").hide();
        $("#form-login").show();
        showHintMsg("登录已过期，请重新登录");
        return;
    }
    var sb = "";
    $.ajax({
        type: "get",
        url: "/ZzApiDoc/v1/todoList/getAllMyTodoListToday",
        data: "userId=" + userId,
        async: false,
        success: function (data) {
            if (data.code === 0) {
                //error msg
                showHintMsg(data.msg);
            } else {
                //重新加载数据
                $.each(data.data, function (n, value) {
                    sb += ((n + 1) + ".");
                    sb += value.content;
                    sb += ":";
                    sb += value.note;
                    sb += "( 完成进度：" + value.progress + " %)";
                    sb += "\n";
                });
            }
        }
    });
    return sb;

}

/**
 * 清空hint
 */
function clearHint() {
    $("#row-hint").html("");
}