
public class Levels{
  
  
  public Levels(){
    
  }
  
  // maximum experience points
  public int max_exp;

  // maximum level
  public int max_level;

  // get a level minimum exp
  public int getExperience (int level)
  {
      return Math.ceil((double)max_exp / Math.pow(2, max_level-level));
  }
  
  public int getExpForNextLevel(int level){
      return 
  }

  // get current level
  public int level getLevel (int exp)
  {
      return Math.floor((double)max_level - (Math.log2(max_exp) - Math.log2(exp)));
  }
  
  
  
}
