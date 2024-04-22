/**I, Xuan Huy Pham, 000899551, certify that this material is my original work. No other person's work has been used without suitable acknowledgment and I have not made my work available to anyone else.
 */
public class BookWord implements Comparable<BookWord> {
    private String text;
    private int count;

    /**
     * this constructor is to construct a bookword object with the given word text
     * @param wordText the word text
     */
    public BookWord(String wordText) {
        this.text = wordText.toLowerCase();
        this.count = 1;
    }

    /**
     *
     * @return the word text
     */
    public String getText(){
        return text;
    }

    /**
     * this is to get the count of occurrences of the word
     * @return the count
     */
    public int getCount(){
        return count;
    }

    /**
     * this method is to increment the count of occurrences of the word
     */
    public void incrementCount(){
        count++;
    }

    /**
     * this method is to compare this BookWord with the specified object
     * @param obj the object
     * @return true if the objects are the equal, return false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (this==obj) {
            return true;
        }
        if (obj==null || getClass() != obj.getClass()) {
            return false;
        }
        BookWord other = (BookWord) obj;

        return text.equals(other.text);
    }

    /**
     * this method is to return a hash code value for the BookWord object
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 17;//initialize the hash code with a prime number to reduce collisions
        for (int i = 0; i<text.length();i++) {
            hash = hash*31 + text.charAt(i);
        }
        return hash;
    }

    /**
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return text+": "+count;
    }

    /**
     * this method is to compare this BookWord with another BookWord for ordering
     * @param other the object to be compared.
     * @return a negative integer, a positive integer or 0
     */
    @Override
    public int compareTo(BookWord other) {
        return this.text.compareTo(other.text);
    }
}

