import org.json.simple.JSONObject;

public class Main {
  public static void main(String[] args) {
    JSONObject login = new JSONObject();
    login.put("username", "webinar5");
    login.put("password", "webinar5");

  //  System.out.println(login.toJSONString());

    JSONObject issue = new JSONObject();
    JSONObject fields = new JSONObject();
    fields.put("summary", "Test summary API ticket");

    JSONObject issueType = new JSONObject();
    issueType.put("id", "10105");
    issueType.put("name", "test");

    JSONObject project = new JSONObject();
    project.put("id", "10508");

    JSONObject reporter = new JSONObject();
    reporter.put("name", "webinar5");

    fields.put("issueType", issueType);
    fields.put("reporter", reporter);
    fields.put("project", project);
    issue.put("fields", fields);

    System.out.println(issue.toString());
  }
}