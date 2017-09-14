package com.example.administrator.calc.design;

import java.util.List;

/**
 * Created by Administrator on 2017-09-14.
 */

public interface ICalc {

    // 입력받은 문장 문자열, 연산자로 분리
    List<String> splitSentence(String sentence);

    // 분리한 식 계산
    double calc(String sentence);

//    굳이 프레젠터로 빼지 않아도 되는 것이, 입력받는 것까지는 뷰가 하는 일이기 때문.
//    String append(String sentence);

    // 괄호 계산
    double doParam(String paramSent);

}
