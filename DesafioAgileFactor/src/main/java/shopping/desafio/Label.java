package shopping.desafio;

import java.sql.SQLException;

public class Label {
	private String name;
	private long id;
	private String tax;
	ShoppingDB conn = new ShoppingDB();
	
	
	public boolean checkSubLabel(long taxlabelid) throws SQLException {
		boolean check = conn.checkSubLabel("SELECT product_id "+
										  "FROM shp_classification "+
										  "WHERE label_id in ("+this.id+","+taxlabelid+") "+
										  "Group BY product_id "+
										  "HAVING COUNT(*) = 2 ;"); 
		return check;
	}
	public String getName() {
		return name;
	}

	public void setName(String categorie) {
		this.name = categorie;
	}

	public long getId() {
		return id;
	}

	public void setId(long labelid) {
		this.id = labelid;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}
	
	
}
