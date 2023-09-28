document.addEventListener("DOMContentLoaded", function () {
    // Obtén una referencia a los campos de entrada por su ID.
    const nombreInput = document.getElementById("nombre");
    const apellidosInput = document.getElementById("apellidos");
    const fechaNacimientoInput = document.getElementById("fechaNacimiento");
  
    // Obtiene los datos del cliente almacenados en localStorage.
    const clienteDataString = localStorage.getItem("clienteData");
  
    if (clienteDataString) {
      // Parsea los datos del cliente desde la cadena JSON almacenada en localStorage.
      const clienteData = JSON.parse(clienteDataString);
  
      // Llena los campos del formulario con los datos del cliente.
      nombreInput.value = clienteData.nombre;
      apellidosInput.value = clienteData.apellido;
      fechaNacimientoInput.value = clienteData.fecha_Nacimiento;
    } else {
      // No se encontraron datos del cliente en localStorage.
      console.log("No se encontraron datos del cliente en localStorage.");
    }
  });
  