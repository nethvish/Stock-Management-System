package com.xadmin.stockmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xadmin.stockmanagement.bean.Stock;


public class StockDao {
	
	//all JDBC
	private String jdbcURL = "jdbc:mysql://localhost:3306/stockmanagement?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "nethum";
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	
	//all SQL quarries
	private static final String insert_stocks_sql = "insert into stocks"+" (productname,price,quantity)values" + "(?,?,?);";
	private static final String select_stocks_by_ID = "select stockid,productname,price,quantity from stocks where stockid = ?";
	private static final String select_all_stocks = "select * from stocks";
	private static final String delete_stocks_sql = "delete from stocks where stockid = ?;";
	private static final String update_stocks_sql = "update stocks set productname = ?,price = ?,quantity = ? where stockid = ?;";
	
	//default constructor
	public StockDao() {
		
	}
	
	//method for get the connection of the JDBC for return the connection
	protected Connection getConnection() {
		
		Connection connection =  null;
		
		try {
			//loaded the JDBC
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return connection;
		
	}
	
	
	//CRUD OPERATION METHODS
	
	//insert stock method 
	public void insertStock(Stock stock) throws SQLException {
		
		System.out.println(insert_stocks_sql);
		try(
				Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(insert_stocks_sql)){
			
			preparedStatement.setString(1,stock.getProductname());
			preparedStatement.setString(2, stock.getPrice());
			preparedStatement.setString(3, stock.getquantity());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			
		}catch(SQLException e) {
			
			printSQLException (e);
		}
		
	}
	
	
	//select stock method by id
	public Stock selectStock(int stockid) {
		
		Stock stock = null;
		//step 1 : establishing the connection
		try(Connection connection = getConnection(); 
		//step2 : creating the statement using connection object
		PreparedStatement preparedStatement = connection.prepareStatement(select_stocks_by_ID);){
			
			preparedStatement.setInt(1, stockid);
			System.out.println(preparedStatement);
			//step3 : execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			
			//step 4 : process the resultSet object
			while(rs.next()) {
				
				String productname = rs.getString("productname");
				String price = rs.getString("price");
				String quantity = rs.getString("quantity");
				stock = new Stock(stockid,productname,price,quantity);
				
			}
		}catch(SQLException e) {
			
			printSQLException(e);
		}
		
		return stock;
		
	}
	
	
	//select all stocks method
	public List<Stock> selectAllStocks(){
		
		//using try-with-resources to avoid closing resources(boiler plate code)
		List<Stock> stocks = new ArrayList<>();
		//step 1 : establishing the connection
		try(Connection connection = getConnection();
		//step2 : creating the statement using connection object
		PreparedStatement preparedStatement = connection.prepareStatement(select_all_stocks);){
		System.out.println(preparedStatement);
		//step3 : execute the query or update query
		ResultSet rs = preparedStatement.executeQuery();
		//step 4 : process the resultSet object
		while(rs.next()) {
				
			int stockid = rs.getInt("stockid");
			String productname = rs.getString("productname");
			String price = rs.getString("price");
			String quantity = rs.getString("quantity");
			stocks.add(new Stock(stockid,productname,price,quantity)); //storing the created user objects in the users list
				
		}
			
		}catch(SQLException e) {
			
			printSQLException(e);
		}
		
		return stocks;
		
	}
	
	
	//update stock method
	public boolean updateStock(Stock stock) throws  SQLException{
		
		boolean rowUpdated;
		//step 1 : establishing the connection
				try(Connection connection = getConnection();
				//step2 : creating the statement using connection object
				PreparedStatement statement = connection.prepareStatement(update_stocks_sql);){
				System.out.println("update stock :"+statement);
				
				statement.setString(1, stock.getProductname());
				statement.setString(2, stock.getPrice());		
				statement.setString(3, stock.getquantity());
				statement.setInt(4, stock.getStockid());
		
		rowUpdated = statement.executeUpdate() > 0;
	}
		return rowUpdated;		
	}
	
	
	//delete stock
	public boolean deleteStock(int stockid) throws SQLException{
		
		boolean rowDeleted;
		
		//step 1 : establishing the connection
		try(Connection connection = getConnection();
		//step2 : creating the statement using connection object
		PreparedStatement statement = connection.prepareStatement(delete_stocks_sql);){
			statement.setInt(1,stockid);
			rowDeleted = statement.executeUpdate() > 0;
			
		}
		return rowDeleted;
		
	}
	
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
	
}
