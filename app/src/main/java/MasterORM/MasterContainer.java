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
      rec = new MasterRecord(cont, this.getClass());
   }

   public String getTableName(){
      String[] arr = getClass().getName().split("\\.", -1);
      return arr[arr.length-1];
   }

   public void save(){
      Field[] Campos = this.getClass().getDeclaredFields();
      String[] Dados = new String[Campos.length];
      String query = MasterTableBuilder.getInsertStatement(Campos, getTableName());
      //MasterContainer master = this;
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
         rec.getInstance(cont).getWritableDatabase().execSQL(query, Dados);
      }finally{
      }
   }

   public <E extends MasterContainer> List<E> listAll(Class<E> classe){
      //cria lista
      //E elemento = classe.newInstance();
      ArrayList<E> lista = new ArrayList<E>();
      //busca dados no sqlite
      Cursor cur = rec.getInstance(cont).getReadableDatabase().rawQuery(MasterTableBuilder.getSelectStatement(this.getClass().getDeclaredFields(), getTableName()), null);
      if(cur.getCount() > 0 ){
         int count = 0;
         E rowItem;
         while(cur.moveToNext()){
            try{

               rowItem = classe.newInstance();
               Field[] campos = rowItem.getClass().getDeclaredFields();
               for(int i = 0; i < cur.getColumnCount(); i++){
                  //get value from cursor
                  String value = cur.getString(i);
                  //seta valor ao campo
                  campos[i].set(rowItem, value);
                  lista.add(rowItem);
               }
            }catch(Exception e){
               Log.d("MASTER", e.toString());
            }
            finally{
            }
            count++;
         }
      }
      cur.close();

      return lista;
   }
}
