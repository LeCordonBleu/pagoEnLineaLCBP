<%-- 
    Document   : cuentacorriente
    Created on : 26/09/2020, 08:16:23 PM
    Author     : Eduardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../componentes/a.jsp"%>
<%@include file="../componentes/b.jsp"%>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-9 col-xl-9">
            <div class="card">
                <div class="card-status card-status-left bg-blue"></div>
                <div class="card-header">
                    <h3 class="card-title"><b>DATOS DEL ESTUDIANTE</b></h3>
                </div>
                <div class="card-body" id="divEstudiante">
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-3 col-xl-3">
            <div class="card">
                <div class="card-status card-status-left bg-blue"></div>
                <div class="card-header">
                    <h3 class="card-title"><b>MONTO A CANCELAR</b></h3>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-xl-12">
                            <h2 class="text-right" id="divTotalizada">S/ 0.00</h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xl-12">
                            <button id="checkout-button" type="button" class="btn btn-info  btn-block" style="display:none"><i class="fe fe-credit-card mr-2"></i>Pagar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-xl-12">
            <div class="card">
                <div class="card-status bg-blue"></div>
                <div class="card-header">
                    <h3 class="card-title"><b>CUENTA CORRIENTE</b></h3>
                </div>
                <div class="card-body">
                    <div class="table-responsive" id="divListadoCuentaCorriente">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://payment.flywire.com/assets/js/checkout.js"></script>
<script src="../js/pages/CuentaCorriente.js"></script>
<%@include file="../componentes/c.jsp"%>

