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
  * Prints rule info (name, description)
  * @return rule's name and description
  */
  public String toString() {
    return name+": "+description;
  }

  /**
  * Prints rule type (action, inaction)
  * @param r rule to check
  * @return rule type
  */
  public String getType(Rule r) {
    if(r.getName().equals("BOMB CARD") ||
      r.getName().equals("SUDDEN DEATH CARD") ||
      r.getName().equals("INK CARD") ||
      r.getName().equals("EQUALITY CARD") ||
      r.getName().equals("RAIN CARD") ||
      r.getName().equals("CLONE CARD") ||
      r.getName().equals("JUSTICE CARD") ||
      r.getName().equals("THUNDER CARD") ||
      r.getName().equals("MAGNET CARD")){
      return "ACTION";
    }else{
      return "NOT ACTION";
    }
  }

  //GET METHODS//
  public String getName() {
    return name;
  }

}
