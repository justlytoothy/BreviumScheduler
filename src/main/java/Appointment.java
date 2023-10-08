public class Appointment {
  private int doctorId;

  private int personId;

  private String appointmentTime;

  private boolean isNewPatientAppointment;

  public Appointment(int doctorId, int personId, String appointmentTime, boolean isNewPatientAppointment) {
    this.doctorId = doctorId;
    this.personId = personId;
    this.appointmentTime = appointmentTime;
    this.isNewPatientAppointment = isNewPatientAppointment;
  }

  public Appointment(ScheduleAppointmentRequest appointment) {
    this.doctorId = appointment.getDoctorId();
    this.personId = appointment.getPersonId();
    this.appointmentTime = appointment.getAppointmentTime();
    this.isNewPatientAppointment = appointment.isNewPatientAppointment();
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

  @Override
  public String toString() {
    return String.format("Appointment:\n\tPerson:%d\n\tDoctor:%d\n\tTime:%s\n\tIs New:%b\n",
            personId, doctorId, appointmentTime, isNewPatientAppointment);
  }
}
