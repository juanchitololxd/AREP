
const URL = "http://localhost:35000"
class MovieService {
    async getMovie(movieName) {
        return fetch(`${URL}/movies?name=${movieName}`).then(async (res) => {
            let aux =await res.json();
            console.log(aux)
            if (aux.Error) throw new Error("Error");
            return aux;
        }).catch(() => null);
    }
}

writeReadField =(fieldName, value, place) =>{
    place.innerHTML +=`<span class=\"data\"><strong>${fieldName}</strong><p>${value}</p></span>`
}

createImg = (src, node) => {
    const imgElement = document.createElement("img");
    imgElement.src = src;
    
    node.appendChild(imgElement);
}

let movieService = new MovieService();

async function loadMovie() {
    const clase = document.createElement("div");
    
    let movieName = document.getElementById("movieTitle").value;
    document.getElementById("result").innerHTML = "";
    const movieData = await movieService.getMovie(movieName);

    if (movieData == null) document.getElementById("result").innerHTML = "<strong> La pelicula no existe, escriba bien :)</strong>";
    else{
        createImg(movieData.Poster, document.getElementById("result"))

        writeReadField("Nombre", movieData.Title, clase)
        writeReadField("Lanzamiento", movieData.Released, clase)
        writeReadField("Calificación", movieData.Rated, clase)
        writeReadField("Genero", movieData.Genre,clase)
        writeReadField("Escritor", movieData.Writer,clase)
        document.getElementById("result").appendChild(clase);
    }



}