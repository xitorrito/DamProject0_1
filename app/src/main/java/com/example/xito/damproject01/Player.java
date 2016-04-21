public class Player{
    public Player{
        
    }
    
    public void getPlayerStates() {
        Cursor cursor = bd.query("player",nivel, null,null,null,null,null);
        //Vamos a la primera fila.
        cursor.moveToFirst();
        //Obtenemos el dato de la primera (y única) columna.
        int coins = cursor.getInt(0);
        
        Cursor cursor = bd.query("player",exp, null,null,null,null,null);
        //Vamos a la primera fila.
        cursor.moveToFirst();
        //Obtenemos el dato de la primera (y única) columna.
        int level = cursor.getInt(0);
        
        
    }
    
    public int getExpForNextLevel(int level){
        int expRequired = 40*1.5^(level-1);
    }
  
  
  
}
