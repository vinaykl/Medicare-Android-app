package com.example.vinaykl.bs2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by memal on 11/12/2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by memal on 11/1/2016.
 */

public class Priscription_Submission extends Activity {

    EditText doctor,patient,age,tablets,name,amount;
    Button btnCreateAccount;
    MultiAutoCompleteTextView ma;
    Button GenaratePdf;
    LoginDataBase_adapter priscription_LoginDataBaseAdapter;
    LoginDataBase_adapter doctorLoginDataBaseAdapter;
    LoginDataBase_adapter Patient_loginDataBase_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_priscription__submission);

        doctorLoginDataBaseAdapter =new LoginDataBase_adapter(this);
        doctorLoginDataBaseAdapter = doctorLoginDataBaseAdapter.open();

        Patient_loginDataBase_Adapter =new LoginDataBase_adapter(this);
        Patient_loginDataBase_Adapter = Patient_loginDataBase_Adapter.open();

        // get Instance  of Database Adapter
        priscription_LoginDataBaseAdapter =new LoginDataBase_adapter(this);
        priscription_LoginDataBaseAdapter = priscription_LoginDataBaseAdapter.open();

        // Get Refferences of Views
        doctor=(EditText)findViewById(R.id.editDoctorName);
        patient=(EditText)findViewById(R.id.editTextPatientName);
        age=(EditText)findViewById(R.id.editTextPatientAge);
        amount=(EditText)findViewById(R.id.editTextConsultationFees);
        //tablets=(EditText)findViewById(R.id.editTextTablets);
        /**   GenaratePdf=(Button)findViewById(R.id.buttonGenaratePdf);
         GenaratePdf.setOnClickListener(new View.OnClickListener() {

         public void onClick(View v) {

         String pdfname=name.getText().toString();

         String resultdoc=doctorLoginDataBaseAdapter.getSinlgeEntry(pdfname);
         Document document = new Document(PageSize.A4,0,0,10,10);
         PdfWriter.getInstance(document, new FileOutputStream("Image.pdf"));
         Paragraph p =new Paragraph(resultdoc,new Font(Font.FontFamily.ZAPFDINGBATS,20));
         document.open();
         document.add(p);
         document.close();


         }
         });**/

        //a=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        ma=(MultiAutoCompleteTextView) findViewById(R.id.MultiAutoCompleteTextView);

        List<String> Countries = new ArrayList<String>();
        Countries.add("Dolo650");
        Countries.add("Sinerest");
        Countries.add("Cetrezin");

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,Countries);

        //a.setAdapter(adapter);
        ma.setAdapter(adapter);
        ma.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        btnCreateAccount=(Button)findViewById(R.id.buttonAuthorize);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String docname=doctor.getText().toString();
                String patientname=patient.getText().toString();
                String patientage=age.getText().toString();
                String fees=amount.getText().toString();
                // String tabletlist=tablets.getText().toString();
                String tabletlist=ma.getText().toString();
                System.out.println("tablets : "+ tabletlist);
                // check if any of the fields are vaccant
                if(docname.equals("")||patientname.equals("")||patientage.equals("") || tabletlist.equals("")|| fees.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches

                else
                {
                    String resdoc= doctorLoginDataBaseAdapter.subcription_doc_check(docname);
                    //System.out.println(resdoc);

                    String respat= Patient_loginDataBase_Adapter.subcription_pat_check(patientname);
                    //System.out.println(respat);
                    if(resdoc.equals("NOT EXIST") || respat.equals("NOT EXIST"))
                    {

                        Toast.makeText(Priscription_Submission.this, "DOCTOR OR PATIENT DOES not match", Toast.LENGTH_LONG).show();
                    }
                    else {
                        // Save the Data in Database
                        priscription_LoginDataBaseAdapter.insert_prisc_Entry(resdoc,respat,docname, patientname, patientage, tabletlist,fees);
                        Toast.makeText(getApplicationContext(), "PRESCRIPTION UPLOADED  ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        priscription_LoginDataBaseAdapter.close();
    }
}

/**
 <EditText
 android:id="@+id/editTextTablets"
 android:layout_width="fill_parent"
 android:layout_height="wrap_content"
 android:hint="Tablets"
 />
 **/

