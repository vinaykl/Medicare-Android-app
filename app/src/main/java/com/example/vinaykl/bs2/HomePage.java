package com.example.vinaykl.bs2;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HomePage extends AppCompatActivity {

    Button btnPatient,btnDoctor,btnRequest;

    int totalSize = 0;
    int downloadedSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnPatient = (Button) findViewById(R.id.buttonPatient);
        btnPatient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intentSignUP=new Intent(getApplicationContext(),Patient_HomeActivity.class);
                startActivity(intentSignUP);
                finish();
            }
        });

        btnDoctor = (Button) findViewById(R.id.buttonDoctor);
        btnDoctor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intentSignUP=new Intent(getApplicationContext(),Doctor_Home_Activity.class);
                startActivity(intentSignUP);

                finish();
            }

        });
        btnRequest = (Button) findViewById(R.id.buttonRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intentReq=new Intent(getApplicationContext(),Req_Blood_Activity.class);
                startActivity(intentReq);
            }
        });

    }

    private void downloadFile() {
        try{
            URL url = new URL("https://impact.asu.edu/CSE535Fall16Folder/vkl.db");



            // Sir Code
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    // Not implemented
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    // Not implemented
                }
            } };

            try {
                SSLContext sc = SSLContext.getInstance("TLS");

                sc.init(null, trustAllCerts, new java.security.SecureRandom());

                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            // Sir Code end
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            urlConnection.connect();

            //set the path where we want to save the file
            //Context context = getApplicationContext();
            //String s = String.valueOf(context.getDatabasePath("vkl13.db"));
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //System.out.println(SDCardRoot);
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot,"vkl.db");

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        float per = ((float)downloadedSize/totalSize) * 100;
                        //System.out.println("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int)per + "%)" );
                    }
                });
            }
            //close the output stream when complete //

            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    // pb.dismiss(); // if you want close it..
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
