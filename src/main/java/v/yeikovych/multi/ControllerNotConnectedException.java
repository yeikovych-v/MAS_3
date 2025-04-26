package v.yeikovych.multi;

public class ControllerNotConnectedException extends RuntimeException {
  public ControllerNotConnectedException(String message) {
    super(message);
  }
}