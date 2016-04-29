package org.nearbyshops.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.nearbyshops.contractClasses.ItemCategoryContract;
import org.nearbyshops.contractClasses.ItemContract;
import org.nearbyshops.contractClasses.JDBCContract;
import org.nearbyshops.contractClasses.ShopContract;
import org.nearbyshops.contractClasses.ShopItemContract;
import org.nearbyshops.model.Item;


public class ItemService {

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
		
	
	public int saveItem(Item item)
	{
		
		
		Connection conn = null;
		Statement stmt = null;
		int idOfInsertedRow = 0;

		String insertItemCategory = "INSERT INTO " 
				+ ItemContract.TABLE_NAME 
				+ "(" + ItemContract.ITEM_NAME + "," 
				+ ItemContract.ITEM_DESC + "," 
				+ ItemContract.ITEM_IMAGE_URL + "," 
				+ ItemContract.ITEM_BRAND_NAME + "," 
				+ ItemContract.ITEM_CATEGORY_ID 
				+ ") VALUES("
				+ "'" + item.getItemName() + "',"
				+ "'" + item.getItemDescription() + "'," 
				+ "'" + item.getItemImageURL() + "'," 
				+ "'" + item.getBrandName() + "'," 
				+ item.getItemCategoryID()
				+ ")";
		
		try {
			
			conn = DriverManager.getConnection(
					JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			idOfInsertedRow = stmt.executeUpdate(insertItemCategory,Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if(rs.next())
			{
				idOfInsertedRow = rs.getInt(1);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return idOfInsertedRow;
		
	}
	
	
	public int updateItem(Item item)
	{
		
		//,int itemCategoryID
		
		//item.setItemCategoryID(itemCategoryID);
		
		String updateStatement = "UPDATE ITEM SET ITEM_NAME = "
				+ "'" + item.getItemName() + "'"
				+ ","
				+ " ITEM_DESC = "
				+ "'" + item.getItemDescription() + "'"
				+ ","
				+ " ITEM_IMAGE_URL = "
				+ "'" + item.getItemImageURL() + "'"
				+ ","
				+ " ITEM_BRAND_NAME = "
				+ "'" + item.getBrandName() + "'"
				+ ","
				+ " ITEM_CATEGORY_ID = "
				+  item.getItemCategoryID()
				+ " WHERE ITEM_ID = "
				+ item.getItemID();
		
		Connection conn = null;
		Statement stmt = null; 
		
		int rowCountUpdated = 0;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCountUpdated = stmt.executeUpdate(updateStatement);
			
			
			System.out.println("Total rows updated: " + rowCountUpdated);	
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rowCountUpdated;
	}
	
	
	
	public int deleteItem(int itemID)
	{

		String deleteStatement = "DELETE FROM ITEM WHERE ITEM_ID = " 
				+ itemID;
		
		
		Connection conn= null;
		Statement stmt = null;
		int rowCountDeleted = 0;
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCountDeleted = stmt.executeUpdate(deleteStatement);
			
			System.out.println("Rows Deleted: " + rowCountDeleted);	
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rowCountDeleted;
	}

	
	
	public List<Item> readAllItems()
	{
		
		String query = "SELECT * FROM ITEM";
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				Item item = new Item();
				
				item.setItemID(rs.getInt("ITEM_ID"));
				item.setItemName(rs.getString("ITEM_NAME"));
				item.setItemDescription(rs.getString("ITEM_DESC"));
				item.setItemImageURL(rs.getString("ITEM_IMAGE_URL"));
				item.setBrandName(rs.getString("ITEM_BRAND_NAME"));
				item.setItemCategoryID(rs.getInt("ITEM_CATEGORY_ID"));
				
				itemList.add(item);
				
				
			}
			
			
			
			System.out.println("Total item queried " + itemList.size());	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		finally
		
		{
			
			try {
					if(rs!=null)
					{rs.close();}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		return itemList;
		
	}
	
	

	
	public List<Item> getItems(int itemCategoryID, int shopID)
	{
		String query = "";
		
		String queryNormal = "SELECT * FROM " + ItemContract.TABLE_NAME;
		
		
		String queryJoin = "SELECT DISTINCT " 
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_BRAND_NAME + ","
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_CATEGORY_ID + ","
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_ID + ","
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_IMAGE_URL + ","
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_NAME + ","
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_DESC + ""
				
				+ " FROM " 
				+ ShopContract.TABLE_NAME  + "," + ShopItemContract.TABLE_NAME + "," 
				+ ItemContract.TABLE_NAME + "," + ItemCategoryContract.TABLE_NAME
				+ " WHERE " 
				+ ShopContract.TABLE_NAME + "." + ShopContract.SHOP_ID 
				+ "="
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.SHOP_ID
				+ " AND "
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.ITEM_ID
				+ "="
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_ID
				+ " AND "
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_CATEGORY_ID
				+ "="
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID;
		
		

		if(shopID > 0)
		{
				queryJoin = queryJoin + " AND "
						+ ShopContract.TABLE_NAME 
						+ "."
						+ ShopContract.SHOP_ID + " = " + shopID; 	
			
		}
		
		if(itemCategoryID>0)
		{
			queryJoin = queryJoin + " AND "
					+ ItemContract.TABLE_NAME 
					+ "."
					+ ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID; 
			
			
			
			//" WHERE ITEM_CATEGORY_ID = " + itemCategoryID
			
			queryNormal = queryNormal + " WHERE "
					+ ItemContract.TABLE_NAME 
					+ "."
					+ ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID; 
			
		}
		
		
		if(shopID == 0 )
		{
			query = queryNormal;
			
		}else
		{
			query = queryJoin;			
		}
		
		
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				Item item = new Item();
				
				item.setItemID(rs.getInt("ITEM_ID"));
				item.setItemName(rs.getString("ITEM_NAME"));
				item.setItemDescription(rs.getString("ITEM_DESC"));
				item.setItemImageURL(rs.getString("ITEM_IMAGE_URL"));
				item.setBrandName(rs.getString("ITEM_BRAND_NAME"));
				item.setItemCategoryID(rs.getInt("ITEM_CATEGORY_ID"));
				
				itemList.add(item);
			}
			
			
			
			System.out.println("Item By CategoryID " + itemList.size());	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		finally
		
		{
			
			try {
					if(rs!=null)
					{rs.close();}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return itemList;
	}
	
	
			
	public Item getItem(int ItemID)
	{
		
		String query = "SELECT * FROM " +  ItemContract.TABLE_NAME 
				+ " WHERE " + ItemContract.ITEM_ID + " = " + ItemID;
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		//ItemCategory itemCategory = new ItemCategory();
		Item item = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			
			
			while(rs.next())
			{
				item = new Item();
				item.setItemID(rs.getInt("ITEM_ID"));
				item.setItemName(rs.getString("ITEM_NAME"));
				item.setItemDescription(rs.getString("ITEM_DESC"));		
				item.setItemImageURL(rs.getString("ITEM_IMAGE_URL"));
				item.setBrandName(rs.getString("ITEM_BRAND_NAME"));
				item.setItemCategoryID(rs.getInt("ITEM_CATEGORY_ID"));
			
				System.out.println("Get Item by ID : " + item.getItemID());
			}
			
			//System.out.println("Total itemCategories queried " + itemCategoryList.size());	
	
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		
		{
			
			try {
					if(rs!=null)
					{rs.close();}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return item;
	}
	
}