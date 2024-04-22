/**I, Xuan Huy Pham, 000899551, certify that this material is my original work. No other person's work has been used without suitable acknowledgment and I have not made my work available to anyone else.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Discussion of the results:
 * 1. ArrayList method
 * the ArrayList contains method performs a linear search through the list
 * which results in 0(n), with n is the size of the dictionary, time complexity
 * it needs to iterate through the entire dictionary for each word
 * --> performs the slowest because
 *
 * 2. SimpleHashSet
 * this method doesn't depend on the size of the dictionary for its performance
 * because it has a time complexity of 0(1) on average due to its hash-based lookup
 * --> this method is the most efficient one for large dictionaries
 *
 * 3. Binary search:
 * this method has a time complexity of 0(log n), with n is the size of the dictionary,
 * for sorted arrays. it reduces the search space by half
 * with each iteration
 * --> faster than ArrayList, slower than SimpleHashSet
 */

public class Assignment3 {
    public static void main(String[] args) {
        //read dictionary into ArrayList and HashSet
        ArrayList<BookWord> dictionaryArrayList = readDictionaryArrayList("US.txt");
        SimpleHashSet<BookWord> dictionaryHashSet = readDictionaryHashSet("US.txt");

        ArrayList<BookWord> textWords = readWords("WarAndPeace.txt");
        System.out.println("=================== PART A ==================================");
        System.out.println("total # of words in the novel: " + countWords(textWords));
        System.out.println("total # of unique words in the file: " + countUniqueWords(textWords));
        printWordsByCount(textWords, 41);
        printFrequentWords(textWords, 15);
        System.out.println("\n# of words not contained in the dictionary (ArrayList): " + countNotContainedArrayList(textWords, dictionaryArrayList));
        System.out.println("# of words not contained in the dictionary (HashSet): " + countNotContainedHashSet(textWords, dictionaryHashSet));
        System.out.println("# of words not contained in the dictionary (Binary Search): " + countNotContainedBinarySearch(textWords, dictionaryArrayList));
        System.out.println("=================== PART B ==================================");
        ArrayList<Integer> warPositions = findPositions(textWords, "war");
        ArrayList<Integer> peacePositions = findPositions(textWords, "peace");
        printDistance(warPositions, peacePositions);
    }

    /**
     * this method reads dictionary from the file into ArrayList
     * @param filename name of the file that contains dictionary
     * @return an arrayList that contains the words from the dictionary
     */
    private static ArrayList<BookWord> readDictionaryArrayList(String filename) {
        ArrayList<BookWord> dictionary = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().toLowerCase();
                dictionary.add(new BookWord(word));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Collections.sort(dictionary);
        return dictionary;
    }

