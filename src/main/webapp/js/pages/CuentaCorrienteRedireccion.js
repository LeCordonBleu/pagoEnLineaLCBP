/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var lsSession = JSON.parse(localStorage.getItem("ls_session"));
var lsToken = localStorage.getItem("ls_token");

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

require(['jquery'], function ($) {


    let methods = {
        enviaVariables(variable1, variable2, variable3) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: 'get',
                    url: '../ValidacionServlet?accion=validarRespuesta',
                    crossDomain: true,
                    dataType: 'JSON',
                    // Añade un header:
                    headers: {'Authorization': 'Bearer ' + lsToken},
                    data: 'txt_v1=' + variable1 + '&txt_v2=' + variable2 + '&txt_v3=' + variable3 + '&txt_data=' + JSON.stringify(lsSession),
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

    function consulta() {
        startLoading();

        var v1 = getParameterByName('v');
        var v2 = getParameterByName('reference');
        var v3 = getParameterByName('status');

        if (v1 == "" || v2 == "" || v3 == "") {
            cerrar();
        } else {
            methods.enviaVariables(v1, v2, v3).then(data => {
                console.log(data);
                stopLoading();

                if (data.existe == 1) {
                    print_resultado = ``;

                    if (data.estado == 1) {
                        print_resultado = `<div class="alert alert-icon alert-success" role="alert">
                                                <i class="fe fe-bell mr-2" aria-hidden="true"></i>
                                                <h4>Información</h4>
                                                <p>Se esta procesando su pago, los cambios se veran reflejados cuando termine el proceso.
                                                </p>
                                                <div class="btn-list">
                                                    <button class="btn btn-success" type="button" onclick="regresar();">Regresar</button>
                                                    <button class="btn btn-secondary" type="button" onclick="cerrar();">Cerrar Sesión</button>
                                                </div>
                                            </div>`;
                    } else if (data.estado == 2) {
                        print_resultado = `<div class="alert alert-icon alert-primary" role="alert">
                                                <i class="fe fe-bell mr-2" aria-hidden="true"></i>
                                                <h4>Información</h4>
                                                <p>Se esta procesando su pago, los cambios se veran reflejados cuando termine el proceso.
                                                </p>
                                                <div class="btn-list">
                                                    <button class="btn btn-primary" type="button" onclick="regresar();">Regresar</button>
                                                    <button class="btn btn-secondary" type="button" onclick="cerrar();">Cerrar Sesión</button>
                                                </div>
                                            </div>`;

                    } else if (data.estado == 0) {
                        print_resultado = `<div class="alert alert-icon alert-danger" role="alert">
                                                <i class="fe fe-triangle mr-2" aria-hidden="true"></i>
                                                <h4>Error</h4>
                                                <p>Ha ocurrido un error al momento de la transaccion, por favor volver a intentar en unos minutos</p>
                                                <div class="btn-list">
                                                    <button class="btn btn-danger" type="button" onclick="regresar();">Regresar</button>
                                                    <button class="btn btn-secondary" type="button" onclick="cerrar();">Cerrar Sesión</button>
                                                </div>
                                            </div>`;

                    }

                    $("#respuesta").html(print_resultado);
                } else {
                    cerrar();
                }
            });
        }
    }

    consulta();

});
