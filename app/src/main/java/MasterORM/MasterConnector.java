package MasterORM;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by saulo on 13/06/15.
 */
public abstract class MasterConnector extends SQLiteOpenHelper{

   private Context cont;
   private SQLiteDatabase MasterDB;
   private MasterWrapper wrapper;
   protected static SQLiteDatabase DB;

   public MasterConnector(Context cont, MasterWrapper wrapper){
      super(cont, wrapper.getDBName(), null, wrapper.getDBVersion());
      this.cont = cont;
      Log.d("MASTER", "Constructor");
      DB = getWritableDatabase();
   }

   @Override
   public void onCreate(SQLiteDatabase db){
      try{
         db.execSQL(getCreateSintax());
         Log.d("MASTER", "create");
      }catch(SQLException e){
         Log.d("MASTER", e.toString());
      }
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
      db.execSQL("DROP TABLE if exists ".concat(getTableName()));
      Log.d("MASTER", "Tabela removida");
   }

   protected abstract String getCreateSintax();

   public abstract String getTableName();
}
