package com.example.android.kwizquiz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.M;

public class DisplayResult extends AppCompatActivity {
    int finalScore;
    String salutation;
    String theVerdict;
    String candidateName;

    //xml views
    TextView txtSalute;
    EditText etFinalScore;
    EditText etTheVerdict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);


        Intent intent = getIntent();

        salutation = intent.getStringExtra("salutation");
        theVerdict = intent.getStringExtra("verdict");
        candidateName = intent.getStringExtra("candName");
        finalScore = intent.getIntExtra("score",0);

        txtSalute = (TextView)findViewById(R.id.tvWelcomeMsg);
        etFinalScore = (EditText) findViewById(R.id.etYourScore);
        etTheVerdict = (EditText) findViewById(R.id.etVerdict);

        txtSalute.setText(salutation);
        etFinalScore.setText(""+ finalScore);
        etTheVerdict.setText(theVerdict);
    }

    public void retake_app(View v){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
        System.exit(0);
    }

    public void mail_result(View v){

        String mailSubject = txtSalute.getText().toString();
        String mailBody ="Dear " + candidateName + "\n\n See details of your quiz below:\n\nYour score: " + finalScore+""+
                "\nVerdict: " + theVerdict;
        composeEmail(mailSubject, mailBody);

    }


    public void composeEmail( String subject, String  body){
        //ensures only email applications handle the sending
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager())!= null){

            startActivity(Intent.createChooser(intent, "Open With"));
        }
        else{
            Toast toast = Toast.makeText(this, "Package manager not found", Toast.LENGTH_SHORT);
            toast.show();
        }
    }



}
