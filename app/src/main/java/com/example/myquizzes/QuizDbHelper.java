package com.example.myquizzes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myquizzes.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyQuizzes.db";
    private static final int DATABASE_VERSION = 1;


    private SQLiteDatabase db;

    public QuizDbHelper( Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db =db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + "INTEGER PRIMARY KEY, " +
                QuestionsTable.COLUMN_QUESTION + "TEXT, " +
                QuestionsTable.COLUMN_ANSWER1 + "TEXT, " +
                QuestionsTable.COLUMN_ANSWER2 + "TEXT, " +
                QuestionsTable.COLUMN_ANSWER3 + "TEXT, " +
                QuestionsTable.COLUMN_ANSWER4 + "TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + "INTEGER" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    private void fillQuestionsTable(){
        Question q1 = new Question("A is correct" , "A", "B", "C", "D",1);
        addQuestion(q1);
        Question q2 = new Question("B is correct" , "A", "B", "C", "D",2);
        addQuestion(q2);
        Question q3 = new Question("C is correct" , "A", "B", "C", "D",3);
        addQuestion(q3);
        Question q4 = new Question("D is correct" , "A", "B", "C", "D",4);
        addQuestion(q4);
        Question q5 = new Question("A is correct again" , "A", "B", "C", "D",1);
        addQuestion(q5);

    }

    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_ANSWER1, question.getAnswer1());
        cv.put(QuestionsTable.COLUMN_ANSWER2, question.getAnswer2());
        cv.put(QuestionsTable.COLUMN_ANSWER3, question.getAnswer3());
        cv.put(QuestionsTable.COLUMN_ANSWER4, question.getAnswer4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer1((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER1))));
                question.setAnswer2((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER2))));
                question.setAnswer3((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER3))));
                question.setAnswer4((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER4))));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return  questionList;

    }
}
