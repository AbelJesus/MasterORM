package MasterORM;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by saulo on 13/06/15.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MasterField {
   int Length() default 36;
   boolean Nullable() default false;
   boolean HasIndex() default false;
   String Default() default "";
}
