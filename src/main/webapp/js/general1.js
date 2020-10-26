/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
