package shopping.desafio;

import java.sql.SQLException;

public class Label {
	private String name;
	private long id;
	private String tax;
	
	
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
