/**
 * Created on 3 Nov 2018 by happygirlzt
 *
 * LeetCode #LeetCode 127 Word Ladder
 *
 */

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, Set<String> wordList) {
        if(beginWord.equals(endWord)) return 1;
        Set<String> set = new HashSet<>(wordList);
        Queue<String> q = new LinkedList<>();

        int len = 2;
        q.offer(beginWord);

        while(!q.isEmpty()){
            int size = q.size();

            for(int i = 0; i < size; i++){
                String cur = q.poll();

                for(int j = 0; j < cur.length(); j++){
                    StringBuilder sb = new StringBuilder(cur);

                    for(char c = 'a'; c <= 'z'; c++){
                        sb.setCharAt(j, c);
                        String tmp = sb.toString();

                        if(set.contains(tmp)){
                            if(tmp.equals(endWord)) return len;

                            q.offer(tmp);
                            set.remove(tmp);
                        }
                    }
                }
            }
            len++;
        }
        return 0;
    }

    // Updated on 25 Jan 2019
    // Solution 0
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> unvisited = new HashSet(wordList);
        Queue<String> q = new LinkedList<>();

        if (!unvisited.contains(endWord)) return 0;
        q.offer(beginWord);
        unvisited.remove(beginWord);

        int distance = 2;
        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                StringBuilder curWord = new StringBuilder(q.poll());
                for (int j = 0; j < curWord.length(); j++) {
                    char tmp = curWord.charAt(j);
                    for (int k = 0; k < 26; k++) {
                        curWord.setCharAt(j, (char) ('a' + k));

                        if (unvisited.contains(curWord.toString())) {
                            if (curWord.toString().equals(endWord)) {
                                return distance;
                            } else {
                                unvisited.remove(curWord.toString());
                                //visited.add(curWord.toString());
                                q.offer(curWord.toString());
                            }
                        }
                        curWord.setCharAt(j, tmp);
                    }
                }
            }

            distance++;
        }

        return 0;
    }

    // Remember the position where the word comes from
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> unvisited = new HashSet(wordList);
        Map<String, Integer> fromWhere = new HashMap<>();
        Queue<String> q = new LinkedList<>();

        if (!unvisited.contains(endWord)) return 0;
        q.offer(beginWord);
        unvisited.remove(beginWord);
        fromWhere.put(beginWord, -1);

        int distance = 2;
        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                StringBuilder curWord = new StringBuilder(q.poll());
                for (int j = 0; j < curWord.length(); j++) {
                    if (fromWhere.get(curWord.toString()) == j) continue;

                    char tmp = curWord.charAt(j);
                    for (int k = 0; k < 26; k++) {
                        curWord.setCharAt(j, (char) ('a' + k));

                        if (unvisited.contains(curWord.toString())) {
                            if (curWord.toString().equals(endWord)) {
                                return distance;
                            } else {
                                unvisited.remove(curWord.toString());
                                //visited.add(curWord.toString());
                                q.offer(curWord.toString());
                                fromWhere.put(curWord.toString(), j);
                            }
                        }
                        curWord.setCharAt(j, tmp);
                    }
                }

                fromWhere.remove(curWord.toString());
            }

            distance++;
        }

        return 0;
    }

    // Solutioon 3:
    // Bidirectional BFS
    public int ladderLength(String beginWord, String endWord, List<String> wordDict) {
        Set<String> dict = new HashSet(wordDict);
        if (!dict.contains(endWord)) return 0;

        int level = 0;
        int wordLen = beginWord.length();

        Set<String> front = new HashSet<>();
        Set<String> back = new HashSet<>();

        while (!front.isEmpty() && !back.isEmpty()) {
            level++;

            if (front.size() > back.size()) {
                Set<String> tmp = front;
                front = back;
                back = tmp;
            }

            Set<String> newFront = new HashSet<>();

            for (String word: front) {
                StringBuilder sb = new StringBuilder(word);

                for (int pos = 0; pos < wordLen; pos++) {
                    char original = sb.charAt(pos);

                    for (char c = 'a'; c <= 'z'; c++) {
                        sb.setCharAt(pos, c);
                        if (back.contains(sb.toString())) {
                            if (sb.toString().equals(endWord)) {
                                return level + 1;
                            }
                        }
                        dict.remove(sb.toString());

                        newFront.add(sb.toString());
                    }

                    sb.setCharAt(pos, c);
                }
            }

            Set<String> tmp = newFront;
            newFront = front;
            front = tmp;
        }

        return 0;
    }

    // Updated on 30 Jan 2019
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null || wordList.size() == 0) return 0;
        Set<String> wordSet = new HashSet(wordList);

        if (!wordSet.contains(endWord)) return 0;
        StringBuilder begin = new StringBuilder(beginWord);
        Queue<StringBuilder> q = new LinkedList<>();
        //Queue<String> q = new LinkedList<>();
        q.add(begin);
        //q.add(beginWord);
        if (wordSet.contains(beginWord)) wordSet.remove(beginWord);

        int level = 1;

        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                //StringBuilder cur = new StringBuilder(q.poll());
                StringBuilder cur = new StringBuilder(q.poll());

                for (int k = 0; k < cur.length(); k++) {
                    char tmp = cur.charAt(k);

                    for (int j = 0; j < 26; j++) {
                        cur.setCharAt(k, (char) (j + 'a'));

                        if (wordSet.contains(cur.toString())) {
                            if (cur.toString().equals(endWord)) return level + 1;
                            q.offer(new StringBuilder(cur));
                            // 这个地方，不能直接q.offer(cur)
                            // 如果直接这样加，就是传进去cur的引用，cur的所有变化
                            // 都会引起q中值的变化！！！！！
                            wordSet.remove(cur.toString());
                        }

                       cur.setCharAt(k, tmp);
                    }
                }
            }
            level++;
        }

        return 0;
    }
}
