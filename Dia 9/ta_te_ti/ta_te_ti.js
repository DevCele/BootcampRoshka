const boardEl  = document.getElementById('board');
const statusEl = document.getElementById('status');
const resetBtn = document.getElementById('resetBtn');

const WINS = [
  [0,1,2],[3,4,5],[6,7,8],
  [0,3,6],[1,4,7],[2,5,8],
  [0,4,8],[2,4,6]
];

let board, current, ended, cells;

init();

resetBtn.addEventListener('click', reset);

function init(){
  board   = Array(9).fill(null);
  current = 'X';
  ended   = false;
  boardEl.innerHTML = '';
  cells = [];

  for (let i = 0; i < 9; i++){
    const btn = document.createElement('button');
    btn.type = 'button';
    btn.className = 'cell';
    btn.setAttribute('role', 'gridcell');
    btn.setAttribute('aria-label', `Celda ${i+1}, vacía`);
    btn.dataset.index = String(i);
    btn.addEventListener('click', onCellClick);
    boardEl.appendChild(btn);
    cells.push(btn);
  }
  updateStatus(`Turno de ${current}`);
}

function onCellClick(e){
  if (ended) return;

  const idx = Number(e.currentTarget.dataset.index);
  if (board[idx] !== null) return;

  board[idx] = current;

  const btn = cells[idx];
  btn.textContent = current;
  btn.classList.add(current.toLowerCase());
  btn.setAttribute('aria-label', `Celda ${idx+1}, ${current}`);
  btn.setAttribute('aria-disabled', 'true');

  const { winner, combo } = checkWinner();

  if (winner){
    ended = true;
    combo.forEach(i => cells[i].classList.add('win'));
    updateStatus(`¡Ganó ${winner}!`);
    lockRemaining();
    return;
  }

  if (board.every(Boolean)){
    ended = true;
    updateStatus('Empate');
    lockRemaining();
    return;
  }

  current = current === 'X' ? 'O' : 'X';
  updateStatus(`Turno de ${current}`);
}

function checkWinner(){
  for (const [a,b,c] of WINS){
    if (board[a] && board[a] === board[b] && board[a] === board[c]){
      return { winner: board[a], combo: [a,b,c] };
    }
  }
  return { winner: null, combo: [] };
}

function lockRemaining(){
  cells.forEach((btn, i) => {
    if (!board[i]){
      btn.setAttribute('aria-disabled', 'true');
    }
  });
}

function updateStatus(text){
  statusEl.textContent = text;
}

function reset(){
  init();
}
