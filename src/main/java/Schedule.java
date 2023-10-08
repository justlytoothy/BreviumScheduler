import java.text.DateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Schedule {
  private final Map<DoctorIdAppointmentTimeCompositeKey, Appointment> appointmentByCompositeKey;
  private final Map<Integer, Set<LocalDate>> patientAppointmentDatesByPatientId;
  public Schedule(List<Appointment> initialSchedule) {
    appointmentByCompositeKey = new HashMap<>();
    patientAppointmentDatesByPatientId = new HashMap<>();
    for (final Appointment appt : initialSchedule) {
      addAppointmentNoValidation(appt);
    }
  }

  /**
   * Runs appointment through all validation for a specific appointment time and doctor id
   * @param appointment
   * @return true if time is valid, false if not
   */
  public boolean isAppointmentValid(final ScheduleAppointmentRequest appointment, ZonedDateTime dateTime) {
    LocalTime time = dateTime.toLocalTime();
    boolean allowedWeekdays = dateTime.getMonth() == Month.NOVEMBER || dateTime.getMonth() == Month.DECEMBER;
    if (dateTime.getMinute() != 0) {
      return false;
    }
    if (!allowedWeekdays &&
            dateTime.getDayOfWeek() != DayOfWeek.SATURDAY &&
            dateTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
      return false;
    }
    //Out of range
    if (time.isBefore(LocalTime.of(8,0,0)) || time.isAfter(LocalTime.of(16,0,0))) {
      return false;
    }
    if (appointment.isNewPatientAppointment() && time.getHour() != 15 && time.getHour() != 16) {
      return false;
    }
    if (appointmentByCompositeKey.containsKey(
            new DoctorIdAppointmentTimeCompositeKey(appointment.getDoctorId(), dateTime))) {
      System.out.println("Doctor has an appointment at that time");
      return false;
    }
    if (!appointmentAtLeastOneWeekApart(appointment.getPersonId(), dateTime)) {
      return false;
    }
    return true;
  }

  private boolean appointmentAtLeastOneWeekApart(int personId, ZonedDateTime dateTime) {
    Set<LocalDate> existingAppointments = patientAppointmentDatesByPatientId
            .getOrDefault(personId, new HashSet<>());
    if (existingAppointments.size() == 0) {
      return true;
    }
    for (LocalDate date : existingAppointments) {
      if (ChronoUnit.DAYS.between(date, dateTime) < 7) {
        return false;
      }
    }
    return true;
  }

  /**
   * Chooses correct time for appointment and adds to internal schedule.
   * @param appointmentRequest appointment with list of preferred times
   * @return appointment object ready for api with correct appointment time selected
   */
  public ScheduleAppointmentRequest addAppointmentToSchedule(AppointmentRequest appointmentRequest) {
    ScheduleAppointmentRequest appointment =
            new ScheduleAppointmentRequest(appointmentRequest.getPersonId(),
                    appointmentRequest.getRequestId(),
                    appointmentRequest.isNew());
    for (Integer i : appointmentRequest.getPreferredDocs()) {
      for (String s : appointmentRequest.getPreferredDays()) {
        appointment.setDoctorId(i);
        ZonedDateTime dateTime = ZonedDateTime.parse(s);
        for (int j = 8; j < 17; j++) {
          ZonedDateTime currDateTime = dateTime.with(LocalTime.of(j, 0));
          if (isAppointmentValid(appointment, currDateTime)) {
            appointment.setAppointmentTime(currDateTime.toString());
            Appointment convertedAppointment = new Appointment(appointment);
            addAppointmentNoValidation(convertedAppointment);
            System.out.printf("Added New %s", convertedAppointment);
            return appointment;
          }
        }

      }
    }
    return null;
  }

  public void addAppointmentNoValidation(Appointment appointment) {
    ZonedDateTime parsedApptTime = ZonedDateTime.parse(appointment.getAppointmentTime());
    appointmentByCompositeKey.put(
            new DoctorIdAppointmentTimeCompositeKey(appointment.getDoctorId(), parsedApptTime),
            appointment);
    Set<LocalDate> dateSet = patientAppointmentDatesByPatientId.getOrDefault(appointment.getPersonId(), new HashSet<>());
    dateSet.add(parsedApptTime.toLocalDate());
    patientAppointmentDatesByPatientId.put(appointment.getPersonId(), dateSet);
  }

}
