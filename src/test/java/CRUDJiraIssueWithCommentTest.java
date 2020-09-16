import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import utils.JiraAPISteps;
import utils.JiraJSONObjects;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class CRUDJiraIssueWithCommentTest {
  private String ticketId;
  private String commentId;

  String issue = JiraJSONObjects.newIssueJSON();

  @Test
  public void crudNewIssue() {
    //POST - create new issue and status is 201
    Response postIssueResponse = JiraAPISteps.postIssue(issue);
    ticketId = postIssueResponse.path("id");
    assertTrue(postIssueResponse.path("key").toString().contains("WEBINAR-"));
//    System.out.println(ticketId);

    //Get created issue and status is 200
    Response getIssueResponse = JiraAPISteps.getIssue(ticketId);
    assertEquals(getIssueResponse.path("fields.summary"), "Test summary API ticket");
    assertEquals(getIssueResponse.path("fields.creator.name"), JiraAPISteps.username);

    //Add comment and status is 201
    Response postCommentInIssueResponse = JiraAPISteps.postCommentInIssue(ticketId, commentId);
    //postCommentInIssueResponse.print();
    commentId = postCommentInIssueResponse.path("id");


    //Get added comment and status is 200
    Response getCommentResponseInIssue = JiraAPISteps.getCommentInIssue(ticketId, commentId);
//    getCommentResponseInIssue.print();
    assertEquals(getCommentResponseInIssue.statusCode(), 200);
    assertEquals(getCommentResponseInIssue.path("body"), "Test API added comment");
    assertEquals(getCommentResponseInIssue.path("author.name"), JiraAPISteps.username);

    //Delete added comment and status is 204
    Response deleteAddedCommentInIssueResponse = JiraAPISteps.deleteAddedCommentInIssue(ticketId, commentId);
//    deleteAddedCommentInIssueResponse.print();

    //Get issue with with Not Found Comment and status is 404
    Response getIssueNotFoundCommentResponse = JiraAPISteps.getIssueNotFoundComment(ticketId, commentId);
//    getIssueNotFoundCommentResponse.print();

    //Delete created issue and status is 204
    Response deleteIssueResponse =JiraAPISteps.deleteIssue(ticketId);

    //Get deleted issue and status is 404
    Response getDeletedIssueResponse = JiraAPISteps.getDeletedIssue(ticketId);
  }
}