document.addEventListener('DOMContentLoaded', function() {
    const botonLanzar = document.getElementById('lanzarDados');
    const contenedor = document.getElementById('contenedor-dados');

    // Función para crear un solo dado
    function crearDado(numero) {
        const dadoDiv = document.createElement('div');
        dadoDiv.classList.add('dado');
        dadoDiv.dataset.cara = numero; // Ej: data-cara="5"

        // Añadimos tantos puntos como el número de la cara
        for (let i = 0; i < numero; i++) {
            const puntoDiv = document.createElement('div');
            puntoDiv.classList.add('punto');
            dadoDiv.appendChild(puntoDiv);
        }
        return dadoDiv;
    }
    
    // Función principal para lanzar los 5 dados
    function lanzar() {
        // Limpiamos los dados anteriores
        contenedor.innerHTML = ''; 

        for (let i = 0; i < 5; i++) {
            // Número aleatorio entre 1 y 6
            const numeroAleatorio = Math.floor(Math.random() * 6) + 1;
            const nuevoDado = crearDado(numeroAleatorio);
            contenedor.appendChild(nuevoDado);
        }
    }

    // Evento de click para el botón
    botonLanzar.addEventListener('click', lanzar);

    // Lanzamos una vez al cargar la página
    lanzar();
});