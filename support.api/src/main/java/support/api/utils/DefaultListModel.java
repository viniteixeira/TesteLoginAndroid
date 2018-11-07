package support.api.utils;


import java.util.ArrayList;
import java.util.List;

public class DefaultListModel {

    private String descricao = null;
    private String titulo = null;
    private String resposta = "";
    private String marcado = "";
    private int id = 0;
    private int pos = 0;
    private String cod = null;
    private Object objeto = null;


    public String getMarcado() {
        return this.marcado;
    }

    public void setMarcado(String var1) {
        this.marcado = var1;
    }


    public int getPosicao() {
        return this.pos;
    }

    public void setPosicao(int var1) {
        this.pos = var1;
    }


    public String getResposta() {
        return this.resposta;
    }

    public void setResposta(String var1) {
        this.resposta = var1;
    }

    public int getId() {
        return this.id;
    }

    public String getCodigo() {
        return this.cod;
    }

    public Object getObjeto() {
        return this.objeto;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String var1) {
        this.titulo = var1;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String var1) {
        this.descricao = var1;
    }

    public void setId(int var1) {
        this.id = var1;
    }

    public void setCodigo(String var1) {
        this.cod = var1;
    }

    public void setObjeto(Object var1) {
        this.objeto = var1;
    }

}
