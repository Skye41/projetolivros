<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="dominio.BandeiraCartao"%>
<%@page import="dominio.EntidadeDominio"%>
<%@page import="core.aplicacao.Resultado"%>
<%@page import="java.text.DateFormat"%>
<%@page import="dominio.Cartao"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <!-- Bootstrap -->
        <link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/extra.css" rel="stylesheet">

        <title>Cadastro de Cliente</title>
    </head>
    <body>
        <%@include file="header.jsp"%>

        <!-- container-externo -->
        <div class="container-fluid">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="index.jsp">Início</a></li>
                    <li><a href="#">Clientes</a></li>
                    <li class="active">Cadastrar Novo Cartão</li>
                </ol>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Cadastro de Clientes</h3>
                        </div> <!--panel-heading-->

                        <div class="panel-body">
                            <div class="container-fluid">
                                <form action="${pageContext.request.contextPath}/SalvarCartao" method="post" class="form">
                                    <div class="row">
                                        <div class="page-header col-sm-12">
                                            <label class="control-label">Cartão:</label>
                                        </div>	
                                    </div> <!--row-->

                                    <%Cartao c = (Cartao) request.getAttribute("cartao");
                                    String id = request.getParameter("txtIdCliente");
                                    Resultado bandeiras = (Resultado) request.getSession().getAttribute("bandeiras");%>

                                    <div class="row">
                                        <input type="hidden" id="txtIdCliente" name="txtIdCliente" 
                                            value="<%
                                            if(c != null)
                                               out.print(c.getCliente().getId());
                                            else if(id != null)
                                                out.print(id);%>">

                                        <input type="hidden" id="txtIdCartao" name="txtIdCartao" 
                                            value="<%if(c != null) out.print(c.getId());%>">
                                    </div> <!--row-->

                                    <div class="row">
                                        <div class="form-group col-sm-2">
                                            <label for="cbBandeiraCartao" class="control-label">Bandeira:</label>
                                            <select id="cbBandeiraCartao" name="cbBandeiraCartao" class="form-control">
                                                <%
                                                if(bandeiras != null)
                                                {
                                                    for(EntidadeDominio e : bandeiras.getEntidades())
                                                    {
                                                        BandeiraCartao b = (BandeiraCartao) e;
                                                %>
                                                <option value="<%out.print(b.getId());%>"<%
                                                    if(c != null && c.getId() == b.getId())
                                                        out.print(" selected=\"selected\"");%>>
                                                    <%out.print(b.getNomeBandeira());%>
                                                </option>
                                                <%  }
                                                } %>
                                            </select>
                                        </div>

                                        <div class="form-group col-sm-3">
                                            <label for="txtNumeroCartao" class="control-label">Número do Cartão:</label>
                                            <input type="text" id="txtNumeroCartao" name="txtNumeroCartao" class="form-control"
                                                value="<%if(c != null) out.print(c.getNumero());%>">
                                        </div>

                                        <div class="form-group col-sm-2">
                                            <label for="txtCodigoSeguranca" class="control-label">Código de Segurança:</label>
                                            <input type="text" id="txtCodigoSeguranca" name="txtCodigoSeguranca" class="form-control"
                                                   value="<%if(c != null) out.print(c.getCodigoSeguranca());%>">
                                        </div>
                                    </div> <!--row-->

                                    <div class="row">
                                        <div class="form-group col-sm-5">
                                            <label for="txtNomeCartao" class="control-label">Nome no cartão:</label>
                                            <input type="text" id="txtNomeCartao" name="txtNomeCartao" class="form-control"
                                                   value="<%if(c != null) out.print(c.getNomeCartao());%>">
                                        </div>

                                        <div class="form-group col-sm-2">
                                            <label for="txtDtVenc" class="control-label">Data de Vencimento:</label>
                                            <div class="input-group">
                                                <input type="text" id="txtDtVenc" name="txtDtVenc" class="form-control" aria-describedby="calendario"
                                                    value="<%if(c != null) 
                                                        out.print(DateFormat.getDateInstance().format(c.getDtVencimento()));%>">
                                                <span class="input-group-addon" id="calendario">
                                                    <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div> <!--row-->
                                    
                                    <div class="row">
                                        <div class="form-group col-sm-5 checkbox">
                                            <label for="chPreferencial" class="control-label">
                                                <input type="checkbox" id="chPreferencial" name="chPreferencial">
                                                Definir como cartão preferencial
                                            </label>
                                        </div>
                                    </div>

                                    <div class="row form-inline col-sm-12">
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary" id="operacao" name="operacao" value="<%
                                                if(c != null)
                                                {
                                                    out.print("ALTERAR");
                                                }
                                                else
                                                {
                                                    out.print("SALVAR");
                                                }
                                            %>">
                                                <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                                                SALVAR
                                            </button>
                                        </div>
                                        <div class="form-group">
                                            <button type="reset" class="btn btn-default">
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