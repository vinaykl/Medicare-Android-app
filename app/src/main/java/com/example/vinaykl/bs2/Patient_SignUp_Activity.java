package com.example.vinaykl.bs2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Patient_SignUp_Activity extends AppCompatActivity {

    EditText editTextUserName,editTextPassword,editTextConfirmPassword,
            editTextage,editTextsex,editTextsblood,
            editTextpreconditions,editTextphone,editTextgaurdian_email,editTextemail;
    Button btnCreateAccount;

    LoginDataBase_adapter Patient_loginDataBase_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_singup);

        // get Instance  of Database Adapter
        Patient_loginDataBase_Adapter =new LoginDataBase_adapter(this);
        Patient_loginDataBase_Adapter = Patient_loginDataBase_Adapter.open();

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextage=(EditText)findViewById(R.id.editTextage);
        editTextsex=(EditText)findViewById(R.id.editTextsex);
        editTextsblood=(EditText)findViewById(R.id.editTextblood);
        editTextpreconditions=(EditText)findViewById(R.id.editTextpreconditions);

        editTextphone=(EditText)findViewById(R.id.editTextphone);

        editTextgaurdian_email=(EditText)findViewById(R.id.editTextgaurdian_email);

        editTextemail=(EditText)findViewById(R.id.editTextemail);



        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName=editTextUserName.getText().toString();

                String age=editTextage.getText().toString();
                String Psex=editTextsex.getText().toString();
                String Pblood=editTextsblood.getText().toString();
                String Pprecondition=editTextpreconditions.getText().toString();
                String Pphone=editTextphone.getText().toString();

                String Pguardianemail=editTextgaurdian_email.getText().toString();

                String Pemail=editTextemail.getText().toString();


                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();



                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||confirmPassword.equals("")
                        ||age.equals("")||Psex.equals("")||Pblood.equals("")||Pguardianemail.equals("")
                        ||Pprecondition.equals("")||Pphone.equals("")||Pemail.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                int Page =Integer.parseInt(age);
                //int Pphone=Integer.parseInt(phone);
                //int Dnum =Integer.parseInt(num);
                // check if both password matches

                 Page =Integer.parseInt(age);
                 //long Pphone=Long.parseLong(phone);
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    //doctorLoginDataBaseAdapter.insertEntry(userName, password);
                    Patient_loginDataBase_Adapter.insertEntry_patient(userName, password,Page,Psex,Pblood,Pprecondition,Pphone,Pguardianemail,Pemail);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        Patient_loginDataBase_Adapter.close();
    }
}

