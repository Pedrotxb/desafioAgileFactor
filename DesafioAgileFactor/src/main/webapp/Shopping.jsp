<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="shopping.desafio.*"%>
<%@page import="java.util.ArrayList"%>

<%  
  	request.setAttribute("session", session.getId());
  
	// retrieve your list from the request, with casting 
    ArrayList<Product> cartproducts = (ArrayList<Product>) request.getAttribute("cartproducts");
  	ArrayList<Integer> productquantity = (ArrayList<Integer>) request.getAttribute("productsquantity");
	ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
	ArrayList<Label> labels = (ArrayList<Label>) request.getAttribute("labels");
	long order_id = (long)request.getAttribute("order_id");
	
  %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping</title>
<style type="text/css">
#test.column {
	float: left;
	width: 33.33%;
	padding: 5px;
}

.cart-align{
	display:flex; 
	float:left;
}
.popup {
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
	display: none;
}

.popup-content {
	background-color: white;
	margin: 10% auto;
	padding: 20px;
	border: 1px solid #888888;
	width: 30%;
	font-weight: bolder;
}

.popup-content button {
	display: block;
	margin: 0 auto;
}

.show {
	display: block;
}

.popup-closebtn {

	color:red;
	float: right;
	cursor: pointer;
}

.form-popup-cart {
	display: none;
	border: 3px solid #f1f1f1;
	z-index: 9;
	position: fixed;
	right: 0px;
	top: 60px;
	width: 200px;
}

.searchlabel {
	display: block;
	width: 300px;
}

.search {
	display: block;
	position: fixed;
	padding: 1px;
	width: 400px;
	top: 8px;
	left: 250px;
}

.cart {
	display: block;
	position: relative;
	float: right;
	padding: 1px;
	cursor: pointer;
}

.home {
	display: block;
	position: relative;
	float: left;
	padding: 1px;
}

ul {
	margin: 0;
	padding: 0;
	list-style: none;
	background-color: #333;
}

.menu a {
	display: inline-block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

li.dropdown {
	display: inline-block;
	position: relative;
}

.dropdown:hover>a {
	background: #f1f1f1;
}

.dropdown-content {
	position: absolute;
	background-color: #BDBDBD;
	box-shadow: 5px 5px 5px 5px;
	z-index: 1;
	top: 0;
	left: -999em;
	transition: top .5s ease;
}

.dropdown-content li {
	display: block;
	width: 215px;
}

.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover, .dropdown-content li:hover>a {
	background-color: #f1f1f1;
}

.dropdown:hover>.dropdown-content {
	left: 0;
	top: 100%;
}

.dropdown .dropdown>.dropdown-content {
	transition: transform .5s ease, opacity .3s ease;
	transform: translateX(-100%);
	opacity: 0;
}

.dropdown .dropdown:hover>.dropdown-content {
	left: 100%;
	transform: translateX(0);
	top: 0;
	opacity: 1;
}

#searchByName {
	background-image: url('/css/searchicon.png');
	/* Add a search icon to input */
	background-position: 10px 12px; /* Position the search icon */
	background-repeat: no-repeat; /* Do not repeat the icon image */
	width: 50%;
	font-size: 16px; /* Increase font-size */
	padding: 12px 20px 12px 40px; /* Add some padding */
	border: 1px solid #ddd; /* Add a grey border */
	justify-content: left;
	margin-bottom: 12px; /* Add some space below the input */
}

#product {
	/* Remove default list styling */
	list-style-type: none;
	padding: 0;
	margin: 0;
	cursor: pointer;
}

#product {
	display: block;
	position: relative;
	float: center;
	padding: 1px;
	background-color: white;
}

#product li a {
	width: 50%;
	border: 1px solid #ddd; /* Add a border to all links */
	margin-top: -1px; /* Prevent double borders */
	background-color: #f6f6f6; /* Grey background color */
	padding: 12px; /* Add some padding */
	text-decoration: none; /* Remove default text underline */
	font-size: 18px; /* Increase the font-size */
	color: black; /* Add a black text color */
	display: block;
	/* Make it into a block element to fill the whole list */
	justify-content: center;
}

#product li a:hover:not(.header) {
	background-color: #eee;
	/* Add a hover effect to all links, except for headers */
}

{
box-sizing
:
 
border-box
;
}

/* The popup form - hidden by default */
.form-popup {
	display: none;
	bottom: 0;
	border: 3px solid #f1f1f1;
	z-index: 9;
	width: 50%;
}

/* Add styles to the form container */
.form-container {
	max-width: 300px;
	padding: 10px;
	background-color: white;
}

/* Set a style for the submit/login button */
.form-container .btn {
	background-color: #04AA6D;
	color: white;
	padding: 10px 10px;
	border: none;
	cursor: pointer;
	width: 50%;
	margin-bottom: 10px;
	opacity: 0.8;
}

/* Add a red background color to the cancel button */
.form-container .cancel {
	background-color: red;
}

/* Add some hover effects to buttons */
.form-container .btn:hover, .open-button:hover {
	opacity: 1;
}

.quantity {
	display: flex;
	border: 2px solid #3498db;
	border-radius: 4px;
	overflow: hidden;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	width: 50%
}

.quantity button {
	background-color: #3498db;
	color: #fff;
	border: none;
	cursor: pointer;
	font-size: 20px;
	width: 30px;
	height: auto;
	text-align: center;
	transition: background-color 0.2s;
}

.quantity button:hover {
	background-color: #2980b9;
}

