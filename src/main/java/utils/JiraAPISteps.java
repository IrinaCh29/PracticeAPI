package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class JiraAPISteps {

  public static Response postIssue() {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            body(JiraJSONObjects.newIssueJSON()).
            when().
            post(APIPathes.issue).
            then().
            statusCode(201).
            contentType(ContentType.JSON).
            extract().response();
    return response;
  }

  public static Response getIssue(String ticketId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            get(APIPathes.issue + ticketId).
            then().
            statusCode(200).
            contentType(ContentType.JSON).
            extract().response();
    return response;
  }

  public static Response deleteIssue(String ticketId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            delete(APIPathes.issue + ticketId).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response getDeletedIssue(String ticketId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            get(APIPathes.issue + ticketId).
            then().
            statusCode(404).
            extract().response();
    return response;
  }

  public static Response postCommentInIssue(String ticketId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            body(JiraJSONObjects.bodyCommentJSON()).
            when().
            post(String.format(APIPathes.comment, ticketId)).
            then().
            statusCode(201).
            contentType(ContentType.JSON).
            extract().response();
    return response;
  }

  public static Response getCommentInIssue(String ticketId, String commentId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            get(String.format(APIPathes.commentForGetting, ticketId , commentId)).
            then().
            extract().response();
    return response;
  }

  public static Response deleteAddedCommentInIssue(String ticketId, String commentId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            delete(String.format(APIPathes.commentForDeleting, ticketId, commentId)).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response getIssueNotFoundComment(String ticketId, String commentId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            get(String.format(APIPathes.commentForDeleting, ticketId, commentId)).
            then().
            contentType(ContentType.JSON).
            statusCode(404).
            extract().response();
    return response;
  }
}