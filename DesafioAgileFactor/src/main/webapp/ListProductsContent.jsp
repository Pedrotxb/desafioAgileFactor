<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var = "products" value ="${sessionScope.products}"/>
<c:if test="${products != null}">
	<c:forEach var="product" items="${products}">
		<div class="product" data-id="${product.id}"
			data-name="${product.name}">
			<form class="form-container" action="Shopping" method="post">
				<input type="hidden" name="addproduct" value="yes"> <input
					type="hidden" name="prodid" value="${product.id}"> <img
					src="images/${product.name}.jpg">
				<h2>${product.name}</h2>
				<p>Preço:
					${product.getPrice().setScale(2)}${product.getPriceCurrency()}</p>
				<p>Categorias: ${product.getLabelsString()}</p>
				<input id="Input=${product.getName()}" name="quantity" type="number"
					min="1" value="1" max="10" class="quantity">
				<button type="submit" class="add-to-cart">Adicionar</button>
			</form>
		</div>
	</c:forEach>
</c:if>
