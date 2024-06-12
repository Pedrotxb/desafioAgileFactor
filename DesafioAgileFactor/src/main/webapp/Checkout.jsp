<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@page import="shopping.desafio.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.math.RoundingMode"%>

<c:set var = "productquantity" value ="${sessionScope.productsquantity}"/>
<c:set var = "products" value ="${sessionScope.products}"/>
<c:set var = "labels" value ="${sessionScope.labels}"/>
<c:set var = "order_id" value ="${sessionScope.order_id}"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>
	<script type="text/javascript" src="Script.js"></script>
    <link rel="stylesheet" href="Style.css">
</head>
<body>
    <nav class="navbar">
        <div class="logo"><a href="Shopping" >Shopping</a></div>
        <div class="cart-icon" id="cart-icon">
            <img src="images/cart.png" alt="Cart" />
            <span id="cart-count"></span>
        </div>
    </nav>  
    
	<c:if test ="${sessionScope.cartproducts != null}">
    <div id="cart-popup" class="checkout" >    
		<c:set var = "cartproducts" value ="${sessionScope.cartproducts}"/>
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
				<c:forEach var = "i" begin = "1" end ="${cartproducts.size()}">
					<c:set var = "product" value ="${cartproducts[i-1]}"/>
					<c:set var = "pricetax" value ="${product.getPrice()}"/>					
					<c:set var = "quantity" value ="${productquantity[i-1]}"/>
					<c:set var ="labels" value ="${product.getLabels()}"/>			
                <tr>
                	<td><img src="images/${product.name}.jpg"></td>
                    <td>${product.name}</td>
                    <td>
                    	<form action="Shopping" method="post">                 
                    		<div class="quantity-container">
                    			<button type="button" class="quantity-btn" onclick="decrementQuantity('${i}')">-</button>
									<input style="padding-left:15px;width:45px;text-align:center;" id="quantity${i}" type="number" value="${quantity}" min="1" max="20" >
								<button type="button" class="quantity-btn" onclick="incrementQuantity('${i}')">+</button> 
							</div>	
							<input id="prodid${i}u" type="hidden" value="${product.getId()}"> 
							<input id="order_id${i}u" type="hidden" value="${order_id}"> 
									
                    </td>
                    <td>
                    	<c:forEach var="label" items="${labels}">
                    		<c:if test="${label.getTaxes()!=null}">	
                    			<c:forEach var="tax" items="${label.getTaxes()}">	
                    				<c:set var="pricetax" value="${pricetax=pricetax.add(pricetax.multiply(tax.getValue())).setScale(2,RoundingMode.HALF_EVEN)}"/>
                    				${tax.getCode()}${tax.getType()}<br>                   					     		 		 
                    			</c:forEach>                    	
                    					
                    		</c:if>	
                    	</c:forEach>
                    	${pricetax}${product.getPriceCurrency()}
                     </td>
                    <td>${pricetax.multiply(quantity).setScale(2)}${product.getPriceCurrency()}</td>
                    <td>
                    	<input id="update" type="button" onclick="updateProduct('${i}')" value="Atualizar">
                    	</form> 
                    	<form action="Shopping" method="post">
							<input id="prodid${i}r" type="hidden" value="${product.getId()}"> 
							<input id="order_id${i}r" type="hidden" value="${order_id}"> 
                    		<input id="remove" type="button" onclick="removeProduct('${i}r')" value="Remover"></td>
                    	</form>                  
                </tr>
                 <c:set var="total" value="${total=total.add(pricetax.multiply(quantity)).setScale(2)}"/>        
                </c:forEach>
            </tbody>
        </table>
        </div>
        <div class="checkout-footer">
            <div class="cart-total">
                <strong>Total:<span id="cart-total">${total}${product.getPriceCurrency()}</span></strong>
            </div>
            <button class="clear-btn" onclick="clearCart('${order_id}')">Limpar Carrinho</button>
            <button class="checkout-btn" onclick="generateFiles('${order_id}')">Submeter Encomenda</button>
        </div>
    	
    </div>
    </c:if>
</body>
</html>