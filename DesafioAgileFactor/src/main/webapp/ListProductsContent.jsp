<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page import="java.math.RoundingMode"%>
<c:set var = "products" value ="${sessionScope.products}"/>

<c:if test="${products != null}">
	<c:forEach var="product" items="${products}">
		<c:set var = "pricetax" value ="${product.getPrice()}"/>
		<div class="product" data-id="${product.id}"
			data-name="${product.name}">
			<form class="form-container" action="Shopping" method="post"> 
				<img src="images/${product.name}.jpg">
				<h2>${product.name}</h2>
				<p>Preço:
					<c:forEach var="label" items="${product.getLabels()}">
						<c:forEach var="tax" items="${label.getTaxes()}">	
							<c:set var="pricetax" value="${pricetax=pricetax.add(pricetax.multiply(tax.getValue())).setScale(2,RoundingMode.HALF_EVEN)}"/>				
						</c:forEach>
					</c:forEach>
					${pricetax}${product.getPriceCurrency()}		
				<p>Categorias: ${product.getLabelsString()}</p>
				<input id="Input=${product.getName()}" name="quantity" type="number"
					min="1" value="1" max="10" class="quantity">
				<input type="button" onclick="addProduct('${product.id}',document.getElementById('Input=${product.getName()}').value)" class="add-to-cart" value="Adicionar">
			</form>
		</div>
	</c:forEach>
</c:if>
