const host = window.location.host;
const form = document.querySelector(".register-form");
const formLogin = document.querySelector(".login-form");

function removeMsg(campo) {
  document.querySelector(`.${campo}`).style.display = "none";
}

async function validateForm() {
  const nome = document.getElementById("nome");
  const user = document.getElementById("user");
  const email = document.getElementById("email");
  const senha = document.getElementById("senha");
  const telefone = document.getElementById("telefone");
  let contError = 0;

  /* Valida campo nome*/
  let error_nome = document.querySelector(".msg-nome");
  if (nome.value === "") {
    error_nome.innerHTML = "Favor preencher o nome completo";
    error_nome.style.display = "block";
    contError += 1;
  } else {
    error_nome.style.display = "none";
  }

  /* Valida campo user*/
  let error_user = document.querySelector(".msg-user");
  if (user.value === "") {
    error_user.innerHTML = "Favor preencher com o nome de usuário";
    error_user.style.display = "block";
    contError += 1;
  } else {
    error_user.style.display = "none";
    url = `https://${host}/usuario/find/${user.value}`;
    
    const response = await fetch(url);
    if (response.ok) {
      const json = await response.json();
      if (json === true) {
        error_user.innerHTML =
          "Usuário já cadastrado, crie um novo nome de usuário";
        error_user.style.display = "block";
        user.value = "";
        user.focus();
        contError += 1;
      }
    }
  }

  /* Valida campo email*/
  let error_email = document.querySelector(".msg-email");
  padrao = /\S+@\S+\.\S+/;
  if (email.value === "" || !padrao.test(email.value)) {
    error_email.innerHTML = "Favor preencher com um endereço de e-mail";
    error_email.style.display = "block";
    contError += 1;
  } else {
    error_email.style.display = "none";
    url = `https://${host}/usuario/find/${email.value}`;
    const response = await fetch(url);
   
    if (response.ok) {
      const json = await response.json();
      if (json === true) {
        error_email.innerHTML =
          "E-mail já cadastrado, entre com um novo endereço de e-mail";
        error_email.style.display = "block";
        email.value = "";
        email.focus();
        contError += 1;
      }
    }
  }

  /* Valida campo senha*/
  let error_senha = document.querySelector(".msg-senha");
  if (senha.value === "" || senha.value.length < 5) {
    error_senha.innerHTML = "Senha de no mínimo 5 caracteres";
    error_senha.style.display = "block";
    contError += 1;
  } else {
    error_senha.style.display = "none";
  }

  /* Valida campo telefone*/
  let error_telefone = document.querySelector(".msg-telefone");
  padrao = /^\([0-9]{2}\)[0-9]?[0-9]{4}-[0-9]{4}$/;
  if (telefone.value === "" || !padrao.test(telefone.value)) {
    error_telefone.innerHTML = "Favor preencher com um numero de telefone";
    error_telefone.style.display = "block";
    contError += 1;
  } else {
    error_telefone.style.display = "none";
  }

  if (contError === 0) {
    form.submit();
  }
}

async function validateLogin(){

  user = document.getElementById("user");
  pwd = document.getElementById("pwd");

  if(!pwd.value && !user.value){
    document.getElementById("user-erro").innerHTML = "Usuário em branco, favor informa-lo";
    document.getElementById("pwd-erro").innerHTML = "Senha em branco, favor informa-la";

  }
  else if(!user.value){
    document.getElementById("user-erro").innerHTML = "Usuário em branco, favor informa-lo";
  }
  else if(!pwd.value){
    document.getElementById("pwd-erro").innerHTML = "Senha em branco, favor informa-la";

  }
  else {
    formLogin.submit();
  }

}

