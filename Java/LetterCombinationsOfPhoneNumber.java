/*
* LeetCode #17. Letter Combinations of a Phone Number
* happygirlzt
* 2018/7/24
* dfs
*/
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.lang.StringBuilder;

public class LetterCombinationsOfPhoneNumber {
    public static List<String> letterCombinations(String digits) {
        // corner case
        if (digits == null || digits.length() == 0) return null;

        List<String> res = new LinkedList<>();

        HashMap<Integer, String> map = new HashMap<>();

        map.put(0, "");
        map.put(1, "");
        map.put(2, "abc");
        map.put(3, "def");
        map.put(4, "ghi");
        map.put(5, "jkl");
        map.put(6, "mno");
        map.put(7, "pqrs");
        map.put(8, "tuv");
        map.put(9, "wxyz");

        StringBuilder sb = new StringBuilder();
        // dfsHelper()
        dfsHelper(digits, 0, map, sb, res);
        return res;
    }

    public static void dfsHelper(String s, int index, HashMap<Integer, String> map, StringBuilder sb, List<String> res) {
        // base case
        if (index == s.length()) {
            res.add(sb.toString());
            return;
        }

        // recursive case
        char ch = s.charAt(index);
        String values = map.get(ch - '0');
        for (char c : values.toCharArray()) {
            sb.append(c);
            dfsHelper(s, index + 1, map, sb, res);
            // recover
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public List<String> letterCombinations2(String digits) {
        Map<String, String> map = new HashMap<>();
        map.put("2", "abc");
        map.put("3", "def");
        map.put("4", "ghi");
        map.put("5", "jkl");
        map.put("6", "mno");
        map.put("7", "pqrs");
        map.put("8", "tuv");
        map.put("9", "wxyz");
        List<String> res = new ArrayList<>();
        if (digits.length() == 0) return res;
        helper(map, res, "", digits);
        return res;
    }

    private void helper(Map<String, String> map, List<String> res, String cur, String rest) {
        if (rest.length() == 0) {
            res.add(new String(cur));
            return;
        }

        String s = rest.substring(0, 1);
        String cand = map.get(s);
        for (int i = 0; i < cand.length(); i++) {
            String c = cand.substring(i, i + 1);
            cur += c;
            helper(map, res, cur, rest.substring(1));
            cur = cur.substring(0, cur.length() - 1);
        }
    }

    // Updated on 9 Feb 2019
    public List<String> letterCombinations3(String digits) {
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> res = new ArrayList<>();
        if (digits.length() == 0) return res;
        helper(digits, 0, mapping, res, new StringBuilder());
        return res;
    }
    
    private void helper(String digits, int index, String[] mapping, List<String> res, StringBuilder sb) {
        if (index == digits.length()) {
            res.add(sb.toString());
            return;
        } else {
            String digit = digits.substring(index, index + 1);
            for (char c : mapping[Integer.parseInt(digit)].toCharArray()) {
                sb.append(c);
                helper(digits, index + 1, mapping, res, sb);
                sb.setLength(sb.length() - 1);
            }
        }
    }
}
