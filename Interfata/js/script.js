var id=0;

function ModifyTime(ii) {
  if(id==0)
  {

      id=1;
      alert("1");
      document.getElementsById("8").style.backgroundColor = "red";
  }
  else
  {
      id=0;
     // document.getElementsByClassName('free').style.backgroundColor = "green";
  }

}

var data = document.querySelectorAll('#data-table tr td select');

data.forEach(function(item) {
  setColor(item);

  item.addEventListener('change', function() {
    setColor(this);
  });
});

function setColor(element) {
  var container = element.parentElement;
  container.classList.remove('free','busy');

  var value = element.options[element.selectedIndex].value;
  container.classList.add(value);
}