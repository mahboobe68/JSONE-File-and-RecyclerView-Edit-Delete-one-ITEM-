package com.example.RecyclerViewTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.RecyclerViewTest.adapters.MyAdapterNames;
import com.example.RecyclerViewTest.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapterNames myAdapterNames;
    ArrayList<Student> students;
    FloatingActionButton floatingActionButton;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText edtname, edtfamily,edtphone;
    String filename = "InternalFilestest";

    JSONArray array = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtname= findViewById(R.id.edtname);
        edtfamily= findViewById(R.id.edtfamily);
        edtphone= findViewById(R.id.edtphone);
        swipeRefreshLayout = findViewById(R.id.swiprefresh);
        floatingActionButton = findViewById(R.id.floatingActionButton2);
        students = new ArrayList<>();
     //   setStudentValues();

        recyclerView = findViewById(R.id.RView);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student(edtname.getText().toString(), edtfamily.getText().toString(),edtphone.getText().toString());
                students.add(student);
                myAdapterNames.notifyItemInserted(0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {







                Toast.makeText(getApplicationContext(), "SWip", Toast.LENGTH_SHORT).show();
               swipeRefreshLayout.setRefreshing(false);



            }
        });


            }
        });

    }







    public void AddtoJSON(View view) {
     //   Student student = new Student(edtname.getText().toString(), edtfamily.getText().toString(),edtphone.getText().toString());
     //   students.add(student);

        try {
            JSONObject object = new JSONObject();
        object.put("Name", edtname.getText().toString());
        object.put("Family", edtfamily.getText().toString());
        object.put("idnumber", Integer.valueOf(edtphone.getText().toString()));
        array.put(object);


        String value = array.toString();

        writfile(value);

    } catch (
    JSONException e) {
        e.printStackTrace();
    }






    }

    public void Register(View view) {

    }

    public void Showlist(View view) {

       ArrayList<Student> stu = setStudentValues();
        myAdapterNames = new MyAdapterNames(getApplicationContext(),stu,edtname,edtfamily,edtphone);
        recyclerView.setAdapter(myAdapterNames);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));




    }


    public void writfile( String str) {

        String content = str;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();

        } catch (Exception e) {
            Toast.makeText(this, e+"", Toast.LENGTH_SHORT).show();        }
    }

    public String readFile() {

        BufferedReader bufferedReader = null;
        File filenametwo = new File(getFilesDir(), filename);
        StringBuffer buffer = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filenametwo)));

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                buffer.append(line);

            }

            //txt1.setText(buffer.toString());

        } catch (Exception e) {
            Toast.makeText(this, e+"", Toast.LENGTH_SHORT).show();        }

        return  buffer.toString();
    }



    public  ArrayList<Student> setStudentValues(){

        try{

            String json = readFile();
            JSONArray data = new JSONArray(json);
            students.clear();
            for (int i=0; i<data.length();i++){
                JSONObject jsonObject = data.getJSONObject(i);
                Student studenttest = new Student();
                studenttest.setName( jsonObject.getString("Name"));
                studenttest.setFamily( jsonObject.getString("Family"));
                studenttest.setStunumber(jsonObject.getString("idnumber"));
                students.add(studenttest);

            }

            //  txt1.setText(studentInfos.get(1).getName());
            //  txt.setText(studentInfos.get(0).getFamily());
            //  for (StudentInfo studentInfo:studentInfos){
            //txt2.setText("Name: "+ studentInfo.getName()+ " Family: "+studentInfo.getFamily() + " idnumber: " + studentInfo.getIdnumber());

            //  }





        } catch (JSONException e) {
            e.printStackTrace();


        }

        return students;






    }

}

