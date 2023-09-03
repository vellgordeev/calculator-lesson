import common.Calculator;
import common.DivisionByZeroException;
import common.HistoryHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculationTests {
    private HistoryHandler historyHandler = new HistoryHandler();

    @AfterEach
    void readFromHistory() {
        assertThat(historyHandler.readHistoryFromFile())
                .isNotEmpty();
    }

    @Test
    void plusTest() throws DivisionByZeroException {
        assertThat(new Calculator("5", "15", "+")
                .calculate())
                .isEqualTo(20);
    }

    @Test
    void minusTest() throws DivisionByZeroException {
        assertThat(new Calculator("1500", "2500", "-")
                .calculate())
                .isEqualTo(-1000);
    }

    @Test
    void multipleTest() throws DivisionByZeroException {
        assertThat(new Calculator("-5", "25", "*")
                .calculate())
                .isEqualTo(-125);
    }

    @Test
    void divideTest() throws DivisionByZeroException {
        assertThat(new Calculator("15", "3", "/")
                .calculate())
                .isEqualTo(5);
    }

    @Test
    void divideZeroTest() {
        assertThatThrownBy(new Calculator("15", "0", "/")::calculate)
                .isInstanceOf(DivisionByZeroException.class)
                .hasMessage("Division by zero is forbidden");
    }

    @Test
    void emptyValueTest() {
        assertThatThrownBy(() -> new Calculator("", "2500", "5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Inputs and operator must be non-null and non-empty");

        assertThatThrownBy(() -> new Calculator("1500", "", "5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Inputs and operator must be non-null and non-empty");

        assertThatThrownBy(() -> new Calculator("1500", "2500", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Inputs and operator must be non-null and non-empty");

    }

    @Test
    void spacesTest() throws DivisionByZeroException {
        assertThat(new Calculator("1500", "2500", "                  -      ")
                .calculate())
                .isEqualTo(-1000);
    }

    @Test
    void nullValuesTest() {
        assertThatThrownBy(() -> new Calculator(null, "5", "+"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Inputs and operator must be non-null and non-empty");

        assertThatThrownBy(() -> new Calculator("5", null, "+"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Inputs and operator must be non-null and non-empty");

        assertThatThrownBy(() -> new Calculator("15", "5", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Inputs and operator must be non-null and non-empty");
    }
}
