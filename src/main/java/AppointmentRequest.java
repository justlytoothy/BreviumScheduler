import java.util.List;

public class AppointmentRequest {
  private final int requestId;
  private final int personId;
  private final List<String> preferredDays;
  private final List<Integer> preferredDocs;
  private final boolean isNew;

  public AppointmentRequest(int requestId, int personId, List<String> preferredDays, List<Integer> preferredDocs, boolean isNew) {
    this.requestId = requestId;
    this.personId = personId;
    this.preferredDays = preferredDays;
    this.preferredDocs = preferredDocs;
    this.isNew = isNew;
  }

  public int getRequestId() {
    return requestId;
  }

  public int getPersonId() {
    return personId;
  }

  public List<String> getPreferredDays() {
    return preferredDays;
  }

  public List<Integer> getPreferredDocs() {
    return preferredDocs;
  }

  public boolean isNew() {
    return isNew;
  }
}
