let meetId = document.getElementById("meetId").getAttribute("value")
let commentSection = document.getElementById("commentsCntr")

fetch(`http://localhost:8080/api/${meetId}/comments`)
.then((response) => response.json())
.then((body) => {
    console.log(body)
    for(comment of body){
        let commentHtml = '<h3>' + comment.authorUsername + '</h3>'
        commentHtml += '<div class="item">\n'
        commentHtml += '<h4>' + comment.content + '</h4>'
        commentHtml += '</div>\n'

        commentSection.innerHTML += commentHtml
    }
})