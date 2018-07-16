<%@page import="core.aplicacao.Resultado"%>
<%@page import="dominio.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!-- Bootstrap -->
    	<link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link href="css/extra.css" rel="stylesheet">

    	<title>Login</title>
	</head>
	<body>
		<%@include file="header.jsp"%>
		
		<!-- container-externo -->
		<div class="container-fluid">
			<div class="row">
				<ol class="breadcrumb">
		  			<li><a href="index.jsp">Início</a></li>
		  			<li class="active">Login</li>
				</ol>
			</div>
			
			<%Resultado resultado = (Resultado) request.getAttribute("resultado");
			if(resultado != null && resultado.getMsg() != null)
			{%>
			<div class="row">
				<div class="col-sm-12">
					<div class="alert alert-<%
					if(resultado.getMsg().endsWith("sucesso!")) out.print("success");
					else out.print("danger");%> alert-dismissible" role="alert">
						 <button type="button" class="close" data-dismiss="alert" aria-label="Close">
						 	<span aria-hidden="true">&times;</span>
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
							<h3 class="panel-title">Cadastro de Clientes</h3>
						</div> <!--panel-heading-->
						
						<div class="panel-body">
							<div class="container-fluid">
								<form action="${pageContext.request.contextPath}/Login" method="post" class="form">
									<div class="row">
										<div class="page-header col-sm-12">
											<label class="control-label">Login de Usuário:</label>
										</div>	
									</div> <!--row-->
									
									<div class="row">
										<input type="hidden" name="txtIdUsuario" id="txtIdUsuario">
									</div>
									
									<div class="row">
										<div class="form-group col-sm-3">
											<label for="txtEmail" class="control-label">E-mail:</label>
											<input type="text" id="txtEmail" name="txtEmail" class="form-control">
										</div>
									</div> <!-- row -->
									
									<div class="row">
										<div class="form-group col-sm-3">
											<label for="txtSenha" class="control-label">Senha:</label>
											<input type="password" id="txtSenha" name="txtSenha" class="form-control">
										</div>
									</div> <!-- row -->
									
									<div class="row form-inline col-sm-12">
						  				<div class="form-group">
						  					<button type="submit" class="btn btn-primary" id="operacao" 
						  					name="operacao" value="CONSULTAR">
						  						<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
						  						ENTRAR
						  					</button>
						  				</div>
						  			</div> <!--row-->
								</form>
							</div> <!--container-->
						</div> <!--panel-body-->
					</div> <!--panel-->
				</div>
			</div>
		</div> <!-- container-externo -->				
		<!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    	<!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessÃ¡rio -->
    	<script src="lib/Bootstrap/js/bootstrap.min.js"></script>
	</body>
</html>