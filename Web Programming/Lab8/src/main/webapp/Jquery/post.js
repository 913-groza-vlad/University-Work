function addComment() {
    console.log(document.getElementById('commentInput').value)
    $.ajax({
        url: 'addComment',
        method: 'post',
        data: {
            'commentText': document.getElementById('commentInput').value
        },
        success: function () {
            location.reload()
        }
    })
}

function deleteComment(commentId) {
    if (confirm("Are you sure you want to delete this comment?")) {
        console.log(commentId)
        $.ajax({
            url: 'deleteComment',
            method: 'post',
            data: {
                'commentId': commentId
            },
            success: function () {
                location.reload()
            }
        })
    }
}
