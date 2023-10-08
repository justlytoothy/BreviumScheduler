import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AppointmentScheduler {
  private final AppointmentSchedulerService appointmentSchedulerService;
  private Schedule schedule;
  private final Gson gson;

  public AppointmentScheduler() {
    this.appointmentSchedulerService = new AppointmentSchedulerService();
    this.gson = new Gson();
  }

  public boolean loadAppointmentScheduler() {
    if (appointmentSchedulerService.startScheduler() == 200) {
      HttpResponse<String> response = appointmentSchedulerService.getSchedule();
      Type typeList = new TypeToken<ArrayList<Appointment>>() {}.getType();

      List<Appointment> initialSchedule = gson.fromJson(response.body(), typeList);
      this.schedule = new Schedule(initialSchedule);
      return true;
    } else {
      return false;
    }
  }

  public boolean scheduleNextAppointment() {
    HttpResponse<String> response = appointmentSchedulerService.getNextAppointmentRequest();
    if (response.statusCode() == 200) {
      AppointmentRequest appointmentRequest = gson.fromJson(response.body(), AppointmentRequest.class);
      ScheduleAppointmentRequest request = schedule.addAppointmentToSchedule(appointmentRequest);
      return appointmentSchedulerService.scheduleAppointment(request);
    } else {
      return false;
    }
  }

  public void stopScheduler() {
    HttpResponse<String> response = appointmentSchedulerService.stopScheduler();
    if (response.statusCode() == 200) {
      Type typeList = new TypeToken<ArrayList<Appointment>>() {}.getType();

      List<Appointment> finalSchedule = gson.fromJson(response.body(), typeList);
      System.out.println("Final Schedule");
      for (Appointment a : finalSchedule) {
        System.out.println(a);
      }
    }
  }
}
