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

public class Patient_Welcome extends AppCompatActivity {
    Button btnSignIn,chat,diet,access,rate,cvs,gtc,viewpris;
    Bundle bundle;
    ProgressDialog dialog = null;
    String dest;
    int serverResponseCode =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);


        btnSignIn = (Button) findViewById(R.id.button);
        diet = (Button)findViewById(R.id.diet);
        access =(Button)findViewById(R.id.access);
        rate =(Button)findViewById(R.id.rate);
        cvs =(Button)findViewById(R.id.cvs);
        gtc =(Button)findViewById(R.id.gtc);
        viewpris =(Button)findViewById(R.id.viewPris);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(Patient_Welcome.this, Select.class);
                Bundle b = new Bundle();
                // @ mallikarjun. write a code to send Patient who logs in in the variable patient, next line
                String patient = getIntent().getExtras().getString("patientname");
                b.putString("patient",patient);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Tab_Healthy_Activity.class);
                startActivity(intent);
            }
        });


        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Access_Records_Activity.class);

                Bundle bundle = getIntent().getExtras();
                String val = bundle.getString("PEMAIL");
                intent.putExtra("PEMAIL", val);
                startActivity(intent);

            }
        });
        /**rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Doct_List_Rate_Activity.class);
                Bundle bundle = getIntent().getExtras();
                String val = bundle.getString("PEMAIL");
                intent.putExtra("PEMAIL", val);
                startActivity(intent);
            }
        });**/
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Payment_Submission.class);
                Bundle bundle = getIntent().getExtras();
                String val = bundle.getString("PEMAIL");
                intent.putExtra("PEMAIL", val);
                startActivity(intent);
            }
        });
        cvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CVS_Search_Activity.class);
                startActivity(intent);
            }
        });

        gtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CVSStore.class);
                startActivity(intent);
            }
        });

        viewpris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PlaceOrder.class);
                Bundle bundle = getIntent().getExtras();
                String val = bundle.getString("PEMAIL");
                String patient = getIntent().getExtras().getString("patientname");
                intent.putExtra("PEMAIL", val);
                intent.putExtra("pname",patient);
                startActivity(intent);
            }
        });
        Button logout = ((Button) findViewById(R.id.logout));
        logout.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Patient_Welcome.this, Patient_HomeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        Button Calories = ((Button) findViewById(R.id.calories));
        Calories.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),wear.class);
                Bundle bundle = getIntent().getExtras();
                String val = bundle.getString("PEMAIL");
                String patient = getIntent().getExtras().getString("patientname");
                intent.putExtra("PEMAIL", val);
                intent.putExtra("pname",patient);
                startActivity(intent);
            }
        });

        Button password = ((Button) findViewById(R.id.password));
        Calories.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),changepass.class);
                Bundle bundle = getIntent().getExtras();
                String val = bundle.getString("PEMAIL");
                String patient = getIntent().getExtras().getString("patientname");
                intent.putExtra("PEMAIL", val);
                intent.putExtra("pname",patient);
                startActivity(intent);
            }
        });

        /*
        chat = (Button) findViewById(R.id.dirctchat);
        chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Uri mUri = Uri.parse("smsto:+14804105806");
                Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
                mIntent.setPackage("com.whatsapp");
                mIntent.putExtra("sms_body", "The text goes here");
                mIntent.putExtra("chat",true);
                startActivity(mIntent);
                /** Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("content://com.android.contacts/data/" + "14804109749@s.whatsapp.net"));
                 i.setPackage("com.whatsapp");
                 startActivity(i);
            }

        });  */
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
                            Toast.makeText(Patient_Welcome.this, "File Upload Complete.",
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
