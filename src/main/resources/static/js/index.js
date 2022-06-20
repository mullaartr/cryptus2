 type="module"
    function login() {

}
    //Reset Inputfield code.
    function clearFunc() {

    // document.getElementById("gebruikersnaam").value = "";
    // document.getElementById("wachtwoord").value = "";
}



     function register() {
     window.location.href = "Register.html"

 }



     document.querySelector('#btnLogin').addEventListener('click', (e) => {
     var uname = document.getElementById("email").value;
     var pwd = document.getElementById("pwd1").value;
     var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
     if (uname == '') {
     alert("please enter your email address.");
 }
     else if (pwd == '') {
     alert("enter the password");
 }
     else if (!filter.test(uname)) {
     alert("Enter valid email id.");
 }
     else if (pwd.length < 6 || pwd.length > 25) {
     alert("Password min length is 6 and max length is 15.");
 }

     else {

     // fetch
     //   let userName = document.querySelector('#gebruikersnaam').value
     // let password = document.querySelector('#wachtwoord').value

     // JS Object -> to a string version
     let data = {
     username: uname,
     password: pwd

 }

     console.log( data );

     //var bearer = 'Bearer ' + bearer_token;

     fetch('http://localhost:8080/login',
 {
     method: 'POST',
     headers: {
     // 'Authorization': bearer,
     'content-Type': 'application/json'
     //'Authorization': 'Bearer ${token}'

 },
     body: JSON.stringify(data)
 })

     .then(response => { // callback
     console.log(response)
     if (response.status === 200) {
     let token = response.headers.get("Authorization").split(" ")[1]
     console.log(token)
     return token

 }


 }
     ).then(json => {

     if(json === undefined) {
     console.log('not logged in')
     alert('Wrong password username combination');
 }else {
     console.log(json)
     // select element uit de DOM
     // const p = document.querySelector('#antwoord')

     localStorage.setItem ('token', json)

     // p.innerHTML = json.waarde
     alert('Thank You for Login & You are being Redirected to Cryptus Website');



     window.location.href = "WelkomsPagina.html" // redirect after loggin in
 }



 })

 }


 })




     console.log('Voor de request')




     console.log('Na request')





