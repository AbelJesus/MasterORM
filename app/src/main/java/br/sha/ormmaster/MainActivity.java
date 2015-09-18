package br.sha.ormmaster;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import MasterORM.MasterConnector;
import MasterORM.MasterHelper;
import MasterORM.MasterWrapper;


public class MainActivity extends AppCompatActivity{

   EditText memo;

   @Override
   protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      memo = (EditText) findViewById(R.id.memo);
   }

   public void salvar(View v){
      Clientes cli = new Clientes(this);
      cli.cidade = 1;
      cli.estado = 1;
      cli.Endereco = "soapsoapsaspao";
      cli.Nome = "asasasas";
      cli.save();
   }

   public void testar(View v ){
/*
      Clientes cli = new Clientes(this);
      cli.cidade = 1;
      cli.estado = 1;
      cli.Endereco = "soapsoapsaspao";
      cli.Nome = "asasasas";
      cli.save();
*/
      //verificar generic
      Clientes cli = new Clientes(this);
      List<Clientes> lista = cli.listAll(Clientes.class);

      memo.setText("");
      //TODO estudar mais sobre generics, ver como retornar os valores corretos dentro do array
      for(int i = 0; i < lista.size(); i++){
         String nome = lista.get(i).Nome;
         memo.append(lista.get(i).Nome + "\n");
         memo.append(lista.get(i).Endereco + "\n");
         memo.append(String.valueOf(lista.get(i).cidade) + "\n");
         memo.append(String.valueOf(lista.get(i).estado) + "\n\n");
      }
   }

}
