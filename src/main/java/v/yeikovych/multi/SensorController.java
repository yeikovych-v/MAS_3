package v.yeikovych.multi;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;


public class SensorController extends Controller implements ISensor {

    private double alertMin = -100.0d;
    private double alertMax = 100.0d;
    private double currentReading;
    private Thread sensorThread;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private Consumer<Double> feedbackAction;
    private boolean feedbackLoopEnabled = false;

    public SensorController(String name, String manufacturer, PowerSource powerSource, LocalDate installationDate) {
        super(name, manufacturer, powerSource, installationDate);
        startSensorThread();
    }

    private void startSensorThread() {
        if (sensorThread == null || !sensorThread.isAlive()) {
            isRunning.set(true);
            sensorThread = new Thread(this);
            sensorThread.setDaemon(true);
            sensorThread.start();
        }
    }

    public void stopSensorThread() {
        isRunning.set(false);
        if (sensorThread != null) {
            sensorThread.interrupt();
        }
    }

    @Override
    public void connect() {
        super.connect();
        startSensorThread();
    }

    @Override
    public void disconnect() {
        super.disconnect();
        stopSensorThread();
    }

    @Override
    public String getStatus() {
        return super.getStatus() + "\nSensor Status:" +
                "\nCurrent Reading: " + currentReading +
                "\nAlert Thresholds: Min=" + alertMin + ", Max=" + alertMax +
                "\nFeedback Loop: " + (feedbackLoopEnabled ? "Enabled" : "Disabled");
    }

    @Override
    public double getReading() {
        return currentReading;
    }

    @Override
    public void setAlertThresholds(double min, double max) {
        this.alertMin = min;
        this.alertMax = max;
    }

    @Override
    public double measure() {
        var randomSign = Math.random() > 0.5 ? 1 : -1;
        var range = Math.abs(alertMax) + Math.abs(alertMin);
        return Math.random() * range * randomSign;
    }

    @Override
    public void run() {
        while (isRunning.get()) {
            try {
                Thread.sleep(10000);

                if (isConnected()) {
                    currentReading = measure();

                    if (currentReading < alertMin || currentReading > alertMax) {
                        triggerAlert();
                    }

                    if (feedbackLoopEnabled && feedbackAction != null) {
                        triggerFeedbackAction();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void triggerAlert() {
        try {
            executeAction("Alert Triggered", () ->
                    System.out.println("Alert! Reading out of range: " + currentReading)
            );
        } catch (Exception e) {
            System.err.println("Failed to log alert: " + e.getMessage());
        }
    }

    private void triggerFeedbackAction() {
        try {
            executeAction("Feedback Response", () ->
                    feedbackAction.accept(currentReading)
            );
        } catch (Exception e) {
            System.err.println("Failed to execute feedback action: " + e.getMessage());
        }
    }


    public void setFeedbackLoopEnabled(boolean enabled) {
        this.feedbackLoopEnabled = enabled;
    }


    public void setFeedbackAction(Consumer<Double> action) {
        this.feedbackAction = action;
    }

    public void configureControlResponses(
            Runnable belowMinAction,
            Runnable normalAction,
            Runnable aboveMaxAction
    ) {
        setFeedbackAction(reading -> {
            try {
                if (reading < alertMin) {
                    executeAction("Below Threshold Response", belowMinAction);
                } else if (reading > alertMax) {
                    executeAction("Above Threshold Response", aboveMaxAction);
                } else {
                    executeAction("Normal Range Response", normalAction);
                }
            } catch (Exception e) {
                System.err.println("Failed to execute control response: " + e.getMessage());
            }
        });
    }
}
