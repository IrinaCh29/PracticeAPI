import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.JiraAPISteps;
import utils.JiraJSONObjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

//JiraAPITests
public class CRUDJiraIssueTest {
  private String ticketId;
  String username = "IrinaChub";
  String password = "IrinaChub";
  String issue = JiraJSONObjects.newIssueJSON();

  @Test
  public void crudNewIssue() {
    Response postIssueResponse = JiraAPISteps.postIssue(issue);
    ticketId = postIssueResponse.path("id");
    assertTrue(postIssueResponse.path("key").toString().contains("WEBINAR-"));
//    System.out.println(ticketId);

    Response getIssueResponse = JiraAPISteps.getIssue(ticketId);
    assertEquals(getIssueResponse.path("fields.summary"), "Test summary API ticket");
    assertEquals(getIssueResponse.path("fields.creator.name"), username);


    Response deleteIssueResponse =JiraAPISteps.deleteIssue(ticketId);

    Response getDeletedIssueResponse = JiraAPISteps.getDeletedIssue(ticketId);
  }
}