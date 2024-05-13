package shopping.desafio;

import java.util.ArrayList;
import java.util.List;

public class Product {
	
	private long market;
	private long id;
	private String name;
	private String author;
	private String editor;
	private String createddate;
	private String updateddate;
	private Price price = new Price();

    private  ArrayList<Label> labels = new ArrayList<Label>();
 
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(String updateddate) {
		this.updateddate = updateddate;
	}

}
