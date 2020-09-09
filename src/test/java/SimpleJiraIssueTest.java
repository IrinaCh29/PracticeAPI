import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class SimpleJiraIssueTest {

  @Test
  public void crudNewIssue() {
    //POST - create new issue and status is 201

    JSONObject issue = new JSONObject();
    JSONObject fields = new JSONObject();
    JSONObject reporter = new JSONObject();
    JSONObject issueType = new JSONObject();
    JSONObject project = new JSONObject();

    issueType.put("id", "10105");
    issueType.put("name", "test");
    project.put("id", "10508");
    reporter.put("name", "IrinaChub");
    fields.put("issuetype", issueType);
    fields.put("summary", "Test summary API ticket");
    fields.put("reporter", reporter);
    fields.put("project", project);
    issue.put("fields", fields);

        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            body(issue.toJSONString()).
            when().
            post("https://jira.hillel.it/rest/api/2/issue").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            extract().response().print();
  }
}