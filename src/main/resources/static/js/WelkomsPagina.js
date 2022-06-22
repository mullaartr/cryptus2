


klantOphaler()

document.querySelector('#item1').addEventListener("click", () => {
    window.location.href = "register.html";
})

document.querySelector('#item2').addEventListener("click", () => {
    // window.location.href = "BuyAssetsFromBank.html";
    checkStatus("BuyAssetsFromBank.html");
})

document.querySelector('#logoutButton').addEventListener("click", () => {
    logout();
    window.location.href = "index.html";
})

function logout(){
    let token = localStorage.getItem('token')
    fetch('/status/logout', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then((response) => response.text())
        .then((data) => console.log(data))
        .catch(error => console.log("error::", error));
}

function checkStatus(locationString){
    let token = localStorage.getItem('token')
    fetch('/status/check', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then((response) => {
        console.log(response)
        if (response.status === 200) {
            window.location.href = locationString;
        }else window.location.href = "index.html";
    })
}


function klantOphaler() {
    let token = localStorage.getItem('token')
    //let klantnaam = `${klantNaamOphaler()}`
    fetch(`/klant/findByUsernamePassword`,
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then((response) => {
            console.log(response)
            if (response.status === 200) {
                console.log("We zien hier")
                return response.json();
            }
        }
    ).then(json => {
            console.log(json);
            tabelKlantGegevens(json);
            adresGegevens(json.address);
            portefeuilleToner(json.portefeuilleDTO)
            console.log(json.portefeuilleDTO)


        }
    ).catch(ex => {
        console.log(ex)
    })
}
function tabelKlantGegevens(json) {
    const table = document.createElement('table');
    const main = document.querySelector('#klantGegevens')
    table.style.padding = '3px';
    let header = table.createTHead();
    header.style.fontWeight = 'Bold';
    header.style.fontSize = '1.5em';
    let rij1 = table.insertRow(0);
    let rij2 = table.insertRow(1);

    let rij1Cell1 = rij1.insertCell(0);
    let rij1cel2 = rij1.insertCell(1);
    let rij2cel1 = rij2.insertCell(0);
    let rij2cel2 = rij2.insertCell(1);
    let rij3Cell1 = rij1.insertCell(2);
    let rij3Cell2 = rij1.insertCell(3);
    let rij4Cell1 = rij2.insertCell(2)
    let rij4Cell2 = rij2.insertCell(3);
    let rij5Cell1 = rij1.insertCell(4)
    let rij5Cell2 = rij1.insertCell(5);
    let rij6Cell1 = rij2.insertCell(4);
    let rij6Cell2 = rij2.insertCell(5);


    header.innerText = 'Gegevens:'
    rij1Cell1.innerText = 'Voornaam:';
    rij1cel2.innerText = json.firstName;
    rij2cel1.innerText = 'Achternaam:';
    rij2cel2.innerText = json.preposition +  " " + json.lastName;
    rij3Cell1.innerText = 'Geboortedatum:';
    rij3Cell2.innerText = json.birthDate;
    rij4Cell1.innerText = 'E-mail:';
    rij4Cell2.innerText =  json.email;
    rij5Cell1.innerText = 'Telefoon Nummer:';
    rij5Cell2.innerText = json.phone;
    rij6Cell1.innerText = 'BSN:';
    rij6Cell2.innerText = json.bsn;
    main.appendChild(table);
}

function adresGegevens(json){
    const table = document.createElement('table');
    const main = document.querySelector('#adresGegevens')
    table.style.padding = '3px';
    let header = table.createTHead();
    header.style.fontWeight = 'Bold';
    header.style.fontSize = '1.5em';
    let rij1 = table.insertRow(0);
    let rij2 = table.insertRow(1);

    let rij1Cell1 = rij1.insertCell(0);
    let rij1cel2 = rij1.insertCell(1);
    let rij1Cell3 = rij1.insertCell(2);
    let rij1cel4 = rij1.insertCell(3);
    let rij2cel1 = rij2.insertCell(0);
    let rij2cel2 = rij2.insertCell(1);
    let rij2cel3 = rij2.insertCell(2);
    let rij2cel4 = rij2.insertCell(3);

    header.innerText = 'Adres:'
    rij1Cell1.innerText = 'Straat:';
    rij1cel2.innerText = json.street
    rij2cel1.innerText = 'huisnr';
    rij2cel2 .innerText = json.houseNumber;
    rij2cel3.innerText = 'postcode:';
    rij2cel4.innerText = json.postcode;
    rij1Cell3.innerText = 'Stad: ';
    rij1cel4.innerText = json.city;
    main.appendChild(table);
}

function bankRekening(json){

}

function portefeuilleToner(json){
    const assets = json.assets;
    for(const asset of assets){
        const tabelRij =  document.querySelector(`#${asset.assetNaam}T`)
        const waardePlaatsing =  document.querySelector(`#${asset.assetNaam}Waarde`)
        if(asset.saldo !== 0){
            let koersen = asset.koersDTO;
            let koersInEuro = koersen.koersInEuro;
            let waarde = koersInEuro * asset.saldo;
            tabelRij.innerText = asset.saldo;
            waardePlaatsing.innerText = waarde.toFixed(2);

        }

    }


}

