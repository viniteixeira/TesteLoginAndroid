package support.api.http;

public enum ContentType {
    Text("text/plain"), Json("application/json");

    private String mContentString;

    ContentType(String mContentString) {
        this.mContentString = mContentString;
    }

    @Override
    public String toString() {
        return mContentString;
    }
}
