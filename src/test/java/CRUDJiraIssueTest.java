import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class CRUDJiraIssueTest {
  private String ticketId;

  @Test
  public void crudNewIssue() {
    //POST - create new issue
    Response postResponse =
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
    ticketId = postResponse.path("id");
    System.out.println(ticketId);

    //Get created issue
    Response getResponse =
        given().
            auth().preemptive().basic("IrinaChub", "IrinaChub").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    getResponse.print();
    assertEquals(getResponse.path("fields.summary"), "Test summary API ticket");
    assertEquals(getResponse.path("fields.creator.name"), "IrinaChub");


//    //Delete created issue and status is 204
//    Response deleteIssueResponse =
//        given().
//            auth().preemptive().basic("IrinaChub", "IrinaChub").
//            contentType(ContentType.JSON).
//            when().
//            delete("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
//            then().
//            statusCode(204).
//            extract().response();
//    deleteIssueResponse.print();
//
//    //Get deleted issue and status is 404
//    Response getDeletedIssueResponse =
//        given().
//            auth().preemptive().basic("IrinaChub", "IrinaChub").
//            contentType(ContentType.JSON).
//            when().
//            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
//            then().
//            statusCode(404).
//            extract().response();
//    getDeletedIssueResponse.print();
  }
}