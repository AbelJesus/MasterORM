package MasterORM;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by saulo on 13/06/15.
 */
public class MasterTableBuilder{
   public static String getCreateStatement(Field[] campos, String TableName){
      final String ID = "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
      StringBuilder SQL = new StringBuilder();
      SQL.append("CREATE TABLE ").append(TableName.toUpperCase()).append("(").append(ID);
      for(int i = 0; i < campos.length; i++){
         SQL.append(getValidFieldName(campos[i].getName()).concat(" TEXT"));
         SQL.append(",");
      }
      SQL.delete(SQL.length() - 1, SQL.length());// tira última virgula
      SQL.append(");");// finaliza string
      return SQL.toString();
   }

   public static String getValidFieldName(String field){
      String r = field.replace("_", "").toUpperCase();
      return r;
   }

   public static String getSelectStatement(Field[] campos, String TableName){

      StringBuilder SQL = new StringBuilder("SELECT ");
      for(int i = 0; i < campos.length; i++){
         SQL.append(getCoalescedValidFieldName(campos[i].getName()));
         SQL.append(",");
      }

      SQL.delete(SQL.length() - 1, SQL.length());// tira última virgula
      SQL.append(" FROM ").append(TableName.toUpperCase()).append(";");
      return SQL.toString();
   }

   public static String getCoalescedValidFieldName(String FieldName){
      return "Coalesce(".concat(getValidFieldName(FieldName)).concat(", '') as ").concat(getValidFieldName(FieldName)).concat(" ");
   }

   public static String getInsertStatement(Field[] campos, String TableName){
      StringBuilder SQL = new StringBuilder("INSERT INTO ".concat(TableName.toUpperCase()).concat("("));
      StringBuilder params = new StringBuilder(") VALUES (");

      for(int i = 0; i < campos.length; i++){
         SQL.append(getValidFieldName(campos[i].getName().toString()));
         SQL.append(",");

         params.append("?");
         params.append(" ,");
      }

      SQL.delete(SQL.length()-1, SQL.length());
      params.delete(params.length()-1, params.length());
      return SQL.toString().concat(params.toString()).concat(");");
   }
}
