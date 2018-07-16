<%@page import="dominio.Telefone"%>
<%@page import="dominio.TipoTelefone"%>
<%@page import="java.text.DateFormat"%>
<%@page import="dominio.Cliente"%>
<%@page import="java.util.Set"%>
<%@page import="dominio.Genero"%>
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
        <script src="js/cadastroClienteCtrl.js"></script>

        <title>Cadastro de Clientes</title>
    </head>
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
                            <h3 class="panel-title">Cadastro de Clientes</h3>
                        </div> <!--panel-heading-->

                        <div class="panel-body">
                            <div class="container-fluid">
                                <form action="${pageContext.request.contextPath}/<%
                                if(request.getParameter("txtCodigo") != null)
                                {
                                    out.print("SalvarCliente");
                                }
                                else
                                {
                                    out.print("SalvarEndereco.jsp");}%>" method="post" class="form">
                                
                                    <div class="row">
                                    <%
                                    Set<String> chaves = request.getParameterMap().keySet();
                                    for(String chave : chaves)
                                    {
                                        if(!chave.equals("operacao"))
                                        {
                                            if (request.getParameterValues(chave).length > 1)
                                            {
                                                for (String valor : request.getParameterValues(chave))
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
                                    Cliente c = null;
                                    if(request.getSession().getAttribute("usuario") instanceof Cliente)
                                    {
                                        c = (Cliente)request.getSession().getAttribute("usuario");
                                    }
                                    else
                                    {
                                        c = (Cliente) request.getAttribute("cliente");
                                    }
                                    Telefone t = null;
                                    if (c != null)
                                    {
                                        t = c.getTelefones().get(0);
                                    }
                                    %>
                                    </div> <!-- row -->

                                    <div class="row">
                                        <div class="page-header col-sm-12">
                                            <label class="control-label">Dados do Cliente:</label>
                                        </div>	
                                    </div> <!--row-->

                                    <div class="row">
                                        <div class="form-group col-sm-2">
                                            <label for="txtCodigo" class="control-label">Código:</label>
                                            <input type="text" id="txtCodigo" name="txtCodigo" class="form-control" 
                                                readonly="true" value="<%
                                                if (c != null) out.print(c.getId());%>">
                                        </div>

                                        <div class="form-group col-sm-2">
                                            <label for="txtDtNasc" class="control-label">Data de Nascimento:</label>
                                            <div class="input-group">
                                                <input type="text" id="txtDtNasc" name="txtDtNasc" class="form-control"
                                                    aria-describedby="calendario" placeholder="dd/mm/aaaa" value="<%
                                                    if (c != null) out.print(DateFormat.getDateInstance().format(c.getDtNascimento()));%>" required="true">
                                                <span class="input-group-addon" id="calendario">
                                                    <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div> <!--row-->

                                    <div class="row">
                                        <div class="form-group col-sm-4">
                                            <label for="txtNomeCliente" class="control-label">Nome:</label>
                                            <input type="text" id="txtNomeCliente" name="txtNomeCliente" class="form-control"
                                                value="<%if (c != null) out.print(c.getNome());%>" required="true">
                                        </div>
                                    </div> <!--row-->

                                    <div class="row">
                                        <div class="form-group col-sm-2">
                                            <label for="cbGenero" class="control-label">Gênero:</label>
                                            <select id="cbGenero" name="cbGenero" class="form-control">
                                            <%for (Genero genero : Genero.values())
                                            {%>
                                                <option value="<%out.print(genero.toString());%>"<%
                                                    if (c != null && genero.getValor().equals(c.getGenero().getValor()))
                                                        out.print(" selected=\"selected\"");%>>
                                                    <%out.print(genero.getValor());%></option>
                                            <%}%>
                                            </select>
                                        </div>

                                        <div class="form-group col-sm-2">
                                            <label for="txtCpf" class="control-label">CPF:</label>
                                            <input type="text" id="txtCpf" name="txtCpf" class="form-control" value="<%
                                            if (c != null) out.print(c.getCPF());%>" required="true">
                                        </div>
                                    </div> <!--row-->

                                    <div class="row">
                                        <input type="hidden" name="txtIdTelefone" id="txtIdTelefone"
                                            value="<%if (t != null) out.print(t.getId());%>" required="true">
                                    </div>

                                    <div class="row">
                                        <div class="form-group col-sm-4">
                                            <label for="cbTipoTelefone" class="control-label">Tipo de telefone:</label>
                                            <select id="cbTipoTelefone" name="cbTipoTelefone" class="form-control">
                                            <%for (TipoTelefone tipo : TipoTelefone.values())
                                            { %>
                                                <option value="<%out.print(tipo.toString());%>"<%
                                                    if (t != null && t.getTipo().getValor().equals(tipo.getValor()))
                                                        out.print(" selected=\"selected\"");%>>
                                                    <%out.print(tipo.getValor());%>
                                                </option>
                                            <%}%>
                                            </select>
                                        </div>
                                    </div> <!--row-->

                                    <div class="row">
                                        <div class="form-group col-sm-1">
                                            <label for="txtDDD" class="control-label">DDD:</label>
                                            <input type="text" id="txtDDD" name="txtDDD" class="form-control" 
                                                value="<%if (t != null) out.print(t.getDdd());%>" required="true">
                                        </div>

                                        <div class="form-group col-sm-3">
                                            <label for="txtNumeroTelefone" class="control-label">Número:</label>
                                            <input type="text" id="txtNumeroTelefone" name="txtNumeroTelefone" class="form-control"
                                                value="<%if (t != null) out.print(t.getNumero());%>" required="true">
                                        </div>
                                    </div> <!--row-->

                                    <div class="row">
                                        <div class="form-group col-sm-2">
                                            <label for="txtRanking" class="control-label">Ranking:</label>
                                            <input type="text" id="txtRanking" name="txtRanking" class="form-control" 
                                                readonly="true" value="<%if (c != null) out.print(c.getRanking());%>">
                                        </div>

                                        <div class="form-group col-sm-2">
                                            <label for="cbStatus" class="control-label">Status:</label>
                                            <select id="cbStatus" name="cbStatus" class="form-control">
                                                <option value="true">Ativo</option>
                                                <option value="false">Inativo</option>
                                            </select>
                                        </div>
                                    </div> <!--row-->

                                    <div class="row form-inline col-sm-12">
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary" id="operacao" 
                                                name="operacao" value="<%
                                                if (c != null)
                                                {
                                                    out.print("ALTERAR");
                                                }
                                                else
                                                {
                                                    out.print("SALVAR");
                                                }%>">
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
        <!-- Inclui todos os plugins compilados (abaixo), ou inclua arquivos separadados se necessário -->
        <script src="lib/Bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>