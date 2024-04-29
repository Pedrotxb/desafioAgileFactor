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
					product.setId(result.getInt("product_id"));
					product.setMarket(result.getInt("market_id"));
					product.setName(result.getString("variant_gid"));
					product.setAuthor(result.getString("author_gid"));
					product.setEditor(result.getString("editor_gid"));
					product.setCreateddate(result.getString("created_dt"));
					product.setUpdateddate(result.getString("updated_dt"));
			
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
				
				product.setId(result.getInt("product_id"));
				product.setMarket(result.getInt("market_id"));
				product.setName(result.getString("variant_gid"));
				product.setAuthor(result.getString("author_gid"));
				product.setEditor(result.getString("editor_gid"));
				product.setCreateddate(result.getString("created_dt"));
				product.setUpdateddate(result.getString("updated_dt"));
				product.setPrice(result.getDouble("price_vl"));
				product.setPriceCurrency(result.getString("currency_cd"));
				product.setCategories(getCategories("Select * from shp_classification where product_id="+product.getId()+";"));
			}
			}catch (SQLException e) {
				
				System.out.println("Exception connecting to database::"+e.getMessage());
	            e.printStackTrace();
	            
			}
			return product;
		}
			
	
	public List<Categorie> getCategories(String query) throws SQLException{
		List<Categorie> categories = new ArrayList<Categorie>();
		loadDriver(driver);
		try (
				//Connect to database, run the query and keep results
		
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","admin");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
				
		){
			while(result.next()) {
				Categorie categorie = new Categorie();
				categorie.setName(result.getString("classification_st"));
				
				categories.add(categorie);
			}
		} 
		catch (SQLException e) {
			
			System.out.println("Exception connecting to database::"+e.getMessage());
            e.printStackTrace();
            
		}
		return categories;
	}
	
}
