package MasterORM;

import java.lang.reflect.Field;

/**
 * Created by Abel Jesus on 22/09/2015.
 */
public final class MasterQueryBuilder {


    public static <T extends MasterRecord> String  Select(Class<T> klass){

        StringBuilder sb = new StringBuilder();
        sb.append("Select ");

        for(Field field : klass.getDeclaredFields()){
            sb.append(field.getName() + ",");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(" from " + klass.getSimpleName());

        return sb.toString();
    }

    public static <T extends MasterRecord> String Delete(Class<T> klass){
        StringBuilder sb = new StringBuilder();
        sb.append("Delete from " + klass.getSimpleName());
        return sb.toString();
    }

    public static void Test(){
        //return "";
    }

    public static void Test2(){
        //return "";
    }


    public static  <T extends MasterRecord> String Where(){
        return "Where";
    }


}