    /**
     * reads the dictiopnary from the file into a SimpleHashSet
     * @param filename name of the file containing the dictionary
     * @return a simpel hash set containing the words from the dictionary
     */
    private static SimpleHashSet<BookWord> readDictionaryHashSet(String filename) {
        SimpleHashSet<BookWord> dictionary = new SimpleHashSet<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().toLowerCase();
                dictionary.insert(new BookWord(word));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    /**
     * reads the words from the text file into an ArrayList
     * @param filename name of the text file
     * @return  an arrayList containing the words from the novel
     */
    private static ArrayList<BookWord> readWords(String filename) {
        ArrayList<BookWord> novelWords = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            scanner.useDelimiter("\\.|\\?|\\!|\\s|\"|\\(|\\)|\\,|\\_|\\-|\\:|\\;|\\n");
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                if (!word.isEmpty() && !word.contains("'")) {
                    incrementWord(novelWords, word);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return novelWords;
    }


    /**
     * counts the number of words
     * @param words
     * @return
     */
    private static int countWords(ArrayList<BookWord> words) {
        int totalWords = 0;
        for (BookWord word:words) {
            totalWords += word.getCount();
        }
        return totalWords;
    }

    /**
     * counts the number of unique words from the file
     * @param words the list of words
     * @return number of unique words
     */
    private static int countUniqueWords(ArrayList<BookWord> words) {
        return words.size();
    }

    /**
     * print the most frequent words
     * @param words the list of words
     * @param n the number of words to print out
     */
    private static void printFrequentWords(ArrayList<BookWord> words, int n) {
        System.out.println("\ntop "+n+" most frequent words:");
        Collections.sort(words, (a, b) -> {
            if (a.getCount() != b.getCount()) {
                return b.getCount() - a.getCount();
            } else {
                return a.getText().compareToIgnoreCase(b.getText());
            }
        });
        for (int i = 0; i < Math.min(n, words.size()); i++) {
            BookWord word = words.get(i);
            System.out.println(word.getText() + ": " + word.getCount());
        }
    }

    /**
     * print words occurring by count times
     * @param words list of words
     * @param count the number of times words occur
     */
    private static void printWordsByCount(ArrayList<BookWord> words, int count) {
        System.out.println("\nwords occurring exactly "+count+ " times:");
        for (BookWord word : words) {
            if (word.getCount() == count) {
                System.out.println(word.getText());
            }
        }
    }

    /**
     * count the number of words which are not contained using ArrayList
     * @param words the list of words
     * @param dictionary the dictionary of valid words
     * @return the number of words which are not contained
     */
    private static int countNotContainedArrayList(ArrayList<BookWord> words, ArrayList<BookWord> dictionary) {
        int notContainedWord = 0;

        for (BookWord word : words) {
            if (!dictionary.contains(word)) {
                notContainedWord++;
            }
        }
        return notContainedWord;
    }

    /**
     * count the number of words which are not contained using SimpleHashSet
     * @param words the list of words
     * @param dictionary dictionary of valid words
     * @return the number of words which are not contained
     */
    private static int countNotContainedHashSet(ArrayList<BookWord> words, SimpleHashSet<BookWord> dictionary) {
        int notContainedWord = 0;

        for (BookWord word : words) {
            if (!dictionary.contains(word)) {
                notContainedWord++;
            }
        }
        return notContainedWord;
    }

    /**
     * count the number of words which are not contained using BinarySearch
     * @param words the list of words
     * @param dictionary dictionary of valid words
     * @return the number of words which are not contained
     */
    private static int countNotContainedBinarySearch(ArrayList<BookWord> words, ArrayList<BookWord> dictionary) {
        Collections.sort(dictionary);
        int notContainedWord = 0;
        //use binary search to find the word in the sorted dictionary
        for (BookWord word : words) {
            if (Collections.binarySearch(dictionary, word) < 0) {
                notContainedWord++;
            }
        }
        return notContainedWord;
    }


    /**
     * increments the count of an existing words
     * if a word is not in the list, add a new one to the list
     * @param words the list of words
     * @param text the word to add
     */
    private static void incrementWord(ArrayList<BookWord> words, String text) {
        for (BookWord word : words) {
            if (word.getText().equals(text)) {
                word.incrementCount();
                return;
            }
        }
        words.add(new BookWord(text));
    }

    /**
     * finds positions of 2 words war and peace in the text file
     * @param words the list of words
     * @param wordToFind the word needed to be found
     * @return a list of positions where the word is found
     */
    private static ArrayList<Integer> findPositions(ArrayList<BookWord> words, String wordToFind) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getText().equals(wordToFind)) {
                positions.add(i + 1);
            }
        }
        return positions;
    }

    /**
     * print the distance between matched pairs of war and peace
     * @param warPositions position of war
     * @param peacePositions position of peace
     */
    private static void printDistance(ArrayList<Integer> warPositions, ArrayList<Integer> peacePositions) {
        int totalDistance = 0;
        int matchedPairs = 0;
        ArrayList<Integer> matchedWarPositions = new ArrayList<>();
        Collections.sort(warPositions);
        Collections.sort(peacePositions);

        for (Integer peacePosition:peacePositions) {
            if (warPositions.isEmpty())
                break;
            int closestWarPosition = findClosestWarPosition(warPositions, peacePosition);
            if (closestWarPosition != -1 && !matchedWarPositions.contains(closestWarPosition)) {
                int minDistance = Math.abs(peacePosition - closestWarPosition);
                totalDistance += minDistance;
                matchedPairs++;
                System.out.println("The word peace at position " + peacePosition + " matched with the word war at position " + closestWarPosition);
                matchedWarPositions.add(closestWarPosition);
            }
        }
        System.out.println("Number of pairs of 'war' and 'peace' found: " + matchedPairs);
        System.out.println("The total distance between all the war/peace pairs was: " + totalDistance);
        double averageDistance = matchedPairs > 0 ? (double) totalDistance / matchedPairs : 0;
        System.out.println("The average distance between the war/peace pairs was: " + averageDistance);
    }

    /**
     * finds the closet word war position of the given peace position
     * using binary search
     * @param warPositions the list of war positions
     * @param peacePosition the list if oeace positions
     * @return the closet war position to the word peace
     */
    private static int findClosestWarPosition(ArrayList<Integer> warPositions, int peacePosition) {
        int left = 0;
        int right = warPositions.size() - 1;
        int closestWarPosition = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (warPositions.get(mid) == peacePosition) {
                return peacePosition;
            } else if (warPositions.get(mid) < peacePosition) {
                closestWarPosition = warPositions.get(mid);
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        //if there is no exact match is found, find the closest war position
        if (closestWarPosition == -1){
            int closestDistance = Integer.MAX_VALUE;
            for (int warPosition : warPositions) {
                int distance = Math.abs(peacePosition - warPosition);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestWarPosition = warPosition;
                }
            }
        }
        return closestWarPosition;
    }
}

