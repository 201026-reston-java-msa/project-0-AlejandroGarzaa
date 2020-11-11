import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.revature.repositories.ValidationDAO;

import org.junit.Test;

public class ValidationDAOTest {
    ValidationDAO v = new ValidationDAO();

    // username validation

    @Test
    public void testUsername(){
        assertTrue(v.usercheck("alejandro@gmail.com"));
        assertFalse(v.usercheck("alejandro@gmail"));
    }

// password validation
@Test
public void testPassword(){
    assertTrue(v.passcheck("alejandro@gmail.com", "alejandropassword"));
    assertFalse(v.passcheck("alejandro@gmail.com", "alejandropass"));
}

// test access level validation
@Test
public void testAccess(){
    assertEquals(3, v.accesslevel("alejandro@gmail.com", "alejandropassword"));
    assertEquals(2, v.accesslevel("billy@gmail.com", "billypassword"));
    assertEquals(1, v.accesslevel("tom@gmail.com", "tompassword"));
}

// test account status
@Test
public void testStatus(){
    assertTrue(v.status("alejandro@gmail.com", "alejandropassword"));
    
}
@Test
public void testFindAll(){
    assertTrue(v.findAll());
}
    
}
