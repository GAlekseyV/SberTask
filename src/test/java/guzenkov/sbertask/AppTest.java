/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package guzenkov.sbertask;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test public void onlyOneInstanceOfSomeClass(){
        SomeClass obj_1 = SomeClass.getInstance();
        SomeClass obj_2 = SomeClass.getInstance();
        assertNotNull(obj_1);
        assertNotNull(obj_2);
        assertEquals(obj_1, obj_2);
    }
}
