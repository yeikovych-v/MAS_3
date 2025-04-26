package v.yeikovych.multi;

import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public abstract class Device {

    private String name;
    private String manufacturer;
    private PowerSource powerSource;
    private LocalDate installationDate;

    protected Device(String name, String manufacturer, PowerSource powerSource, LocalDate installationDate) {
        setName(name);
        setInstallationDate(installationDate);
        setPowerSource(powerSource);
        setManufacturer(manufacturer);
    }

    private int calculateAge(LocalDate installationDate) {
        LocalDate now = LocalDate.now();
        return now.getYear() - installationDate.getYear() - (now.getDayOfYear() < installationDate.getDayOfYear() ? 1 : 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        throwIfFalse(() -> isValidName(name));
        this.name = name;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(LocalDate installationDate) {
        throwIfFalse(() -> installationDate != null && installationDate.isBefore(LocalDate.now()));
        this.installationDate = installationDate;
    }

    public int getAge() {
        return calculateAge(installationDate);
    }

    public abstract void connect();

    public abstract void disconnect();

    public abstract String getStatus();

    public PowerSource getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(PowerSource powerSource) {
        this.powerSource = powerSource;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        throwIfFalse(() -> isValidString(manufacturer));
        this.manufacturer = manufacturer;
    }
}
