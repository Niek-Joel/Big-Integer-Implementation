import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.DefaultStyledDocument;

public class Huge {
    Deque<Integer> hugeNumber; 
	static private final int MAX_SIZE = 500; // As in max amount of digits a number can be.

	// Requirements for inputed numbers
	static enum Validity {
	       TRUE,
	       FALSE_IS_NEGATIVE,
	       FALSE_IS_TOO_LARGE,
	       FALSE_IS_NOT_WHOLE,
	       CONTAINS_NON_INT_CHAR
	   }
	 
	// Constructors
	public Huge() {
	    hugeNumber = new ArrayDeque<>();
	    hugeNumber.add(0);
	}

	public Huge(Huge huge) {
	    hugeNumber = new ArrayDeque<>();
	    setHuge(huge.toString());
	}

	public Huge(Integer[] digits) {
	    hugeNumber = new ArrayDeque<>();
	    setHuge(digits);
	}

	public Huge(String hugeString) {
	    hugeNumber = new ArrayDeque<>();
	    setHuge(hugeString);
	}
	

	
	// SetHuge method for array argument
    private void setHuge(final Integer[] digits) {
        for (Integer digit : digits) {
            hugeNumber.addLast(digit); 
        }
     }  
    
    
    
    // SetHuge method for String argument
    private void setHuge(final String hugeString) {
        // Formatting the raw string and checking for input violations
        String value = extractValue(hugeString);

        // Creating dequeue to store the large number
        hugeNumber = new ArrayDeque<Integer>(); 
          
        // Parsing string by char   
        String currentDigit;
        for (int i=0; i<value.length(); i++) {
            currentDigit = value.substring(i, i+1);
                        
            // Adds each character into queue in correct order in the dequeue(Input: "12345" -> [1,2,3,4,5])
            hugeNumber.addLast(Integer.parseInt(currentDigit)); 
        }
     }
    
	
	
	// Enum definitions
		static private Validity checkValidity(final String value) {
		   Validity validity = Validity.TRUE;
		   // Negative value check
		   if (value.contains("-")) {
		       validity = Validity.FALSE_IS_NEGATIVE;
		   }
		   // Overflow check
		   else if (value.length() > MAX_SIZE) {
		       validity = Validity.FALSE_IS_TOO_LARGE;
		   }
		   // Integer check
		   else if (value.contains(".")) {
			   validity = Validity.FALSE_IS_NOT_WHOLE;
		   }
		   else if (!value.matches("[0-9]+") ) {
		       validity = Validity.CONTAINS_NON_INT_CHAR;
		   }
		   return validity;
		   }
		    
		 
		// Implements Validity enums and strips leading zeros
		static private String extractValue(final String value) {
		   if (checkValidity(value) == Validity.FALSE_IS_NEGATIVE) {
		       throw new IllegalArgumentException("Input must be positive");
		   }
		   else if (checkValidity(value) == Validity.FALSE_IS_TOO_LARGE) {
		       throw new IllegalArgumentException("Input must be smaller than " + MAX_SIZE + " digits.");
		   }
		   else if (checkValidity(value) == Validity.FALSE_IS_NOT_WHOLE) {
		       throw new IllegalArgumentException("Input must be a whole number");
		   }
		   else if (checkValidity(value) == Validity.CONTAINS_NON_INT_CHAR) {
		       throw new IllegalArgumentException("Input not recognized. Enter only integers.");
		   }
		       
		       // Remove leading zeros.
		       return value.replaceFirst("^0+", "");
		   }
		
		
	
	 
    public void add(final Huge other) {
        Deque<Integer> result = new ArrayDeque<>();
        
        // In addition between 2 operands, the digits of the sum can never be larger than the largest operand plus 1 digit. 
        // Example: 999 + 99 = 1098. 3 digits + 1 digit = 4 digits. 4 digits is the limit for the sum. 

        final int limit = Math.max(hugeNumber.size(), other.hugeNumber.size()) + 1;
        Iterator<Integer> iteratorThis = hugeNumber.descendingIterator(); 
        Iterator<Integer> iteratorOther = other.hugeNumber.descendingIterator();
        int carryOver = 0;
        
        for (int i=0; i < limit; i++) { 
            Integer digit1 = 0;
            Integer digit2 = 0;
            
            if (iteratorThis.hasNext()) {
                digit1 = iteratorThis.next();
            }
            
            if (iteratorOther.hasNext()) {
                digit2 = iteratorOther.next();
            }
            
            int currentDigit = digit1 + digit2 + carryOver;   

            if (currentDigit >= 10) {
                carryOver = 1;
                currentDigit = currentDigit - 10;
            }
            else {  // currentDigit < 10
                carryOver = 0;
            }
            result.addFirst(currentDigit);
        }
        
        if (result.getFirst() == 0) {
            result.removeFirst();
        }
        
        // Testing for digit overflow
        if (result.size() > MAX_SIZE) {
            throw new IllegalArgumentException("Value computed is too large and the digits exceed " + MAX_SIZE); 
        }
        // Keep result 
        else {
            this.hugeNumber = result;
        }
     }
    
    
    
