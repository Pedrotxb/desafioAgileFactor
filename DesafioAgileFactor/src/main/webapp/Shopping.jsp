<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="shopping.desafio.Product"%> 
<%@page import="java.util.ArrayList"%> 

  <%  
	// retrieve your list from the request, with casting 
	ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
  %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping</title>
</head>
<body>
 <jsp:include page="/Menu.jsp" />
 <div align="center">
	<form action="InformaçãoProduto" method="post">
		<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for names..">
	</form>
 	<ul id="myUL">

  		<%
  			for(Product product : products){
  			%> <li><a href="ProductInfo?productid=<%=product.getId()%>"><%=product.getName()%></a></li>
  		<%}
  		%>
 
 	</ul>
 </div>
</body>
</html>
<style type="text/css">
	#myInput {

  background-image: url('/css/searchicon.png'); /* Add a search icon to input */
  background-position: 10px 12px; /* Position the search icon */
  background-repeat: no-repeat; /* Do not repeat the icon image */
  width: 50%;
  font-size: 16px; /* Increase font-size */
  padding: 12px 20px 12px 40px; /* Add some padding */
  border: 1px solid #ddd; /* Add a grey border */
  justify-content: center;
  margin-bottom: 12px; /* Add some space below the input */
}



#myUL {
  /* Remove default list styling */
  list-style-type: none;
  padding: 0;
  margin: 0;
  
}

#myUL li a {
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

#myUL li a:hover:not(.header) {
  background-color: #eee; /* Add a hover effect to all links, except for headers */
}
</style>

<script>
function myFunction() {
  // Declare variables
  var input, filter, ul, li, a, i, txtValue;
  input = document.getElementById('myInput');
  filter = input.value.toUpperCase();
  ul = document.getElementById("myUL");
  li = ul.getElementsByTagName('li');

  // Loop through all list items, and hide those who don't match the search query
  for (i = 0; i < li.length; i++) {
    a = li[i].getElementsByTagName("a")[0];
    txtValue = a.textContent || a.innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      li[i].style.display = "";
    } else {
      li[i].style.display = "none";
    }
  }
}
</script>