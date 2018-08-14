$(document).ready(function () {
    // getAllProjectGroups();
    getAllProjectGroups();
});

function getAllProjectGroups() {
    $.get("/AndCode/v1/projectTypeGroup/getAllProjectTypeGroup?pid=0",
        function (data, status) {
            if (status === 'success') {
                //填充表格
                var c = "";
                $.each(data.data, function (n, value) {
                    c += getContent(value);
                });
                $("#group-menu").html(c);
            }

        });
}

function getContent(data) {
    var c = "";
    c += '<li class="has-children">' +
        '<input type="checkbox" name="' + data.groupName + '" id="' + data.groupId + '">' +
        '<label for="' + data.groupId + '">' + data.groupName + '</label>';
    c += '<ul>';
    if (data.childs.length > 0 || data.types.length > 0) {
        if (data.childs.length > 0) {
            $.each(data.childs, function (n, value) {
                c += getContent(value);
            });
        }
        if (data.types.length > 0) {
            $.each(data.types, function (n, value) {
                c += getChilds(value);
            });
        }
    }
    c += '</ul>';
    c += '</li>';
    return c;
}

function getChilds(data) {
    var c = "";
    c +=
        '<li><a href="javascript:void(0);" onclick="getProjectList(\''+data.typeId+'\')">' + data.typeName + '</a></li>';
    return c;
}

function getProjectList(typeId) {
    $.get("/AndCode/v1/project/getAllProjectByType?typeId="+typeId,
        function (data, status) {
            if (status === 'success') {
                //填充表格
                var c = "";
                $.each(data.data, function (n, value) {
                    c += getProjectItem(value);
                });
                $("#img-box").html(c);
                baguetteBox.run('.tz-gallery');
            }

        });
}

function getProjectItem(data) {
    var c = "";
    $.each(data.imgs, function (n, value) {
        c += '<div class="col-sm-6 col-md-4">' +
            '<h3>'+value.title+"-"+value.content+'</h3>' +
            '<p>'+"（一口价:"+data.price+")"+'<a href="https://zhouzhuo810.taobao.com" target="_blank"> 下单请点击这里>> </a></p>' +
            '<div class="thumbnail">' +
            '<a class="lightbox" href="'+value.imgPath+'">' +
            '<img src="'+value.imgPath+'"alt="'+value.title+'">' +
            '</a>' +
            '</div>' +
            '</div>';
    });
    return c;

}