/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require(['jquery'], function ($) {

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }

    let methods = {
        enviaVariables(variable1, variable2, variable3, variable4) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: 'get',
                    url: '../ValidacionServlet?accion=validarVariables',
                    crossDomain: true,
                    dataType: 'JSON',
                    data: 'txt_v1=' + variable1 + '&txt_v2=' + variable2 + '&txt_v3=' + variable3 + '&txt_v4=' + variable4,
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

                        if (jqXHR.status == 500 || jqXHR.status == 404) {
                            window.location = '../error.jsp'
                        }
                        reject('No se pudo realizar la petición: ' + jqXHR)
                    }
                })
            })
        },
    }


    function consultaCuentaCorriente() {

        startLoading();

        var v1 = getParameterByName('v1');
        var v2 = getParameterByName('v2');
        var v3 = getParameterByName('v3');
        var v4 = getParameterByName('v4');

        methods.enviaVariables(v1, v2, v3, v4).then(data => {

            stopLoading();
            
            if (data.existe == 0) {
                localStorage.clear();
                window.location.href = "index.jsp";
            } else if (data.existe == 1) {
                localStorage.clear();
                localStorage.setItem("ls_session", JSON.stringify(data));
                localStorage.setItem("ls_token", data.token);
                window.location.href = "cuentaCorriente.jsp";
            }

        });
    }

    consultaCuentaCorriente();

});

