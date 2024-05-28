<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@page import="shopping.desafio.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>

<c:set var = "productquantity" value ="${sessionScope.productsquantity}"/>
<c:set var = "products" value ="${sessionScope.products}"/>
<c:set var = "labels" value ="${sessionScope.labels}"/>
<c:set var = "order_id" value ="${sessionScope.order_id}"/>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Style.css">
<meta charset="UTF-8">
<title>Shopping</title>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	
<script type="text/javascript" src="Script.js"></script>

<body>
	<ul id="menu" class="menu">

		<li class="home"><a href="Shopping">Home</a></li>
		<li  class="dropdown"><a class="dropbtn">Labels</a>
			<ul class="dropdown-content">
				<li>
					<form action="Shopping" method="post">
						<input type="text" id="searchlabel" placeholder="Procurar label..">
					</form>

				</li>
				<c:if test = "${labels != null}">				
					<c:forEach var="label" items="${labels}">
					<p>
						<label>${label.name}</label>  
						<a style="color:red;display:inline-block;" href="Shopping?filter=remove&&labelname=${label.name}">&#10006;</a>
					</p>	
					</c:forEach>
				</c:if>
			</ul></li>

		<li class="dropdown"><a class="dropbtn">Produtos</a>
			<ul class="dropdown-content">
				<li>
					<form action="Shopping" method="post">
						<input type="text" id="searchproduct" name="byname"
							placeholder="Procurar produto..">
					</form>

				</li>
			</ul></li>

		<li class="cart"><a id="cart">Carrinho</a></li>
	</ul>

	<div align="center">

		<ul id="product">
			<c:if test ="${products != null}">
				<c:forEach var = "product" items = "${products}">
			<li><a onclick="openForm('${product.name}')">${product.name}</a></li>
			<div class="form-popup" id="${product.name}">
				<form class="form-container" action="Shopping" method="post">
					<input type="hidden" name="addproduct" value="yes"> <input
						type="hidden" name="prodid" value="${product.id}">
					<table border="1" cellpadding="5">
						<caption>
							<h2>${product.name}</h2>
						</caption>
						<tr>
							<th>Nome</th>
							<td>${product.name}</td>
						</tr>
						<tr>
							<th>Categorias</th>
							<td>${product.getLabelsString()}</td>
						</tr>
						<tr>
							<th padding:="5">Price</th>
							<td>${product.getPrice().setScale(2)}</td>
						</tr>

					</table>
					<p></p>
					<div class="quantity">
						<button type="button" class="minus"
							onclick="decrement('Input=${product.getName()}')">&minus;</button>
						<input id="Input=${product.getName()}" name="quantity"
							type="number" class="input-box" value="1" min="1" max="10">
						<button type="button" class="plus"
							onclick="increment('Input=${product.getName()}')">&plus;</button>
					</div>
					<p></p>
					<button type="submit" class="btn">Adicionar ao Carrinho</button>
					<button type="button" class="btn cancel"
						onclick="closeForm('${product.getName()}')">Fechar</button>
				</form>
			</div>
				</c:forEach>
			</c:if>
		</ul>
	</div>

	<div id="myPopup" class="popup">
		<div class="popup-content">
			<div class="popup-closebtn">
				<a id="closePopup">&#10006;</a>
			</div>
			<h1>Seu carrinho</h1>
			<c:if test ="${sessionScope.cartproducts != null}">
				<c:set var = "cartproducts" value ="${sessionScope.cartproducts}"/>
				<c:if test ="${cartproducts.size()==0}">			
					<c:out value="Não tem produtos no seu carrinho!!!" /> 
				</c:if>
			<c:set var = "total" value ="${BigDecimal.ZERO}"/>
				<c:forEach var = "i" begin = "1" end ="${cartproducts.size()}">
					<c:set var = "product" value ="${cartproducts[i-1]}"/>
					<c:set var = "quantity" value ="${productquantity[i-1]}"/>
			<form action="Shopping" method="post">
				<input id="prodid${i}r" type="hidden" type="number"
					value="${product.id}"> <input id="order_id${i}r"
					type="hidden" type="number" value="${order_id}">
				<div class="cart-align">
					Nome:${product.name}
					<br> Preço:${product.getPrice().setScale(2)}${product.getPriceCurrency()}
					<br> Total:${product.getPrice().multiply(quantity).setScale(2)}${product.getPriceCurrency()}
				</div>
				<input type="button" onclick="removeproduct('${i}r')"
					value="&#10006;" />
			</form>
			<br> <br>
			<form action="Shopping" method="post">
				<div class="cart-align">
					<input id="quantity${i}u" type="number" value="${quantity}"
						min="1" max="20"> <input id="prodid${i}u" type="hidden"
						value="${product.getId()}"> <input id="order_id${i}u"
						type="hidden" value="${order_id}"> <input type="button"
						onclick="updateproduct('${i}u')" value="Atualizar">
				</div>
			</form>
			<br> <br>
			<c:set var="total" value="${total=total.add(product.getPrice().multiply(quantity)).setScale(2)}"/>
			<c:if test ="${cartproducts.size() == i}">
				Total Carrinho:${total}${product.getPriceCurrency()}
			</c:if>
			</c:forEach>
			</c:if>
			
		</div>
	</div>
</body>
</html>