.input-box {
	width: 70px;
	text-align: center;
	border: none;
	padding: 8px 10px;
	font-size: 16px;
	outline: none;
}

/* Hide the number input spin buttons */
.input-box::-webkit-inner-spin-button, .input-box::-webkit-outer-spin-button
	{
	-webkit-appearance: none;
	margin: 0;
}

.input-box[type="number"] {
	-moz-appearance: textfield;
}
</style>
<script type="text/javascript">
	function openForm(idValue) {
	  document.getElementById(idValue).style.display = "block";
	}

	function closeForm(idValue) {
	  document.getElementById(idValue).style.display = "none";
	}
	function increment(input) {
	      document.getElementById(input).stepUp();
	   }
	   function decrement(input) {
	      document.getElementById(input).stepDown();
	   }
</script>
</head>
<body>

	<ul id="menu" class="menu">

		<li class="home"><a href="Shopping">Home</a></li>
		<li class="dropdown"><a class="dropbtn">Labels</a>
			<ul class="dropdown-content">
				<li>
					<form class="searchlabel" action="Shopping" method="post">
						<input type="text" id="searchByName" name="byname"
							onkeyup="searchByName()" placeholder="Procurar label..">
					</form>

				</li>
				<%for(Label label : labels){%>
				<li class="dropdown"><a
					href="Shopping?filter=<%=label.getId()%>"><%=label.getName()%></a></li>
				<%}%>
			</ul></li>
		<li class="home"><a href="Shopping?filter=all"> Produtos</a></li>
		<form class="search" action="Shopping" method="post">
			<input type="text" id="searchByName" name="byname"
				onkeyup="searchByName()" placeholder="Procurar produto..">
		</form>

		<li class="cart"><a id="cart">Carrinho</a></li>
	</ul>

	<div align="center">

		<ul id="product">
			<%for(Product product : products){%>
			<li><a onclick="openForm('<%=product.getName()%>')"><%=product.getName()%></a></li>
			<div class="form-popup" id="<%=product.getName()%>">
				<form class="form-container" action="Shopping" method="post">
					<input type="hidden" name="addproduct" value="yes"> <input
						type="hidden" name="prodid" value="<%=product.getId()%>">
					<table border="1" cellpadding="5">
						<caption>
							<h2><%=product.getName()%></h2>
						</caption>
						<tr>
							<th>Nome</th>
							<td><%=product.getName()%></td>
						</tr>
						<tr>
							<th>Categorias</th>
							<td><%=product.getLabelsString()%></td>
						</tr>
						<tr>
							<th padding:="5">Price</th>
							<td><%=product.getPrice()%></td>
						</tr>

					</table>
					<p></p>
					<div class="quantity">
						<button type="button" class="minus"
							onclick="decrement('Input=<%=product.getName()%>')">&minus;</button>
						<input id="Input=<%=product.getName()%>" name="quantity"
							type="number" class="input-box" value="1" min="1" max="10">
						<button type="button" class="plus"
							onclick="increment('Input=<%=product.getName()%>')">&plus;</button>
					</div>
					<p></p>
					<button type="submit" class="btn">Adicionar ao Carrinho</button>
					<button type="button" class="btn cancel"
						onclick="closeForm('<%=product.getName()%>')">Fechar</button>
				</form>

			</div>
			<%}%>
		</ul>
	</div>

	<div id="myPopup" class="popup">
		<div class="popup-content">
			<div class="popup-closebtn">
				<a id="closePopup">&#10006;</a>
			</div>
			<h1>Seu carrinho</h1>
			<%
   				if(cartproducts!=null){
					if(cartproducts.size()==0){%>
						Não tem produtos no seu carrinho!!!
					<%}
				double total=0.0;
				for(int i=0; i<cartproducts.size();i++){
   					Product product = cartproducts.get(i);
   					
   					int quantity = productquantity.get(i);%>
						<form action="Shopping" method="post">
							<input type="hidden" type="number" name="prodid" value="<%=product.getId()%>"> 
							<input type="hidden" type="number" name="order_id" value="<%=order_id%>">
							<input type="hidden" name="cart" value="remove"> 
							<div class="cart-align">
								Nome:<%=product.getName()%>
								<br>
								Preço:<%=product.getPrice()%><%=product.getPriceCurrency()%>
								<br>
								Total:<%=product.getPrice()*quantity%><%=product.getPriceCurrency()%>
							</div>
							<button type="submit">&#10006;</button>		
						</form>
						<br><br>
						<form action="Shopping" method="post">
							<div class="cart-align">
								<input id="Input=<%=product.getName()%>" name="quantity" type="number" value="<%=quantity%>" min="1" max="10">
								<input type="hidden" name="prodid" value="<%=product.getId()%>">
								<input type="hidden" name="order_id" value="<%=order_id%>">
								<input type="hidden" name="cart" value="update">
								<button type="submit">Atualizar</button>
							</div>
						</form>
					<br><br>
					
			<%total+=product.getPrice()*quantity;%>
			Total Carrinho:<%=total%><%=product.getPriceCurrency()%>
			<%}}%>
			
	</div>
	</div>

	<script>
            cart.addEventListener(
                "click",
                function () {
                    myPopup.classList.add("show");
                }
            );
            closePopup.addEventListener(
                "click",
                function () {
                    myPopup.classList.remove(
                        "show"
                    );
                }
            );
            window.addEventListener(
                "click",
                function (event) {
                    if (event.target == myPopup) {
                        myPopup.classList.remove(
                            "show"
                        );
                    }
                }
            );
        </script>

</body>
</html>
