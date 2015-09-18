package br.sha.ormmaster;

import android.content.Context;

import MasterORM.MasterContainer;

/**
 * Created by saulo on 13/06/15.
 */
public class Clientes extends MasterContainer{

   public String Nome, Endereco;
   public int cidade, estado;

   public Clientes(){
   }

   public Clientes(Context cont){
      super(cont);
   }
}
