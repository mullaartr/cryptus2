src = "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"

src = "https://code.jquery.com/jquery-3.6.0.js"
src = "https://code.jquery.com/ui/1.13.1/jquery-ui.js"

type = "module"

function login() {

}


function clearFunc() {
    document.getElementById("gebruikersnaam").value = "";
    document.getElementById("wachtwoord").value = "";
}


function elfProefValidation(BSN) {
    document.querySelector('#bsn').addEventListener('focusout', checkForAddressPart(BSN));
    let returnValue = false;

    if (BSN === '00000000000' || value.length !== 9) {
        return false;
    }
    const values = BSN.split('');
    const lastCharacter = parseInt(values[values.length - 1], 10);
    const [a, b, c, d, e, f, g, h, i] = values.map((char) => parseInt(char, 10));
    let result = 0;

    if (BSN !== '00000000000' || value.length == 9) {
        result = 9 * a + 8 * b + 7 * c + 6 * d + 5 * e + 4 * f + 3 * g + 2 * h + -1 * i;
        returnValue = result > 0 && result % 11 === 0;
        // } else if (type === 'own') {
        //     result = 9 * a + 8 * b + 7 * c + 6 * d + 5 * e + 4 * f + 3 * g + 2 * h;
        //     returnValue = result > 0 && result % 11 === lastCharacter + 5;
    } else {
        returnValue = false;
        alert("Please enter a valid BSN.");
    }

    return returnValue;
}


function register() {
    window.location.href = "Haircut.html"

}

function containsAnyLetter(str) {
    return /[a-zA-Z]/.test(str);
}

function underAgeValidate(birthday) {
    // it will accept two types of format yyyy-mm-dd and yyyy/mm/dd
    let optimizedBirthday = birthday.replace(/-/g, "/");

    //set date based on birthday at 01:00:00 hours GMT+0100 (CET)
    let myBirthday = new Date(optimizedBirthday);

    // set current day on 01:00:00 hours GMT+0100 (CET)
    let currentDate = new Date().toJSON().slice(0, 10) + ' 01:00:00';

    // calculate age comparing current date and borthday
    let myAge = ~~((Date.now(currentDate) - myBirthday) / (31557600000));

    if (myAge < 18) {
        return false;
        // alert("You need to be at least 18 years old");

    } else {
        return true;
    }

}


function isOverEighteen(day, month, year) {
    let now = parseInt(new Date().toISOString().slice(0, 10).replace(/-/g, ''));
    let dob = day * 1 + month * 100 + year * 10000; // Coerces strings to integers

    return now - dob > 180000;
}


