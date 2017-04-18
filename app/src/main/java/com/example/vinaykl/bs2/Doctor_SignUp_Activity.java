package com.example.vinaykl.bs2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Doctor_SignUp_Activity extends AppCompatActivity {

    EditText editTextUserName,editTextPassword,editTextConfirmPassword,
            editTextage,editTextsex,editTextspecialization,
            editTextdegnum,editTextavailabiltystart,editTextavailabiltyend,editTextphone,editTextemail;
    Button btnCreateAccount;

    LoginDataBase_adapter doctorLoginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // get Instance  of Database Adapter
        doctorLoginDataBaseAdapter =new LoginDataBase_adapter(this);
        doctorLoginDataBaseAdapter = doctorLoginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextage=(EditText)findViewById(R.id.editTextage);
        editTextsex=(EditText)findViewById(R.id.editTextsex);
        editTextspecialization=(EditText)findViewById(R.id.editTextspecilization);
        editTextdegnum=(EditText)findViewById(R.id.editTextdegnum);
        editTextavailabiltystart=(EditText)findViewById(R.id.editTextavailabilitystart);
        editTextavailabiltyend=(EditText)findViewById(R.id.editTextavailabilityend);
        editTextphone=(EditText)findViewById(R.id.editTextphone);
        editTextemail=(EditText)findViewById(R.id.editTextemail);



        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName=editTextUserName.getText().toString();

                String age=editTextage.getText().toString();
                String Dsex=editTextsex.getText().toString();
                String Dspecilization=editTextspecialization.getText().toString();
                String num=editTextdegnum.getText().toString();

                String Dphone=editTextphone.getText().toString();
                String Demail=editTextemail.getText().toString();


                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();



                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||confirmPassword.equals("")
                        ||age.equals("")||Dsex.equals("")||Dspecilization.equals("")||num.equals("")
                        ||Dphone.equals("")||Demail.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                int Dage =Integer.parseInt(age);
                //int Dphone=Integer.parseInt(phone);
                int Dnum =Integer.parseInt(num);
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    //doctorLoginDataBaseAdapter.insertEntry(userName, password);
                    int Drating=0;
                    doctorLoginDataBaseAdapter.insertEntry_doctor(userName, password,Dage,Dsex,Dspecilization,Dnum,Dphone,Demail,Drating);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        doctorLoginDataBaseAdapter.close();
    }
}

