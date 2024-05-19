package shopping.desafio;

import java.util.ArrayList;
public class Cart {
	private long order_id;
	private String session;
	private ArrayList<Integer> quantity;
	private  ArrayList<Product> products = new ArrayList<Product>();

	public long getId() {
		return order_id;
	}
	public void setId(long order_id) {
		this.order_id = order_id;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public ArrayList<Integer> getQuantity() {
		return quantity;
	}
	public void setQuantity(ArrayList<Integer> itemquantity) {
		this.quantity = itemquantity;
	}

	public ArrayList<Product> getCartProducts() {
		return products;
	}
	public void setCartProducts(ArrayList<Product> products) {
		this.products = products;
	}

}
