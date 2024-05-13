package shopping.desafio;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.filters.AddDefaultCharsetFilter;

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
	
	public void manipulateDB(String query) throws SQLException{
		loadDriver(driver);
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
			){
			int rslt=stmt.executeUpdate(query);
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, manipulateDB method::"+e.getMessage());
            e.printStackTrace();
            
		}
		
	}
	

	public Cart getCart(String query) throws SQLException{
		loadDriver(driver);
		Cart cart = new Cart();
		ArrayList<Integer> itemquantity = new ArrayList<Integer>();
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
			){
			while(result.next()) {
				if(cart.getSession()==null) {
					cart.setId(result.getLong("order_id"));
					cart.setSession(result.getString("session_tk"));
					cart.setCartProducts(getProductList("SELECT prod.product_id,market_id,prod.variant_gid,pc.price_vl,pc.currency_cd "
							+ "FROM shp_price pc "
							+ "INNER JOIN shp_product prod "
							+ "ON prod.product_id=pc.product_id "
							+ "INNER JOIN shp_order_item orderitm "
							+ "ON prod.product_id=orderitm.product_id "
							+ "INNER JOIN shp_order orderr "
							+ "ON orderitm.order_id=orderr.order_id "
							+ "WHERE orderr.session_tk='"+cart.getSession()+"';"));
				}
				itemquantity.add(result.getInt("item_qt"));
			}
			cart.setQuantity(itemquantity);
		
				
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getCart method::"+e.getMessage());
            e.printStackTrace();
            
		}
		
		
		return cart;
	}
	
	public Cart getCartSession(String query) throws SQLException{
		loadDriver(driver);
		Cart cartsession = new Cart();
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
			){
				if(result.next()) {
					cartsession.setSession(result.getString("session_tk"));
					cartsession.setId(result.getLong("order_id"));
				}
				
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getCartSession method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return cartsession;
	}
	
	public ArrayList<Product> getProductList(String query) throws SQLException{
		loadDriver(driver);
		ArrayList<Product> products = new ArrayList<Product>();
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
					product.setLabels(getLabels("SELECT * "+
							"FROM shp_label "+
							"INNER JOIN shp_classification "+
							"ON shp_label.label_id= shp_classification.label_id "+
							"INNER JOIN shp_product "+
							"ON shp_classification.product_id = shp_product.product_id "+
							"WHERE shp_product.product_id="+product.getId()+";"));
					
					
					products.add(product);
					
				}
		
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getProductList method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return products;
	}
	
	
	public ArrayList<Label> getLabels(String query) throws SQLException{
		ArrayList<Label> labels = new ArrayList<Label>();
		loadDriver(driver);
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
				
		){
			while(result.next()) {
				Label label = new Label();
				label.setId(result.getLong("label_id"));
				label.setName(result.getString("label_nm"));
				label.setTax(result.getString("label_tp"));
				
				labels.add(label);
			}
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database, getLabels method::"+e.getMessage());
            e.printStackTrace();
            
		}
		return labels;
	}
	
}
