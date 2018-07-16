<%@page import="dominio.Endereco"%>
<%@page import="java.util.Map"%>
<%@page import="dominio.TipoEndereco"%>
<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="cadastroCliente">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!-- Bootstrap -->
    	<link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link href="css/extra.css" rel="stylesheet">

    	<!-- Angular-->
    	<script src="lib/Angular/angular.js"></script>
		<script src="lib/Angular/angular-locale_pt-br.js"></script>
		<script src="lib/Angular/angular-messages.js"></script>
		
		<title>Cadastro de Clientes</title>
	</head>
	<body>
		<body ng-controller="cadastroClienteCtrl">
		<%@include file="header.jsp"%>
		
		<!-- container-externo -->
		<div class="container-fluid">
			<div class="row">
				<ol class="breadcrumb">
		  			<li><a href="index.jsp">Início</a></li>
		  			<li><a href="#">Clientes</a></li>
		  			<li class="active">Cadastrar Novo Cliente</li>
				</ol>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Endereço de Cadastro</h3>
						</div> <!--panel-heading-->
						
						<div class="panel-body">
							<div class="container-fluid">
								<form action="${pageContext.request.contextPath}/<%
                                                                    String cliente = request.getParameter("txtIdCliente");
								if(cliente != null)
								    out.print("SalvarEndereco");
								else
									out.print("SalvarCliente");%>" method="post" class="form">
								<%Endereco end = (Endereco)request.getAttribute("endereco"); %>
									
									<div class="row">
									<%
									    Set<String> chaves = request.getParameterMap().keySet();
									    for (String chave : chaves)
									    {
											if(!chave.equals("operacao"))
											{
												if(request.getParameterValues(chave).length > 1)
												{
												    for(String valor : request.getParameterValues(chave))
												    {
														out.print("<input type=\"hidden\" name=\"" + chave + "\" id=\"" + chave + "\" value=\""
															+ valor + "\">");
												    }
												}
												else
												{
												    out.print("<input type=\"hidden\" name=\"" + chave + "\" id=\"" + chave + "\" value=\""
														+ request.getParameter(chave) + "\">");
												}
											}
									    }
									%>
									</div>
									
									<div class="row">
										<div class="form-group col-sm-2">
											<label for="cbTipoEndereco" class="control-label">Tipo de endereço:</label>
											<select id="cbTipoEndereco" name="cbTipoEndereco" class="form-control"<%
											if(cliente == null)
												out.print(" readonly=\"true\"");%>>
												<%
												for(TipoEndereco tipo : TipoEndereco.values()) 
												{ %>
													<option value="<%out.print(tipo.toString());%>"<%
													if(end != null && tipo.getValor().equals(end.getTipoEndereco().getValor())) 
													    out.print(" selected=\"selected\"");
													else if(cliente == null && tipo.getValor().equals("Cadastro"))
														out.print(" selected=\"selected\"");
													else if(cliente == null)
														out.print(" disabled=\"disabled\"");%>> 
														<% out.print(tipo.getValor()); %>
													</option>
												<%}%>
											</select>
										</div>
									</div> <!--row-->
									
									<div class="row">
										<div class="form-group col-sm-2">
											<label for="txtTipoResidencia" class="control-label">Tipo de Residência:</label>
											<input type="text" id="txtTipoResidencia" name="txtTipoResidencia" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getTipoResidencia() + "\"");%>>
										</div>
										
										<div class="form-group col-sm-2">
											<label for="txtTipoLogradouro" class="control-label">Tipo de Logradouro:</label>
											<input type="text" id="txtTipoLogradouro" name="txtTipoLogradouro" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getTipoLogradouro() + "\"");%>>
										</div>
										
										<div class="form-group col-sm-5">
											<label for="txtLogradouro" class="control-label">Logradouro:</label>
											<input type="text" id="txtLogradouro" name="txtLogradouro" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getLogradouro() + "\"");%>>
										</div>
										
										<div class="form-group col-sm-1">
											<label for="txtNumeroCasa" class="control-label">Número:</label>
											<input type="text" id="txtNumeroCasa" name="txtNumeroCasa" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getNumero() + "\"");%>>
										</div>
									</div> <!--row-->
									
									<div class="row">
										<div class="form-group col-sm-2">
											<label for="txtBairro" class="control-label">Bairro:</label>
											<input type="text" id="txtBairro" name="txtBairro" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getBairro() + "\"");%>>
										</div>
										
										<div class="form-group col-sm-2">
											<label for="txtCep" class="control-label">CEP:</label>
											<input type="text" id="txtCep" name="txtCep" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getCep() + "\"");%>>
										</div>
										
										<div class="form-group col-sm-2">
											<label for="txtCidade" class="control-label">Cidade:</label>
											<input type="text" id="txtCidade" name="txtCidade" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getCidade() + "\"");%>>
										</div>
										
										<div class="form-group col-sm-2">
											<label for="txtEstado" class="control-label">Estado:</label>
											<input type="text" id="txtEstado" name="txtEstado" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getEstado() + "\"");%>>
										</div>
										
										<div class="form-group col-sm-2">
											<label for="txtPais" class="control-label">País:</label>
											<input type="text" id="txtPais" name="txtPais" class="form-control"
											<%if(end != null) out.print("value=\"" + end.getPais() + "\"");%>>
										</div>
									</div> <!--row-->
									
									<div class="row">
										<div class="form-group col-sm-10">
											<label for="txtOBS" class="control-label">Observações:</label>
											<textarea id="txtOBS" name="txtOBS" class="form-control"
											style="resize: none;"><%
												if(end != null && end.getObservacoes() != null)
												    out.print(end.getObservacoes());
											%></textarea>
										</div>
									</div> <!--row-->
									
									<div class="row form-inline col-sm-12">
						  				<div class="form-group">
						  					<button type="submit" class="btn btn-primary" id="operacao" 
						  					name="operacao" value="<%
						  					if(end != null) out.print("ALTERAR");
						  					else out.print("SALVAR");%>">
						  						<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
						  						SALVAR
						  					</button>
						  				</div>
						  				<div class="form-group">
						  					<button type="submit" class="btn btn-danger" id="operacao" 
						  					name="operacao" value="EXCLUIR">
						  						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						  						EXCLUIR
						  					</button>
						  				</div>
						  				<div class="form-group">
						  					<button type="reset" class="btn btn-default" ng-click="zerarTelefone(); zerarCartoes();">
						  						<span class="glyphicon glyphicon-erase" aria-hidden="true"></span>
						  						REINICIAR
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