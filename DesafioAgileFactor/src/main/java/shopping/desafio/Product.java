package shopping.desafio;

import java.util.ArrayList;

public class Product {

	private long market;
	private long id;
	private String name;
	private Price price = new Price();
	private  ArrayList<Label> labels = new ArrayList<Label>();

	
	public boolean containLabel(long label_id) {
		for(Label label : this.labels) {
			if(label.getId()==label_id)
				return true;
		}
		return false;
	}
	
	public void setLabels(ArrayList<Label> labels) {
		this.labels=labels;
	}

	public ArrayList<Label> getLabels(){
		return this.labels;	
	}

	public String getLabelsString() {
		String label= "";
		for(Label lab : labels) {
			label += lab.getName()+" ";
		}   	
		return label;
	}

	public double getPrice() {
		return price.getPrice();
	}
	public String getPriceCurrency() {
		return this.price.getCurrency();
	}
	public void setPrice(double price) {
		this.price.setPrice(price);
	}
	public void setPriceCurrency(String currency) {
		this.price.setCurrency(currency);
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMarket() {
		return market;
	}
	public void setMarket(long market) {
		this.market = market;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
