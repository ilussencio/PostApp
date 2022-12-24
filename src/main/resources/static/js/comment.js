const host = window.location.host;
const post = document.getElementById("id_post").innerHTML

async function updateComment(){
    const url = `http://${host}/comentario/get`
    const postagem = {
        id:post
    }
    const request = new Request(url, {
        method: 'POST',
        body: JSON.stringify(postagem),
        headers: new Headers({
            'Content-Type': 'application/json'
        })
    })

    const response = await fetch(request)
        .then(res => res.json())
        .then(res => comment(res))
    
}

function comment(comentario){
    document.getElementById("comments").innerHTML = ""
    for(i = 0; i < comentario.length; i ++){
        var div = document.createElement('div')
        div.setAttribute("class", "d-flex gap-3 comment");
        var div_img = document.createElement('div')
        var img = document.createElement('img')
        img.setAttribute("class", "comment_img");
        img.setAttribute("src",comentario[i].id_usuario.foto);
        div_img.appendChild(img)
        var comment = document.createElement('div')
        var data = document.createElement('label')
        data.setAttribute("class","fs-6")
        data.setAttribute("class","p-small ms-1")
        date = new Date(Date.parse(comentario[i].create_data))
        data.innerHTML = ` - ${(date.getDate() < 10)?"0"+date.getDate():date.getDate()}/${(date.getMonth() < 10)?"0"+date.getMonth():date.getMonth()}/${date.getFullYear()}  ${(date.getHours() < 10)?"0"+date.getHours():date.getHours()}:${(date.getMinutes() < 10)?"0"+date.getMinutes():date.getMinutes()} `

        var a = document.createElement('a')
        a.setAttribute("href",`../usuario/${comentario[i].id_usuario.username}`)
        var label = document.createElement('label')
        label.setAttribute("class","comment_name")
        a.appendChild(label)
        label.innerHTML = comentario[i].id_usuario.nome
        var p = document.createElement('p')
        p.setAttribute("class","comment_text");
        p.innerText = comentario[i].comentario
        comment.appendChild(a)
        
        comment.appendChild(p)
        a.appendChild(data)
        
        div.appendChild(div_img)
        div.appendChild(comment)
        document.getElementById("comments").appendChild(div);
    }
}

async function addComment(){
    text = document.getElementsByName('text_comment')[0];
    const url = `http://${host}/comentario`
    // post body data
    const comentario = {
        id_postagem:{id:post},
        id_usuario:{id:user_id},
        comentario:text.value
    }

    // create request object
    const request = new Request(url, {
        method: 'POST',
        body: JSON.stringify(comentario),
        headers: new Headers({
            'Content-Type': 'application/json'
        })
    })

    const response = await fetch(request)
        .then(res => res.json())
        .then(res => console.log(res))

    await updateComment();
    text.value = ""
}

function commentCont(){
    text = document.getElementsByName('text_comment')[0];
    button = document.getElementById('comment_button')
    document.getElementById('comment_cont').innerHTML = 255 - text.value.length
    if(text.value.length > 0 && text.value.length <= 255){
        button.disabled = false
    }else{
        button.disabled = true
    }
}

updateComment();