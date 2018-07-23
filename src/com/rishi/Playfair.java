package com.rishi;
import java.util.Scanner;

public class Playfair
{
    private static String key       = new String();
    private static String new_key            = new String();
    private static char   matrix[][] = new char[5][5];

    public static void checkKey(String key)
    {
        String new_key1 = new String();
        boolean flag = false;
        new_key1 = new_key1 + key.charAt(0);
        for (int i = 1; i < key.length(); i++)
        {
            for (int j = 0; j < new_key1.length(); j++)
            {
                if (key.charAt(i) == new_key1.charAt(j))
                {
                    flag = true;
                }
            }
            if (flag == false)
                new_key1 = new_key1 + key.charAt(i);
            flag = false;
        }
        generateKeys(new_key1);
    }

    public static void generateKeys(String key)
    {
        boolean flag = true;
        char letter;
        new_key = key;
        for (int i = 0; i < 26; i++)
        {
            letter = (char) (i + 97);
            if (letter == 'j')
                continue;
            for (int j = 0; j < key.length(); j++)
            {
                if (letter == key.charAt(j))
                {
                    flag = false;
                    break;
                }
            }
            if (flag)
                new_key = new_key + letter;
            flag = true;
        }
        System.out.println(new_key);
        matrix();
    }

    private static void matrix()
    {
        int count = 0;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                matrix[i][j] = new_key.charAt(count);
                System.out.print(matrix[i][j] + " ");
                count++;
            }
            System.out.println();
        }
    }

    private static String newText(String old)
    {
        int i = 0;
        int len = 0;
        String text = new String();
        len = old.length();
        for (int tmp = 0; tmp < len; tmp++)
        {
            if (old.charAt(tmp) == 'j')
            {
                text = text + 'i';
            }
            else
                text = text + old.charAt(tmp);
        }
        len = text.length();
        for (i = 0; i < len; i = i + 2)
        {
            if (text.charAt(i + 1) == text.charAt(i))  //if same char in a word add 'x' in the middle
            {
                text = text.substring(0, i + 1) + 'x' + text.substring(i + 1);
            }
        }
        return text;
    }

    private static String[] divideIntoTwo(String new_string)
    {
        String Original = newText(new_string); // evaluating the source message
        int size = Original.length();

        String x[] = new String[size / 2];
        int counter = 0;
        for (int i = 0; i < size / 2; i++)
        {
            x[i] = Original.substring(counter, counter + 2);
            counter = counter + 2;
        }
        return x;
    }

    public static int[] getPosition(char letter)
    {
        int[] key = new int[2];
        if (letter == 'j')
            letter = 'i';
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (matrix[i][j] == letter)
                {
                    key[0] = i;
                    key[1] = j;
                    break;
                }
            }
        }
        return key;
    }

    public static String encryptMessage(String Source)
    {
        String pair[] = divideIntoTwo(Source);  // seperating the source into pairs
        String Code = new String();
        char first;
        char second;
        int pos1[] = new int[2];     //row and column for first letter
        int pos2[] = new int[2];    // row and column for second letter
        for (int i = 0; i < pair.length; i++)
        {
            first = pair[i].charAt(0);
            second = pair[i].charAt(1);
            pos1 = getPosition(first);
            pos2 = getPosition(second);
            if (pos1[0] == pos2[0])   // checking same row
            {
                if (pos1[1] < 4)
                    pos1[1]++;
                else
                    pos1[1] = 0;
                if (pos2[1] < 4)
                    pos2[1]++;
                else
                    pos2[1] = 0;
            }
            else if (pos1[1] == pos2[1]) //checking same column
            {
                if (pos1[0] < 4)
                    pos1[0]++;
                else
                    pos1[0] = 0;
                if (pos2[0] < 4)
                    pos2[0]++;
                else
                    pos2[0] = 0;
            }
            else
            {
                int temp = pos1[1];
                pos1[1] = pos2[1];
                pos2[1] = temp;
            }
            Code = Code + matrix[pos1[0]][pos1[1]]
                    + matrix[pos2[0]][pos2[1]];
        }
        return Code;
    }

    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);
        System.out  .println("Enter word to encrypt: ");
        String a = sc.nextLine();
        String key_input=a.replaceAll("\\s+","");  //remove all the spaces

        System.out.println("Enter a keyword:");
        String keyword = sc.next();
        checkKey(keyword);


        if (key_input.length() % 2 == 0)
        {
            System.out.println("Encryption: " + encryptMessage(key_input));
        }
        else
        {
            key_input=key_input+'x';
            System.out.println("Encryption: " + encryptMessage(key_input));
        }

    }
}