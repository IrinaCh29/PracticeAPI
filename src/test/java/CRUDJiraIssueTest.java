import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.Credentials;
import utils.JiraAPISteps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Feature("CRUDJiraIssueTest")
public class CRUDJiraIssueTest {
  private String ticketId;

  @Test
  public void crudNewIssue() {
    Response postIssueResponse = JiraAPISteps.postIssue();
    ticketId = postIssueResponse.path("id");
    assertEquals(postIssueResponse.statusCode(), 201);
    assertTrue(postIssueResponse.path("key").toString().contains("WEBINAR-"));

    Response getIssueResponse = JiraAPISteps.getIssue(ticketId);
    assertEquals(getIssueResponse.statusCode(), 200);
    assertEquals(getIssueResponse.path("fields.summary"), "Test summary API ticket");
    assertEquals(getIssueResponse.path("fields.creator.name"), Credentials.username);

    Response deleteIssueResponse =JiraAPISteps.deleteIssue(ticketId);
    assertEquals(deleteIssueResponse.statusCode(),204);

    Response getDeletedIssueResponse = JiraAPISteps.getDeletedIssue(ticketId);
    assertEquals(getDeletedIssueResponse.statusCode(), 404);
  }
}