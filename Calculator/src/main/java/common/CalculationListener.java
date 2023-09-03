package common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculationListener implements CalculationCompletedListener {

    private final HistoryHandler historyHandler = new HistoryHandler();

    @Override
    public void onCalculationComplete(Calculator calculator,Double result) {
        historyHandler.writeHistoryToFile(calculator, result.toString(), "UTC+4");

        log.info(String.format("Calculation has completed with result: %.9f!", result));
    }
}
