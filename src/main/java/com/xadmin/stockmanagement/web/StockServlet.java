package com.xadmin.stockmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.stockmanagement.bean.Stock;
import com.xadmin.stockmanagement.dao.StockDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class StockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StockDao stockDao;
       
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
		stockDao = new StockDao();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		try{switch(action){
			
		case "/new":
			showNewForm(request,response);
		break;
		
		case "/insert":
			insertStock(request,response);
		break;
		
		case "/delete":
			deleteStock(request,response);
		break;
		
		case "/edit":
			showEditForm(request,response);
			break;
			
		case "/update":
			updateStock(request,response);
		break;
			
		default :
			listStock(request,response);
			break;
			
		}
		}catch (SQLException ex) {
			throw new ServletException(ex);
		}
		
	}
		//new
		private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("stock-form.jsp");
			dispatcher.forward(request,response);
			
		}
		
		//insert stock
		private void insertStock(HttpServletRequest request, HttpServletResponse response) throws SQLException,IOException{
			
			String productname = request.getParameter("productname");
			String price = request.getParameter("price");
			String quantity = request.getParameter("quantity");
			Stock newStock = new Stock (productname,price,quantity);
			stockDao.insertStock(newStock);
			response.sendRedirect("list");
			
		}
		//delete stock
		private void deleteStock(HttpServletRequest request, HttpServletResponse response) throws SQLException,IOException{
			
			int stockid = Integer.parseInt(request.getParameter("stockid"));
			
			stockDao.deleteStock(stockid);
			
			response.sendRedirect("list");
			
		}
		//edit 
		private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException,IOException,ServletException{
			
			int stockid = Integer.parseInt(request.getParameter("stockid"));
			Stock existingStock;
			try {
				existingStock = stockDao.selectStock(stockid);
				RequestDispatcher dispatcher = request.getRequestDispatcher("stock-form.jsp");
				request.setAttribute("stock",existingStock);
				dispatcher.forward(request,response);
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
			
		}
		
		//update 
		private void updateStock(HttpServletRequest request, HttpServletResponse response) throws SQLException,IOException{
			
		
			int stockid = Integer.parseInt(request.getParameter("stockid"));
			String productname = request.getParameter("productname");
			String price = request.getParameter("price");
			String quantity = request.getParameter("quantity");
			
			Stock stock = new Stock (stockid,productname,price,quantity);
			
			stockDao.updateStock(stock);
			response.sendRedirect("list");
			
		}
		
		//default
		private void listStock(HttpServletRequest request, HttpServletResponse response) throws SQLException,IOException,ServletException {
			
			try {
				
				List<Stock> listStock = stockDao.selectAllStocks();
				request.setAttribute("listStock",listStock);
				RequestDispatcher dispatcher = request.getRequestDispatcher("stock-list.jsp");
				dispatcher.forward(request,response);
				
			}catch(Exception e) {
				
				e.printStackTrace(); 
			}
			
		}
		
	}



