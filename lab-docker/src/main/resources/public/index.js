
const URL = "http://localhost:4567"
class Service {
    async getRequest(endpoint) {
        return fetch(`${URL}/${endpoint}`).then(async (res) => {
        console.log(res)
            let aux =await res.text();

            console.log(aux)
            if (aux.Error) throw new Error("Error");
            return aux;
        }).catch(() => null);
    }
}

let service = new Service();

async function loadSeno(){
    let seno = document.getElementById("seno").value;
    const rta = await service.getRequest(`sin?value=${seno}`)
    document.getElementById("result").innerHTML = rta;
}

async function loadCoseno(){
    let cos = document.getElementById("coseno").value;
    const rta = await service.getRequest(`cos?value=${cos}`)
    document.getElementById("result").innerHTML = rta;
}


async function loadPalindromo(){
    let palin = document.getElementById("palindromo").value;
    const rta = await service.getRequest(`palindromo?value=${palin}`)
    document.getElementById("result").innerHTML = rta;
}

async function loadMagnitud(){
    let x = document.getElementById("x").value;
    let y = document.getElementById("y").value;
    const rta = await service.getRequest(`magnitud?x=${x}&y=${y}`)
    document.getElementById("result").innerHTML = rta;
}