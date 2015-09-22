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
      //cli.Nome = "asasasas";
      //cli.ValorReceber = 2.0;
      cli.save();
   }

   public void testar(View v ){
      Clientes cli = new Clientes(this);
      List<Clientes> lista = cli.listAll(Clientes.class);

      memo.setText("");
      StringBuilder dados = new StringBuilder();
      for(int i = 0; i < lista.size(); i++){
         dados.append(String.valueOf(i));
         dados.append(lista.get(i).Nome + "\n");
         dados.append(lista.get(i).Endereco + "\n");
         dados.append(String.valueOf(lista.get(i).cidade) + "\n");
         dados.append(String.valueOf(lista.get(i).estado) + "\n");
         dados.append(String.valueOf(lista.get(i).ValorReceber.toString()) + "\n\n");
      }
      memo.setText(dados.toString());
   }

}
