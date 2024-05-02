<%@page import="shopping.desafio.Product"%> 
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
  <%  
	// retrieve your list from the request, with casting 
	Product product = (Product)request.getAttribute("product");
  %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><%=product.getName()%></title>
</head>
<body>
 </ul>
	 <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2><%=product.getName()%></h2></caption>
            <tr>
                <th>Nome</th>			<td><%=product.getName()%></td>
            </tr>
            <tr>  
                <th>Categorias</th>			<td><%=product.getLabelsString()%></td>    
     		</tr>
            <tr>  
                <th>Price</th>			<td><%=product.getPrice()%></td>
     		</tr>
            
        </table>
    </div>
</body>
</html>