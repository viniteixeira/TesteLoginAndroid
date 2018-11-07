package support.api.IO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 12/06/17.
 */

public class Log {
    @SerializedName("id_log")
    private int  id;
    @SerializedName("classe")
    private String classe;
    @SerializedName("stacktrace")
    private String stacktrace;
    @SerializedName("dataHoraMM")
    private String dataHoraMM;
    @SerializedName("fatal")
    private boolean fatal;
    @SerializedName("app")
    private String app;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public String getDataHoraMM() {
        return dataHoraMM;
    }

    public void setDataHoraMM(String dataHoraMM) {
        this.dataHoraMM = dataHoraMM;
    }

    public boolean isFatal() {
        return fatal;
    }

    public void setFatal(boolean fatal) {
        this.fatal = fatal;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Log(int id, String classe, String stacktrace, String dataHoraMM, boolean fatal, String app) {
        this.id = id;
        this.classe = classe;
        this.stacktrace = stacktrace;
        this.dataHoraMM = dataHoraMM;
        this.fatal = fatal;
        this.app = app;
    }

    public Log() {

    }
}
