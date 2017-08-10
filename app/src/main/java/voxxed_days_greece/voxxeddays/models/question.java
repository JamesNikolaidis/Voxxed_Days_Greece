package voxxed_days_greece.voxxeddays.models;

/**
 * Created by James Nikolaidis on 8/5/2017.
 */

public class question {

    public String mQuestion =null, mQuestionTime=null,mSpeach=null;

    public question(){
        this.mQuestion="";
        this.mQuestionTime = "";
        mSpeach = "";
    }

    public question (String question, String QuestionTime,String Speach){
        this.mQuestion = question;
        this.mQuestionTime = QuestionTime;
        this.mSpeach = Speach;
    }


    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public String getmQuestionTime() {
        return mQuestionTime;
    }

    public void setmQuestionTime(String mQuestionTime) {
        this.mQuestionTime = mQuestionTime;
    }
}
