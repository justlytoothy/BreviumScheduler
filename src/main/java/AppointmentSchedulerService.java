import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AppointmentSchedulerService {
  private static final String baseUrl = "http://scheduling-interview-2021-265534043.us-west-2.elb.amazonaws.com";
  private static final String token = "?token=1fd3fac1-afc1-4e75-bcc0-b2c3b7c43035";
  private static final Gson gson = new Gson();
  private final HttpClient client;

  public AppointmentSchedulerService() {
    this.client = HttpClient.newHttpClient();
  }

  public Integer startScheduler () {
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(baseUrl + "/api/Scheduling/Start" + token))
              .POST(HttpRequest.BodyPublishers.ofString(""))
              .setHeader("accept", "*/*")
              .setHeader("content-type", "application/x-www-form-urlencoded")
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode();
    }
    catch (Exception e) {
      e.printStackTrace();
      return 404;
    }
  }

  public HttpResponse<String> getSchedule() {
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(baseUrl + "/api/Scheduling/Schedule" + token))
              .GET()
              .setHeader("accept", "text/plain")
              .timeout(Duration.ofMinutes(5))
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public HttpResponse<String> getNextAppointmentRequest() {
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(baseUrl + "/api/Scheduling/AppointmentRequest" + token))
              .GET()
              .setHeader("accept", "text/plain")
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public HttpResponse<String> stopScheduler() {
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(baseUrl + "/api/Scheduling/Stop" + token))
              .POST(HttpRequest.BodyPublishers.ofString(""))
              .setHeader("accept", "application/json")
              .setHeader("content-type", "application/x-www-form-urlencoded")
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean scheduleAppointment(final ScheduleAppointmentRequest newAppointment) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create("http://scheduling-interview-2021-265534043.us-west-2.elb.amazonaws.com/api/Scheduling/Schedule?token=1fd3fac1-afc1-4e75-bcc0-b2c3b7c43035"))
              .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(newAppointment)))
              .setHeader("accept", "*/*")
              .setHeader("Content-Type", "application/json")
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode() == 200;
    }
    catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
