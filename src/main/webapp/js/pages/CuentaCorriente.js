/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let totalFlywire = 0;
let cadena = '';
let agregado = '';

const urlRedireccion = "http://siteslcb.eastus.cloudapp.azure.com:8080/pagoEnLinea/pages/cuentaCorrienteRedireccion.jsp";

var config = {
    // Set your environment (demo, production)
    env: "demo",
    // Set your unique code (may differ between environments)
    recipient: "LEP",
    // Set your preferred locale
    locale: "es-ES",
    // Set provider to checkout_edu
    provider: "checkout_edu",
    // Define payor’s information
    sender_country: 'ES',
    sender_first_name: "",
    sender_middle_name: "",
    sender_last_name: "",
    sender_address1: "1 First St",
    sender_address2: "202",
    sender_state: "Spitalfields",
    sender_zip_code: "E1 6GL",
    sender_phone: "+441234567890",
    sender_city: "London",
    sender_email: "john.doe@flywire.com",
    // Define custom attributes (based on your portal settings)
    amount: 0,
    // Enable payment status notification callbacks
    callback_url: "http://siteslcb.eastus.cloudapp.azure.com:8080/centralFlywire/NotificacionServlet",
    //callback_url: "http://localhost:8080/gestionPagoenLinea/NotificationServlet",
    callback_id: "",
    // Set the return url to redirect the user on process completion
    //return_url: "?codigo=12345",
    return_url: "",

    student_id: "",
    student_first_name: "",
    student_last_name: "",
    invoice_number: "",

};

window.flywire.Checkout.render(config, "#checkout-button");

require(['jquery'], function ($) {

    let methods = {
        listadoCuentaCorriente() {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: 'get',
                    url: '../CuentaCorrienteServlet?accion=listarCuentaCorriente',
                    crossDomain: true,
                    dataType: 'JSON',
                    // Añade un header:
                    headers: {'Authorization': 'Bearer ' + lsToken},
                    data: 'txt_data=' + JSON.stringify(lsSession),
                    beforeSend: function (xhr) {
                        //console.log('Cargando...')
                    },
                    success: function (data, textStatus, jqXHR) {
                        if (textStatus === 'success' && jqXHR.status === 200) {
                            resolve(data)
                        } else {
                            reject('La petición retorno un error: ' + data + ' - ' + textStatus)
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        reject('No se pudo realizar la petición: ' + jqXHR)
                    }
                })
            })
        },
    }

    function consultaCuentaCorriente() {
        startLoading();

        let contadorTotalizado = 1;

        methods.listadoCuentaCorriente().then(data => {
            $.each(data.cuentaCorriente, function (i, item) {
                contadorTotalizado++;

            });
            contadorTotalizado = contadorTotalizado - 1;

            print = `
                    <table class="table table-hover table-outline table-vcenter text-nowrap card-table">
                            <thead>
                                <tr>
                                    <th class="text-center">
                                        Acción
                                        <label class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="customCheckTotal" onclick="totalizadaChecked(${contadorTotalizado})">
                                            <span class="custom-control-label"></span>
                                        </label>
                                    </th>
                                    <th class="text-center">Periodo</th>
                                    <th class="text-center">Nro. Cuota</th>
                                    <th class="text-center">Item</th>
                                    <th class="text-center">Nro. Pre Factura</th>
                                    <th class="text-center">Concepto</th>
                                    <th class="text-center">Mes</th>
                                    <th class="text-center">Fecha Vencimiento</th>
                                    <th class="text-center">Monto Inicial</th>
                                    <th class="text-center">Mondo Descuento</th>
                                    <th class="text-center">Monto Pendiente</th>
                                </tr>
                            </thead>
                            <tbody>
                    `;

            let contador = 1;
            let estadoChk = "";

            $.each(data.cuentaCorriente, function (i, item) {
                if (contador == 1) {
                    estadoChk = ``;
                } else {
                    estadoChk = `disabled="disabled"`;
                }


                print += `
                        <tr>
                            <td class="text-center">
                                <label class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" id="customCheck_${contador}" onclick="activaCuenta(${contador},${contadorTotalizado});" ${estadoChk} >
                                    <span class="custom-control-label"></span>
                                </label>
                            </td>
                            <td class="text-center">${item.periodo}</td>
                            <td class="text-center">${item.nro_cuota}</td>
                            <td class="text-center">${item.item}</td>
                            <td class="text-center">${item.prefactura}</td>
                            <td class="text-center">${item.concepto}</td>
                            <td class="text-center">${item.mes}</td>
                            <td class="text-center">${item.fecha_vencimiento}</td>
                            <td class="text-right">${item.monto_inicial}</td>
                            <td class="text-right">${item.monto_descuento}</td>
                            <td class="text-right">${item.monto_pendiente}
                                <input type="hidden" id="txtIdCCT_${contador}" value="${item.id_cct}"> 
                                <input type="hidden" id="txtMontoPendiente_${contador}" value="${item.monto_pendiente}"> 
                            </td>
                        </tr>
                        `;
                contador++;
            });

            print += `
                            </tbody>
                        </table>
                    `;

            print_estudiante = `
                                <div class="row">
                                    <div class="col-xl-2">
                                        <p class="leading-normal"><b>Código :</b><br>${data.estudiante.codigo}</p>
                                    </div>
                                    <div class="col-xl-5">
                                        <p class="leading-normal"><b>Nombres Completos :</b><br>${data.estudiante.nombreCompleto}</p>
                                    </div>
                                    <div class="col-xl-5">
                                        <p class="leading-normal"><b>Carrera :</b><br>${data.estudiante.carrera}</p>
                                    </div>
                                </div>
                                `;
            $("#bannerEstudiante").html(data.estudiante.nombreCompleto);
            $("#divEstudiante").html(print_estudiante);
            $("#divListadoCuentaCorriente").html(print);

            config.student_id = data.estudiante.codigo;

            config.student_first_name = data.estudiante.nombre;
            config.student_last_name = data.estudiante.paterno + " " + data.estudiante.materno;

            config.sender_first_name = data.estudiante.nombre;
            config.sender_last_name = data.estudiante.paterno + " " + data.estudiante.materno;

            agregado = lsSession.unidad + '#' + data.estudiante.tipo_documento + '#' + data.estudiante.nro_documento;

            stopLoading();
        })

    }

    consultaCuentaCorriente();

});

