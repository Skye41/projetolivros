<%@page import="java.text.DateFormat"%>
<%@page import="dominio.Analise"%>
<%@page import="dominio.EntidadeDominio"%>
<%@page import="dominio.Pedido"%>
<%@page import="core.aplicacao.Resultado"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <!-- Bootstrap -->
        <link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/extra.css" rel="stylesheet">

        <title>Análise de Vendas</title>

        <!--Load the AJAX API-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <%Analise a = (Analise)request.getAttribute("analise"); %>
        <script type="text/javascript">
            // Load the Visualization API and the controls package.
            google.charts.load('current', {'packages':['corechart', 'controls', 'bar']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawDashboard);

            // Callback that creates and populates a data table,
            // instantiates a dashboard, a range slider and a pie chart,
            // passes in the data and draws it.
            function drawDashboard() {
                // Create our data table.
                
        
        var data = google.visualization.arrayToDataTable([
            ['Categoria', 'Qtde Vendida', 'Valor Vendido'],
            <%if(a != null) out.print(a.getTabela());%>
            ]);
                
                var dashboard = new google.visualization.Dashboard(
                    document.getElementById('dashboard_div'));

                // Create a range slider, passing some options
                var donutRangeSlider = new google.visualization.ControlWrapper({
                    'controlType': 'NumberRangeFilter',
                    'containerId': 'filter_div',
                    'options': {
                        'filterColumnLabel': 'Qtde Vendida'
                    }
                });

                var categoriaSelect = new google.visualization.ControlWrapper({
                    'controlType': 'CategoryFilter',
                    'containerId': 'filter3_div',
                    'options': {
                        'filterColumnLabel': 'Categoria',
                        'ui': {
                            'selectedValuesLayout': 'belowStacked',
                        }
                    }
                });
                var valorRangeSlider = new google.visualization.ControlWrapper({
                    'controlType': 'NumberRangeFilter',
                    'containerId': 'filter4_div',
                    'options': {
                        'filterColumnLabel': 'Valor Vendido'
                    }
                });

                // Create a pie chart, passing some options
                var pieChart = new google.visualization.ChartWrapper({
                    'chartType': 'PieChart',
                    'containerId': 'chart_div',
                    'options': {
                        'width': 500,
                        'height': 500,
                        'pieSliceText': 'value',
                        'legend': 'bottom'
                    },
                    // The pie chart will use the columns 'Name' and 'Donuts eaten'
                    // out of all the available ones.
                    'view': {'columns': [0, 1]}
                });
                var barchart = new google.visualization.ChartWrapper({
                    'chartType': 'Bar',
                    'containerId': 'chart2_div',
                    'options': {
                        'width': 800,
                        'height': 500,
                        'legend': 'bottom',
                        
//                        series: {
//                            0: {targetAxisIndex: 0},
//                            1: {targetAxisIndex: 1}
//                        },
//                        vAxes: {
//                            // Adds titles to each axis.
//                            0: {title: 'Qtde'},
//                            1: {title: 'Valor'}
//                        },

                        series: {
                            0: { axis: 'Qtde' }, // Bind series 0 to an axis named 'distance'.
                            1: { axis: 'Valor' } // Bind series 1 to an axis named 'brightness'.
                        },
                        axes: {
                            y: {
                                Qtde: {label: 'Qtde'}, // Left y-axis.
                                Valor: {side: 'right', label: 'Valor'} // Right y-axis.
                            }
                        }
                        //isStacked: true
                    },
                    'view': {'columns': [0, 1, 2]}
                });

                // Establish dependencies, declaring that 'filter' drives 'pieChart',
                // so that the pie chart will only display entries that are let through
                // given the chosen slider range.
                dashboard.bind([donutRangeSlider, categoriaSelect, valorRangeSlider], [pieChart, barchart]);

                // Draw the dashboard.
                dashboard.draw(data);
            }
        </script>
    </head>
    <body>
        <div>
            <%@include file = "header.jsp"%>
        </div>

        <!-- container-externo -->
	<div class="container-fluid">
            <div class="row">
		<ol class="breadcrumb">
                    <li><a href="index.jsp">Início</a></li>
                    <li><a href="#">Vendas</a></li>
                    <li class="active">Análise de Vendas</li>
		</ol>
            </div>
            
            <%Resultado resultado = (Resultado) session.getAttribute("resultado");
            if(resultado != null && resultado.getMsg() != null)
            {
            %>
            <div class="row">
                <div class="col-sm-12">
                    <div class="alert alert-<%
                    if(resultado.getMsg().contains("sucesso")) out.print("success");
                    else out.print("danger");%> alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">						 	<span aria-hidden="true">&times;</span>
			</button>
                        <%out.print(resultado.getMsg()); %>
                    </div>
                </div>
            </div>
            <%} %>
            
            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-primary">
			<div class="panel-heading">
                            <h3 class="panel-title">Análise de Vendas</h3>
			</div> <!--panel-heading-->
						
			<div class="panel-body">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="page-header">
                                        <label class="control-label">Análise de vendas por Categoria no período de:</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <form action="${pageContext.request.contextPath}/Dashboard" method="post" class="form">
                                    <div class="col-sm-2">
                                        <label for="txtDataInicio" class="control-label">Data Inicial:</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="txtDataInicio" name="txtDataInicio" 
                                                   aria-describedby="calendario" placeholder="dd/mm/aaaa"
                                                   value="<%if(a != null && a.getDtInicio() != null) 
                                                       out.print(DateFormat.getDateInstance().format(a.getDtInicio())); %>">
                                            <span class="input-group-addon" id="calendario">
                                                <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <label for="txtDataFinal" class="control-label">Data Final:</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="txtDataFinal" name="txtDataFinal" 
                                                   aria-describedby="calendario" placeholder="dd/mm/aaaa"
                                                   value="<%if(a != null && a.getDtFinal()!= null) 
                                                       out.print(DateFormat.getDateInstance().format(a.getDtFinal())); %>">
                                            <span class="input-group-addon" id="calendario">
                                                <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                                            </span>
                                            <span class="input-group-btn">
                                                <button type="submit" class="form-control btn btn-default" name="operacao" value="CONSULTAR">
                                                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                                </button>
                                            </span>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            
                            <div class="row">
                                <hr>
                            </div>
                            
                            <!--Div that will hold the dashboard-->
                            <div id="dashboard_div">
                                <!--Divs that will hold each control and chart-->
                                <div class="row">                                   
                                    <div class="col-sm-4" id="filter_div"></div>
                                    <div class="col-sm-4" id="filter3_div"></div>
                                    <div class="col-sm-4" id="filter4_div"></div>
                                </div>
                                
                                <div class="row">
                                    <div class="col-sm-5" id="chart_div"></div>
                                    <div class="col-sm-7" id="chart2_div"></div>
                                </div>
                            </div>
                        </div> <!--panel-body-->
                    </div> <!--panel-->
		</div>
            </div>
	</div> <!-- container-externo -->
        
         <!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    	<!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessário -->
    	<script src="lib/Bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
