package com.example.vinaykl.bs2;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Doctor_Home_Activity extends AppCompatActivity {

    Button btnSignIn,btnSignUp;
    LoginDataBase_adapter doctorLoginDataBaseAdapter;

    private static final String NAME="name";
    private static final String PASSWORD="country";
    SharedPreferences sp=null;
    CheckBox CB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        sp=getSharedPreferences("LOGININFODOC", MODE_PRIVATE);

        // create a instance of SQLite Database
        doctorLoginDataBaseAdapter =new LoginDataBase_adapter(this);
        doctorLoginDataBaseAdapter = doctorLoginDataBaseAdapter.open();

        // Get The Refference Of Buttons
        btnSignIn=(Button)findViewById(R.id.buttonSignIN);
        btnSignUp=(Button)findViewById(R.id.buttonSignUP);

        // Set OnClick Listener on SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  and Start The Activity
                Intent intentSignUP=new Intent(getApplicationContext(),Doctor_SignUp_Activity.class);
                startActivity(intentSignUP);
            }
        });
    }
    // Methos to handleClick Event of Sign In Button
    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(Doctor_Home_Activity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");
        CB =(CheckBox)dialog.findViewById(R.id.Remember);


        // get the Refferences of views
        //final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextemail=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        String name = getuser();
        String pass= getPass();


        if(name!="-1" && pass!="-1") {
            editTextemail.setText(name);
            editTextPassword.setText(pass);
        }

        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                //String userName=editTextUserName.getText().toString();
                String email="";
                String password="";
                if(CB.isChecked()){
                    String name = getuser();
                    String pass= getPass();
                    email=editTextemail.getText().toString();
                    password=editTextPassword.getText().toString();
                    if(email!=name || pass!=password){
                        SaveInfo(email,password);
                    }

                    if(name=="-1"||pass=="-1"){
                        email=editTextemail.getText().toString();
                        password=editTextPassword.getText().toString();
                        SaveInfo(email,password);
                        password=editTextPassword.getText().toString();
                        // Toast.makeText(Patient_HomeActivity.this,"Returned:",Toast.LENGTH_SHORT).show();
                    }else {
                        editTextemail.setText(name);
                        editTextPassword.setText(pass);
                        //Toast.makeText(Patient_HomeActivity.this,"Returned:"+name+pass,Toast.LENGTH_SHORT).show();
                        email=editTextemail.getText().toString();
                        password=editTextPassword.getText().toString();
                    }


                }else{
                    email=editTextemail.getText().toString();
                    password=editTextPassword.getText().toString();
                    SaveInfo("-1","-1");
                }

                // fetch the Password form database for respective user name
                String storedPassword= doctorLoginDataBaseAdapter.getSinlgeEntry_doctor(email);

                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword)) {
                    String doctorName = doctorLoginDataBaseAdapter.getDoctorName(email);

                    System.out.println("vkl **** "+ doctorName);
                    Intent intentSignUP = new Intent(getApplicationContext(), Doctor_3options.class);
                    intentSignUP.putExtra("Doc", doctorName);
                    startActivity(intentSignUP);
                    //Toast.makeText(Doctor_Home_Activity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    //dialog.dismiss();
                    //Intent intentSignUP=new Intent(getApplicationContext(),Patient_Welcome.class);
                    //Intent intentSignUP=new Intent(getApplicationContext(),Doctor_welcome.class);
                }	//startActivity(intentSignUP);					}
                else
                {
                    Toast.makeText(Doctor_Home_Activity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        doctorLoginDataBaseAdapter.close();
    }

    String getuser(){
        sp=getSharedPreferences("LOGININFODOC", MODE_PRIVATE);
        String name=sp.getString(NAME, "-1");
        if(!name.equals("-1"))
            return name;
        else
            return "-1";



    }

    String getPass(){
        sp=getSharedPreferences("LOGININFODOC", MODE_PRIVATE);
        String name=sp.getString(PASSWORD, "-1");
        if(!name.equals("-1")) {
            return name;

        }
        else
            return "-1";


    }

    void SaveInfo(String name, String pass){
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(NAME, name);
        editor.putString(PASSWORD,pass);
        editor.commit();


    }


}
