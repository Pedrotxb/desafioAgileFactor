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
	
	public List<Product> getProductList(String query) throws SQLException{
		loadDriver(driver);
		List<Product> products = new ArrayList<Product>();
		try (
				//Connect to database, run the query and keep results
		
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
			){
			
				while(result.next()) {
				
					Product product = new Product();
					product.setId(result.getLong("product_id"));
					product.setMarket(result.getInt("market_id"));
					product.setName(result.getString("variant_gid"));
			
					products.add(product);
					
				}
		
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database::"+e.getMessage());
            e.printStackTrace();
            
		}
		return products;
	}


	public Product getProduct(String query) throws SQLException{
		loadDriver(driver);
		Product product = new Product();
		try (
				//Connect to database, run the query and keep results
		
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
			){
			
			while(result.next()) {
				
				product.setId(result.getLong("product_id"));
				product.setMarket(result.getLong("market_id"));
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
				return product;
			}
			}catch (SQLException e) {
				
				System.out.println("Exception connecting to database::"+e.getMessage());
	            e.printStackTrace();
	            
			}
			return product;
		}
			
	public boolean checkSubLabel(String query) throws SQLException{
		boolean check=false;
		loadDriver(driver);
		try (
				//Connect to database, run the query and keep results
		
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
				
		){
		
		check=(result != null && result.next()) ? true:false;
		 	
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database::"+e.getMessage());
            e.printStackTrace();
            
		}return check;
	}
	
	public List<Label> getLabels(String query) throws SQLException{
		List<Label> labels = new ArrayList<Label>();
		loadDriver(driver);
		try (
				//Connect to database, run the query and keep results
		
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
			
			System.out.println("Exception connecting to database::"+e.getMessage());
            e.printStackTrace();
            
		}
		return labels;
	}
	
}
