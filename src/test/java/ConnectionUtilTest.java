import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import com.phillippascual.util.ConnectionUtil;

public class ConnectionUtilTest {

	@Test
	public void testGetConnection_returnsTrueIfConnected() {
		Connection testConn = ConnectionUtil.getConnection();
		assertEquals(testConn != null, true);
	}

}
