package login.visaogrupo.login.task;

import android.os.AsyncTask;

import org.json.JSONObject;

public class TaskLogin extends AsyncTask<Void, Void, Boolean> {


    @Override
    protected Boolean doInBackground(Void... voids) {

        new TaskGenerica().execute(new JSONObject());

        return null;
    }

    class Task3 extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {

            return null;
        }
    }


    class TaskGenerica extends AsyncTask<JSONObject, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Boolean doInBackground(JSONObject... jsonObjects) {
            return null;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
