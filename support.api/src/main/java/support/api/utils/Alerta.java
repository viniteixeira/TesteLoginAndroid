package support.api.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Alerta extends AlertDialog {

    public Alerta(Context context) {
        super(context);
    }

    public Alerta(Context context, int largura) {
        super(context);
    }

    /**
     * Show simples
     * @param context
     * @param titulo
     * @param mensagem
     */
    public static AlertDialog show(Context context, String titulo, String mensagem) {
        new Alerta.Builder(context)
            .setTitle(titulo)
            .setMessage(mensagem)
            .setPositiveButton("Fechar", null)
            .show();
        return null;
    }

    /**
     * Show com opção
     * @param context
     * @param titulo
     * @param mensagem
     * @param botaoPositivo
     * @param onClick
     * @return
     */
    public static AlertDialog show(Context context, String titulo, String mensagem, String botaoPositivo, DialogInterface.OnClickListener onClick) {
        return new Alerta.Builder(context)
            .setTitle(titulo)
            .setMessage(mensagem)
            .setNegativeButton("Cancelar", null)
            .setPositiveButton(botaoPositivo, onClick)
            .setCancelable(false)
            .show();
    }

    /**
     * Show com opção
     * @param context
     * @param titulo
     * @param mensagem
     * @param onClick
     * @return
     */
    public static AlertDialog show(Context context, String titulo, String mensagem,DialogInterface.OnClickListener onClick) {
        return new Alerta.Builder(context)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setNegativeButton("Ok", onClick)
                .show();
    }
}
