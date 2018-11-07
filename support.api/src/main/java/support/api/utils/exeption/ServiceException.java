package support.api.utils.exeption;

public class ServiceException extends Exception {
  private String mensagem;

  public ServiceException(String mMensagem) {
    mensagem = mMensagem;
  }

  @Override public String getMessage() {
    return mensagem;
  }
}
