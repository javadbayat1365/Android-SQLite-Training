package com.example.user.myprofile;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   public static final String Extra_Message = "com.Example.Message";
   public static String InputText= "";
   DataBaseHelper db;
   EditText etName,etsurName,etMark,etID;
   Button btnshowall,btnUpdatedata,btnDeleteData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(this);

        TextView ET = (TextView) findViewById(R.id.textView);
        if(ET != null)
        ET.setText(ET.getText().toString());


        etName = (EditText)findViewById(R.id.txtname);
        etsurName = (EditText)findViewById(R.id.txtsurname);
        etMark = (EditText)findViewById(R.id.txtmark);
        etID = (EditText)findViewById(R.id.txtID);
        btnshowall = (Button)findViewById(R.id.btnShowAll);
        btnUpdatedata = (Button)findViewById(R.id.btnUpdatedata);
        btnDeleteData = (Button)findViewById(R.id.btnDeletedata);



        UpdateData();
        DeleteData();
    }

    public  void InsertData(View view)
    {
        boolean result = db.insertData(etName.getText().toString(),etsurName.getText().toString(),etMark.getText().toString());
        if(result = true)
        {
            Toast.makeText(this,"ثبت موفقیت اطلاعات",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"خطای ثبت اطلاعات",Toast.LENGTH_LONG).show();
        }




        //Intent showmessage = new Intent(this,messaging.class);
        //EditText edittext = (EditText)findViewById(R.id.textView2);
        //String message = edittext.getText().toString();
        //InputText = message;
        //showmessage.putExtra(Extra_Message,message);
        //startActivity(showmessage);
    }

   public void ShowAllData(View view)
    {
        Cursor res = db.GetAllData();
        if(res.getCount() == 0)
        {
            showmessage("Error","No Data Are Found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext())
        {
            buffer.append("Id: "+res.getString(0)+"\n ");
            buffer.append("Name: "+res.getString(1)+"\n ");
            buffer.append("SurName: "+res.getString(2)+"\n ");
            buffer.append("Mark: "+res.getString(3)+"\n\n ");

            showmessage("Data",buffer.toString());
        }
    }

    public void UpdateData()
    {
      btnUpdatedata.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if (etID.getText().toString() == "") {
                          //Toast.makeText(this,"آی دی را وارد کنید", Toast.LENGTH_LONG).show();
                          //return;
                      }
                     boolean result = db.UpgrateData(etID.getText().toString(),etName.getText().toString(),etsurName.getText().toString(),etMark.getText().toString());
                      if(result == true)
                      {
                          Toast.makeText(MainActivity.this,"ویرایش موفقیت اطلاعات",Toast.LENGTH_LONG).show();
                      }
                      else {
                          Toast.makeText(MainActivity.this,"خطا در ویرایش",Toast.LENGTH_LONG).show();
                      }
                  }
              }
      );
    }

    public void DeleteData()
    {
       btnDeleteData.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                     boolean result = db.DeleteData(etID.getText().toString());
                     if (result == true)
                     {
                         Toast.makeText(MainActivity.this,"حذف انجام شد",Toast.LENGTH_LONG).show();
                     }
                     else
                     {
                         Toast.makeText(MainActivity.this,"خطا در حذف",Toast.LENGTH_LONG).show();
                     }
                   }
               }
       );
    }



    public void showmessage(String Title,String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }
}
