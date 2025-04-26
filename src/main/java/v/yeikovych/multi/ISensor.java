package v.yeikovych.multi;

public interface ISensor extends Runnable {

    double getReading();

    void setAlertThresholds(double min, double max);

    double measure();

}
