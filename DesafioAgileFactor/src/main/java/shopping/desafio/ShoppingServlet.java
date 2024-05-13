package shopping.desafio;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		  String session = (String) request.getAttribute("session");
		  ShoppingDB conn= new ShoppingDB();
		  try {
			  
			  //Verify session, Add product to cart 
			  if("yes".equals(request.getParameter("addproduct"))) {
				  long prodid=Long.parseLong(request.getParameter("prodid"));
				  int quantity=Integer.parseInt(request.getParameter("quantity"));
				 
				  cartsession=conn.getCartSession("SELECT session_tk,order_id FROM shp_order WHERE session_tk='"+session+"';");
				  
				//Check cart on db if not creat
				  if (cartsession.getSession()==null) {
					  conn.manipulateDB("INSERT INTO shp_order (client_gid,session_tk,order_st,created_dt,updated_dt) "
					  				 +"VALUES ('me','"+session+"','on',now(),now());");
					  cartsession=conn.getCartSession("SELECT session_tk,order_id FROM shp_order WHERE session_tk='"+session+"';");
				  }
				//Add product to cart
				  conn.manipulateDB("INSERT INTO shp_order_item (order_id,product_id,unit_cd,item_qt,item_st) "
			  				     +"VALUES ('"+cartsession.getId()+"','"+request.getParameter("prodid")+"','Kg','"+request.getParameter("quantity")+"','on');");
				  
				  //Pass result
				  //request.setAttribute("productadded","yes");		 
			 }
			  
			  if(request.getParameter("filter")!=null && !request.getParameter("filter").contains(";")) {
				  String filter=request.getParameter("filter");
				  if("all".equals(filter)) {
					  //Get products
					  query="SELECT prod.product_id,prod.market_id,prod.variant_gid,pc.price_vl,pc.currency_cd "
					  		+ "FROM shp_product prod "
					  		+ "INNER JOIN shp_price pc "
					  		+ "ON prod.product_id=pc.product_id;";
					  		
				  }else if(filter.contains(",")) {
					  String[] filters=filter.split(",");
					 
					  
					  //Get product list filtered by two labels
					  query= "SELECT prod.product_id,prod.variant_gid,prod.market_id,pc.price_vl,pc.currency_cd "
							+"FROM shp_price pc " 
							+"INNER JOIN shp_product prod "
							+"ON pc.product_id=prod.product_id "
							+"INNER JOIN shp_classification classif "
							+"ON prod.product_id=classif.product_id "
							+"INNER JOIN shp_label label "
							+"ON label.label_id=classif.label_id "
							+"WHERE classif.label_id IN ("+filters[0]+","+filters[1]+") "
							+"GROUP BY prod.product_id,pc.price_vl,pc.currency_cd "
							+"HAVING count(*) = 2;";
	  
				  }else if(filter.matches("\\d")){
					  //Get product list filtered by a label
					  	query="SELECT prod.market_id,prod.product_id,prod.variant_gid,pc.price_vl,pc.currency_cd "
					  		 +"FROM shp_price pc "
					  		 +"INNER JOIN shp_product prod "
					  		 +"ON pc.product_id=prod.product_id "
					  		 +"INNER JOIN shp_classification classif "
					  		 +"ON prod.product_id=classif.product_id "
					  		 +"WHERE classif.label_id="+filter+" "
					  		 +"GROUP BY prod.product_id,pc.price_vl,pc.currency_cd";
				  }
				  products = conn.getProductList(query);
			  }
			  
			  //Search product by name
			  if(request.getParameter("byname")!=null) {
				  ArrayList<Product> match = new ArrayList<Product>();
				  products = conn.getProductList("SELECT prod.product_id,prod.market_id,prod.variant_gid,pc.price_vl,pc.currency_cd "
					  							+ "FROM shp_product prod "
					  							+ "INNER JOIN shp_price pc "
					  							+ "ON prod.product_id=pc.product_id;");
				  		
				  String name=request.getParameter("byname");
				  for(Product product : products) {
					  
					  if(product.getName().toLowerCase().contains(name.toLowerCase())){
						  match.add(product);
					  }
				  }
				  
				  products = match;
			  }
			  
			  //Update product quantity
			  if("update".equals(request.getParameter("cart"))){
				  conn.manipulateDB("UPDATE shp_order_item "
						  			+" SET item_qt="+request.getParameter("quantity")
						  			+" WHERE order_id="+request.getParameter("order_id")
								    +" AND product_id="+request.getParameter("prodid")+";");
			  }
			  //Remove product from cart
			  else if("remove".equals(request.getParameter("cart"))){
				  conn.manipulateDB("DELETE FROM shp_order_item "
							      + "WHERE order_id="+request.getParameter("order_id")
								  + " AND product_id="+request.getParameter("prodid")+";");
			  }
			  
			  cart=conn.getCart("SELECT shp_order.session_tk,shp_order.order_id,shp_order_item.item_qt, "
					         +"shp_order_item.product_id FROM shp_order "
					         +"INNER JOIN shp_order_item "
					         +"ON shp_order.order_id=shp_order_item.order_id "
					         +"WHERE session_tk='"+(String)request.getAttribute("session")+"';");
			
			request.setAttribute("cartproducts", cart.getCartProducts());
			request.setAttribute("productsquantity", cart.getQuantity());		
			request.setAttribute("order_id", cart.getId());
			labels = conn.getLabels("SELECT * FROM shp_label");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
		request.setAttribute("products", products);
		request.setAttribute("labels", labels);
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
