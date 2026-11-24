document.addEventListener("DOMContentLoaded", function () {
  const container = document.getElementById("preguntas-container");

  // Contar cuántas preguntas ya existen (evita duplicados)
  let contadorPreguntas = container.querySelectorAll("textarea").length;

  window.agregarPregunta = function () {
    const div = document.createElement("div");
    div.classList.add("form-group", "pregunta-item");

    const textarea = document.createElement("textarea");
    textarea.name = `preguntas[${contadorPreguntas}].texto`;
    textarea.classList.add("form-control", "mb-2");
    textarea.required = true;
    textarea.rows = 3;
    textarea.id = `pregunta-${contadorPreguntas}`;

    // Crear botón de eliminar
    const botonEliminar = document.createElement("button");
    botonEliminar.type = "button";
    botonEliminar.textContent = "Eliminar";
    botonEliminar.classList.add("btn", "btn-danger", "mb-3", "ms-2");
    botonEliminar.onclick = function () {
      div.remove(); // Elimina el div contenedor de la pregunta
    };

    div.appendChild(textarea);
    div.appendChild(botonEliminar);
    container.appendChild(div);

    contadorPreguntas++;
  };
  window.eliminarPregunta = function (button) {
      const preguntaDiv = button.closest(".pregunta-item");
      if (preguntaDiv) {
        preguntaDiv.remove();
      }
    };
});
