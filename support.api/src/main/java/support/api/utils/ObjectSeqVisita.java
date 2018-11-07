package support.api.utils;

import java.util.Calendar;

/**
 * Created by MarioGuerra on 18/05/16.
 */
public class ObjectSeqVisita {

    private int sequencia;
    private int cod_cliente;
    private String nome_cliente;
    
    private int ID_VISITA ;
    private int ID_CLIENTE;
    private int ID_USUARIO;
    private Calendar DATA_VISITACAO;
    private int ORDEM;
    private int ID_SEMANA_CICLO;
    private String RANGE;
    private int  COD_STATUS;
    private int REAGENDADO;
    private int NAO_VISITA;
    private int EXCLUIDO;
    private int ALTERADO;
    private double VALOR_VENDA;
    private int VISITADO;
    private int EFETIVO;
    private String COMENTARIOS;
    private String OBJETIVO_PROXIMA_VISITA;
    private String ID_MOBILE;

    public String getID_MOBILE() {
        return ID_MOBILE;
    }

    public void setID_MOBILE(String ID_MOBILE) {
        this.ID_MOBILE = ID_MOBILE;
    }

    public int getNAO_VISITA() {
        return NAO_VISITA;
    }

    public void setNAO_VISITA(int NAO_VISITA) {
        this.NAO_VISITA = NAO_VISITA;
    }

    public int getEXCLUIDO() {
        return EXCLUIDO;
    }

    public void setEXCLUIDO(int EXCLUIDO) {
        this.EXCLUIDO = EXCLUIDO;
    }


    public int getALTERADO() {
        return ALTERADO;
    }

    public void setALTERADO(int ALTERADO) {
        this.ALTERADO = ALTERADO;
    }


    public double getVALOR_VENDA() {
        return VALOR_VENDA;
    }

    public void setVALOR_VENDA(double VALOR_VENDA) {
        this.VALOR_VENDA = VALOR_VENDA;
    }

    public Calendar getDATA_VISITACAO() {
        return DATA_VISITACAO;
    }

    public void setDATA_VISITACAO(Calendar DATA_VISITACAO) {
        this.DATA_VISITACAO = DATA_VISITACAO;
    }


    public int getId() {
        return ID_VISITA;
    }

    public void setId(int id) {
        this.ID_VISITA = id;
    }

    public int getIdCliente() {
        return ID_CLIENTE;
    }

    public void setIdCliente(int ID_CLIENTE) {
        this.ID_CLIENTE = ID_CLIENTE;
    }

    public int getIdUsuario() {
        return ID_USUARIO;
    }

    public void setIdUsuario(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public int getId_Semana_Ciclo() {
        return ID_SEMANA_CICLO;
    }

    public void setId_Semana_Ciclo(int ID_SEMANA_CICLO) {
        this.ID_SEMANA_CICLO = ID_SEMANA_CICLO;
    }

    public int getOrdem() {
        return ORDEM;
    }

    public void setOrdem(int ORDEM) {
        this.ORDEM = ORDEM;
    }

    public int getReagendado() {
        return REAGENDADO;
    }

    public void setReagendado(int REAGENDADO) {
        this.REAGENDADO = REAGENDADO;
    }


    public int getCodStatus() {
        return COD_STATUS;
    }

    public void setCod_Status(int COD_STATUS) {
        this.COD_STATUS = COD_STATUS;
    }

    public int getEfetivo() {
        return EFETIVO;
    }

    public void setEfetivo(int EFETIVO) {
        this.EFETIVO = EFETIVO;
    }

    public int getVisitado() {
        return VISITADO;
    }

    public void setVisitado(int VISITADO) {
        this.VISITADO = VISITADO;
    }

    public String getRange() {
        return RANGE;
    }

    public void setRange(String RANGE) {
        this.RANGE = RANGE;
    }

    public String getOBJETIVO_PROXIMA_VISITA() {
        return OBJETIVO_PROXIMA_VISITA;
    }

    public void setOBJETIVO_PROXIMA_VISITA(String OBJETIVO_PROXIMA_VISITA) {
        this.OBJETIVO_PROXIMA_VISITA = OBJETIVO_PROXIMA_VISITA;
    }

    public String getComentario() {
        return COMENTARIOS;
    }

    public void setComentario(String COMENTARIOS) {
        this.COMENTARIOS = COMENTARIOS;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }


    public int getCod_cliente() {
        return cod_cliente;
    }

    public void setCod_cliente(int cod_cliente) {
        this.cod_cliente = cod_cliente;
    }


    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

}
