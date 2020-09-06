import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class CRUDJiraIssueTest {
  private String ticketId;
  private String commentId;

  @Test
  public void crudNewIssue() {
    //POST - create new issue and status is 201
    Response postIssueResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            body("{\n" +
                "   \"fields\":{\n" +
                "      \"summary\":\"Test summary API ticket\",\n" +
                "      \"issuetype\":{\n" +
                "         \"id\":\"10105\",\n" +
                "         \"name\":\"test\"\n" +
                "      },\n" +
                "      \"project\":{\n" +
                "         \"id\":\"10508\"\n" +
                "      },\n" +
                "   \"reporter\": {\n" +
                "      \"name\": \"IrinaChub\"\n" +
                "    }\n" +
                "   }\n" +
                "}").
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

    //Add comment and status is 201
    Response postCommentInIssueResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            body("{\n" +
                "    \"body\":\"Test API added comment\"}").
            when().
            post("https://jira.hillel.it/rest/api/2/issue/" + ticketId + "/comment").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            extract().response();
    postCommentInIssueResponse.print();
    commentId = postCommentInIssueResponse.path("id");
    System.out.println(commentId);

    //Get added comment and status is 200
    Response getCommentResponseInIssue =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId + "/comment/" + commentId).
            then().
            extract().response();
    getCommentResponseInIssue.print();
    assertEquals(getCommentResponseInIssue.statusCode(), 200);
    assertEquals(getCommentResponseInIssue.path("body"), "Test API added comment");
    assertEquals(getCommentResponseInIssue.path("author.name"), "IrinaChub");

    //Delete added comment and status is 204
    Response deleteAddedCommentInIssueResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            when().
            delete("https://jira.hillel.it/rest/api/2/issue/" + ticketId + "/comment/" + commentId).
            then().
            statusCode(204).
            extract().response();
    deleteAddedCommentInIssueResponse.print();

    //Get issue with with Not Found Comment and status is 404
    Response getIssueNotFoundCommentResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId + "/comment/" + commentId).
            then().
            contentType(ContentType.JSON).
            statusCode(404).
            extract().response();
    getIssueNotFoundCommentResponse.print();

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