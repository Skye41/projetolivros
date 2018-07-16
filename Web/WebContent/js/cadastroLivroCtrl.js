/**
 * 
 */
angular.module("cadastroLivros", []); //cria o módulo
angular.module("cadastroLivros").controller("cadastroLivrosCtrl", function($scope)
		{
	$scope.autores = [{id: 'cbAutor1'}];
	$scope.categorias = [{id: 'cbCategoria1'}];

	$scope.addAutor = function()
	{
		var idAutor = $scope.autores.length+1;
		$scope.autores.push({id: 'cbAutor'+idAutor});

	}

	$scope.deleteAutor = function()
	{
		var idAutor = $scope.autores.length-1;
		$scope.autores.pop();

	}

	$scope.zerarAutor = function()
	{
		$scope.autores = [{id: 'cbAutor1'}];
	}

	$scope.addCategoria = function()
	{
		var idCategoria = $scope.categorias.length+1;
		$scope.categorias.push({id: 'cbCategoria'+idCategoria});

	}

	$scope.deleteCategoria = function()
	{
		var idCategoria = $scope.categorias.length-1;
		$scope.categorias.pop();

	}

	$scope.zerarCategoria = function()
	{
		$scope.categorias = [{id: 'cbCategoria1'}];
	}

		}); //recupera o módulo e cria a controller