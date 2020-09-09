import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class CRUDJiraIssueTest {
  private String ticketId;

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

    Response postIssueResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            body(issue.toJSONString()).
            when().
            post("https://jira.hillel.it/rest/api/2/issue").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            extract().response();
    ticketId = postIssueResponse.path("id");
    System.out.println(ticketId);

    //Get created issue and status is 200
    Response getIssueResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    getIssueResponse.print();
    assertEquals(getIssueResponse.path("fields.summary"), "Test summary API ticket");
    assertEquals(getIssueResponse.path("fields.creator.name"), "IrinaChub");

    //Delete created issue and status is 204
    Response deleteIssueResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            when().
            delete("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(204).
            extract().response();
    deleteIssueResponse.print();

    //Get deleted issue and status is 404
    Response getDeletedIssueResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(404).
            extract().response();
    getDeletedIssueResponse.print();
  }
}