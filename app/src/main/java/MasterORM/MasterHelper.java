package MasterORM;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by saulo on 12/06/15.
 */
public class MasterHelper{

   public static MasterWrapper getMasterWrapper(Context cont){
      MasterWrapper wrapper = new MasterWrapper();
      try{
         ApplicationInfo ai = cont.getPackageManager().getApplicationInfo(cont.getPackageName(), PackageManager.GET_META_DATA);
         Bundle bundle = ai.metaData;
         wrapper.setDBName(bundle.getString("MasterDBName"));
         wrapper.setDBVersion(bundle.getInt("MasterDBVersion"));
         return wrapper;
      }catch(PackageManager.NameNotFoundException e){
         return null;
      }
   }

}
