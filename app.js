
const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

sign_up_btn.addEventListener("click", () => {
    container.classList.add("sign-up-mode");
});

sign_in_btn.addEventListener("click", () => {
    container.classList.remove("sign-up-mode");
});

document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    const resultDiv = document.getElementById('result');

    loginForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Verifica si el nombre de usuario es alfanumérico con punto (.)
        if (!/^[a-zA-Z0-9.]+$/.test(username)) {
            resultDiv.textContent = 'Nombre de usuario no válido';
            return;
        }

        // Verifica la seguridad de la contraseña (mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número)
        if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(password)) {
            resultDiv.textContent = 'Contraseña no segura';
            return;
        }

        const credentials = {
            username: username,
            password: password
        };

        try {
            const response = await fetch('https://sigbsdeployment.azurewebsites.net/Cliente/authenticate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(credentials)
            });

            if (response.status === 401) {
                // Autenticación fallida
                resultDiv.textContent = 'Credenciales inválidas';
                return;
            }

            if (response.status === 200) {
                // Autenticación exitosa
                const data = await response.json();
                resultDiv.textContent = 'Inicio de sesión exitoso';

                // Verificar si hay una URL de redirección
                if (data.redirectUrl) {
                    // Redirigir a la URL de redirección
                    window.location.href = data.redirectUrl;
                }

                if (data.cliente) {
                    const cliente = data.cliente;
                    document.getElementById('nombre').value = cliente.nombre;
                    document.getElementById('apellidos').value = cliente.apellido;
                    document.getElementById('fechaNacimiento').value = cliente.fecha_Nacimiento;
                    // Llena otros campos del formulario según sea necesario
                }

            } else {
                resultDiv.textContent = 'Error al iniciar sesión';
            }
        } catch (error) {
            console.error('Error:', error);
            resultDiv.textContent = 'Error al iniciar sesión';
        }
    });

    $("#fechaDeNacimiento").datepicker({
        dateFormat: "yy/mm/dd",
        changeYear: true,
        yearRange: "1900:2023"
    });

    registerForm.addEventListener('submit', async function (event) {
        event.preventDefault();
    
        const nombre = document.getElementById('Nombres').value;
        const apellido = document.getElementById('Apellidos').value;
        const usuario = document.getElementById('registerUsername').value;
        const password = document.getElementById('registerPassword').value;
        const fecha_Nacimiento = document.getElementById('fechaDeNacimiento').value;
    
        // Verifica si el nombre de usuario es alfanumérico con punto (.)
        if (!/^[a-zA-Z0-9.]+$/.test(usuario)) {
            alert('Nombre de usuario no válido');
            return; // Evita que se realice la solicitud POST
        }
    
        // Verifica la seguridad de la contraseña (mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número)
        if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(password)) {
            alert('Contraseña no segura');
            return; // Evita que se realice la solicitud POST
        }
    
        // Verifica la fecha de nacimiento (mayor de 18 años)
        const birthDate = new Date(fecha_Nacimiento);
        const today = new Date();
        const age = today.getFullYear() - birthDate.getFullYear();
        if (age < 18) {
            alert('Debes ser mayor de 18 años para registrarte');
            return; // Evita que se realice la solicitud POST
        }
    
        const userData = {
            nombre: nombre,
            apellido: apellido,
            fecha_Nacimiento: fecha_Nacimiento,
            usuario: usuario,
            password: password,
        };
    
        // Realiza la solicitud POST al backend para guardar el cliente
        fetch('https://sigbsdeployment.azurewebsites.net/Cliente/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
        .then(response => response.json())
        .then(data => {
            // Verifica la respuesta del servidor
            if (data.message === 'Registro exitoso') {
                alert('Registro exitoso. Redirigiendo a la página de inicio de sesión.');
                // Puedes redirigir al usuario a la página de inicio de sesión aquí
                window.location.href = 'SignIn.html'; // Cambia la URL según tu estructura de carpetas
            } else {
                alert('Error en el registro. Por favor, inténtalo de nuevo.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al registrar. Por favor, inténtalo de nuevo.');
        });
    });
});