function activaCuenta(numero, total) {
    require(['jquery'], function ($) {

        if ($("#customCheck_" + numero).prop("checked") == true) {
            $("#customCheck_" + (numero + 1)).removeAttr("disabled");

            totalFlywire += parseFloat($("#txtMontoPendiente_" + numero).val());
            console.log(totalFlywire);
            var stringTotal = totalFlywire.toFixed(2);
            $("#divTotalizada").html("S/ " + stringTotal);
            cadena += $("#txtIdCCT_" + numero).val() + "#";

        } else {

            $("#customCheckTotal").prop('checked', false);

            for (b = numero; b <= total; b++) {
                if (($("#customCheck_" + b).prop("checked") == true) || (b == numero)) {

                    totalFlywire -= parseFloat($("#txtMontoPendiente_" + b).val());
                    totalFlywire = redondearDecimales(totalFlywire, 2);

                    let item = $("#txtIdCCT_" + b).val() + "#";
                    cadena = cadena.replace(item, "");
                }
            }

            for (a = (numero + 1); a <= total; a++) {
                $("#customCheck_" + a).prop('checked', false);
                $("#customCheck_" + a).attr("disabled", true);
            }



            var stringTotal = totalFlywire.toFixed(2);
            $("#divTotalizada").html("S/ " + stringTotal);

        }

        if (totalFlywire > 0) {
            $("#checkout-button").css("display", "block");
        } else {
            $("#checkout-button").css("display", "none");
        }

        stringTotal = stringTotal.replace(".", "");
        config.amount = parseInt(stringTotal);
        let callback_id = "";
        callback_id = agregado + "#" + cadena;
        config.callback_id = btoa(callback_id);
        config.return_url = urlRedireccion + "?v=" + btoa(cadena);

    });

}

function totalizadaChecked(total) {
    require(['jquery'], function ($) {

        totalFlywire = 0;
        cadena = '';
        let callback_id = "";

        if ($("#customCheckTotal").prop("checked") == true) {

            for (a = 1; a <= total; a++) {

                $("#customCheck_" + a).removeAttr("disabled");
                $("#customCheck_" + a).prop("checked", true);

                totalFlywire += parseFloat($("#txtMontoPendiente_" + a).val());

                cadena = cadena + $("#txtIdCCT_" + a).val() + "#";
            }
            var stringTotal = totalFlywire.toFixed(2);
            $("#divTotalizada").html("S/ " + stringTotal);

            $("#checkout-button").css("display", "block");

            stringTotal = stringTotal.replace(".", "");
            config.amount = parseInt(stringTotal);
            callback_id = agregado + "#" + cadena;
            config.callback_id = btoa(callback_id);
            config.return_url = urlRedireccion + "?v=" + btoa(cadena);

        } else {

            for (a = 1; a <= total; a++) {
                $("#customCheck_" + a).prop('checked', false);
                if (a != 1) {
                    $("#customCheck_" + a).attr("disabled", true);
                }
            }
            $("#divTotalizada").html("S/ 0.00");

            $("#checkout-button").css("display", "none");

            totalFlywire = 0;
            cadena = '';
            config.amount = 0;
            config.callback_id = "";
            config.return_url = "";
        }
    });
}

function redondearDecimales(numero, decimales) {
    numeroRegexp = new RegExp('\\d\\.(\\d){' + decimales + ',}');   // Expresion regular para numeros con un cierto numero de decimales o mas
    if (numeroRegexp.test(numero)) {         // Ya que el numero tiene el numero de decimales requeridos o mas, se realiza el redondeo
        return Number(numero.toFixed(decimales));
    } else {
        return Number(numero.toFixed(decimales)) === 0 ? 0 : numero;  // En valores muy bajos, se comprueba si el numero es 0 (con el redondeo deseado), si no lo es se devuelve el numero otra vez.
    }
}

