package v.yeikovych.multi;


import java.time.LocalDate;

public class Sensor extends Device implements ISensor {

    private double alertMin = -100.0d;
    private double alertMax = 100.0d;
    private double currentReading;
    private boolean isConnected;

    public Sensor(String name, String manufacturer, PowerSource powerSource, LocalDate installationDate) {
        super(name, manufacturer, powerSource, installationDate);
        connect();
    }

    @Override
    public void connect() {
        isConnected = true;
    }

    @Override
    public void disconnect() {
        isConnected = false;
    }

    @Override
    public String getStatus() {
        return "Device: " + getName() +
                ", Manufacturer: " + getManufacturer() +
                ", Power Source: " + getPowerSource() +
                ", Installation Date: " + getInstallationDate() +
                ", Status: " + (isConnected ? "Connected" : "Disconnected") +
                ", Current Reading: " + currentReading;
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
    @SuppressWarnings("java:S2189")
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                if (isConnected) {
                    currentReading = measure();
                    if (currentReading < alertMin || currentReading > alertMax) {
                        System.out.println("Alert! Reading out of range: " + currentReading);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
