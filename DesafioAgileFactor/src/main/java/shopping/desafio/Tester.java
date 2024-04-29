package shopping.desafio;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class Tester {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		List<Product> list = new ArrayList<Product>();
		ShoppingDB conn= new ShoppingDB();
		try {
			list=conn.getProductList("select * from shp_product;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(Object object : list) {
			Product product = (Product)list.get(list.indexOf(object));
			System.out.println( product.getName()+" "+(product).getAuthor());
		}
	}

}