	public void multiply(final Huge other) {
		// In multiplication between 2 operands, the product can only be as many digits long as the operands combined.
		// Example: 99 * 999 = 98,901. 2 digits * 3 digits = 5 digits. 5 digits is the max length of the product.

		Iterator<Integer> iteratorThis = hugeNumber.descendingIterator(); // Descending order means it starts on the right and goes left.
		List<Deque<Integer>> results = new ArrayList<>();

		int power1 = 0;
		int carryOverDigit = 0;

		while (iteratorThis.hasNext()) {
			Integer digit1 = iteratorThis.next();

			//System.out.println("digit1 " + digit1);

			Deque<Integer> result = new ArrayDeque<>();

			// Add trailing zero's (if any).
			for (int i = 0; i < power1; ++i) {
				result.addLast(0);
			}

			carryOverDigit = 0;
			
			Iterator<Integer> iteratorOther = other.hugeNumber.descendingIterator();
			while (iteratorOther.hasNext()) {

				Integer digit2 = iteratorOther.next();
				//System.out.println("digit2 " + digit2);

				int currentDigit = digit2 * digit1 + carryOverDigit;

				if (currentDigit >= 10) {
					carryOverDigit = currentDigit / 10; // Truncates since it is an integer
					currentDigit = currentDigit % 10;
				}
				else { // currentDigit < 10
					carryOverDigit = 0;
				}
				
				//System.out.println("currentDigit=" + currentDigit + " carryOverDigit=" + carryOverDigit);
				result.addFirst(currentDigit);

				// If this is the last digit, you must go ahead and add the carry over digit
				if (iteratorOther.hasNext() == false) {
					result.addFirst(carryOverDigit);
				}

			}
			//System.out.println("");

			results.add(result); // adds a dequeue of integers to a list of dequeues
			++power1;
		}

		// Creating Huge object to use addition function. This level is for top level addition
		Huge product = new Huge();

		for (Deque<Integer> result : results) {
			int size = result.size();
			Integer[] array = new Integer[size];

			for (int i = 0; i < size; i++) {
				array[i] = result.pop();
			}

			// Creating Huge object to use addition function. This level is for lower level addition
			Huge hugePartial = new Huge(array);
			//System.out.println("hugePartial " + hugePartial);
			product.add(hugePartial);
		}
		
		// Strips leading zero if necessary
		if (product.hugeNumber.getFirst() == 0) {
			product.hugeNumber.removeFirst();
		}
		
		this.hugeNumber = product.hugeNumber;
	}

    
	
	public String toString() {
	    String result = "";
	    for (Integer digit : hugeNumber) {
	        result += digit.toString();
	    }
	    return result;
	}
	
	

}














