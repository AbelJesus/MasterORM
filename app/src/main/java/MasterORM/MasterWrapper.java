package MasterORM;

/**
 * Created by saulo on 13/06/15.
 */
public class MasterWrapper{

   private String DBName;
   private Integer DBVersion;


   public MasterWrapper(){
   }

   public MasterWrapper(String DBName, Integer DBVersion){
      this.DBName = DBName;
      this.DBVersion = DBVersion;
   }

   public String getDBName(){
      return DBName;
   }

   public void setDBName(String DBName){
      this.DBName = DBName;
   }

   public Integer getDBVersion(){
      return DBVersion;
   }

   public void setDBVersion(Integer DBVersion){
      this.DBVersion = DBVersion;
   }
}
