<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page import="java.math.BigDecimal"%>    
        <div class="cart-header">
            <h2>Seu carrinho</h2>
            <button onclick="closePopup()" class="close-cart-btn">×</button>
        </div>
        <c:if test ="${cartproducts != null}">
        <div class="cart-table-container">
        <table id="cart-items">
          	<thead>
                <tr>
                    <th colspan="2">Produto</th>
                    <th>Quantidade</th>
                    <th>Preço</th>
                    <th>Total</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <!-- Cart items will be inserted here -->
                <c:set var = "total" value ="${BigDecimal.ZERO}"/>
				<c:forEach var = "i" begin = "1" end ="${sessionScope.cartproducts.size()}">
					<c:set var = "product" value ="${sessionScope.cartproducts[i-1]}"/>
					<c:set var = "quantity" value ="${sessionScope.productsquantity[i-1]}"/>
                <tr>
                	<td><img src="images/${product.name}.jpg"></td>
                    <td>${product.name}</td>
                    <td>
                    	<form action="Shopping" method="post">
                    	<div class="cart-align">
							<div class="quantity-container">
                    			<button type="button" class="quantity-btn" onclick="decrementQuantity('${i}')">-</button>
									<input style="padding-left:15px;width:45px;text-align:center;" id="quantity${i}" type="number" value="${quantity}" min="1" max="20" >
								<button type="button" class="quantity-btn" onclick="incrementQuantity('${i}')">+</button> 
							</div>	 
							<input id="prodid${i}u" type="hidden" value="${product.getId()}"> 
							<input id="order_id${i}u" type="hidden" value="${order_id}"> 
						</div>
					
                    </td>
                    <td>${product.getPrice().setScale(2)}${product.getPriceCurrency()}</td>
                    <td>${product.getPrice().multiply(quantity).setScale(2)}${product.getPriceCurrency()}</td>
                    <td>
                    	<input id="update" type="button" onclick="updateproduct('${i}')" value="Atualizar">
                    	</form> 
                    	<form action="Shopping" method="post">
							<input id="prodid${i}r" type="hidden" value="${product.getId()}"> 
							<input id="order_id${i}r" type="hidden" value="${order_id}"> 
                    		<input id="remove" type="button" onclick="removeproduct('${i}r')" value="Remover"></td>
                    	</form>                  
                </tr>
                <c:set var="total" value="${total=total.add(product.getPrice().multiply(quantity)).setScale(2)}"/>          
                </c:forEach>
            </tbody>
        </table>
        </div>
        <div class="cart-footer">
            <div class="cart-total">
                <strong>Total:<span id="cart-total">${total}${product.getPriceCurrency()}</span></strong>
            </div>
            <button class="checkout-btn" onclick="generateFiles('${sessionScope.order_id}')">Checkout</button>
        </div>
    	</c:if>
    