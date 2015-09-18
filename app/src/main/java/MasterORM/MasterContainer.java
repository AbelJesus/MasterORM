package MasterORM;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saulo on 13/06/15.
 */
public class MasterContainer<T>{

   private Context cont;
   MasterRecord rec;

   public MasterContainer(){
   }

   public MasterContainer(Context cont){
      this.cont = cont;
      rec = new MasterRecord(cont, this.getClass(),getTableName(), getCreateSintax());
   }

   public String getTableName(){
      String[] arr = getClass().getName().split("\\.", -1);
      return arr[arr.length-1];
   }

   protected String getCreateSintax(){
      return MasterTableBuilder.getCreateStatement(getClass().getDeclaredFields(), getTableName());
   }

   public void save(){
      Field[] Campos = this.getClass().getDeclaredFields();
      String[] Dados = new String[Campos.length];
      String query = MasterTableBuilder.getInsertStatement(Campos, getTableName());
      for(int i = 0; i < Campos.length; i++){
         try{
            //seta field como acessível
            Campos[i].setAccessible(true);
            if(Campos[i].get(this) != null){
               //agrega valor(ao camarote) ao array
               Dados[i] = Campos[i].get(this).toString();
            }
         }catch(IllegalAccessException e){
         }catch(Exception ex){
            Log.d("MASTER", ex.toString());
         }
      }

      try{
         rec.DB.execSQL(query, Dados);
      }finally{
      }
   }

   public <E extends MasterContainer> List<E> listAll(Class<E> classe){
      //cria lista
      ArrayList<E> lista = new ArrayList<E>();
      //busca dados no sqlite
      Cursor cur = rec.DB.rawQuery(MasterTableBuilder.getSelectStatement(this.getClass().getDeclaredFields(), getTableName()), null);
      if(cur.getCount() > 0 ){
         E rowItem;
         while(cur.moveToNext()){
            try{
               rowItem = classe.newInstance();
               Field[] campos = rowItem.getClass().getDeclaredFields();
               for(int i = 0; i < cur.getColumnCount(); i++){
                  //get value from cursor
                  String value = cur.getString(i);
                  //seta valor ao campo
                  //TODO pesquisar como melhorar isso
                  Class<?> C = campos[i].getType();
                  if(isTextType(C)){
                     campos[i].set(rowItem, value);
                  }else if(isIntegerType(C)){
                     campos[i].setInt(rowItem, Integer.parseInt(value));
                  }else if(isFloatType(C)){
                     campos[i].setFloat(rowItem, Float.parseFloat(value));
                  }else if(isDoubleType(C)){
                     campos[i].setDouble(rowItem, Double.parseDouble(value));
                  }else if(isBooleanType(C)){
                     campos[i].set(rowItem, value);
                  }else if(isLongType(C)){
                     campos[i].setLong(rowItem, Long.parseLong(value));
                  }
               }
               //add row to list
               lista.add(rowItem);
            }catch(Exception e){
               Log.d("MASTER", e.toString());
            }
            finally{
            }
         }
      }
      cur.close();

      return lista;
   }

   private boolean isTextType(Class<?> tipoCampo){
      if(tipoCampo.equals(String.class) || tipoCampo.equals(char.class)){
         return true;
      }else return false;
   }


   private boolean isBooleanType(Class<?> tipoCampo){
      if(tipoCampo.equals(boolean.class) || tipoCampo.equals(Boolean.class)){
         return true;
      }else return false;
   }

   private boolean isDoubleType(Class<?> tipoCampo){
      if(tipoCampo.equals(double.class) || tipoCampo.equals(Double.class)){
         return true;
      }else return false;
   }

   private boolean isFloatType(Class<?> tipoCampo){
      if(tipoCampo.equals(float.class) || tipoCampo.equals(Float.class)){
         return true;
      }else return false;
   }

   private boolean isIntegerType(Class<?> tipoCampo){
      if(tipoCampo.equals(int.class) || tipoCampo.equals(Integer.class)){
         return true;
      }else return false;
   }

   private boolean isLongType(Class<?> tipoCampo){
      if(tipoCampo.equals(long.class) || tipoCampo.equals(Long.class)){
         return true;
      }else return false;
   }

}
