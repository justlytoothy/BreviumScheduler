public class ScheduleAppointmentRequest {
  private int doctorId;

  private int personId;

  private String appointmentTime;

  private boolean isNewPatientAppointment;

  private int requestId;
  public ScheduleAppointmentRequest(int personId, int requestId, boolean isNewPatientAppointment) {
    this.personId = personId;
    this.requestId = requestId;
    this.isNewPatientAppointment = isNewPatientAppointment;
  }

  public int getDoctorId() {
    return doctorId;
  }

  public int getPersonId() {
    return personId;
  }

  public String getAppointmentTime() {
    return appointmentTime;
  }

  public boolean isNewPatientAppointment() {
    return isNewPatientAppointment;
  }

  public void setDoctorId(int doctorId) {
    this.doctorId = doctorId;
  }

  public void setPersonId(int personId) {
    this.personId = personId;
  }

  public void setAppointmentTime(String appointmentTime) {
    this.appointmentTime = appointmentTime;
  }

  public void setNewPatientAppointment(boolean newPatientAppointment) {
    isNewPatientAppointment = newPatientAppointment;
  }

  public int getRequestId() {
    return requestId;
  }

  public void setRequestId(int requestId) {
    this.requestId = requestId;
  }
}
