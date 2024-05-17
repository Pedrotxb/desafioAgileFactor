<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="shopping.desafio.*"%>
<%@page import="java.util.ArrayList"%>


<%  
	// retrieve your list from the request, with casting 
  
  	ArrayList<Integer> productquantity = (ArrayList<Integer>) session.getAttribute("productsquantity");
	ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
	ArrayList<Label> labels = (ArrayList<Label>) session.getAttribute("labels");
	long order_id = (long)session.getAttribute("order_id");
	
  %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Style.css">
<meta charset="ISO-8859-1">
<title>Shopping</title>
        
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
 <script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>

        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
    <script >
   
    	   function updateproduct(id) {
    		   $.ajax({
                   type: "post",
                   url: "Shopping", //this is my servlet
                   data: "quantity="+$('#quantity'+id).val()
                   	 +"&prodid="+$('#prodid'+id).val()
                   	 +"&order_id="+$('#order_id'+id).val()
                   	 +"&cart=update",
                   success: function(msg){      
                   	$.get("Shopping", function(data, status){ 
                   		$("body").html(data); 
                   		myPopup.classList.add("show");
                   	}); 
                   	
                   }
               });
    	   }
           //remove product, refresh page content and open popup
           function removeproduct(id) {
        	   $.ajax({
                   type: "post",
                   url: "Shopping", //this is my servlet
                   data: "&prodid="+$('#prodid'+id).val()
                   	 +"&order_id="+$('#order_id'+id).val()
                   	 +"&cart=remove",
                   success: function(msg){      
                   	$.get("Shopping", function(data, status){ 
                   		$("body").html(data); 
                   		myPopup.classList.add("show");
                   	});   
                   }
               });
    	   } 
           
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
        $(function()
                {
                 $('#searchlabel').autocomplete(
                 {
                 source:function(request,response)
                 {
                 //Fetch data
                 $.ajax({
                     url:"AutocompleteServlet",
                     method:"post",
                     dataType:'json',
                     data:{searchlabel:request.term},
                     success:function(data)
                     {
                         response(data);
                     }
                 });
                 }
                 });   
                }); 
        
        $(function()
                {
                 $('#searchproduct').autocomplete(
                 {
                 source:function(request,response)
                 {
                 //Fetch data
                 $.ajax({
                     url:"AutocompleteServlet",
                     method:"post",
                     dataType:'json',
                     data:{searchproduct:request.term},
                     success:function(data)
                     {
                         response(data);
                     }
                 });
                 },minLength: 0,
         		scroll: true,
                 select: function(event, ui) {   
         			console.log(ui.item.value);
        			if(ui.item.value != ""){
        			    location.href="Shopping?byname="+ui.item.value;
        			}
        		}
                 
                 });   
                }); 
        
    </script>

<body>
	<ul id="menu" class="menu">

		<li class="home"><a href="Shopping">Home</a></li>
		<li class="dropdown"><a class="dropbtn">Labels</a>
			<ul class="dropdown-content">
				<li>
					<form action="Shopping" method="post">
						<input type="text"  id="searchlabel"  placeholder="Procurar label..">
					</form>
					
				</li>
				<%for(Label label : labels){%>
				<li class="dropdown"><a
					href="Shopping?filter=<%=label.getId()%>"><%=label.getName()%></a></li>
				<%}%>
			</ul></li>
		
		<li class="dropdown"><a class="dropbtn">Produtos</a>
			<ul class="dropdown-content">
				<li>
					<form action="Shopping" method="post">
						<input type="text" id="searchproduct"  name="byname" placeholder="Procurar produto..">
					</form>

				</li>
			</ul></li>
		
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
   				if(session.getAttribute("cartproducts")!=null){
   					ArrayList<Product>cartproducts =(ArrayList<Product>) session.getAttribute("cartproducts");
					if(cartproducts.size()==0){%>
						Não tem produtos no seu carrinho!!!
					<%}
				double total=0.0;
				for(int i=0; i<cartproducts.size();i++){
   					Product product = cartproducts.get(i);   					
   					int quantity = productquantity.get(i);%>
   					
						<form action="Shopping" method="post">
							<input id="prodid<%=i%>r"  type="hidden" type="number" value="<%=product.getId()%>"> 
							<input id="order_id<%=i%>r" type="hidden" type="number" value="<%=order_id%>">
							<div class="cart-align">
								Nome:<%=product.getName()%>
								<br>
								Preço:<%=product.getPrice()%><%=product.getPriceCurrency()%>
								<br>
								Total:<%=product.getPrice()*quantity%><%=product.getPriceCurrency()%>
							</div>
							<input type="button" onclick="removeproduct('<%=i%>r')" value="&#10006;"/>
						</form>
						<br><br>
						<form action="Shopping" method="post">
							<div class="cart-align">
								<input id="quantity<%=i%>u" type="number" value="<%=quantity%>" min="1" max="20">
								<input id="prodid<%=i%>u" type="hidden" value="<%=product.getId()%>">
								<input id="order_id<%=i%>u" type="hidden" value="<%=order_id%>">
								<input type="button" onclick="updateproduct('<%=i%>u')" value="Atualizar">
							</div>
						</form>
					<br><br>
			
			<%
				total+=product.getPrice()*quantity;
				if(cartproducts.size()-1==i){%>
					Total Carrinho:<%=total%><%=product.getPriceCurrency()%>
			<%}}}%>
			
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
