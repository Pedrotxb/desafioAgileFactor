package shopping.desafio;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.json.JSONArray;


public class ShoppingDB {
private String driver ="com.mysql.cj.jdbc.Driver";
	
	public void loadDriver(String driver) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	public void createCart(String session) throws SQLException{
		loadDriver(driver);
		String query = "INSERT INTO shp_order (client_gid,session_tk,order_st,created_dt,updated_dt) "
 				 +"VALUES ('me',?,'on',now(),now());";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);
			){
			stmt.setString(1, session);
			stmt.executeUpdate();
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, createCart method::"+e.getMessage());
            e.printStackTrace();
            
		}
		
	}
	
	public void addProducttoCart(long order_id, long product_id, int quantity) throws SQLException{
		loadDriver(driver);
		String query = "INSERT INTO shp_order_item (order_id,product_id,unit_cd,item_qt,item_st) "
					  +"VALUES (?,?,'Kg',?,'on')"
					  +"ON duplicate key UPDATE item_qt= item_qt + VALUES(item_qt);";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);
			){
			stmt.setLong(1, order_id);
			stmt.setLong(2, product_id);
			stmt.setInt(3, quantity);
			stmt.executeUpdate();
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, addProducttoCart method::"+e.getMessage());
            e.printStackTrace();
            
		}
		
	}
	
	
	public void updateProduct(int quantity, long order_id, long product_id) {
		loadDriver(driver);
		String query="UPDATE shp_order_item "
	  				+" SET item_qt=?"
	  				+" WHERE order_id=?"
	  				+" AND product_id=?;";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);
				
			){
					stmt.setInt(1, quantity);
					stmt.setLong(2, order_id);
					stmt.setLong(3, product_id);
					stmt.executeUpdate();
			    
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, updateProduct method::"+e.getMessage());
            e.printStackTrace();
            
		}
	}
	
	public void removeProduct(long order_id, long product_id) {
		loadDriver(driver);
		String query="DELETE FROM shp_order_item "
			       + "WHERE order_id=? "
				   + "AND product_id=?;";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);
				
			){
					stmt.setLong(1, order_id);
					stmt.setLong(2, product_id);
					stmt.executeUpdate();
			    
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, removeProduct method::"+e.getMessage());
            e.printStackTrace();
            
		}
	}
	

	public Cart getCart(String session) throws SQLException{
		loadDriver(driver);
		Cart cart = new Cart();
		ArrayList<Integer> itemquantity = new ArrayList<Integer>();
		String query = "SELECT shp_order.session_tk,shp_order.order_id,shp_order_item.item_qt, "
	         	  +"shp_order_item.product_id FROM shp_order "
	         	  +"INNER JOIN shp_order_item "
	         	  +"ON shp_order.order_id=shp_order_item.order_id "
	         	  +"WHERE session_tk=?;";
		
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);
				
			){
					stmt.setString(1,session);
					ResultSet result=stmt.executeQuery();
			    
			while(result.next()) {
				if(cart.getSession()==null) {
					cart.setId(result.getLong("order_id"));
					cart.setSession(result.getString("session_tk"));
					cart.setCartProducts(getProductsinCart(cart.getSession()));
				}
				itemquantity.add(result.getInt("item_qt"));
			}
			cart.setQuantity(itemquantity);
			result.close();
				
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getCart method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return cart;
	}
	
	public Cart getCartSession(String session) throws SQLException{
		loadDriver(driver);
		Cart cartsession = new Cart();
		String query ="SELECT session_tk,order_id FROM shp_order WHERE session_tk=?;";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);
				
			){
			stmt.setString(1, session);
			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				cartsession.setSession(result.getString("session_tk"));
				cartsession.setId(result.getLong("order_id"));
			}
			result.close();	
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getCartSession method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return cartsession;
	}
	
	public ArrayList<Product> getProductListbyName(String name) throws SQLException{
		loadDriver(driver);
		ArrayList<Product> products = new ArrayList<Product>();
		String query = "Select * from shp_product product "+
					   "INNER JOIN shp_price price "+
					   "ON product.product_id=price.product_id "+
					   "WHERE product.variant_gid LIKE ?;";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);				
			){
				stmt.setString(1, "%"+name+"%");
				ResultSet result = stmt.executeQuery();
				while(result.next()) {
				
					Product product = new Product();
					product.setId(result.getLong("product_id"));
					product.setMarket(result.getInt("market_id"));
					product.setName(result.getString("variant_gid"));
					product.setPrice(result.getDouble("price_vl"));
					product.setPriceCurrency(result.getString("currency_cd"));
					product.setLabels(getProductLabels(product.getId()));
					
					products.add(product);
					
				}
				result.close();
		
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getProductListbyName method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return products;
	}
	
	public ArrayList<Product> getProductsinCart(String session) throws SQLException{
		loadDriver(driver);
		ArrayList<Product> products = new ArrayList<Product>();
		String query ="SELECT prod.product_id,market_id,prod.variant_gid,pc.price_vl,pc.currency_cd "
					+ "FROM shp_price pc "
					+ "INNER JOIN shp_product prod "
					+ "ON prod.product_id=pc.product_id "
					+ "INNER JOIN shp_order_item orderitm "
					+ "ON prod.product_id=orderitm.product_id "
					+ "INNER JOIN shp_order orderr "
					+ "ON orderitm.order_id=orderr.order_id "
					+ "WHERE orderr.session_tk=?;";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);				
			){
				stmt.setString(1, session);
				ResultSet result = stmt.executeQuery();
				while(result.next()) {
				
					Product product = new Product();
					product.setId(result.getLong("product_id"));
					product.setMarket(result.getInt("market_id"));
					product.setName(result.getString("variant_gid"));
					product.setPrice(result.getDouble("price_vl"));
					product.setPriceCurrency(result.getString("currency_cd"));
					product.setLabels(getProductLabels(product.getId()));
					
					products.add(product);
					
				}
				result.close();
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getProductListbyName method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return products;
	}

	
	public ArrayList<Product> getProductList() throws SQLException{
		loadDriver(driver);
		ArrayList<Product> products = new ArrayList<Product>();
		String query="SELECT prod.product_id,prod.market_id,prod.variant_gid,pc.price_vl,pc.currency_cd "
		  			+ "FROM shp_product prod "
		  			+ "INNER JOIN shp_price pc "
		  			+ "ON prod.product_id=pc.product_id;";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
			){
			
				while(result.next()) {
				
					Product product = new Product();
					
					product.setId(result.getLong("product_id"));
					product.setMarket(result.getInt("market_id"));
					product.setName(result.getString("variant_gid"));
					product.setPrice(result.getDouble("price_vl"));
					product.setPriceCurrency(result.getString("currency_cd"));
					product.setLabels(getProductLabels(product.getId()));
					
					products.add(product);	
				}
		
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getProductList method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return products;
	}
	
	
	
	public ArrayList<Label> getProductLabels(long product_id) throws SQLException{
		ArrayList<Label> labels = new ArrayList<Label>();
		loadDriver(driver);
		String query = "SELECT * "+
					   "FROM shp_label "+
					   "INNER JOIN shp_classification "+
					   "ON shp_label.label_id= shp_classification.label_id "+
					   "INNER JOIN shp_product "+
				       "ON shp_classification.product_id = shp_product.product_id "+
					   "WHERE shp_product.product_id=?;";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);
			
				
		){
			stmt.setLong(1, product_id);
			ResultSet result = stmt.executeQuery();
			
			while(result.next()) {
				Label label = new Label();
				label.setId(result.getLong("label_id"));
				label.setName(result.getString("label_nm"));
				
				labels.add(label);
			}
			result.close();
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getProductLabels method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return labels;
	}
	
	public ArrayList<Label> getLabels() throws SQLException{
		ArrayList<Label> labels = new ArrayList<Label>();
		loadDriver(driver);
		String query = "SELECT label_id,label_nm FROM shp_label";
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
				
		){
			while(result.next()) {
				Label label = new Label();
				label.setId(result.getLong("label_id"));
				label.setName(result.getString("label_nm"));
				
				labels.add(label);
			}
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getLabels method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return labels;
	}
	
	public JSONArray searchList(String type, String search) throws SQLException{
		loadDriver(driver);
		String query="";
		JSONArray json=new JSONArray();
		
		if("label"==type) {
			query="Select label_nm from shp_label "+
				  "WHERE label_nm LIKE ?;";	
		}else {
			query="Select variant_gid from shp_product "+
				  "WHERE variant_gid LIKE ?;";
		}
		
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				PreparedStatement stmt = conn.prepareStatement(query);
			){
			stmt.setString(1, "%"+search+"%");
			ResultSet result = stmt.executeQuery();
			
			while(result.next()) {
				if("label".equals(type)) {
					json.put(result.getString("label_nm"));
				}else {
					json.put(result.getString("variant_gid"));				
				}
			}
			result.close();
				
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getProductList method::"+e.getMessage());
            e.printStackTrace();
            
		}

		return json;
	}
	
}