document.querySelector('#btnRegister').addEventListener('click', (e) => {
    console.log("lksgflsdkfjdsklfjdsfk");
    let uname = document.getElementById("email").value;
    let pwd = document.getElementById("pwd1").value;
    let firstName = document.getElementById("firstName").value;
    let proposition = document.getElementById("proposition").value;
    let lastName = document.getElementById("lastName").value;
    let dOB = document.getElementById("datepicker").value;

    let houseNumber = document.getElementById("houseNumber").value;
    let postcode = document.getElementById("postcode").value;
    let street = document.getElementById("street").value;
    let city = document.getElementById("city").value;


    let BSN = document.getElementById("bsn").value;
    let pwdConfirm = document.getElementById("pwdConfirm").value;
    let phone = document.getElementById("phone").value;
    let filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;


    if (firstName == '') {
        alert("please enter first name.");
        return
    }

    if (lastName == '') {
        alert("please enter last name.");
        return

    }
    if (uname == '') {
        alert("please enter your email address.");
        return

    }
    if (pwdConfirm === '') {
        alert("passwords don't match");
        return
    }
    if (!filter.test(uname)) {
        alert("please Enter a valid email address.");
        return
    }
    if (pwd == '') {
        alert("please enter the password");
        return
    }
    if (pwd !== pwdConfirm) {
        alert("passwords don't match");
        return
    }
    if (pwd.length < 6 || pwd.length > 25) {
        alert("Password min length is 6 and max length is 15.");
        return
    }
    if (dOB == '') {
        alert("please enter date of birth");
        return
    }

    if(underAgeValidate(dOB) === false){
        alert("You need to be at least 18 years old");
        return;

    }


    if (houseNumber == '') {
        alert("please enter house number.");
        return
    }
    if (postcode == '') {
        alert("please enter your postcode.");
        return
    }
    if (street == '') {
        alert("please enter street name.");
        return
    }
    if (city == '') {
        alert("please enter city name.");
        return
    }
    if (BSN == '') {
        alert("please enter BSN name.");
        return
    }
    if (phone == '') {
        alert("please enter your phone number.");
        return
    }
    if (BSN === '00000000000' || BSN.length !== 9) {
        alert("Please enter a valid BSN.");
        return
    }
    if (uname === '') {
        alert("please enter your email.");
        return
    }
    if (!filter.test(uname)) {
        alert("please enter a valid email.");
        return
    }
    if (BSN !== '00000000000' || BSN.length == 9) {

        const values = BSN.split('');
        const lastCharacter = parseInt(values[values.length - 1], 10);
        const [a, b, c, d, e, f, g, h, i] = values.map((char) => parseInt(char, 10));
        let result = 0;


        result = 9 * a + 8 * b + 7 * c + 6 * d + 5 * e + 4 * f + 3 * g + 2 * h + -1 * i;
        returnValue = result > 0 && result % 11 === 0;

        if (containsAnyLetter(BSN)) {
            alert("Please enter a valid BSN.");
            return;

        }

    }

    let data = {
        firstName: firstName,
        preposition: proposition,
        lastName: lastName,
        userName: uname,
        password: pwd,
        birthDate: dOB,
        address: {
            houseNumber,
            postcode,
            street,
            city,

        },

        bsn: BSN,
        phone: phone


    }
    console.log(data);
    console.log("are you getting it");


    fetch('http://localhost:8080/users/register',
        {

            method: 'POST',
            headers: {
                'content-Type': 'application/json',
                //'Authorization': 'Bearer ${token}'

            },
            body: JSON.stringify(data)

        })
        .then((response) => { // callback
            console.log(response)
            if (response.status === 200) {
                return response.json()


            }


        }).then(json => {
        if (json === undefined) {
            console.log('Not registered')
        } else {
            console.log(json)
            alert('Thank You for Registering & You are being Redirected to Cryptus login page');


            window.location.href = "index.html"
        }



    })


})


console.log('Voor de request')


console.log('Na request')


document.querySelector('#postcode').addEventListener('focusout', checkForAddressPart);

document.querySelector('#houseNumber').addEventListener('focusout', checkForAddressPart);


function checkForAddressPart() {

    let regex = new RegExp(/^[1-9][0-9]{3}[\s]?[A-Za-z]{2}$/i);
    let postcode = document.querySelector('#postcode').value
    let houseNumber = document.querySelector('#houseNumber').value

    console.log('pc is valide: ' + regex.test(postcode))

    if (regex.test(postcode) && houseNumber) {

        let formData = `postcode=${postcode}&number=${houseNumber}`

        fetch("https://postcode.tech/api/v1/postcode?" + formData, {
            headers: {
                'Authorization': 'Bearer 1d3dc31e-842e-4d3d-b2c8-bcd127cb0232',
            },
        })
            .then(response => response.json())
            .then(json => processAddress(json))
            .catch((error) => {
                console.error('Foutje', error)
            });

    }

}

function processAddress(data) {
    document.querySelector('#error').style.display = 'none';

    let addressPart = data;

    document.querySelector('#city').value = addressPart.city; // zonder validatie
    document.querySelector('#street').value = addressPart.street; // zonder validatie

    document.querySelector('#postcode').classList.remove('error');
    document.querySelector('#houseNumber').classList.remove('error');
}


$(function () {
    $("#datepicker").datepicker({
        dateFormat: "yy-mm-dd"
    });
});












