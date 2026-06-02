document.addEventListener('DOMContentLoaded', function() {

    const botonesEliminar = document.querySelectorAll('.btn-eliminar');
    const modalConfirmar = document.querySelector('#modalConfirmar');
    const btnConfirmarEliminar = document.querySelector('#btnConfirmarEliminar');
    let urlEliminar = '';

    for (const boton of botonesEliminar) {
        boton.addEventListener('click', function(event) {
            event.preventDefault();
            urlEliminar = this.getAttribute('href');
            const modal = new bootstrap.Modal(modalConfirmar);
            modal.show();
        });
    };

    if (btnConfirmarEliminar) {
        btnConfirmarEliminar.addEventListener('click', function() {
            window.location.href = urlEliminar;
        });
    };

    const inputMatricula = document.querySelector('#matricula');
    if (inputMatricula) {
        inputMatricula.addEventListener('input', function() {
            const patron = /^[0-9]{4}[A-Z]{3}$/;
            if (!patron.test(this.value) && this.value !== '') {
                this.classList.add('is-invalid');
                this.classList.remove('is-valid');
            } else if (patron.test(this.value)) {
                this.classList.remove('is-invalid');
                this.classList.add('is-valid');
            };
        });
    };

    const inputHoras = document.querySelector('#numeroHoras');
    const inputCostePorHora = document.querySelector('#costePorHora');
    const spanCosteCalculado = document.querySelector('#costeCalculado');

    if (inputHoras && inputCostePorHora && spanCosteCalculado) {
        function calcularCoste() {
            const horas = parseFloat(inputHoras.value) || 0;
            const costePorHora = parseFloat(inputCostePorHora.value) || 0;
            const total = horas * costePorHora;
            spanCosteCalculado.textContent = total.toFixed(2) + ' €';
        };
        inputHoras.addEventListener('input', calcularCoste);
        inputCostePorHora.addEventListener('input', calcularCoste);
    };

    const alertas = document.querySelectorAll('.alert-auto');
    for (const alerta of alertas) {
        setTimeout(function() {
            alerta.style.transition = 'opacity 1s';
            alerta.style.opacity = '0';
            setTimeout(function() {
                alerta.remove();
            }, 1000);
        }, 3000);
    };

});
