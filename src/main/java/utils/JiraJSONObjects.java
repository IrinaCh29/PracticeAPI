package utils;

import org.json.simple.JSONObject;


public class JiraJSONObjects {

  public static String newIssueJSON(){

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

    return issue.toJSONString();
  }

//  JSONObject bodyComment = new JSONObject();
//    bodyComment.put("body", "Test API added comment");


  public static String bodyCommentJSON(){
    JSONObject bodyCommentJSON = new JSONObject();
    bodyCommentJSON.put("body", "Test API added comment");

    return bodyCommentJSON.toJSONString();
  }
}