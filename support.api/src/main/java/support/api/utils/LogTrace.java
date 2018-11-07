package support.api.utils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Build;
import android.system.ErrnoException;
import android.util.Log;

import java.io.FileNotFoundException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;
import org.json.JSONObject;

import support.api.http.HTTP;
import support.api.http.Metodo;
import support.api.utils.exeption.ServiceException;

public abstract class LogTrace {

    /**
     * Trata o erro, printa o erro no terminal e não exibe alerta
     *
     * @param context
     * @param classe
     * @param err
     * @return
     */
    public static String logCatch(Context context, Class classe, Throwable err) {
        return LogTrace.logCatch(context, classe, err, false);
    }

    /**
     * Trata o erro, printa o erro no terminal e exibe alerta amigavel
     *
     * @param context
     * @param classe
     * @param err
     * @param exibeMensagem
     * @return
     */
    public static String logCatch(final Context context, Class classe, Throwable err, boolean exibeMensagem) {
        StringBuilder sb = new StringBuilder();
        String erro;
        final String tag = "REPORTA_ERRO_LOG";

        //Verifica qual é a extensão da Exception
        if (err instanceof NetworkErrorException || err instanceof UnknownHostException || err instanceof SocketException || err instanceof TimeoutException)
            erro = "No momento não foi possível processar uma requisição troca de dados. Verifique sua conexão e tente novamente.";
        else if (err instanceof SocketTimeoutException)
            erro = "O tempo de tentativa de conexão com o servidor foi exedito. Verifique sua conexão e tente novamente.";
        else if (err instanceof FileNotFoundException)
            erro = "Arquivo não encontrado";
        else if (err instanceof SQLiteException)
            erro = "No momento não foi possível processar uma requisição de banco de dados. Tente novamente.";
        else if (err instanceof ServiceException)
            erro = err.getMessage();
        else if (err instanceof JSONException)
            erro = "No momento não foi possível extrair informações de uma estrutura de dados. Tente novamente.";
        else if (err instanceof SecurityException)
            erro = "Sua conexão com a rede não tem acesso ao servidor.";
        else
            erro = err.getMessage() == null ? "Ocorreu um erro não identificado" : err.getMessage();

        //Monta mensagem de erro
        //sb.append(err.getClass().getSimpleName());
        //sb.append(" - ");


        sb.append(erro);
        sb.append("");
        erro = sb.toString();

        //ConnectException - Verifique sua conexão
        //NetworkErrorException - Verifique sua conexão

        err.printStackTrace();
        Log.e(classe.getName(), erro);

        if (exibeMensagem) {
            Alerta.show(context, "Erro", erro);
        }
        String stackTrace = getAllException(err);
        String erroString = err.getMessage();
        String dispositivo = "Android";
        String versao = "1.0.1";

        final JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("codigo_lab", 40);
            jsonBody.put("stacktrack", stackTrace);
            jsonBody.put("erroString", erroString);
            jsonBody.put("dispositivo", dispositivo);
            jsonBody.put("versao", versao);


        } catch (Exception e) {
            e.printStackTrace();
        }

//TODO: TERMINAR IMPLEMENTACAO
        if (HTTP.isOnline(context) && true == false) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {

                    HTTP http;

                    String bodyCripty;
                    String retorno;

                    try {


                        //Verifica se esta online
                        if (HTTP.isOnline(context)) {


                            //EnviaPedido
                            http = new HTTP(context, "URL_LOG");

                            //Obtem parametro

                            //Criptografa envio

                            //Conect
                            http.setMetodo(Metodo.POST);
                            http.connect(jsonBody.toString());

                            //Obtem retorno
                            retorno = http.getRetorno();
                        } else
                            Log.e(tag, "Não foi possivel comunicar erro");
                    } catch (Exception err) {
                        Log.e(tag, "Erro na comunicação ", err);
                    }

                    return null;
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        }

        return erro;
    }

    /**
     * Percorre a exception e retorna uma string com as informações de erro encontradas
     *
     * @param err
     * @return
     */
    public static String getAllException(Throwable err) {
        return LogTrace.getAllException(err, "");
    }

    public static String getAllException(Throwable err, String moreMessage) {
        try {
            StackTraceElement[] arr = err.getStackTrace();
            StringBuilder report = new StringBuilder();

            report.append(err.toString());
            report.append("<br/><br/>");

            report.append("--------- Stack trace ---------");
            report.append("<br/><br/>");
            for (StackTraceElement elem : arr) {
                report.append(String.format("    %1$s", elem.toString()));
                report.append("<br/>");
            }
            report.append("-------------------------------");
            report.append("<br/><br/>");

            // If the exception was thrown in a background thread inside
            // AsyncTask, then the actual exception can be found with getCause
            Throwable cause = err.getCause();
            if (cause != null) {
                report.append("--------- Cause ---------");
                report.append("<br/><br/>");
                report.append(cause.toString());
                report.append("<br/><br/>");
                arr = cause.getStackTrace();
                for (StackTraceElement elem : arr) {
                    report.append(String.format("    %1$s", elem.toString()));
                    report.append("<br/>");
                }
                report.append("-------------------------------");
                report.append("<br/><br/>");
            }

            // Adiciona mais alguma mensagem
            if (!moreMessage.isEmpty()) {
                report.append("--------- More Mensage ---------");
                report.append("<br/><br/>");
                report.append(moreMessage);
                report.append("-------------------------------");
                report.append("<br/><br/>");
            }

            return report.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
