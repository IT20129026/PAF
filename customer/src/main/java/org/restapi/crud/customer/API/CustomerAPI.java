package org.restapi.crud.customer.API;

import java.io.IOException;



import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



import org.restapi.crud.customer.model.Customer;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/CustomerAPI")
public class CustomerAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Customer docObject = new Customer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String output = docObject.insertcustomers(request.getParameter("docId"), 
				request.getParameter("docName"),
				request.getParameter("docEmail"), 
				request.getParameter("docContact"), 
				request.getParameter("docUnit"));	
		
				
												
												
		response.getWriter().write(output);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = docObject.updatecustomers(paras.get("hidItemIDSave").toString(),
				paras.get("docEmail").toString().replace("%", "@").replace("40", ""),
				paras.get("docContact").toString(),
				paras.get("docUnit").toString());
		
												 												
		response.getWriter().write(output);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = docObject.deleteCustomers(paras.get("docId").toString());
		response.getWriter().write(output);
		
	}
	
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params) {

				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {
		}
		return map;
	}


}
