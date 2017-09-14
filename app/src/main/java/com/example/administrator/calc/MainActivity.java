package com.example.administrator.calc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.calc.design.ICalc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ICalc{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CalcView(this);

    }

    /**
     * 괄호가 있는 경우 연산
     */
    @Override
    public double doParam(String paramSent){
        String sentence = paramSent;
        boolean FLAG = true;
        while(FLAG) {
            // 1. 받아온 식 전체를 분리한다
            String[] splitted = sentence.split("");
            List<String> original = new ArrayList<>();
            Collections.addAll(original, splitted);

            // 2. 왼쪽 괄호, 오른쪽 괄호를 전체 식에서 위치 값으로 저장한다.
            List<Integer> paramLeftList = new ArrayList<>();
            List<Integer> paramRightList = new ArrayList<>();
            for (int index = 0; index < original.size(); index++) {
                if (original.get(index).equals("(")) {
                    paramLeftList.add(index);
                    Log.e("들어감", "왼쪽 들어감");
                } else if (original.get(index).equals(")")) {
                    paramRightList.add(index);
                    Log.e("들어감", "오른쪽 들어감");
                } else {
                    Log.e("괄호 이외", original.get(index));
                }
            }

            // 3. 괄호가 없을 경우 or 괄호 계산을 마친 경우
            if(paramLeftList.size() == 0 || paramRightList.size() == 0){
                double result = calc(sentence);
                FLAG = false;
                return result;
            }

            // 4. 괄호가 있을 경우
            if (paramLeftList.size() != 0 && paramRightList.size() != 0) {
                // 4.1 괄호 안의 식을 분리한다
                List<String> subList = original.subList(paramLeftList.get(paramLeftList.size() - 1), paramRightList.get(0) + 1);
                // 4.2 calc()에 넣어주기 위해 문자열로 바꿔준다
                String innerSentence = "";
                for (int index = 1; index < subList.size() - 1; index++) {
                    innerSentence += subList.get(index);
                }

                // 4.3 괄호 안의 식 계산 완료
                double result = calc(innerSentence);

                // 4.4 괄호를 포함한 식을 원래 식에서 빼준다.
                String temp = "("+innerSentence+")";
                sentence = sentence.replace(temp, result + "");
            }
        }
        return 0;
    }

    /**
     * 수식을 넣어주면 연산자와 숫자를 분리해서 List 리턴
     */
    @Override
    public List<String> splitSentence(String sentence) {
        String[] splitList = sentence.split("(?<=[*/+-])|(?=[*/+-])");
        List<String> splitArray = new ArrayList<>();
        Collections.addAll(splitArray, splitList);
        return splitArray;
    }

    /**
     * List 값을 받아서 연산, 마지막에 결과값으로 double 한 개 리턴
     */
    @Override
    public double calc(String sentence) {

        // 분리해 낸 숫자, 연산자 리스트
        List<String> splitArray = splitSentence(sentence);

        outer:
        while (splitArray.size() != 1) {
            for (int index = 0; index < splitArray.size(); index++) {
                double a = 0;
                double b = 0;
                double c = 0;
                String operator = splitArray.get(index);
                if (operator.equals("*") || operator.equals("/")) {
                    a = Double.parseDouble(splitArray.get(index - 1));
                    b = Double.parseDouble(splitArray.get(index + 1));
                    if (operator.equals("*")) {
                        c = a * b;
                    } else if (operator.equals("/")) {
                        c = a / b;
                    }
                    Log.e("c", c + "");
                    splitArray.remove(index);
                    splitArray.remove(index);
                    splitArray.set(index - 1, c + "");
                    continue outer;
                }
            }

            for (int index = 0; index < splitArray.size(); index++) {
                double a = 0;
                double b = 0;
                double c = 0;
                String operator = splitArray.get(index);
                if (operator.equals("+") || operator.equals("-")) {
                    a = Double.parseDouble(splitArray.get(index - 1));
                    b = Double.parseDouble(splitArray.get(index + 1));
                    if (operator.equals("+")) {
                        c = a + b;
                    } else if (operator.equals("-")) {
                        c = a - b;
                    }
                    Log.e("c", c + "");
                    splitArray.remove(index);
                    splitArray.remove(index);
                    splitArray.set(index - 1, c + "");
                    continue outer;
                }
            }

        }
        double result = Double.parseDouble(splitArray.get(0));
        return result;
    }
}
