koersOphaler()


let euroOfDollar = true;
let $tickerWrapper;
let $list;
let $clonedList;
let listWidth;

document.querySelector('.tickerwrapper').addEventListener("click", () => {
    euroOfDollar = !euroOfDollar;
    koersen.forEach(koersen => vullLijstEuro(koersen));
    $tickerWrapper.clear();

})



function koersOphaler(){
    let token = localStorage.getItem('token')

    fetch('http://localhost:8080/koers/meest-recent',
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }

        }).then((response) => {
            console.log(response)
            if (response.status === 200) {
                return response.json();
            }
        }
    ).then(json => {
            koersen = json;
            koersen.forEach(koersen => vullLijstEuro(koersen));
            maakTekstLint();
            return json;

        }
    ).catch(ex => {
        console.log(ex)
    })
}


function vullLijstEuro(json){
    const naam = `#${json.assetNaam}`;
    const e = document.querySelector(naam)
    const plaatje = `Assets/${json.assetNaam}.webp`
    if(euroOfDollar) {
        e.innerHTML = `<img src="${plaatje}" style="margin-top: 2px"> ${json.assetNaam} € ${json.koersInEuro}`;
    } else {
        e.innerHTML = `<img src="${plaatje}" style="margin-top: 2px"> ${json.assetNaam} : $ ${json.koersInDollars}`;
    }

}


function maakTekstLint() {
     $tickerWrapper = $(".tickerwrapper");
    $list = $tickerWrapper.find("ul.list");
    $clonedList = $list.clone();
    listWidth = 20;

    $list.find("li").each(function (i) {
        listWidth += $(this, i).outerWidth(true);
    });

    let endPos = $tickerWrapper.width() - listWidth;

    $list.add($clonedList).css({
        "width": listWidth + "px"
    });

    $clonedList.addClass("cloned").appendTo($tickerWrapper);

    let infinite = new TimelineMax({repeat: -1, paused: true});
    let time = 40;

    infinite.fromTo($list, time, {rotation: 0.01, x: 0}, {force3D: true, x: -listWidth, ease: Linear.easeNone}, 0)
        .fromTo($clonedList, time, {rotation: 0.01, x: listWidth}, {force3D: true, x: 0, ease: Linear.easeNone}, 0)
        .set($list, {force3D: true, rotation: 0.01, x: listWidth})
        .to($clonedList, time, {force3D: true, rotation: 0.01, x: -listWidth, ease: Linear.easeNone}, time)
        .to($list, time, {force3D: true, rotation: 0.01, x: 0, ease: Linear.easeNone}, time)
        .progress(1).progress(1)
        .play();


    $tickerWrapper.on("mouseenter", function(){
        infinite.pause();
    }).on("mouseleave", function(){
        infinite.play();
    });
}
