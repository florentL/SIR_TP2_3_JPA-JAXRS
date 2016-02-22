var i = 0;

document.getElementById('addheater').addEventListener('click', function(){
	var div = document.getElementById('heaters');
	div.innerHTML = div.innerHTML + "<div class=heater id=heater" + i + ">consumption : <input type=number size=20 name=heater" + i + "><input type=button value=clear name=clear  onclick=cc(\"heater" + i + "\")></br></div>";
	i++;
});

function cc(doc) {
	var el = document.getElementById(doc);
    el.parentNode.removeChild(el);
}