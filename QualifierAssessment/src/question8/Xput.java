package question8;
import java.util.*;
public class Xput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int n = s.length();
        if(n<2 || !(s.matches("[a-zA-Z ]+")));
        int mid = n/2;
        int sum1=0;
        int sum2=0;
        for(int i=0;i<mid;i++){
            char a = s.charAt(i);
            char b = s.charAt(n-i-1);
            sum1+=a-96;
            sum2+=b-96;
        }
        if(sum1==sum2){
            System.out.println("Balanced");
        }
        else{
            System.out.println("Not balanced");
        }
        sc.close();
    }
}
