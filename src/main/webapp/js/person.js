var j = 0, friend = 0, home = 0;

document.getElementById('addeds').addEventListener('click', function(){
	var div = document.getElementById('eds');
	div.innerHTML = div.innerHTML + "<div class=ed id=ed" + j + ">consumption : <input type=number size=20 name=ed" + j + "><input type=button value=clear name=clear  onclick=cc(\"ed" + j + "\")></br></div>";
	j++;
});
document.getElementById('addfriends').addEventListener('click', function(){
	var div = document.getElementById('friends');
	div.innerHTML += "<div id=\"friend" + friend + "\"><select name=\"friend" + friend + "\">" + document.getElementById('hiddenfriends').innerHTML + "</select><input type=button value=clear name=clear  onclick=cc(\"friend" + friend + "\")></br></div>";
	friend++;
});

document.getElementById('addhomes').addEventListener('click', function(){
	var div = document.getElementById('homes');
	div.innerHTML += "<div id=\"home" + home + "\"><select name=\"home" + home + "\">" + document.getElementById('hiddenhomes').innerHTML + "</select><input type=button value=clear name=clear  onclick=cc(\"home" + home + "\")></br></div>";
	home++;
});

function cc(doc) {
	var el = document.getElementById(doc);
    el.parentNode.removeChild(el);
}