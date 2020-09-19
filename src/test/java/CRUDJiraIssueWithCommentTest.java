import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.Credentials;
import utils.JiraAPISteps;
import utils.JiraJSONObjects;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRUDJiraIssueWithCommentTest {
  private String ticketId;
  private String commentId;

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

    Response postCommentInIssueResponse = JiraAPISteps.postCommentInIssue(ticketId);
    assertEquals(postCommentInIssueResponse.statusCode(), 201);
    commentId = postCommentInIssueResponse.path("id");

    Response getCommentResponseInIssue = JiraAPISteps.getCommentInIssue(ticketId, commentId);
    assertEquals(getCommentResponseInIssue.statusCode(), 200);
    assertEquals(getCommentResponseInIssue.path("body"), "Test API added comment");
    assertEquals(getCommentResponseInIssue.path("author.name"), Credentials.username);

    Response deleteAddedCommentInIssueResponse = JiraAPISteps.deleteAddedCommentInIssue(ticketId, commentId);
    assertEquals(deleteAddedCommentInIssueResponse.statusCode(), 204);

    Response getIssueNotFoundCommentResponse = JiraAPISteps.getIssueNotFoundComment(ticketId, commentId);
    assertEquals(getIssueNotFoundCommentResponse.statusCode(), 404);

    Response deleteIssueResponse =JiraAPISteps.deleteIssue(ticketId);
    assertEquals(deleteIssueResponse.statusCode(),204);

    Response getDeletedIssueResponse = JiraAPISteps.getDeletedIssue(ticketId);
    assertEquals(getDeletedIssueResponse.statusCode(), 404);
  }
}