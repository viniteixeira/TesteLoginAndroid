package support.api.utils;

/**
 * Created by user on 03/08/17.
 */

public class TryParse {

    public static int tryParse(String parse) {

        try {
            return Integer.parseInt(parse);
        } catch (Exception e) {
            return 0;
        }


    }

}
