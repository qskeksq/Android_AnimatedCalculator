package com.example.administrator.calc.design;

/**
 * Created by Administrator on 2017-09-14.
 */


/**
 * 뷰 인터페이스
 */
public interface IView {

    // 입력받은 값 리턴
    String getSentence();

    // 결과값 뿌려줌
    void showResult(double result);

    // 문자열에 더해주기
    void append(String factor);

    // 연산식 초기화
    void clearSentenceWindow();

    // 결과창 초기화
    void clearResultWindow();

    // 삭제
    void delete();

}
