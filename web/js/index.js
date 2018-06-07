$(document).ready(function () {

});


function getProjectByTypeId(typeId) {

}

function getAllProjectTypes() {
    $.get("/AndCode/v1/projectType/getAllProjectType",
        function (data, status) {
            if (status === 'success') {
                //填充表格
                var c = "";
                $.each(data.data, function (n, value) {
                    c += '<div class="col-sm-6 col-md-4">' +
                        '<div class="thumbnail">' +
                        '<a class="lightbox" href="images/park.jpg"><img src="images/park.jpg" alt="Park"></a>' +
                        '<div class="caption">' +
                        '<h3>'+value.title+'</h3>' +
                        '<p>'+value.content+'</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>			';
                });
                $("#img-box").html(c);
            }

        });
}
