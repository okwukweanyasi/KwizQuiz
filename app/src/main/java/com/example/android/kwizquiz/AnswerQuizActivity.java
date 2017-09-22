package com.example.android.kwizquiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by KWIKWI on 6/16/2017.
 */

public class AnswerQuizActivity extends AppCompatActivity{

    public int totalScore=0;
    String candName;
    int dispScore;
    String verdict;
    boolean assessmentComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_quiz);
        Intent intent = getIntent();
        TextView txtCandName = (TextView) findViewById(R.id.display_cand_name);
        candName = intent.getStringExtra("candName");
        txtCandName.setText("Welcome " + candName + ", you may begin your test.");
        displayResult();

    }

    public void displayResult(){
        final Button submitQuiz = (Button) findViewById(R.id.btnSubmit);
        submitQuiz.setOnClickListener(new View.OnClickListener(){

                                          @Override
                                          public void onClick (View v){
                                              if (assessmentComplete == false){
                                                  Log.i("assessment complete: " , "" + assessmentComplete);

                                                  computeTotalScore();

                                                  Double computedScore = (double)totalScore;
                                                  computedScore = computedScore/6.0 *100.0;
                                                  dispScore = (int) Math.rint(computedScore);

                                                  if (totalScore>= 4){
                                                      verdict = "You have passed!";
                                                      Toast toast = Toast.makeText(getApplicationContext(), candName+
                                                              ", you have passed the test wit a score of "+ dispScore+ "%", Toast.LENGTH_LONG);
                                                      toast.show();
                                                  }
                                                    else{
                                                      verdict = "You have failed!";
                                                      Toast toast = Toast.makeText(getApplicationContext(), candName+
                                                              ", you have failed the test wit a score of "+ dispScore+ "%", Toast.LENGTH_LONG);
                                                      toast.show();

                                                  }
                                                  //submitQuiz.setEnabled(false);

                                                  submitQuiz.setText("View Result");
                                                  assessmentComplete = true;
                                              }
                                              else {

                                                  Log.i("assessment complete: " , "" + assessmentComplete);

                                                  ViewScoreSheet(verdict);
                                              }
                                          }
                                      }
        );
    }



    public int computeTotalScore(){

        computeChkBx1Scores();
        computeRbnScores(R.id.rbnQ2Opt3,2);
        computeEt3Scores();
        computeChkBx4Scores();
        computeRbnScores(R.id.rbnQ5Opt5,5);
        computeRbnScores(R.id.rbnQ6Opt2,6);
        //totalScore = 0;

        for (int i=0; i<scores.length; i++){
            totalScore = totalScore + scores[i];
        }
        return totalScore;
    }

    public void WrongCbxColorRed(int resource){
        CheckBox wrongChoice = (CheckBox) findViewById(resource);
        if (wrongChoice.isChecked() == true){
            wrongChoice.setTextColor(Color.RED);
        }
    }

    public void WrongRbnColorRed(int resource){
        RadioButton wrongChoice = (RadioButton) findViewById(resource);
        if (wrongChoice.isChecked() == true){
            wrongChoice.setTextColor(Color.RED);
        }
    }


    public void computeChkBx1Scores(){
        CheckBox q1cbx1 = (CheckBox) findViewById(R.id.Q1checkBox1);
        CheckBox q1cbx3 = (CheckBox) findViewById(R.id.Q1checkBox3);

        if (q1cbx1.isChecked()==true && q1cbx3.isChecked() == true){
            scores[0] = 1;
        }
        else
        {
            scores[0] = 0;
        }
        q1cbx1.setTextColor(Color.GREEN);
        q1cbx3.setTextColor(Color.GREEN);
        WrongCbxColorRed(R.id.Q1checkBox2);

    }
    public void computeChkBx4Scores(){
        CheckBox q4cbx2 = (CheckBox) findViewById(R.id.Q4checkBox2);
        if (q4cbx2.isChecked() == true ){
            scores[3] = 1;
        }
        else
        {
            scores[3] = 0;
        }
        q4cbx2.setTextColor(Color.GREEN);

        WrongCbxColorRed(R.id.Q4checkBox1);
        WrongCbxColorRed(R.id.Q4checkBox3);
        WrongCbxColorRed(R.id.Q4checkBox4);
        WrongCbxColorRed(R.id.Q4checkBox5);

    }

    public void computeEt3Scores(){
        String ans;
        EditText q3Et = (EditText) findViewById(R.id.Q3EtAnswer);
        TextView correctAns = (TextView) findViewById(R.id.Q3EtCorrectAnswer);
        ans = q3Et.getText().toString();

        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT); // Width , height



        if (ans.equalsIgnoreCase(getString(R.string.q3_ans))){
            scores[2] = 1;
            q3Et.setTextColor(Color.GREEN);
        }
        else{
            scores[2]=0;
            q3Et.setTextColor(Color.RED);
            correctAns.setText("Correct Answer is \"Cut\"");
            correctAns.setTextColor(Color.GREEN);
            correctAns.setVisibility(View.VISIBLE);
           //q3Et.setLayoutParams(lparams);
            /*q3Et.getText().length();
            q3Et.setEms(q3Et.getText().length());
            Log.i("Width is: ", "" + q3Et.getWidth());
            Log.i("length is: ", "" + q3Et.getText().length());
*/
        }
    }

    public void computeRbnScores(int rbnResource, int pos){
        RadioButton rbnAns = (RadioButton) findViewById(rbnResource);

        //Set score 2
        if (pos == 2){
            if ( rbnAns.isChecked()==true){
                scores[1] = 1;
            }
            else if (rbnAns.isChecked()!= false){
                scores[1]=0;

            }
            WrongRbnColorRed(R.id.rbnQ2Opt1);
            WrongRbnColorRed(R.id.rbnQ2Opt2);
            WrongRbnColorRed(R.id.rbnQ2Opt4);
            rbnAns.setTextColor(Color.GREEN);
        }



        //Set score 5
        if (pos == 5){
            if (rbnAns.isChecked()==true){
                scores[4] = 1;
            }
            else if ( rbnAns.isChecked()!= false){
                scores[4]=0;

            }
            WrongRbnColorRed(R.id.rbnQ5Opt1);
            WrongRbnColorRed(R.id.rbnQ5Opt2);
            WrongRbnColorRed(R.id.rbnQ5Opt3);
            WrongRbnColorRed(R.id.rbnQ5Opt4);
            rbnAns.setTextColor(Color.GREEN);
        }



        //Set score 6
        if (pos == 6){
            if (rbnAns.isChecked()==true){
                scores[5] = 1;
            }
            else if (rbnAns.isChecked()!= false){
                scores[5]=0;
            }
            rbnAns.setTextColor(Color.GREEN);
            WrongRbnColorRed(R.id.rbnQ6Opt1);
            WrongRbnColorRed(R.id.rbnQ6Opt3);
            WrongRbnColorRed(R.id.rbnQ6Opt4);
        }

    }

    public int scores[] = {0,0,0,0,0,0};

    public void ViewScoreSheet(String resVerdict){
        Intent intent = new Intent(this,DisplayResult.class);
        intent.putExtra("salutation","Quiz result for: " + candName);
        intent.putExtra("score",dispScore);
        intent.putExtra("candName", candName);
        intent.putExtra("verdict", resVerdict);
        Log.i("verdict", resVerdict);
        startActivity(intent);
    }


}
