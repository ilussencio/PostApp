const host = window.location.host;

async function find(){
    document.getElementById("result").innerHTML = ""
    textFind = document.getElementById("textFind");
    url = `https://${host}/busca/${textFind.value}`;
    const response = await fetch(url)
        .then(data => data.json())
        .then(data => insert(data));
    
}

function insert(post){
    if(post.length === 0){
        console.log("nenhum resultado")
        document.getElementById("result").innerHTML = "<p class='msgError'>Nenhum resultado encontrado! </p>"
    }else{
        for(i = 0; i < post.length; i ++){
            a = document.createElement("a")
            a.setAttribute("href",`/post/${post[i].id}`)
            a.setAttribute("class", "results");
    
            p = document.createElement("p")
            p.setAttribute("class","title")
            p.innerHTML = post[i].titulo
        
            p2 = document.createElement("p")
            p2.setAttribute("class","by")
            span = document.createElement("span")
            span.setAttribute("class","author")
            span.innerHTML = post[i].id_usuario.nome
            p2.innerHTML = "Por: "
            p2.appendChild(span)
        
            a.appendChild(p)
            a.appendChild(p2)
            document.getElementById("result").appendChild(a);
        }
    }
    

}
async function getParams(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const busca = urlParams.get('find')
    if(busca === null){
        url = `https://${host}/busca/desenvolvimento`;
    }else{
        url = `https://${host}/busca/${busca}`;
    }

    const response = await fetch(url)
        .then(data => data.json())
        .then(data => insert(data));

    if(response){
        document.getElementById("textFind").value = "teste";
    }
}


getParams()

