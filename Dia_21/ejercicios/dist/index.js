"use strict";
const data = [
    { id: 1, name: 'Alaska', children: [
            { id: 11, name: 'Bascom' },
        ] },
    { id: 2, name: 'Connecticut' },
    { id: 3, name: 'Wisconsin', children: [
            { id: 31, name: 'Oretta' },
            { id: 32, name: 'Konterra' },
            { id: 33, name: 'Guthrie' },
        ] },
    { id: 4, name: 'Nebraska', children: [
            { id: 41, name: 'Jennings' },
            { id: 42, name: 'Harviell' },
            { id: 43, name: 'Alfarata' },
            { id: 44, name: 'Bluffciew' },
            { id: 45, name: 'Escondida' },
        ] },
    { id: 5, name: 'Georgia' },
];
const expanded = new Set();
let selectedId = null;
function renderTree(items, container) {
    container.innerHTML = '';
    const ul = document.createElement('ul');
    ul.className = 'tree';
    container.appendChild(ul);
    for (const item of items) {
        ul.appendChild(renderItem(item, false));
    }
}
function renderItem(item, isChild) {
    const li = document.createElement('li');
    const row = document.createElement('div');
    row.className = 'item-row';
    li.appendChild(row);
    let chevron = null;
    const hasChildren = Array.isArray(item.children) && item.children.length > 0;
    if (!isChild && hasChildren) {
        chevron = document.createElement('div');
        chevron.className = 'chevron' + (expanded.has(item.id) ? ' open' : '');
        row.appendChild(chevron);
    }
    else {
        const spacer = document.createElement('div');
        spacer.style.width = '14px';
        spacer.style.height = '10px';
        row.appendChild(spacer);
    }
    const rect = document.createElement('div');
    rect.className = 'rect ' + (isChild ? 'blue' : 'black');
    rect.textContent = item.name;
    if (selectedId === item.id)
        rect.classList.add('selected');
    row.appendChild(rect);
    if (!isChild && hasChildren) {
        row.addEventListener('click', () => {
            if (expanded.has(item.id))
                expanded.delete(item.id);
            else
                expanded.add(item.id);
            chevron === null || chevron === void 0 ? void 0 : chevron.classList.toggle('open', expanded.has(item.id));
            if (childrenWrap) {
                childrenWrap.innerHTML = '';
                if (expanded.has(item.id)) {
                    const u = document.createElement('ul');
                    u.className = 'tree children';
                    for (const ch of item.children) {
                        u.appendChild(renderItem(ch, true));
                    }
                    childrenWrap.appendChild(u);
                }
            }
        });
    }
    else {
        row.addEventListener('click', () => {
            if (selectedId === item.id)
                return;
            if (selectedId != null) {
                const prev = document.querySelector(`[data-id="${selectedId}"]`);
                if (prev)
                    prev.classList.remove('selected');
            }
            selectedId = item.id;
            rect.classList.add('selected');
        });
    }
    let childrenWrap = null;
    if (!isChild && hasChildren) {
        childrenWrap = document.createElement('div');
        li.appendChild(childrenWrap);
        if (expanded.has(item.id)) {
            const u = document.createElement('ul');
            u.className = 'tree children';
            for (const ch of item.children) {
                u.appendChild(renderItem(ch, true));
            }
            childrenWrap.appendChild(u);
        }
    }
    rect.setAttribute('data-id', String(item.id));
    return li;
}
document.addEventListener('DOMContentLoaded', () => {
    const root = document.getElementById('tree-root');
    if (!root)
        throw new Error('No se encontró el contenedor #tree-root');
    renderTree(data, root);
});
//# sourceMappingURL=index.js.map