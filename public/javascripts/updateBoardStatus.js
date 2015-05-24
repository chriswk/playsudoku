function updateValue(e) {
  var target = e.srcElement || e.target
  if (target.nodeName === "INPUT") {
    target.setAttribute("value", target.value)
  }
}

for (var i = 0, el = document.getElementsByClassName("sudoku"), len = el.length; i < len; i++){
el[i].addEventListener("change", updateValue, false);
el[i].addEventListener("keyup", updateValue, false);
}