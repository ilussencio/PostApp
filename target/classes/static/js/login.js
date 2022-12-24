const user_id = localStorage.getItem("usuario_id");
const username = localStorage.getItem("usuario_nome");
const user_pic = localStorage.getItem("usuario_foto");

// localStorage.setItem("usuario_id", 1);
// localStorage.setItem("usuario_nome", "Lucas Ilussencio da silva");
// localStorage.setItem(
//   "usuario_foto",
//   "https://media-exp1.licdn.com/dms/image/C4E03AQHR0yPiwVtfjA/profile-displayphoto-shrink_800_800/0/1654528097336?e=2147483647&v=beta&t=pJ5UEI9qA4pBa1dte4Qr04toC5PUfeaVY-0E5dv0-QE"
// );

window.onload = function () {
  if (username && user_pic) {
    usr = document.createElement("span");
    usr.setAttribute("class", "username");
    usr.innerHTML = username;

    pic = document.createElement("img");
    pic.setAttribute("class", "user-pic");
    pic.setAttribute("src", user_pic);

    document.getElementById("user-info").appendChild(usr);
    document.getElementById("user-info").appendChild(pic);
  } else {
    //<a href="/login"><button type="button" class="btn btn-outline-light btn-login">Entrar</button></a>
    user_info_login = document.createElement("a");
    user_info_login.setAttribute("href", "/login");
    user_info_login_btn = document.createElement("button");
    user_info_login_btn.setAttribute(
      "class",
      "btn btn-outline-light btn-login"
    );
    user_info_login_btn.innerHTML = "Entrar";
    user_info_login.appendChild(user_info_login_btn);

    //<a href="/cadastro"><button type="button" class="btn btn-outline-light btn-register">Cadastre-se</button></a>
    user_info_cadastro = document.createElement("a");
    user_info_cadastro.setAttribute("href", "/cadastro");
    user_info_cadastro_btn = document.createElement("button");
    user_info_cadastro_btn.setAttribute(
      "class",
      "btn btn-outline-light btn-register"
    );
    user_info_cadastro_btn.innerHTML = "Cadastre-se";
    user_info_cadastro.appendChild(user_info_cadastro_btn);

    //
    document.getElementById("user-info").appendChild(user_info_cadastro);
    document.getElementById("user-info").appendChild(user_info_login);
  }
};
