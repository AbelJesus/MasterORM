package MasterORM;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.lang.reflect.Field;
import java.nio.DoubleBuffer;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by saulo on 13/06/15.
 */
public class MasterRecord extends MasterConnector{

   //private Field[] camposTabela;
   private String tableName;
   private Context cont;

   public MasterRecord(Context cont){
      super(cont, MasterHelper.getMasterWrapper(cont));
      this.cont = cont;
   }

   @Override
   protected String getCreateSintax(){
      return MasterTableBuilder.getCreateStatement(this.getClass().getDeclaredFields(), getTableName());
   }

   @Override
   public String getTableName(){
      String[] arr = getClass().getName().split("\\.", -1);
      return arr[arr.length-1];
   }


   public void save(){
      Field[] Campos = this.getClass().getDeclaredFields();
      String[] Dados = new String[Campos.length];
      String query = MasterTableBuilder.getInsertStatement(Campos, getTableName());
      MasterRecord master = this;
      for(int i = 0; i < Campos.length; i++){
         try{
            //seta field como acessível
            Campos[i].setAccessible(true);
            //adiciona string com o valor da variável
            String valor = (String) Campos[i].get(this).toString();
            //agrega valor(ao camarote) ao array
            Dados[i] = valor;
         }catch(IllegalAccessException e){
         }
      }

      try{
         DB.execSQL(query, Dados);
      }finally{
         DB.close();
      }
   }

   public <T extends MasterRecord> List<T> listAll(Class<T> klazz){
      //cria lista
      ArrayList<T> lista = new ArrayList<>();
      //busca dados no sqlite
      Cursor cur = DB.rawQuery(MasterTableBuilder.getSelectStatement(this.getClass().getDeclaredFields(), getTableName()), null);
      if(cur.getCount() > 0 ){
         int count = 0;
         while(cur.moveToNext()){
            try{
               //dá erro aqui, diz que não existe esse método
               lista.add(klazz.getConstructor().newInstance());
               //pega campos da nova instancia
               Field[] F = lista.get(count).getClass().getDeclaredFields();
               for(int i = 0; i < cur.getColumnCount(); i++){
                  //get value from cursor
                  String value = "";
                  value = cur.getString(i);

                  //caso seja string
                  if(F[i].equals(String.class)){
                     F[i].set(lista.get(count), value);
                  //caso seja int.. and so on...
                  }else if(F[i].equals(int.class)){
                     if(value.equals("")){
                        F[i].setInt(lista.get(count), 0);
                     }else{
                        F[i].set(lista.get(count), Integer.parseInt((value)));
                     }
                  }else if(F[i].equals(float.class)){
                     F[i].setFloat(this, Float.parseFloat(value));
                  }
                  //F[i].set(lista.get(count), );
               }


            }catch(Exception e){
               Log.d("MASTER", e.toString());
            }finally{
            }
            count++;
         }
      }
      cur.close();
      DB.close();

      return lista;
   }

  /* public <T extends MasterRecord> List<T> listAll(){
      ArrayList<T> list = new ArrayList<>();
      Field[] campos = this.getClass().getFields();
      Cursor cur = DB.rawQuery(MasterTableBuilder.getSelectStatement(this.getClass().getDeclaredFields(), getTableName()), null);

      if(cur.getCount() > 0) {
         while(cur.moveToNext()){

            for(int i = 0; i < cur.getColumnCount(); i++){
               String value = "";
               value = cur.getString(i);
               try{
                  //testa tipo de campo
                  Class<?> C = campos[i].getType();
                  if(C.equals(String.class)){
                     campos[i].set(this, value);
                  }else if(C.equals(int.class)){
                     if(value.equals("")){
                        campos[i].setInt(this, 0);
                     }else{
                        campos[i].set(this, Integer.parseInt((value)));
                     }
                  }else if(C.equals(float.class)){
                     campos[i].setFloat(this, Float.parseFloat(value));
                  }
               }catch(Exception e){
                  Log.d("MASTER", e.toString());
               }
            }
            //cur.moveToNext();
            list.add((T) (MasterRecord) this);
         }
      }
      cur.close();
      DB.close();

      return list;
   }*/
}
