import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void shouldNotNull() {
        assertNotNull(Managers.getDefault());
    }

    @Test
    void shouldNotNullHistory() {
        assertNotNull(Managers.getDefaultHistory());
    }


    @Test
    void getDefault() {
    }

    @Test
    void getDefaultHistory() {
    }
}