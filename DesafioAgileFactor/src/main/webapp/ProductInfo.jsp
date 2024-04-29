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
 <jsp:include page="/Menu.jsp" />
	 <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2><%=product.getName()%></h2></caption>
            <tr>
                <th>Name</th>			<td><%=product.getName()%></td>
            </tr>
            <tr>    
                <th>Author</th>			<td><%=product.getAuthor()%></td> 
                   </tr>
            <tr>  
                <th>Editor</th>			<td><%=product.getEditor()%></td>
                   </tr>
            <tr>  
                <th>Create Date</th>	<td><%=product.getCreateddate()%></td>
            </tr>
            <tr>  
                <th>Update Date</th>	<td><%=product.getUpdateddate()%></td>
            </tr>
            <tr>  
                <th>Categories</th>			<td><%=product.getCategoriesString()%></td>    
     		</tr>
            <tr>  
                <th>Price</th>			<td><%=product.getPrice()%></td>
     		</tr>
            
        </table>
    </div>
</body>
</html>