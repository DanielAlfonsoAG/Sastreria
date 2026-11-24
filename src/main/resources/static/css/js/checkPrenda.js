<script>
function toggleSelect(chkId, selectId) {

    // Desactivar todos los selects
    document.getElementById("selectVestido").disabled = true;
    document.getElementById("selectTraje").disabled = true;
    document.getElementById("selectDisfraz").disabled = true;

    // Deseleccionar selects
    document.getElementById("selectVestido").value = "";
    document.getElementById("selectTraje").value = "";
    document.getElementById("selectDisfraz").value = "";

    // Desmarcar otros checkboxes
    if (chkId !== "chkVestido") document.getElementById("chkVestido").checked = false;
    if (chkId !== "chkTraje") document.getElementById("chkTraje").checked = false;
    if (chkId !== "chkDisfraz") document.getElementById("chkDisfraz").checked = false;

    // Activar solo el select correspondiente
    const chk = document.getElementById(chkId);
    const sel = document.getElementById(selectId);

    sel.disabled = !chk.checked;
}
</script>
