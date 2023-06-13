function goToTopic(value) {
    if (value !== '') {
        document.cookie = `topicId=${value}`;
        $(location).attr("href", "topic");
    } else {
        alert("Invalid topic");
    }
}

function addTopic() {
    let value = document.getElementById("topic").value
    $.ajax({
        url: 'addTopic',
        method: 'post',
        data: {
            content: value
        },
        success: function () {
            location.reload();
        }
    })
}

function logout() {
    if (confirm("Do you want to log out?")) {
        $.ajax({
            url: 'logout',
            method: 'get',
            success: function () {
                $(location).attr("href", "login")
            }
        })
    }
}