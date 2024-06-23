package com.dbdx.demo1;

import java.util.Scanner;

public class helloworld {
    public static void main ( String[] args ) {
        Scanner sc = new Scanner ( System.in );
        int num1 = sc.nextInt ();
        int num2 = sc.nextInt ();
       // boolean b = (num2 == 6) || (((num2 + num1) % 6) == 0);
       boolean ans =( (num1 == 6) || (num2 == 6) || ( (num2 + num1) % 6 == 0 ) );
        System.out.println ( ans );

    }
}
