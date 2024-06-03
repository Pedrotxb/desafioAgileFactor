package shopping.desafio;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.apache.pdfbox.pdmodel.PDDocument;  
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.util.Matrix;

/**
 * Servlet implementation class AutocompleteServlet
 */
public class GenerateFilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	BigDecimal total = BigDecimal.ZERO;
	ArrayList<Product> prods;
	ArrayList<Integer> quantity;
	String timeStamp;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateFilesServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.session = request.getSession(false);
		this.prods = (ArrayList<Product>)session.getAttribute("cartproducts");
		this.quantity = (ArrayList<Integer>)session.getAttribute("productsquantity");

		generateJson();
		generatePdf();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


	private void generateJson() {
		JSONObject products = new JSONObject();
		JSONObject order = new JSONObject();

		for (int i=0; i<prods.size();i++) {	
			JSONObject info = new JSONObject();
			total=total.add(prods.get(i).getPrice().multiply(BigDecimal.valueOf(quantity.get(i))));	
			info.put("Quantity", quantity.get(i));		
			info.put("Price", prods.get(i).getPrice().setScale(2));
			info.put("Total", prods.get(i).getPrice().multiply(BigDecimal.valueOf(quantity.get(i))).setScale(2));
			products.put(prods.get(i).getName(), info);						
		}

		order.put("Date", new SimpleDateFormat("(yyyy-MM-dd)").format(Calendar.getInstance().getTime()));
		order.put("Time", new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
		order.put("ID", (long)session.getAttribute("order_id"));
		order.put("Products", products);
		order.put("Total", total.setScale(2)+prods.get(0).getPriceCurrency());

		this.timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		try {

			FileWriter file = new FileWriter("C:\\Users\\Pedro\\git\\desafioAgileFactor\\DesafioAgileFactor\\src\\main\\webapp\\Orders\\Order"+(long)session.getAttribute("order_id")+"_"+timeStamp+".json");
			file.write(order.toString());
			file.flush();
			file.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception in GenerateFilesServlet ::"+e.getMessage());
		}
	}

	private void generatePdf(){
		PDDocument pdf= new PDDocument();  
		PDPage page = new PDPage(PDRectangle.A4);
		PDRectangle rect = page.getMediaBox();
		float pageWidth = rect.getWidth();
		float pageHeight = rect.getHeight();
		pdf.addPage(page);
		try {
			PDPageContentStream contentStream = new PDPageContentStream(pdf, page);


			PDFont title = (new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD));
			PDFont text = (new PDType1Font(Standard14Fonts.FontName.HELVETICA));

			int line=0;


			contentStream.beginText(); 
			contentStream.setFont(title, 24);
			contentStream.newLineAtOffset(100, pageHeight - 50*(++line));
			contentStream.showText("Encomenda: "+session.getAttribute("order_id")+"_"+timeStamp);
			contentStream.endText();

			contentStream.beginText();  
			contentStream.setFont(title, 15);
			contentStream.newLineAtOffset(50, pageHeight - 50*(++line));
			contentStream.showText("Produto");
			contentStream.endText();

			contentStream.beginText(); 
			contentStream.newLineAtOffset(200, pageHeight - 50*(line));
			contentStream.showText("Quantidade");
			contentStream.endText();

			contentStream.beginText(); 
			contentStream.newLineAtOffset(370, pageHeight - 50*(line));
			contentStream.showText("Preço");
			contentStream.endText();

			contentStream.beginText(); 
			contentStream.newLineAtOffset(500, pageHeight - 50*(line));
			contentStream.showText("Total");
			contentStream.endText();

			for (int i=0; i<prods.size();i++) {	
				contentStream.beginText();  
				contentStream.setFont(text, 12);
				contentStream.newLineAtOffset(50, pageHeight - 50*(++line));
				contentStream.showText(prods.get(i).getName());
				contentStream.endText();

				contentStream.beginText();  
				contentStream.newLineAtOffset(200, pageHeight - 50*(line));
				contentStream.showText(""+quantity.get(i));
				contentStream.endText();

				contentStream.beginText();  
				contentStream.newLineAtOffset(370, pageHeight - 50*(line));
				contentStream.showText(""+prods.get(i).getPrice().setScale(2));
				contentStream.endText();

				contentStream.beginText();  
				contentStream.newLineAtOffset(500, pageHeight - 50*(line));
				contentStream.showText(""+prods.get(i).getPrice().multiply(BigDecimal.valueOf(quantity.get(i))).setScale(2));
				contentStream.endText();
			}

			contentStream.beginText();  
			contentStream.setFont(title, 15);
			contentStream.newLineAtOffset(40, pageHeight - 50*(++line));
			contentStream.showText("Valor Total Encomenda:");
			contentStream.setFont(text, 12);	         
			contentStream.endText();

			contentStream.moveTo(212, pageHeight - 50*(line));
			contentStream.lineTo(479, pageHeight - 50*(line));
			contentStream.stroke();

			contentStream.beginText();  
			contentStream.setFont(title, 15);
			contentStream.newLineAtOffset(480, pageHeight - 50*(line));
			contentStream.showText(String.valueOf(total.setScale(2))+prods.get(0).getPriceCurrency());
			contentStream.endText();


			contentStream.close();  

			pdf.save("C:\\Users\\Pedro\\git\\desafioAgileFactor\\DesafioAgileFactor\\src\\main\\webapp\\Orders\\Order"+(long)session.getAttribute("order_id")+"_"+timeStamp+".pdf");
			pdf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
