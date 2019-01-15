public class Rule {
  private String name;
  private String description;

  public Rule(String name){
    this.name = name;
  }

  public Rule(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String toString() {
    return name+": "+description;
  }

}
