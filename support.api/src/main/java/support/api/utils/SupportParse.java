package support.api.utils;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class SupportParse {

    /**
     * Inicializa parse
     * @param application
     * @param appID
     * @param clienteKey
     */
    public static void init(Application application, String appID, String clienteKey) {
        //Inicializa Parse
        Parse.enableLocalDatastore(application);
        Parse.initialize(application,
                appID,
                clienteKey
        );

        //Usuaio Automatico, não registra na Session
        ParseUser.enableAutomaticUser();

        //Cria default um usuario e uma session
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
