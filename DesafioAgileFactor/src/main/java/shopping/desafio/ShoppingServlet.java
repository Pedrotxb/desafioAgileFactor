package shopping.desafio;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
		  List<Product> products = new ArrayList<Product>();
		  List<Label> labels = new ArrayList<Label>();
		  String query="";
		  ShoppingDB conn= new ShoppingDB();
		  try {
			  if(request.getParameter("filter")!=null) {
				  String filter=request.getParameter("filter");
				  
				  if("all".equals(filter)) {
					  //search all products
					  query="SELECT * FROM shp_product;";
					  
				  }else if(filter.contains(",")) {
					  String[] filters=filter.split(",");
					  String stfilter=filters[0];
					  String ndfilter=filters[1];
					  
					  //Search product belonging to both labels
					  query="SELECT product.product_id,product.variant_gid,"
						  + "product.market_id "
					      + "FROM shp_product product "
					      + "INNER JOIN shp_classification classif "
						  + "ON product.product_id=classif.product_id "
						  + "INNER JOIN shp_label label "
						  + "ON label.label_id=classif.label_id "
						  + "WHERE classif.label_id IN ("+stfilter+","+ndfilter+") "
						  + "GROUP BY product.product_id "
						  + "HAVING count(*) = 2;";
	  
				  }else if(filter.matches("\\d")){
					  //Search products by label
					  	query="SELECT * FROM shp_product "
							 	+ "INNER JOIN shp_classification "
							 	+ "ON shp_product.product_id=shp_classification.product_id "
							 	+ "WHERE shp_classification.label_id="+filter+";";
					  	
					  	
				  }
				 
				  
				  products = conn.getProductList(query);
			  }
			  
			  //Search product by name
			  if(request.getParameter("byname")!=null) {
				  List<Product> match = new ArrayList<Product>();
				  products = conn.getProductList("SELECT * FROM shp_product;");
				  String name=request.getParameter("byname");
				  for(Product product : products) {
					  
					  if(product.getName().toLowerCase().contains(name.toLowerCase())){
						  match.add(product);
					  }
				  }
				  
				  products = match;
			  }
			
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
