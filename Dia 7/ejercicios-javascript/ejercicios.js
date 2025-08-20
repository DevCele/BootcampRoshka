/*Ejercicios de JavaScript*/
/*Suma de una ecuacion cuadrática*/
function SumaCuadratica(a, b, c) {
  const disc = b*b - 4*a*c;
  if (a === 0) throw new Error("a no puede ser 0");
  if (disc < 0) throw new Error("Discriminante negativo");
  return (-b + Math.sqrt(disc)) / (2*a);
}

console.log(SumaCuadratica(1, -3, 2));

/*Resta de una ecuacion cuadrática*/
function restaCuadratica(a, b, c) {
  const disc = b*b - 4*a*c;
  if (a === 0) throw new Error("a no puede ser 0");
  if (disc < 0) throw new Error("Discriminante negativo");
  return (-b - Math.sqrt(disc)) / (2*a);
}

function setInnerHTMLById(id, value) {
  const el = document.getElementById(id);
  if (!el) throw new Error(`No se encontró el elemento con id "${id}"`);
  el.innerHTML = value;
}

function setRandom1to100(id) {
  const el = document.getElementById(id);
  if (!el) throw new Error(`No se encontró el elemento con id "${id}"`);
  const rnd = Math.floor(Math.random() * 100) + 1;
  el.innerHTML = rnd;
}

