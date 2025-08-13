document.addEventListener('DOMContentLoaded', function() {
    const display = document.getElementById('display');
    const teclas = document.querySelector('.teclas');

    let valorDisplay = '0';
    let primerValor = null;
    let operador = null;
    let esperandoSegundoValor = false;

    function actualizarDisplay() {
        display.textContent = valorDisplay;
    }

    actualizarDisplay();

    teclas.addEventListener('click', function(e) {
        const tecla = e.target;
        const valorTecla = tecla.textContent;

        if (!tecla.matches('button')) {
            return;
        }

        if (tecla.classList.contains('operador')) {
            manejarOperador(tecla.dataset.operador);
            return;
        }

        if (tecla.id === 'borrar') {
            borrarUltimo();
            return;
            }

        if (tecla.id === 'decimal') {
            inputDecimal(valorTecla);
            return;
        }

        if (tecla.id === 'limpiar') {
            limpiar();
            return;
        }

        if (tecla.classList.contains('numero')) {
            inputNumero(valorTecla);
            return;
        }

        if (tecla.id === 'igual') {
            manejarOperador('=');
        }
    });

    function inputNumero(numero) {
        if (esperandoSegundoValor) {
            valorDisplay = numero;
            esperandoSegundoValor = false;
        } else {
            valorDisplay = valorDisplay === '0' ? numero : valorDisplay + numero;
        }
        actualizarDisplay();
    }
    
    function inputDecimal(punto) {
        if (esperandoSegundoValor) return;
        if (!valorDisplay.includes(punto)) {
            valorDisplay += punto;
        }
        actualizarDisplay();
    }

    function manejarOperador(proximoOperador) {
        const valorActual = parseFloat(valorDisplay);

        if (operador && esperandoSegundoValor) {
            operador = proximoOperador;
            return;
        }

        if (primerValor === null) {
            primerValor = valorActual;
        } else if (operador) {
            const resultado = calcular(primerValor, valorActual, operador);
            valorDisplay = `${parseFloat(resultado.toFixed(7))}`;
            primerValor = resultado;
        }
        
        esperandoSegundoValor = true;
        operador = proximoOperador;
        actualizarDisplay();
    }
    
    function calcular(primero, segundo, op) {
        if (op === '+') return primero + segundo;
        if (op === '-') return primero - segundo;
        if (op === '*') return primero * segundo;
        if (op === '/') return primero / segundo;
        return segundo; // para el caso del igual
    }

    function limpiar() {
        valorDisplay = '0';
        primerValor = null;
        operador = null;
        esperandoSegundoValor = false;
        actualizarDisplay();
    }

    function borrarUltimo() {
        if (esperandoSegundoValor) return; 
        if (valorDisplay.length > 1) {
            valorDisplay = valorDisplay.slice(0, -1);
        } else {
            valorDisplay = '0';
        }
        actualizarDisplay();
    }

});