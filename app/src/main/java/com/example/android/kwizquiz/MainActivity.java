package com.example.android.kwizquiz;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    String candidateName;
    boolean isNullCandidateName = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textChangeListening();
    }

    public void textChangeListening(){
    final    EditText edittext =(EditText)findViewById(R.id.candidate_name);
        edittext.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                { //do your work here }

                    candidateName = edittext.getText().toString();
                    isNullCandidateName = false;
                }
                else{
                    isNullCandidateName = true;
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static String toTitleCase(String str) {
        if (str == null) {
            return null;
        }
        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i=0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }
    public void beginQuiz(View v){
        EditText candidateNameTextView = (EditText)findViewById(R.id.candidate_name);
        candidateName = candidateNameTextView.getText().toString();
        candidateName = toTitleCase(candidateName);


        if (isNullCandidateName==false && !candidateName.isEmpty() && !candidateName.trim().isEmpty() ){
            Intent intent = new Intent(this,AnswerQuizActivity.class);
            intent.putExtra("candName",candidateName.trim());
            Log.d("beginQuiz", "If condition passed: ");
            startActivity(intent);
        }
        else {
            Log.d("beginQuiz", "Else condition passed: ");
            Toast toast = Toast.makeText(this, "Candidate name can not be blank", Toast.LENGTH_SHORT);
            toast.show();
        }



    }
}
