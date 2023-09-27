function openNav() {
    var menu_texts = document.getElementsByClassName("menu_item_text");

    for(var i = 0; i < menu_texts.length; i++){
      menu_texts[i].style.visibility = "visible";
    }

    document.getElementById("mySidenav").style.width = "15vw";
    document.getElementById("main_area").style.opacity ="0.3";
    document.getElementById("closebtn").style.visibility = "visible";
    document.getElementById("openbtn").style.visibility = "hidden";
    document.getElementById("alma").style.visibility = "hidden";

    
  }

  function closeNav() {
    var menu_texts = document.getElementsByClassName("menu_item_text");
    for(var i = 0; i < menu_texts.length; i++){
      menu_texts[i].style.visibility = "hidden";
    }

    document.getElementById("mySidenav").style.width = "2vw";
    document.getElementById("main_area").style.opacity = "1";
    document.getElementById("closebtn").style.visibility = "hidden";
    document.getElementById("openbtn").style.visibility = "visible";

  }