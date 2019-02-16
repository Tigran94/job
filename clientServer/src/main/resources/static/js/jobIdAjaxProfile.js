$(document).ready(function () {

    $("a[name=jobID]").click(function () {

        //stop submit the form, we will post it manually.
        fire_ajax_submit(this);
    });

});


function fire_ajax_submit(target){
    var job = $(target).attr("value");
    $.ajax({
        type: "GET",
        url: "/profile/" + job,
        success: function(post){
            console.log(JSON.stringify(post));
            $("#title").html("").append("Title: " + post.title);
            $("#description").html("").append("Description: " + post.description);
            $("#time").html("").append("Work Time: " + post.workTime);
            $("#salary").html("").append("Salary: " + post.salary);
            $("#type").html("").append("Type: " + post.type);
            $("#email").html("").append("Email: " + post.email);
            $("#company").html("").append("Company: " + post.company);
            $("#expDate").html("").append("Expiration Date: " + post.expDate);
            $("#postDate").html("").append("Post Date:" + post.postDate);
        }
    })
}
