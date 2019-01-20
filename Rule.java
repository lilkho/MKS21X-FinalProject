public class Rule {
  private String name;
  private String description;

  /**
  * Constructor for rule
  * @param name name of rule
  * @param description description of rule
  */
  public Rule(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
  * Returns rule info (name, description)
  * @return rule's name and description
  */
  public String toString() {
    return name+": "+description;
  }

  //GET METHODS//
  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

}
