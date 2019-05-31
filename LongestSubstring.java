/**
 * Takes a string s and analyzes it to find the largest possible substring of string s such that every character in s appears no less than k times. Below are several test cases as well as the expected results.
"aaabb"
3
Expected Result: 3
"ababbc"
2
Expected Result: 5
"ababacb"
3
Expected Result: 0
"bbaaacbd"
3
Expected Result: 3
"aacbbbdc"
2
Expected Result: 3
"aaabbbacccbdc"
4
Expected Result: 0
"aaabbbcdefcdefgggggggggggggggcde"
3
Expected Result: 15
"abcdedghijklmnopqrstuvwxyz"
1
Expected Result: 26
**/

class Solution {
    
    public int longestIndex = Integer.MIN_VALUE;
    public int maxRepetitions = 0;
    public int[] count = new int[26];
    
    public int longestSubstring(String s, int k) {
        char[] charArray = s.toCharArray();
        int[] count = new int[26];
        StringBuilder sb = new StringBuilder();
        maxRepetitions = k;
        
        count = countLetters(charArray);
        int lastIndex = s.lastIndexOf("" + (char)(longestIndex + 'a'));
        
        sb.append(analyzeString(charArray, lastIndex));
        
        sb = new StringBuilder(splitResult(sb));
        
        if (sb.length() < s.length()) {
            return longestSubstring(sb.toString(), maxRepetitions);
        }
        
        if (sb.length() >= maxRepetitions) {
            return sb.length();
        } else {
            return 0;
        }
    }
    
    /**
     * Checks to see if the letters from the original string have k repetitions and compares the length of the resulting 
     * string against the largest tally. If the character and resulting string fall within the appropriate parameters, it 
     * appends the character onto the end of the resulting string.
    **/
    public String analyzeString(char[] charArray, int lastIndex) {
        StringBuilder sb = new StringBuilder();
        
        for (char a : charArray) {
            if (count[a - 'a'] < maxRepetitions && sb.length() > 0 && sb.length() > lastIndex) {
                break;
            }
                
            sb.append(a);
        }
        
        return sb.toString();
    }
    
    /**
     * Tallies a count of all the letters in the current string which has been broken up into a char array.
    **/
    public int[] countLetters(char[] array) {
        count = new int[26];
        for(int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        
        for (char a : array) {
            count[a - 'a']++;
            if (count[a - 'a'] > longestIndex) {
                longestIndex = a - 'a';
            }
        }
        
        return count;
    }
    
    /**
     * Splits the resulting substring derived from the original and returns the longest substring.
    **/
    public String splitResult(StringBuilder sb) {
        count = countLetters(sb.toString().toCharArray());
        String[] strs = splitRemainingString(sb.toString(), 0);
        int maxIndex = findLargestSubstring(strs);

        if (maxIndex == -1) {
            return sb.toString();
        } else if (maxIndex != -1 && strs[maxIndex].length() == 1) {
            return "";
        } else {
            return strs[maxIndex];
        }
    }
    
    /**
     * Recursively splits the resulting substring that was derived from the original string. The resulting array of 
     * strings is then returned to the parent function.
    **/
    public String[] splitRemainingString(String s, int index) {
        if (index >= s.length()) {
            return null;
        }
        
        String[] strs = null;
        
        if (count[s.charAt(index) - 'a'] < maxRepetitions) {
            strs = s.split("" + s.charAt(index));
            return strs;
        } else {
            index++;
            return splitRemainingString(s, index);
        }
    }
    
    /**
     * Finds the largest substring derived by splitting the original string and returns the index of the longest string.
    **/
    public int findLargestSubstring(String[] strs) {
        int maxIndex = -1, max = Integer.MIN_VALUE;
        
        if (strs != null) {
            for (int i = 0; i < strs.length; i++) {
                if (strs[i].length() > max) {
                    max = strs[i].length();
                    maxIndex = i;
                }
            }
        }
        
        return maxIndex;
    }
}