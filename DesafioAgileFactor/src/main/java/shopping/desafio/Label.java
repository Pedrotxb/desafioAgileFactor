package shopping.desafio;

import java.util.ArrayList;

public class Label {

	private String name;
	private long id;
	ArrayList<Tax> taxes = new ArrayList<Tax>();

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

	public ArrayList<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(ArrayList<Tax> taxes) {
		this.taxes = taxes;
	}

}
