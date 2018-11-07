package support.api.IO;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import support.api.utils.TipoArquivo;

/**
 * Created by user on 12/06/17.
 */

public class GerenciadorDeArquivos {

    private File diretorio;
    private String nomeArquivo;
    private Gson gson;

    public GerenciadorDeArquivos(File diretorio, String nomeArquivo, TipoArquivo tipoArquivo) {
        this.diretorio = diretorio;
        this.nomeArquivo = nomeArquivo;
        if (tipoArquivo.equals(TipoArquivo.JSON)) {
            this.nomeArquivo += ".json";
            this.gson = new Gson();
        }
    }

    /**
     * Cria um JSON ARRAY no qual será adicionado objetos
     *
     * @throws IOException
     */
    public void criaArquivoJson() throws IOException, JSONException {
        File arquivo = new File(diretorio, nomeArquivo);
        Log log = new Log();
        arquivo.getParentFile().mkdirs();
        JSONArray jsonArray = new JSONArray();
        FileOutputStream fos = new FileOutputStream(arquivo);
        JSONObject jsonObject = new JSONObject(gson.toJson(log).toString());
        jsonArray.put(jsonArray.length(),jsonObject);
        fos.write(jsonArray.toString().getBytes());


    }

    public void criaArquivoJson(JSONArray jsonArray) throws IOException, JSONException {
        File arquivo = new File(diretorio, nomeArquivo);
        arquivo.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(arquivo);
        fos.write(jsonArray.toString().getBytes());


    }

    public String lerArquivo() throws IOException {
        String linhaCompleta = "";
        String linha = "";
        File arquivo = new File(diretorio, nomeArquivo);
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));

        while ((linha = reader.readLine()) != null) {
            linhaCompleta += linha;

        }
        return linhaCompleta;
    }

    public void adicionaObjeto(Log log) throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray(lerArquivo());
        JSONObject jsonObject = new JSONObject(gson.toJson(log).toString());
        jsonArray.put(jsonArray.length(),jsonObject);
        criaArquivoJson(jsonArray);


    }


}
