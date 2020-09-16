package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class JiraAPISteps {
  public static String username = "IrinaChub";
  public static String password = "IrinaChub";

  public static Response postIssue(String issue){
    Response response =
    given().
        auth().preemptive().basic(username, password).
        contentType(ContentType.JSON).
        body(issue).
        when().
        post("https://jira.hillel.it/rest/api/2/issue").
        then().
        contentType(ContentType.JSON).
        statusCode(201).
        extract().response();
    return response;
  }

  public static Response getIssue(String ticketId){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    return response;
  }

  public static Response deleteIssue(String ticketId){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            delete("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response getDeletedIssue(String ticketId){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(404).
            extract().response();
    return response;
  }

  public static Response postCommentInIssue(String ticketId, String commentId){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            body(JiraJSONObjects.bodyCommentJSON()).
            when().
            post("https://jira.hillel.it/rest/api/2/issue/" + ticketId + "/comment").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            extract().response();
    return response;
  }

  public static Response getCommentInIssue(String ticketId, String commentId){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId + "/comment/" + commentId).
            then().
            extract().response();
    return response;
  }

  public static Response deleteAddedCommentInIssue(String ticketId, String commentId){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            delete("https://jira.hillel.it/rest/api/2/issue/" + ticketId + "/comment/" + commentId).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response getIssueNotFoundComment(String ticketId, String commentId){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId + "/comment/" + commentId).
            then().
            contentType(ContentType.JSON).
            statusCode(404).
            extract().response();
    return response;
  }
}