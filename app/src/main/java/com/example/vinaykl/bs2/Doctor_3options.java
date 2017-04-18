package com.example.vinaykl.bs2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Doctor_3options extends AppCompatActivity {

    String DocName;
    String TableName;
    ProgressDialog dialog = null;
    String dest;
    int serverResponseCode =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_3options);

        DocName =  getIntent().getExtras().getString("Doc");
        TableName = DocName + "_Availability";

        System.out.println(DocName + "  "+ TableName);

        Button Add_Availability = ((Button) findViewById(R.id.AddAvailability));
        Add_Availability.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                // This is where I need the Doc Id Of the doctor via login

                // Rest all will remain the same
                Intent intent = new Intent(Doctor_3options.this, doctorHome.class);
                Bundle b = new Bundle();
                b.putString("Doc",DocName);
                b.putString("Name", TableName);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });


        Button Show_Records = ((Button) findViewById(R.id.ViewRecords));
        Show_Records.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                // This is where I need the Doc Id Of the doctor via login

                // Rest all will remain the same
                Intent intent = new Intent(Doctor_3options.this, ShowRecords.class);
                Bundle b = new Bundle();
                b.putString("Doc",DocName);
                TableName = DocName+"_access";
                b.putString("Name", TableName);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        Button Sessions = ((Button) findViewById(R.id.Sessions));
        Sessions.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Doctor_3options.this, Doctor_View_Appointments.class);
                Bundle b = new Bundle();
                b.putString("DocName",DocName);
                b.putString("TableName", TableName);
                intent.putExtras(b);
                startActivity(intent);
                finish();
                // Mallikarjun Code
            }
        });
        Button logout = ((Button) findViewById(R.id.logout));
        logout.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Doctor_3options.this, Doctor_Home_Activity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    public int uploadFile(String filenameandpath) {
        //System.out.println(" Area of interest "+filenameandpath);
        String fileName = filenameandpath;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead = 0, bytesAvailable, bufferSize = 0;
        byte[] buffer = new byte[0];
        int maxBufferSize =  1*1024*1024;
        String UploadURI = "https://impact.asu.edu/CSE535Fall16Folder/UploadToServer.php";

        File sourceFile = new File(filenameandpath);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    +dest);

            runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            return 0;

        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(UploadURI);




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
                conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                //int index = fileName.indexOf("vkl13.db");
                //String dest1 = fileName.substring(0,index);
                conn.setRequestProperty("uploaded_file", fileName);

                //conn.connect();

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);
                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Doctor_3options.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            dialog.dismiss();
            return serverResponseCode;
        }
    }
}
