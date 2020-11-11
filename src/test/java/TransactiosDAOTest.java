
import static org.junit.Assert.assertTrue;

import com.revature.repositories.TransactionsDAO;
import org.junit.Test;

public class TransactiosDAOTest {

    TransactionsDAO t = new TransactionsDAO();

    @Test
    public void testtransfer(){
        assertTrue(t.transfer(1, 4, 1));
    }

    @Test
    public void testDeposit(){
        assertTrue(t.deposit(1, 2));
    }

    @Test
    public void testWithdraw(){
        assertTrue(t.withdraw(1, 2));
    }

    @Test
    public void testBalance(){
        assertTrue(t.balance("alejandro@gmail.com", "alejandropassword"));
    }



	
}

