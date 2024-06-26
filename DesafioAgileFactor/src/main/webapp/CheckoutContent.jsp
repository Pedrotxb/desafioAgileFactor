<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page import="java.math.BigDecimal"%>    
<%@page import="java.math.RoundingMode"%>
<c:set var = "cartproducts" value ="${sessionScope.cartproducts}"/>
<c:set var = "productquantity" value ="${sessionScope.productsquantity}"/>
		<h2>Carrinho</h2>
        <div class="cart-table-container">
            <table id="cart-items">
                <thead>
                    <tr>
                        <th colspan="2">Product</th>
                        <th>Labels</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="total" value="${BigDecimal.ZERO}"/>
                    <c:forEach var="i" begin="1" end="${cartproducts.size()}">
                        <c:set var="product" value="${cartproducts[i-1]}"/>
                        <c:set var="pricetax" value="${product.getPrice()}"/>                    
                        <c:set var="quantity" value="${productquantity[i-1]}"/>
                        <c:set var="labels" value="${product.getLabels()}"/>            
                        <tr>
                            <td><img src="images/${product.name}.jpg"></td>
                            <td>${product.name}</td>
                            <td>${product.getLabelsString()}</td>
                            <td>
                                <form action="Shopping" method="post">                 
                                    <div class="quantity-container">
                                        <button type="button" class="quantity-btn" onclick="decrementQuantity('${i}','1')">-</button>
                                        <input style="padding-left:15px;width:45px;text-align:center;" id="quantity${i}" type="number" value="${quantity}" min="1" max="20" >
                                        <button type="button" class="quantity-btn" onclick="incrementQuantity('${i}','1')">+</button> 
                                    </div>    
                                    <input id="prodid${i}u" type="hidden" value="${product.getId()}"> 
                                    <input id="order_id${i}u" type="hidden" value="${order_id}"> 
                                </form>
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
            <button class="clear-btn" onclick="clearCart('${order_id}')">Clear Cart</button>
            <button class="checkout-btn" onclick="generateFiles('${order_id}')">Submit Order</button>
        </div>
 