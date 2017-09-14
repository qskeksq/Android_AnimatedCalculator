package com.example.administrator.calc;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.calc.design.ICalc;
import com.example.administrator.calc.design.IView;

/**
 * Created by Administrator on 2017-09-14.
 */

/**
 * 뷰 영역
 */
public class CalcView implements IView {

    /**
     * 위젯, 자원
     */
    private EditText resultWindow,sentenceWindow;
    private Button btnAC,btnLeftParan,btnRightParan,btnDelete,btnMulti,btnDot,btnEqual,btnDiv,btnSubs;
    private Button btnSeven,btnEight,btnNine,btnAdd,btnFour,btnFive,btnSix,btnOne,btnTwo,btnThree,btnZero;

    private ICalc calc;
    private Activity activity;

    public CalcView(ICalc calc) {
        this.calc = calc;
        this.activity = (Activity) calc;
        initView();
        setListener();
    }

    /**
     * 위젯 초기화
     */
    private void initView() {
        btnAC = activity.findViewById(R.id.btnAC);
        btnLeftParan = activity.findViewById(R.id.btnLeftParan);
        btnRightParan = activity.findViewById(R.id.btnRightParan);
        btnDelete = activity.findViewById(R.id.btnDelete);
        btnSeven = activity.findViewById(R.id.btnSeven);
        btnEight = activity.findViewById(R.id.btnEight);
        btnNine = activity.findViewById(R.id.btnNine);
        btnAdd = activity.findViewById(R.id.btnAdd);
        btnFour = activity.findViewById(R.id.btnFour);
        btnFive = activity.findViewById(R.id.btnFive);
        btnSix = activity.findViewById(R.id.btnSix);
        btnSubs = activity.findViewById(R.id.btnSubs);
        btnOne = activity.findViewById(R.id.btnOne);
        btnTwo = activity.findViewById(R.id.btnTwo);
        btnThree = activity.findViewById(R.id.btnThree);
        btnMulti = activity.findViewById(R.id.btnMulti);
        btnZero = activity.findViewById(R.id.btnZero);
        btnDot = activity.findViewById(R.id.btnDot);
        btnEqual = activity.findViewById(R.id.btnEqual);
        btnDiv = activity.findViewById(R.id.btnDiv);
        resultWindow = activity.findViewById(R.id.resultWindow);
        sentenceWindow = activity.findViewById(R.id.sentenceWindow);
    }

