package support.api.utils;

/**
 * Classe que cria mascara
 */
public class Mask {
    private String mMask;
    boolean isUpdating;
    String old = "";

    public Mask(String mMask) {
        this.mMask = mMask;
    }

    //Tira caracteres especiais
    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    /**
     * Retorna texto com a mascara informada
     * @param texto
     * @return
     */
    public String format(String texto) {
        String str = Mask.unmask(texto.toString());
        String mascara = "";
        if (isUpdating) {
            old = str;
            isUpdating = false;
            return texto;
        }
        int i = 0;
        for (char m : mMask.toCharArray()) {
            if (m != '#' && str.length() > old.length()) {
                mascara += m;
                continue;
            }
            try {
                mascara += str.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        isUpdating = true;
        return mascara;
    }
}