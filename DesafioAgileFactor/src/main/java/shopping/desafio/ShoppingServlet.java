package shopping.desafio;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Servlet implementation class Shopping
 */

public class ShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ShoppingDB db= new ShoppingDB();
	HttpSession session;
	Cart cart ;
	ArrayList<Label> labels = new ArrayList <Label>();
	ArrayList<Product> products = new ArrayList <Product>();

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
		this.session = request.getSession();
		getCart();	

		//Create cart if null and Add product to cart 
		if("yes".equals(request.getParameter("addproduct"))) {
			checkSession(Long.parseLong(request.getParameter("prodid")),
					Integer.parseInt(request.getParameter("quantity")));
		}

		// Update/Remove product from cart
		if("update".equals(request.getParameter("cart"))) {
			manageCart(request.getParameter("cart"),
					Long.parseLong(request.getParameter("prodid")),
					Integer.parseInt(request.getParameter("quantity")),	
					Long.parseLong(request.getParameter("order_id")));

		}else if("remove".equals(request.getParameter("cart"))){
			manageCart(Long.parseLong(request.getParameter("prodid")),
					Long.parseLong(request.getParameter("order_id")));
		}

		//Search product by name
		if(request.getParameter("byname")!=null) {
			getProductsByName(request.getParameter("byname"));
		}

		//Add/Remove label filter
		if(request.getParameter("filter")!=null){
			manageLabels(request.getParameter("filter"),
						 request.getParameter("labelname"));					 
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

	private void getCart() {
		try {
			//Get cart from session
			this.cart=db.getCart();
			session.setAttribute("cartproducts", cart.getCartProducts());
			session.setAttribute("productsquantity", cart.getQuantity());		
			session.setAttribute("order_id", cart.getId());
		}
		catch (SQLException e) {
			System.out.println("Exception connecting to database, getCart method in ShoppingServlet::"+e.getMessage());
			e.printStackTrace();
		}
	}

	private void getProductsByName(String name) {
		try {
			labels.clear();
			session.setAttribute("byname", name);
			products = db.getProductListbyName();
			session.setAttribute("products", products);
		}
		catch (SQLException e) {
			System.out.println("Exception connecting to database, getProductsByName method in ShoppingServlet::"+e.getMessage());
			e.printStackTrace();
		}

	}

	private void getProducts() {
		try {
			products.clear();
			products = db.getProducts();
			session.setAttribute("products", products);	
		}	
		catch (SQLException e) {
			System.out.println("Exception connecting to database, sgetProducts method in ShoppingServlet::"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void checkSession(long product_id, int quantity) {
		try {
			//Check cart on db if null create
			if (cart.getSession()==null) {
				db.createCart();
				this.cart=db.getCartSession();
			}	  
			//Add product to cart
			manageCart("add",product_id,quantity,cart.getId());
			//Update cart	
			getCart();	

		}  	
		catch (SQLException e) {
			System.out.println("Exception connecting to database, checkSession method in ShoppingServlet::"+e.getMessage());
			e.printStackTrace();
		}

	}

	private void manageCart(String manage, long product_id, int quantity, long order_id) {
		if("update".equals(manage)){
			try {
				db.updateProduct(quantity,order_id,product_id);
			}
			catch (SQLException e) {
				System.out.println("Exception connecting to database, manageCart method in ShoppingServlet::"+e.getMessage());
				e.printStackTrace();
			}
		
		}else{
			try {
				db.addProducttoCart(order_id,product_id,quantity);	
			}
			catch (SQLException e) {
				System.out.println("Exception connecting to database, manageCart method in ShoppingServlet::"+e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void manageCart(long product_id, long order_id) {
		try {
			db.removeProduct(order_id,product_id);
		}
		catch (SQLException e) {
			System.out.println("Exception connecting to database, manageCart method in ShoppingServlet::"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void manageLabels(String manage, String name) {
		try {
			Label label = db.getLabel(name);
			if("add".equals(manage)) {
				labels.add(label);
				session.setAttribute("labels", labels);
				getProducts();

			}else {
				//Remove label!!!
				for(int i=0;i<labels.size();i++) {
					if(label.getId()==labels.get(i).getId())
						labels.remove(i);
				}
				if(labels.isEmpty()) {
					//Remove products from removed label
					products.clear();
					session.setAttribute("products", products);	
					
				}else{
					session.setAttribute("labels", labels);
					getProducts();
				}
			}
			
		}	
		catch (SQLException e) {
			System.out.println("Exception connecting to database, manageLabels method in ShoppingServlet::"+e.getMessage());
			e.printStackTrace();
		}
	}
	
}
