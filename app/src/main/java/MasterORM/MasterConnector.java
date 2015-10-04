package MasterORM;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by saulo on 13/06/15.
 */
public class MasterConnector extends SQLiteOpenHelper{

   private final String tableName;
   private final String createSintax;
   private Context cont;
/*
   private SQLiteDatabase MasterDB;
   private MasterWrapper wrapper;

   protected static SQLiteDatabase DB;
*/

   private String CreateSintax, TableName;
   private static MasterConnector db;
   //todo VERIFICAR CREATE, não está entrando no OnCreate
   public static synchronized MasterConnector getInstance(Context cont,String TableName, String CreateSintax){
      if(db == null){
         db = new MasterConnector(cont.getApplicationContext(), TableName, CreateSintax);
      }
      return db;
   }



   protected MasterConnector(Context cont, String TableName, String CreateSintax){
      super(cont, MasterHelper.getMasterWrapper(cont).getDBName(), null, MasterHelper.getMasterWrapper(cont).getDBVersion());
      tableName = TableName;
      createSintax = CreateSintax;
   }

   @Override
   public void onCreate(SQLiteDatabase db){
      db.execSQL(createSintax);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
      //TODO criar método para exportação de dados
      db.execSQL("DROP TABLE if exists ".concat(tableName));
      Log.d("MASTER", "Tabela removida");
   }

}
