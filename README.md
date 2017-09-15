# 계산기
괄호처리 4칙연산, 애니메이션 적용 계산기

- 인터페이스 설계 : 계산기의 뷰, 프레젠터 영역을 인터페이스로 설계
- MVP 구조
    - 뷰 : 연산식을 입력받고 결과값 출력
    - 프레젠터 : 연산 알고리즘
- 사칙연산/괄호연산 알고리즘
- 애니메이션
    - ObjectAnimator
    - View의 속성으로부터 Dummy Button 값 설청
    
    
![sentence](https://github.com/qskeksq/Calculator/blob/master/pic/%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98_sentence.png)
![result](https://github.com/qskeksq/Calculator/blob/master/pic/%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98_result.png)
![anim]()


#### 연산식 분리
```java
String[] splitList = sentence.split("(?<=[*/+-])|(?=[*/+-])");
List<String> splitArray = new ArrayList<>();
Collections.addAll(splitArray, splitList);
```

#### 괄호 분리
``` java
// 괄호 분리 여산
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
```

#### 계산
```java
// 괄호 없는 연산식 계산
public double calc(String sentence) {

   // 분리해 낸 숫자, 연산자 리스트
   List<String> splitArray = splitSentence(sentence);

   outer:
   while (splitArray.size() != 1) {
        // 곱, 나눗셈 계산
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
       // 덧셈, 뺄셈 계산
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
```


#### 예외 처리

```java
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
```

#### 애니메이션 적용
```java
public void fly(Button button){
    // 날아가야 할 좌표
    float toX = space.getX();
    float toY = space.getY();

    // 1. 버튼 생성
    final Button obj = new Button(activity);

    // 2. 버튼의 초기 좌표
    float x = button.getX();
    float y = button.getY();

    // 3. 버튼의 위치 지정
    obj.setX(x);
    obj.setY(y);

    // 4. 버튼 속성 지정
    ConstraintLayout.LayoutParams cp = new ConstraintLayout.LayoutParams(btnOne.getWidth(),btnOne.getHeight());
    obj.setBackground(button.getBackground());
    obj.setTextColor(button.getCurrentTextColor());
    obj.setTypeface(Typeface.SANS_SERIF);
    obj.setText(button.getText());
    obj.setLayoutParams(cp);
    obj.setTextSize(30);

    // 5. 뷰에 추가
    layout.addView(obj);

    // 6. 애니메이션으로 날려주기
    ObjectAnimator transX = ObjectAnimator.ofFloat(
            obj, "x", toX
    );

    ObjectAnimator transY = ObjectAnimator.ofFloat(
            obj, "y", toY
    );

    AnimatorSet set = new AnimatorSet();
    set.playTogether(transX,transY);
    set.setInterpolator(new BounceInterpolator());
    set.setDuration(3000);
    set.addListener(new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            // 애니메이션이 끝나면 뷰에서 삭제
            layout.removeView(obj);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    });
    set.start();
}
```
