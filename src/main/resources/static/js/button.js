var clickStatus = 0;

// Verifica se o botão já foi clicado

const statusVerification = () => {
  clickStatus == 0 ? showPostOptions() : closePostOptions();
};

const add = (x) => {
  var text = document.getElementById("text");

  text.innerHTML += "<b>";
};

const showPostOptions = () => {
  document.getElementById("postOptions").classList.remove("hidden");
  clickStatus++;
};

const closePostOptions = () => {
  document.getElementById("postOptions").classList.add("hidden");
  clickStatus--;
};


tinymce.init({
  selector: 'textarea#tiny',
plugins: [
      'a11ychecker', 'advlist', 'advcode', 'advtable', 'autolink', 'checklist', 'export',
      'lists', 'link', 'image', 'charmap', 'preview', 'anchor', 'searchreplace', 'visualblocks',
      'powerpaste', 'fullscreen', 'formatpainter', 'insertdatetime', 'media', 'table', 'help', 'wordcount'
  ],
  toolbar: 'undo redo | a11ycheck casechange blocks | bold italic backcolor | alignleft aligncenter alignright alignjustify |' +
      'bullist numlist checklist outdent indent | removeformat | code table help'
})

var send = () => {
  var tiny = tinymce.get("tiny").getContent();
  var title = document.getElementById("title")
  var subtitle = document.getElementById("subtitle")
  var categoryList = []
  var category = document.querySelectorAll('.btn-check');
  for(i = 0; i < category.length; i ++){
    if(category[i].checked)
      categoryList.push(category[i].value)
  }
  
  console.log(title.value)
  console.log(subtitle.value)
  console.log(categoryList)
  console.log(tiny);
}

var cat = []
var selectCategory = (elem) => {
  listCategory = document.getElementById("id_usuario.nome")
  
  if(elem.checked){
    cat.push(elem.value)
  }
  else{
    cat.splice(cat.indexOf(elem.value), 1);
  }

  listCategory.value = cat
}