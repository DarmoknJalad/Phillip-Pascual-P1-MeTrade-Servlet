import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.phillippascual.repository.UserRepository;
import com.phillippascual.util.ConnectionUtil;

public class UserRepositoryTests {
	static Connection testConn = null;
	
	@BeforeClass
	public static void connectDatabase() {
		testConn = ConnectionUtil.getConnection();
	}

	
	@Test
	public void removeUserTest_returnsTrue() {
		String username = "test2";
		UserRepository.insertNewUser(username, testConn);
		assertEquals(UserRepository.removeUser(username, testConn), 1);
	}
	
	@Test
	public void insertNewUserTest_returnsTrue() {
		String username = "test";
		assertEquals(UserRepository.insertNewUser(username, testConn), 1);
	}
	
	
	
	@After
	public void truncateUserTable() {
		String sqlStatement = "TRUNCATE TABLE public.usertable CASCADE";
		Statement stmt;
		try {
			stmt = testConn.createStatement();
			stmt.execute(sqlStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
