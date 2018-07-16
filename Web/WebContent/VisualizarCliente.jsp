<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="dominio.Cartao"%>
<%@page import="dominio.Endereco"%>
<%@page import="dominio.Telefone"%>
<%@page import="java.text.DateFormat"%>
<%@page import="dominio.Cliente"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <!-- Bootstrap -->
        <link href="lib/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/extra.css" rel="stylesheet">

        <title>Cadastro de Clientes</title>
    </head>

    <body>
        <%@include file="header.jsp"%>

        <!-- container-externo -->
        <div class="container-fluid">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="index.jsp">Início</a></li>
                    <li><a href="#">Clientes</a></li>
                    <li class="active">Consultar Clientes</li>
                </ol>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Cadastro de Cliente</h3>
                        </div> <!--panel-heading-->

                        <div class="panel-body">
                            <div class="container-fluid">
                                <% Cliente c = null;
                                if(request.getSession().getAttribute("usuario") instanceof Cliente)
                                {
                                    c = (Cliente) request.getSession().getAttribute("usuario");
                                }
                                else
                                {
                                    c = (Cliente) request.getAttribute("cliente");
                                }
                                String texto = null;
                                if (c != null)
                                {
                                %>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <div class="page-header col-sm-12">
                                            <label class="control-label">Dados do Cliente:</label>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <a class="btn btn-primary" 
                                           href="<%out.print("SalvarCliente?txtCodigo=" + c.getId() + "&operacao=VISUALIZAR&alterar=true"); %>">
                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                            ALTERAR
                                        </a>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="txtNomeCliente" class="control-label col-sm-4">Nome do Cliente:</label>
                                            <p class="form-control-static" name="txtNomeCliente" id="txtNomeCliente">
                                                <% out.print(c.getNome()); %>
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label for="txtDtNasc" class="control-label col-sm-4">Data de Nascimento:</label>
                                            <p class="form-control-static" name="txtDtNasc" id="txtDtNasc">
                                                <% out.print(DateFormat.getDateInstance().format(c.getDtNascimento())); %>
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label for="txtCpf" class="control-label col-sm-4">CPF:</label>
                                            <p class="form-control-static" name="txtCpf" id="txtCpf">
                                                <% out.print(c.getCPF()); %>
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label for="txtGenero" class="control-label col-sm-4">Gênero:</label>
                                            <p class="form-control-static" name="txtGenero" id="txtGenero">
                                                <% out.print(c.getGenero().getValor()); %>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="txtRanking" class="control-label col-sm-4">Ranking:</label>
                                            <p class="form-control-static" name="txtRanking" id="txtRanking">
                                                <% out.print(c.getRanking()); %>
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label for="txtStatus" class="control-label col-sm-4">Status:</label>
                                            <p class="form-control-static" name="txtStatus" id="txtStatus">
                                                <% if (c.isStatus())
                                                {
                                                    out.print("Ativo");
                                                }
                                                else
                                                {
                                                    out.print("Inativo");
                                                }%>
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label for="txtEmail" class="control-label col-sm-4">E-mail:</label>
                                            <p class="form-control-static" name="txtEmail" id="txtEmail">
                                                <% out.print(c.getUsuario().getEmail()); %>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <%for (Telefone t : c.getTelefones())
                                        {%>
                                        <div class="form-group">
                                            <label for="txtTelefone" class="control-label col-sm-4">
                                                <% out.print(t.getTipo().getValor()); %>:
                                            </label>
                                            <p class="form-control-static" name="txtTelefone" id="txtTelefone">
                                                <% out.print(t.getDdd() + " " + t.getNumero()); %>
                                            </p>
                                        </div>
                                        <% } %>
                                    </div>

                                    <div class="form-group">
                                        <a class="btn btn-primary" 
                                           href="<%out.print("SalvarSenha?txtIdUsuario=" + c.getUsuario().getId() + "&operacao=VISUALIZAR"); %>">
                                            <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                                            ALTERAR SENHA
                                        </a>
                                    </div>

                                    <div class="form-group">
                                        <div class="page-header col-sm-12">
                                            <label class="control-label">Endereço:</label>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <form action="SalvarEndereco.jsp"  method="post" class="form">
                                            <input type="hidden" name="txtIdCliente" id="txtIdCliente" value="<%out.print(c.getId());%>">
                                            <button type="submit" class="btn btn-success">
                                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                                ADICIONAR ENDEREÇO
                                            </button>
                                        </form>
                                    </div>

                                    <%for (Endereco e : c.getEnderecos())
                                        {
                                    %>
                                    <div class="form-group">
                                        <label for="txtEndereco" class="control-label col-sm-2">
                                            <% out.print(e.getTipoEndereco().getValor()); %>:
                                        </label>
                                        <p class="form-control-static" name="txtEndereco" id="txtEndereco">
                                            <% texto = e.getTipoLogradouro() + " " + e.getLogradouro() + ", "
                                                        + e.getNumero() + " - " + e.getTipoResidencia() + ", "
                                                        + e.getBairro() + ", " + e.getCidade() + " - " + e.getEstado() + " - "
                                                        + e.getPais();
                                                                                                            out.print(texto); %>
                                            <a class="btn btn-primary btn-xs" 
                                               href="<%out.print("SalvarEndereco?txtIdEndereco=" + e.getId() + "&txtIdCliente=" + c.getId() + "&operacao=VISUALIZAR"); %>">
                                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                                ALTERAR
                                            </a>

                                            <%if (!e.getTipoEndereco().getValor().equals("Cadastro"))
                                                                                                    { %>
                                            <a class="btn btn-danger btn-xs" 
                                               href="<%out.print("SalvarEndereco?txtIdEndereco=" + e.getId() + "&operacao=EXCLUIR"); %>">
                                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                                EXCLUIR
                                            </a>
                                            <%} %>
                                        </p>
                                        <%if (e.getObservacoes() != null)
                                                                                            {%>
                                        <label for="txtObs" class="control-label col-sm-2">Obs:</label>
                                        <p class="form-control-static" name="txtObs" id="txtObs">
                                            <% out.print(e.getObservacoes()); %>
                                        </p>
                                        <%} %>
                                    </div>
                                    <%} %>

                                    <div class="form-group">
                                        <div class="page-header col-sm-12">
                                            <label class="control-label">Cartões:</label>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <a class="btn btn btn-success" 
                                           href="<%out.print("SalvarCartao?txtIdCliente=" + c.getId() + "&operacao=CONSULTAR");%>">
                                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                            ADICIONAR CARTÃO
                                        </a>
                                    </div>

                                    <%
                                        if (c.getCartoes() != null)
                                        {
                                            for (Cartao ct : c.getCartoes())
                                            {
                                    %>
                                    <div class="form-group">
                                        <label for="txtCartao" class="control-label col-sm-2">
                                            <%out.print(ct.getBandeira().getNomeBandeira());%>
                                        </label>
                                        <p class="form-control-static" name="txtCartao" id="txtCartao">
                                            <%out.print("*****" + ct.getNumero().substring(ct.getNumero().length() - 4));%>
                                        </p>
                                    </div>
                                    <%	}//for 
                                                                            }// if cartao%>
                                </div>
                                <%} %>
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