<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="shopping.desafio.*"%> 
<%@page import="java.util.ArrayList"%> 

  <%  
	// retrieve your list from the request, with casting 
	ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
	ArrayList<Label> labels = (ArrayList<Label>) request.getAttribute("labels");
	
	// create a list of labels with tax
	ArrayList<Label> taxlabels = new ArrayList<Label>();
	for(Label label : labels){
		if("yes".equals(label.getTax())){
			taxlabels.add(label);
		}
	}	
  %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping</title>
<style type="text/css">

.cart {
    	display: block;
    	position: relative;
    	float: right;
     	padding: 1px;
}
.home {
    	display: block;
    	position: relative;
    	float: left;
     	padding: 1px;
}
ul {
	margin:0;
	padding:0;
	list-style:none;
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
	position:relative;
}
.dropdown:hover > a {
	background:#f1f1f1;
}
.dropdown-content {
	position: absolute;
	background-color: #BDBDBD;
	min-width: 70px;
	box-shadow: 5px 5px 5px 5px;
	z-index: 1;
	top:0;
	left:-999em;
	transition:top .5s ease;
}
.dropdown-content li {
	display:block;
}
.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}
.dropdown-content a:hover, .dropdown-content li:hover > a {
	background-color: #f1f1f1;
}
.dropdown:hover > .dropdown-content {
	left:0;
	top:100%;
}
.dropdown .dropdown > .dropdown-content {
	transition:transform .5s ease, opacity .3s ease;
	transform:translateX(-100%);
	opacity:0;
}
.dropdown .dropdown:hover > .dropdown-content {
	left:100%;
	transform:translateX(0);
	top:0;
	opacity:1;
}
#searchByName {
	background-image: url('/css/searchicon.png'); /* Add a search icon to input */
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
}
#product  {
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
	display: block; /* Make it into a block element to fill the whole list */
	justify-content: center;
}
#product li a:hover:not(.header) {
	background-color: #eee; /* Add a hover effect to all links, except for headers */
}
</style>
</head>
<body>
<ul id="menu" class= "menu">
    <li class="cart" ><a href="Cart">Carrinho</a></li>
    <li class="home"><a href="Shopping" >Home</a></li> 
    
 <li class="dropdown" >

		<a href="Shopping?filter=all" class="dropbtn">Produtos</a>
     		<ul class="dropdown-content">
				<%for(Label label : labels){%>
  					<% if("no".equals(label.getTax())){%> 
  						<li class="dropdown"><a href="Shopping?filter=<%=label.getId()%>"><%=label.getName()%></a>
  							<ul class="dropdown-content">
  								<!-- Compare labels with same product_id to get sub-labels -->
  								<%for(Label taxlabel : taxlabels){%>
  									<% if(true==label.checkSubLabel(taxlabel.getId())){%> 
  										<li><a href="Shopping?filter=<%=label.getId()%>,<%=taxlabel.getId()%>" ><%=taxlabel.getName()%></a>
  									<%}%>	
  								<%}%>	
  							</ul>
  						</li>	
  					<%}%>
  				<%}%>  								        		
     		</ul>    		    
 </li>
 </ul>    		
 
 <div align="center" >
	<form action="Shopping" method="get">
		<input type="text"  id="searchByName" name="byname" onkeyup="searchByName()" placeholder="Procurar por nome..">
		
	</form>
	
	<ul id ="product">
  		<%for(Product product : products){%>
  			<li><a href="ProductInfo?productid=<%=product.getId()%>"><%=product.getName()%></a></li>
  		<%}%>
  	</ul>
 </div>
 
</body>
</html>
