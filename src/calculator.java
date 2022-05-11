import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class calculator {
    public static void main(String[] args) {
        System.out.println();
        Scanner myScanner = new Scanner(in);
        String myInputString = myScanner.next();
        System.out.println(calc(myInputString));
    }

    public static String calc(String input){
        String[] mySignS = {"+","-","/","*"};
        int arabicResult = 0;
        String result = "";
        operaND firstSTR = new operaND();
        operaND secondSTR = new operaND();
        boolean isOperationSignFounded = false;

        for (String mySTR : mySignS) {

            if (input.indexOf(mySTR)>0) {
                    firstSTR.setValue(input.substring(0,input.indexOf(mySTR)).trim());
                    secondSTR.setValue(input.substring(input.indexOf(mySTR)+1).trim());
                    checkRomanian(firstSTR);
                    checkRomanian(secondSTR);
                    if (firstSTR.isRomanian != secondSTR.isRomanian)
                        {throw new NumberFormatException("--Type of both operand  must be equal--");}
                    else {
                        try {
                            if (firstSTR.isRomanian & secondSTR.isRomanian) {
                                firstSTR.arabicValue = toArabicInt(firstSTR.getValue());
                                secondSTR.arabicValue = toArabicInt(secondSTR.getValue());
                            } else {
                                firstSTR.arabicValue = Integer.parseInt(firstSTR.value);
                                secondSTR.arabicValue = Integer.parseInt(secondSTR.value);
                            }
                        } catch (NumberFormatException exception){
                            out.println("--Operands must be only integer--");
                        }
                        if ((firstSTR.arabicValue > 10) | (firstSTR.arabicValue <= 0) | (secondSTR.arabicValue > 10) | (secondSTR.arabicValue <= 0)){
                            throw new NumberFormatException("--Operand  must be in range from 1 to 10 --");
                        }
                    }
                switch (mySTR) {
                    case "+":
                        arabicResult = firstSTR.arabicValue + secondSTR.arabicValue;
                        break;
                    case "-":
                        arabicResult = firstSTR.arabicValue - secondSTR.arabicValue;
                        break;
                    case "/":
                        arabicResult = firstSTR.arabicValue / secondSTR.arabicValue;
                        break;
                    case "*":
                        arabicResult = firstSTR.arabicValue * secondSTR.arabicValue;
                        break;

                }
                if (firstSTR.isRomanian)  {
                    if (arabicResult > 0 ) {
                        result = toRomanianString(arabicResult);
                    } else {throw new NumberFormatException("--Uncorrect expression with romanian operands--");}
                } else
                {
                    result = String.valueOf(arabicResult);
                }
                isOperationSignFounded = true;
            }

        }
        if (!isOperationSignFounded) {throw new NumberFormatException("---Correct operation sign not found--");}
        return  result;
    }

    // diced if operand is romanian number
    public static void checkRomanian(operaND op){
        if ((!Character.isDigit(op.getValue().charAt(0))) & Character.isLetterOrDigit(op.getValue().charAt(0))){
            op.setRomanian();
        }
    }

    public static int[] numbersArray = {100, 90, 50, 40, 10, 9, 5, 4, 1};
    public static String[] lettersArray = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
    // convert romanian symbol number to digital format
    public static int toArabicInt(String roman){
        int result=0;
        int j = 0;
        int arabic;
        roman = roman.toUpperCase();
        while (j < roman.length()){
            char letter = roman.charAt(j);
            arabic = letToNumber(letter);
            if (arabic < 0)
                throw new NumberFormatException("--Illegal char \"" + letter + "\" in roman numeral--");
            j++;
            if (j == roman.length()){
                result += arabic;
            }
            else {
                int nextArabic =  letToNumber(roman.charAt(j));
                if (nextArabic > arabic){
                    result += (nextArabic - arabic);
                    j++;
                }
                else{
                    result +=arabic;
                }
            }
        }
        return result;
    }
    // convert from arabic number to romanian string number
    public static String toRomanianString(Integer arabic){
        String result = "";
        for (int j = 0; j < numbersArray.length; j++){
            while (arabic >= numbersArray[j]){
                result += lettersArray[j];
                arabic -= numbersArray[j];
            }
        }
        return result;
    }
    // convert romanian symbol to arabic number
    public static int letToNumber(char letter) {
        switch (letter) {
            case 'I':  return 1;
            case 'V':  return 5;
            case 'X':  return 10;
            case 'L':  return 50;
            case 'C':  return 100;
            default:   return -1;
        }
    }
}


class operaND{
    String value;
    boolean isRomanian;
    int arabicValue;
    void setValue(String val) {
        this.value = val;
    }
    String getValue(){
        return this.value;
    }
    void setRomanian() {
        this.isRomanian = true;
    }

    operaND (){
        this.isRomanian = false;
        this.arabicValue=0;
    }
}