    private void setListener(){
        // 기타
        btnAC.setOnClickListener(etcListener);
        btnDelete.setOnClickListener(etcListener);
        btnEqual.setOnClickListener(etcListener);

        // 연산자
        btnAdd.setOnClickListener(operatorListener);
        btnSubs.setOnClickListener(operatorListener);
        btnMulti.setOnClickListener(operatorListener);
        btnDiv.setOnClickListener(operatorListener);
        btnDot.setOnClickListener(operatorListener);
        btnLeftParan.setOnClickListener(operatorListener);
        btnRightParan.setOnClickListener(operatorListener);

        // 숫자
        btnNine.setOnClickListener(numberListener);
        btnEight.setOnClickListener(numberListener);
        btnSeven.setOnClickListener(numberListener);
        btnSix.setOnClickListener(numberListener);
        btnFive.setOnClickListener(numberListener);
        btnFour.setOnClickListener(numberListener);
        btnThree.setOnClickListener(numberListener);
        btnTwo.setOnClickListener(numberListener);
        btnOne.setOnClickListener(numberListener);
        btnZero.setOnClickListener(numberListener);
    }

   
    // 숫자 관련 리스너
    View.OnClickListener numberListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnZero: append("0"); break;
                case R.id.btnOne:  append("1"); break;
                case R.id.btnTwo:  append("2"); break;
                case R.id.btnThree:append("3"); break;
                case R.id.btnFour: append("4"); break;
                case R.id.btnFive: append("5"); break;
                case R.id.btnSix:  append("6"); break;
                case R.id.btnSeven:append("7"); break;
                case R.id.btnEight:append("8"); break;
                case R.id.btnNine: append("9"); break;
            }
        }
    };
    
    // 연산자 관련 리스너
    View.OnClickListener operatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnAdd:   append("+"); break;
                case R.id.btnSubs:  append("-"); break;
                case R.id.btnMulti: append("*"); break;
                case R.id.btnDiv:   append("/"); break;
                case R.id.btnDot:        append("."); break;
                case R.id.btnLeftParan:  append("("); break;
                case R.id.btnRightParan: append(")"); break;
            }
        }
    };
    
    // AC, 괄호, 삭제, 점, eqaul 리스너
    View.OnClickListener etcListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnEqual:
                    // 1. 결과값 리턴
                    double result = calc.doParam(getSentence());
                    // 2. resultWindow 에 결과값 보여줌
                    showResult(result);
                    // 3. sentenceWindow 지워줌
                    clearSentenceWindow();
                    break;
                case R.id.btnAC:
                    clearSentenceWindow();
                    clearResultWindow();
                    break;
                case R.id.btnDelete:
                    delete();
                    break;
            }
        }
    };
    

    /**
     * @return 입력받은 수, 연산자 문자열로 리턴
     */
    @Override
    public String getSentence() {
        String result = sentenceWindow.getText().toString();
        return result;
    }

    /**
     * 결과값 resultWindow 에 보여줌
     */
    @Override
    public void showResult(double result) {
        resultWindow.setText(result+"");
    }

    /**
     * 숫자, 연산자 입력/입력 받는 값 예외 처리
     * TODO 프레젠터로 뺄 것인지 고민해보자
     */
    @Override
    public void append(String factor) {
        int size = sentenceWindow.getText().toString().length();

        // 첫 값으로 연산자 혹은 0 입력할 경우 입력 불가
        if(sentenceWindow.getText().toString().equals("")){
            if(factor.equals("+") || factor.equals("-") || factor.equals("/") || factor.equals("*") || factor.equals(".")){
                Toast.makeText(activity, "첫 값으로 연산자 입력 불가", Toast.LENGTH_SHORT).show();
                return;
            }
            if(factor.equals("0")){
                Toast.makeText(activity, "처음에 0을 입력할 수 없습니다", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(size != 0){
            // 마지막으로 입력된 문자
            String lastChar = sentenceWindow.getText().toString().charAt(size-1)+"";
            // 연산자를 연속으로 입력할 경우 입력 불가
            if(lastChar.equals("+")||lastChar.equals("-")||lastChar.equals("/")||lastChar.equals("*")){
                if(factor.equals("+")||factor.equals("-")||factor.equals("/")||factor.equals("*")||factor.equals(".")){
                    Toast.makeText(activity, "연속으로 연산자 입력 불가", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            // 숫자 다음에 바로 괄호 입력 불가
            for(int i=0; i<10; i++){
                if(lastChar.equals(i+"")){
                    if(factor.equals("(")){
                        Toast.makeText(activity, "숫자 다음에 바로 괄호 입력 불가", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        }
        sentenceWindow.append(factor);
    }


    /**
     * 잘못 입력시 제거
     */
    @Override
    public void delete() {
        String sentence = getSentence();
        if(sentence.length() == 0){
            Toast.makeText(activity, "지울 내용이 없습니다", Toast.LENGTH_SHORT).show();
            return;
        } else if(sentence.length() == 1){
            clearSentenceWindow();
        } else if(sentence.length() > 1) {
            String newSentence = sentence.substring(0, sentence.length()-1);
            sentenceWindow.setText(newSentence);
        }
    }

    /**
     * 입력창 초기화
     */
    @Override
    public void clearSentenceWindow() {
        sentenceWindow.setText("");
    }

    /**
     * 결과창 초기화
     */
    @Override
    public void clearResultWindow() {
        resultWindow.setText("");
    }

}
