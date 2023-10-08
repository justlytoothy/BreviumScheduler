import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public class DoctorIdAppointmentTimeCompositeKey {
  private final Integer doctorId;
  private final ZonedDateTime appointmentTime;
  public DoctorIdAppointmentTimeCompositeKey(final Integer doctorId, final ZonedDateTime appointmentTime) {
    this.doctorId = doctorId;
    this.appointmentTime = appointmentTime;
  }

  @Override
  public boolean equals(Object obj) {
    return Objects.equals(this, obj);
  }
}
