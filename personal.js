document.addEventListener("DOMContentLoaded", function () {
    // Obtén una referencia al botón "Agregar" por su ID.
    const addButton = document.getElementById("agregarButton");
  
    // Obtén una referencia al campo de fecha de nacimiento por su ID.
    const fechaNacimientoInput = document.getElementById("fechaNacimiento");
  
    // Inicializa Flatpickr para el campo de entrada de fecha de nacimiento
    flatpickr(fechaNacimientoInput, {
      dateFormat: "Y-m-d", // Formato deseado "yyyy-mm-dd"
      // Otras opciones y configuraciones pueden ir aquí
    });
  
    // Agrega un controlador de eventos click al botón "Agregar".
    addButton.addEventListener("click", function (event) {
      event.preventDefault(); // Evita el envío del formulario por defecto
  
      // Obtiene los valores de los campos de entrada en el formulario.
      const nombreInput = document.querySelector('input[name="nombre"]');
      const apellidoInput = document.querySelector('input[name="apellidos"]');
      const usuarioInput = document.querySelector('input[name="username"]');
      const passwordInput = document.querySelector('input[name="password"]');
      
      // Obtiene la fecha de nacimiento en el formato seleccionado por Flatpickr (generalmente "yyyy-mm-dd").
      const fechaNacimiento = fechaNacimientoInput.value;
  
      // Verifica si el nombre de usuario es alfanumérico con punto (.)
      if (!/^[a-zA-Z0-9.]+$/.test(usuarioInput.value)) {
          alert('Nombre de usuario no válido');
          return; // Evita que se realice la solicitud POST
      }
  
      // Verifica la seguridad de la contraseña (mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número)
      if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(passwordInput.value)) {
          alert('Contraseña no segura');
          return; // Evita que se realice la solicitud POST
      }
  
      // Verifica la fecha de nacimiento (mayor de 18 años)
      const birthDate = new Date(fechaNacimiento);
      const today = new Date();
      const age = today.getFullYear() - birthDate.getFullYear();
      if (age < 18) {
          alert('Debes ser mayor de 18 años para registrarte');
          return; // Evita que se realice la solicitud POST
      }
  
      // Define los datos que deseas enviar en formato JSON.
      const userData = {
        nombre: nombreInput.value,
        apellido: apellidoInput.value,
        fecha_Nacimiento: fechaNacimiento,
        usuario: usuarioInput.value,
        password: passwordInput.value
      };
  
      // Realiza la solicitud POST.
      fetch('https://sigbsdeployment.azurewebsites.net/Cliente/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
      })
        .then(response => {
          if (response.ok) {
            // La solicitud fue exitosa (código de respuesta 200).
            alert("Solicitud exitosa");
            // Limpiar los campos después de una solicitud exitosa
            nombreInput.value = '';
            apellidoInput.value = '';
            usuarioInput.value = '';
            passwordInput.value = '';
            fechaNacimientoInput._flatpickr.clear(); // Limpiar el campo de fecha
            // Puedes realizar cualquier otra acción que desees aquí.
          } else {
            // La solicitud falló, maneja el error aquí.
            console.error("Error en la solicitud");
          }
        })
        .catch(error => {
          // Maneja errores de red u otros errores aquí.
          console.error("Error en la solicitud: " + error.message);
        });
    });
  });
  