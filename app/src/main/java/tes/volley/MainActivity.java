package tes.volley;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import tes.volley.volleynet.Konstans;
import tes.volley.volleynet.SetDataAPI;
import tes.volley.volleynet.VolleyAppController;

public class MainActivity extends AppCompatActivity {


    private ProgressDialog dialogprogs;
    private String jsonhasil = "";

    private Button tombolmintadata;
    private String requestLinks = "";



    private TextView tekshasil;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halamanminta);

        tombolmintadata = (Button) findViewById(R.id.tombol_mintadata);
        dialogprogs = new ProgressDialog(MainActivity.this);


        requestLinks = SetDataAPI.getDataParamURL("1", Konstans.API_POSTBARU_HALAMAN);

        tekshasil = (TextView) findViewById(R.id.teks_hasil);
        tombolmintadata.setOnClickListener(listenertombol);

        Log.w("LINK","LINK " + requestLinks);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        VolleyAppController.getInstance().getmReqQueue().cancelAll("GET");
        VolleyAppController.getInstance().getmReqQueue().getCache().clear();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    View.OnClickListener listenertombol = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            cekMintaData();
        }
    };



    private void cekMintaData() {

        dialogprogs = new ProgressDialog(MainActivity.this);
        dialogprogs.setMessage("Memuat data...");
        dialogprogs.setCancelable(false);

        dialogprogs.show();

        tombolmintadata.setEnabled(false);

        StringRequest strReq = new StringRequest(Request.Method.GET, requestLinks,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.w("SUKSES","SUKSES HASIL");
                        tekshasil.setText("" + response);

                        dialogprogs.dismiss();
                        tombolmintadata.setEnabled(true);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                        Log.w("ERROR","ERROR HASIL");
                        tekshasil.setText(error.getMessage() + " ");

                        dialogprogs.dismiss();
                        tombolmintadata.setEnabled(true);
                    }
                }
        ) {

            //UNTUK BODY JIKA METODENYA POST DAN HEADER JIKA METODENYA GET DAN POST
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                super.getParams();
//
//                Map<String, String> parambody = new HashMap<>();
//                parambody.put("sampelparam","isi sampel");
//
//
//                return parambody;
//            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                super.getHeaders();
//
//                Map<String, String> paramheaders = new HashMap<>();
//                paramheaders.put("Content-Type","application/json");
//                paramheaders.put("apiKey","XXXXXXX");
//
//                paramheaders.put("sampelheaders","XXXXXXX");
//
//                return paramheaders;
//            }
        };

        //tambahkan ke request voli
        VolleyAppController.getInstance().addToRequestQueue(strReq,"GET");

    }



















}
