package shopping.desafio;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Shopping
 */

public class ShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con	= null;
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public ShoppingServlet() {
        super();
        // TODO Auto-generated constructor stub
      
    }
      
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  //Get label and product lists
		  ArrayList<Product> products = new ArrayList<Product>();
		  ArrayList<Label> labels = new ArrayList<Label>();
		  Cart cart = new Cart();
		  Cart cartsession = new Cart();
		  String query = ""; 
		  ShoppingDB conn= new ShoppingDB();
		  HttpSession session = request.getSession();
		  try {
			  
			  //Verify session, Add product to cart 
			  if("yes".equals(request.getParameter("addproduct"))) {
				  long prodid=Long.parseLong(request.getParameter("prodid"));
				  int quantity=Integer.parseInt(request.getParameter("quantity"));
				  cartsession=conn.getCartSession(session.getId());

				//Check cart on db if not create
				  if (cartsession.getSession()==null && session.getId()!=null) {
					  conn.createCart(session.getId());
					  cartsession=conn.getCartSession(session.getId());
				  }
				//Add product to cart
				  conn.addProducttoCart(cartsession.getId(),
						  Long.parseLong(request.getParameter("prodid")),
						  Integer.parseInt(request.getParameter("quantity")));	 
			  	}
			  
			 
			  
			  if(request.getParameter("filter")!=null && !request.getParameter("filter").contains(";")) {
				  String filter=request.getParameter("filter");				 			
					  		
			  if("labels".equals("filter")) {
					  //ArrayList<Label> filters = new ArrayList<Label>();
					  //Get product list filtered by labels
					  //filters = (ArrayList<Label>) session.getAttribute("filters");
				  }
			  }
			  
			  
			  //Search product by name
			  if(request.getParameter("byname")!=null) {
				  products = conn.getProductListbyName(request.getParameter("byname"));
			  }
			  
			  //Update product quantity
			  if("update".equals(request.getParameter("cart"))){
				  conn.updateProduct(Integer.parseInt(request.getParameter("quantity")),
						  			Long.parseLong(request.getParameter("order_id")),
						  			Long.parseLong(request.getParameter("prodid")));
			  }
			  //Remove product from cart
			  else if("remove".equals(request.getParameter("cart"))){
				  conn.removeProduct(Long.parseLong(request.getParameter("order_id")),
						  			 Long.parseLong(request.getParameter("prodid")));
			  }
			  

			  //Get cart from session
			  cart=conn.getCart(session.getId());
			  
			  //Get labels name and id
			  labels = conn.getLabels();	
			  				  
			  //Save all info in session 
 
			  session.setAttribute("cartproducts", cart.getCartProducts());
			  session.setAttribute("productsquantity", cart.getQuantity());		
			  session.setAttribute("order_id", cart.getId());
			  session.setAttribute("products", products);
			  session.setAttribute("labels", labels);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher rd=request.getRequestDispatcher("Shopping.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
}
