package generalJavaQuestions.commonQuestions.desingPattern;


public class DesignPatternSingleton {
    /*
    * 1. private constructor
    * 2. static volatile instance of the class
    * 3. public static getInstance method
    * 4. synchronized block for multi threaded env
    * 5. double null check to avoid multi threads waiting for the synchronous block
    * */

    private DesignPatternSingleton(){

    }


    /*1. Via volatile and static and synchronized block*/

    private volatile static DesignPatternSingleton INSTANCE;

    public static DesignPatternSingleton getInstance(){
        if(INSTANCE == null){
            synchronized (DesignPatternSingleton.class){
                if(INSTANCE == null)
                    INSTANCE = new DesignPatternSingleton();
            }
        }
        return INSTANCE;
    }


    /*
    * 1. the inner static class is loaded lazily when getInstance is called
    * 2. and as this is the static HolderClass which will be loaded once and
    *    serves the same instance for the life
    * */

    /*2. via holder class */
   private static final class HolderClass{
       private static final DesignPatternSingleton INSTANCE = new DesignPatternSingleton();
    }

    public static DesignPatternSingleton getInstanceViaHolder(){
       return HolderClass.INSTANCE;
    }



}

/*3. via Enum */
enum SingleTonViaEnum{
    INSTANCE;
}

