package br.sha.ormmaster;

import android.content.Context;

import MasterORM.MasterField;
import MasterORM.MasterRecord;

/**
 * Created by saulo on 13/06/15.
 */
public class Clientes extends MasterRecord{

   public String Nome, Endereco;
   public int cidade, estado;

   public Clientes(Context cont){
      super(cont);
   }
}
