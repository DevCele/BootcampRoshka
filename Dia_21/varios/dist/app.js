"use strict";
/* Listado */
const data = [
    { id: 10, name: 'DATA PORTAL' },
    { id: 20, name: 'ABOUT' },
    { id: 30, name: 'CONTACT', children: [
            { id: 301, name: 'Get in touch' },
            { id: 302, name: 'Press' },
            { id: 303, name: 'Careers' },
            { id: 304, name: 'Partnerships' },
            { id: 305, name: 'Media kit' },
        ] },
    { id: 40, name: 'HOME', children: [
            { id: 401, name: 'Investor or regulator?' },
            { id: 402, name: 'First things to know' },
            { id: 403, name: 'Critical Inflection Point' },
            { id: 404, name: 'Uneven Distribution' },
            { id: 405, name: 'Risks may Filter up' },
        ] },
];
const expanded = new Set();
let selectedId = null;
function renderTree(items, container) {
    container.innerHTML = '';
    const ul = document.createElement('ul');
    ul.className = 'tree';
    container.appendChild(ul);
    for (const item of items)
        ul.appendChild(renderItem(item, false));
}
function renderItem(item, isChild) {
    const li = document.createElement('li');
    const row = document.createElement('div');
    row.className = 'item-row';
    li.appendChild(row);
    const hasChildren = Array.isArray(item.children) && item.children.length > 0;
    const rect = document.createElement('div');
    rect.className = 'rect ' + (isChild ? 'blue' : 'black');
    rect.textContent = item.name;
    if (selectedId === item.id)
        rect.classList.add('selected');
    let chevron = null;
    if (!isChild && hasChildren) {
        chevron = document.createElement('div');
        chevron.className = 'chevron ' + (expanded.has(item.id) ? '' : 'collapsed');
    }
    row.appendChild(rect);
    if (chevron)
        row.appendChild(chevron);
    if (!isChild && hasChildren) {
        row.addEventListener('click', () => {
            if (expanded.has(item.id))
                expanded.delete(item.id);
            else
                expanded.add(item.id);
            chevron === null || chevron === void 0 ? void 0 : chevron.classList.toggle('collapsed', !expanded.has(item.id));
            if (childrenWrap) {
                childrenWrap.innerHTML = '';
                if (expanded.has(item.id)) {
                    const u = document.createElement('ul');
                    u.className = 'tree children';
                    for (const ch of item.children)
                        u.appendChild(renderItem(ch, true));
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
            for (const ch of item.children)
                u.appendChild(renderItem(ch, true));
            childrenWrap.appendChild(u);
        }
    }
    rect.setAttribute('data-id', String(item.id));
    return li;
}
/* Acordeon */
function setupAccordion(rootSelector) {
    const root = document.querySelector(rootSelector);
    if (!root)
        return;
    root.addEventListener('click', (ev) => {
        const header = ev.target.closest('.acc-header');
        if (!header)
            return;
        const item = header.closest('.acc-item');
        if (!item)
            return;
        item.classList.toggle('open');
    });
}
/* Init */
document.addEventListener('DOMContentLoaded', () => {
    const treeRoot = document.getElementById('tree-root');
    if (!treeRoot)
        throw new Error('No se encontró #tree-root');
    renderTree(data, treeRoot);
    setupAccordion('#docs-accordion');
});
//# sourceMappingURL=app.js.map