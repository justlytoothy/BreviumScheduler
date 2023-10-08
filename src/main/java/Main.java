import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;

public class Main {
  public static void main(String[] args) {
//    Properties props = System.getProperties();
//    props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
    final AppointmentScheduler appointmentScheduler = new AppointmentScheduler();
    if (appointmentScheduler.loadAppointmentScheduler()) {
      boolean success = true;
      while (success) {
        success = appointmentScheduler.scheduleNextAppointment();
      }
      System.out.println("Finished Scheduling All Appointments");
      appointmentScheduler.stopScheduler();
    } else {
      System.out.println("Error while starting the appointment scheduler! Please try again later.");
    }
  }
}
