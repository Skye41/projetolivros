<%@page import="dominio.Carrinho"%>
<%@page import="dominio.EntidadeDominio"%>
<%@page import="dominio.Cliente"%>
<%EntidadeDominio uLogin = (EntidadeDominio)request.getSession().getAttribute("usuario"); %>
<nav class="navbar navbar default navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
            data-toggle="collapse" data-target="#navbar-collapse"
            aria-expanded="false">
                <span class="sr-only">Toggle navigation</span> 
                <span class="icon-bar"></span> 
                <span class="icon-bar"></span> 
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">E-commerce de Livros</a>
	</div>

	<div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a href="index.jsp">
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                        Início
                    </a>
                </li>
                
		<li>
                    <a href="ComprarLivros?operacao=CONSULTAR">
                        <span class="glyphicon glyphicon-book" aria-hidden="true"></span>
                        Buscar Livros
                    </a>
                </li>
		<li>
                    <a href="ZYXTeste.jsp">Teste</a>
		</li>
                
                <%
                if(uLogin != null && !(uLogin instanceof Cliente))
                {%>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" 
                    role="button" aria-haspopup="true" aria-expanded="false">
                        Livros <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="ConsultarLivros?operacao=CONSULTAR">Consultar Livros</a></li>
                        <li><a href="SalvarLivro?operacao=CONSULTAR">Cadastrar Novo Livro</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" 
                       role="button" aria-haspopup="true" aria-expanded="false">
                        Clientes <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="ConsultarClientes?operacao=CONSULTAR">Consultar Clientes</a></li>
                        <li><a href="SalvarSenha.jsp">Cadastrar Novo Cliente</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" 
                    role="button" aria-haspopup="true" aria-expanded="false">
                        Vendas <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
			<li><a href="Carrinho?operacao=CONSULTAR">Carrinho de Compras</a></li>
                        <li><a href="ConsultarPedidos?operacao=CONSULTAR">Consultar Pedidos</a></li>
                        <li><a href="Dashboard?operacao=CONSULTAR">Análise de Vendas</a></li>
                    </ul>
		</li>
                <%}
                %>
            </ul>
			
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="Carrinho?operacao=CONSULTAR">
                        <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
                        Meu Carrinho
                        <span class="badge">
                            <%if(request.getSession().getAttribute("carrinho") != null)
                            {
                                Carrinho p = (Carrinho)request.getSession().getAttribute("carrinho");
                                if(p.getItens() != null) 
                                    out.print(p.getItens().size());
                                else
                                    out.print(0);
                            }
                            else
                                out.print(0);%>
                        </span>
                    </a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" 
                    role="button" aria-haspopup="true" aria-expanded="false">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        Olá, <%
			if(uLogin != null)
			{
                            if(uLogin instanceof Cliente)
                            {
                                out.print(((Cliente)uLogin).getNome());
                            }
                            else
                            {
				out.print("Admin");
                            }
			}
			else
			{
                            out.print("Visitante");
			}%> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                    <%
                    if(uLogin == null)
                    {%>
                        <li><a href="Login.jsp">Login</a></li>
                        <li role="separator" class="divider"></li>
			<li><a href="SalvarSenha.jsp">Cadastre-se</a></li>
                    <%}
                    else
                    {
                    %>
                        <li><a href="Login?operacao=EXCLUIR">Logout</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="VisualizarCliente.jsp">Meu Perfil</a></li>
                        <li><a href="ConsultarPedidos?operacao=CONSULTAR">Meus Pedidos</a></li>
                        
                    <%} %>
                    </ul>
                </li>
            </ul>
	</div> <!-- /.navbar-collapse -->
    </div> <!-- /.container-fluid -->
</nav>