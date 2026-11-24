const participantesSeleccionados = new Set();

function agregarParticipante() {
    const select = document.getElementById("participantesSelect");
    const selectedOption = select.options[select.selectedIndex];
    const id = selectedOption.value;
    const nombre = selectedOption.text;

    if (id && !participantesSeleccionados.has(id)) {
        participantesSeleccionados.add(id);

        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between align-items-center";
        li.setAttribute("data-id", id); // Importante para saber a quién eliminar
        li.innerHTML = `
            ${nombre}
            <button type="button" class="btn btn-danger btn-sm" onclick="eliminarParticipante('${id}')">❌</button>
        `;
        document.getElementById("listaParticipantes").appendChild(li);

        actualizarInputParticipantes();
    }
}

function eliminarParticipante(id) {
    participantesSeleccionados.delete(id);
    const lista = document.getElementById("listaParticipantes");
    const items = lista.querySelectorAll("li");

    items.forEach(item => {
        if (item.getAttribute("data-id") === id) {
            lista.removeChild(item);
        }
    });

    actualizarInputParticipantes();
}

function actualizarInputParticipantes() {
    document.getElementById("participantesInput").value = Array.from(participantesSeleccionados).join(",");
}
