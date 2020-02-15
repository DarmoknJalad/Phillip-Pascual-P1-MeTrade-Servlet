import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.phillippascual.repository.StockRepository;
import com.phillippascual.repository.UserRepository;
import com.phillippascual.util.ConnectionUtil;

public class StockRepositoryTest {
	static Connection conn = null;

	@BeforeClass
	public static void connectDatabase() {
		conn = ConnectionUtil.getConnection();
	}
	
	@Test
	public void removeStockTest_returnsEqual() {
		String username = "testUser2";
		String ticker = "TEST2";
		double price = 2.50;
		int qty = 2;
		UserRepository.insertNewUser(username, conn);
		StockRepository.addStock(username, ticker, price, qty, conn);
		assertEquals(StockRepository.removeStock(username, ticker, conn), 1);
	}
	
	@Test
	public void addStockTest_returnsEqual() {
		String username = "testUser";
		String ticker = "TEST";
		double price = 1.50;
		int qty = 1;
		UserRepository.insertNewUser(username, conn);
		assertEquals(StockRepository.addStock(username, ticker, price, qty, conn), 1);
	}
	
	@After
	public void cleanUpTables() {
		String sqlStatement = "TRUNCATE TABLE public.stocks CASCADE";
		String sqlStatement2 = "TRUNCATE TABLE public.usertable CASCADE";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.execute(sqlStatement);
			stmt.execute(sqlStatement2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
