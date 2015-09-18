package MasterORM;

import android.content.Context;

/**
 * Created by saulo on 17/09/15.
 */
public class MasterRecord extends MasterConnector{

   private Class<? extends MasterContainer> classe;

   public MasterRecord(Context cont, Class<? extends MasterContainer> classe){
      super(cont);
      this.classe = classe;
   }

   @Override
   protected String getCreateSintax(){
      return MasterTableBuilder.getCreateStatement(classe.getClass().getDeclaredFields(), getTableName());
   }

   @Override
   public String getTableName(){
      String[] arr = classe.getName().split("\\.", -1);
      return arr[arr.length-1];
   }
}
