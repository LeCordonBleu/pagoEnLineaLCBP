/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var lsSession = JSON.parse(localStorage.getItem("ls_session"));
var lsToken = localStorage.getItem("ls_token");

if (lsSession == null || lsToken == null) {
    cerrar();
}



function startLoading() {
    require(['jquery', 'loadingModal'], function ($, loadingModal) {
        $('body').loadingModal({text: ''}).loadingModal('animation', 'circle').loadingModal('backgroundColor', 'black');
    });
}
function stopLoading() {
    require(['jquery', 'loadingModal'], function ($, loadingModal) {
        $('body').loadingModal('destroy');
    });
}

function regresar() {
    location.href = "cuentaCorriente.jsp";
}

function cerrar() {
    localStorage.clear();
    location.href = "index.jsp";
}