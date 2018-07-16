/**
 * 
 */
angular.module("cadastroCliente", []); //cria o módulo
angular.module("cadastroCliente").controller("cadastroClienteCtrl", function($scope)
		{
	$scope.telefones = [{id: 'cbTelefone1'}];
	$scope.cartoes = [];

	$scope.addTelefone = function()
	{
		var idTelefone = $scope.telefones.length+1;
		$scope.telefones.push({id: 'cbTelefone'+idTelefone});

	}

	$scope.deleteTelefone = function()
	{
		var idTelefone = $scope.telefones.length-1;
		$scope.telefones.pop();

	}

	$scope.zerarTelefone = function()
	{
		$scope.telefones = [{id: 'cbTelefone1'}];
	}

	$scope.addCartoes = function()
	{
		var idCartoes = $scope.cartoes.length+1;
		$scope.cartoes.push({id: 'cbCartoes'+idCartoes});

	}

	$scope.deleteCartoes = function()
	{
		var idCartoes = $scope.cartoes.length-1;
		$scope.cartoes.pop();

	}

	$scope.zerarCartoes = function()
	{
		$scope.cartoes = [];
	}

		}); //recupera o módulo e cria a controller