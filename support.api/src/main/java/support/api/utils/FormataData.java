package support.api.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Formata data
 */
public enum FormataData {
    Completo("dd/MM/yyyy HH:mm:ss"),
    CompletoInvertido("HH:mm:ss dd/MM/yyyy"),
    DiaMes("dd/MM"),
    Mes("MM"),
    AnoMesDias("yyyy/MM/dd")
    ,
    DiaMesAno("dd/MM/yyyy"),
    HoraMin("HH:mm"),
    HoraMinSeg("HH:mm:ss"),
    SQL("yyyy-MM-dd HH:mm:ss"),
    SQLData("yyyy-MM-dd"),
    Numerico("yyyyMMdd"),
    DataDoc("yyyyMMddHH");

    public static final String NULL_DATE = "1900-01-01";
    String mFormato;

    FormataData(String mFormato) {
        this.mFormato = mFormato;
    }

    /**
     * Obtem string da data com o formato selecionado
     */
    public static String get(Calendar data, FormataData dataFormato) {

        String dataFormatada;
        String formato = dataFormato.getFormato();

        //Data formatada
        dataFormatada = new SimpleDateFormat(formato, Locale.getDefault()).format(data.getTime());

        return dataFormatada;
    }

    /**
     * Obtem calendar da data com o formato selecionado
     */
    public static Calendar getCalendar(String data, FormataData dataFormato) throws Exception {

        Date date;
        SimpleDateFormat formatter;
        Calendar calendar = Calendar.getInstance();
        String formato = dataFormato.getFormato();

        //Data formatada
        formatter = new SimpleDateFormat(formato, Locale.getDefault());
        date = formatter.parse(data);
        calendar.setTime(date);

        return calendar;
    }

    private String getFormato() {
        return mFormato;
    }
}