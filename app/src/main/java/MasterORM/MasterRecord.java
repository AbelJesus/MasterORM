package MasterORM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by saulo on 17/09/15.
 */
public class MasterRecord extends MasterConnector{

   private Class<? extends MasterContainer> classe;
   public SQLiteDatabase DB;

   public MasterRecord(Context cont, Class<? extends MasterContainer> classe, String TableName, String CreateSintax){
      super(cont, TableName, CreateSintax);
      this.classe = classe;
      DB = getWritableDatabase();
   }
}
