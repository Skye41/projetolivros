/**
 * 
 */
angular.module("pedido", []); //cria o módulo
angular.module("pedido").controller("pedidoCtrl", function($scope)
    {
	$scope.cartao = [];
	
	$scope.addCartao = function()
	{
            var idCartao = $scope.cartao.length+1;
            $scope.cartao.push({id: 'cbCartao'+idCartao});
	}

	$scope.deleteCartao = function()
	{
            var idCartao = $scope.cartao.length-1;
            $scope.cartao.pop();
	}

	$scope.zerarCartao = function()
	{
            $scope.cartao = [{id: 'cbCartao1'}];
	}
        
       // $scope.cbStatus = {'APROVADO', 'REPROVADO', 'TRANSPORTE', 'ENTREGUE', 'TROCA', 'TROCADO', 'PROCESSAMENTO'};
    }); //recupera o módulo e cria a controller