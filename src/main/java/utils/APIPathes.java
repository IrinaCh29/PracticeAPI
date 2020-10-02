package utils;

public interface APIPathes {
  String baseURL = YamlReader.getEnvironment(System.getenv("dev"));
//  String baseURL = "https://jira.hillel.it";
  String issue = baseURL + "/rest/api/2/issue/";
  String comment = issue + "%s/comment/";
  String commentForGetting = issue + "%s/comment/%s/";
  String commentForDeleting = issue + "%s/comment/%s/";
}