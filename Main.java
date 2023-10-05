import java.math.BigInteger;

public class Main {
	public static void main(String[] args) {                                             

		// Testing
		String input1 = "012";
		String input2 = "150";
		Integer[] input3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		final Huge hugeInput2 = new Huge(input2);
		final Huge hugeInput3 = new Huge(input3);
		System.out.println("My code: ");
		
		
		Huge hugeNoArg = new Huge();
		System.out.println("Huge() = " + hugeNoArg);

		Huge hugeForAdd = new Huge(input1);
		hugeForAdd.add(hugeInput2);
		System.out.println("Huge(" + input1 + ").add(Huge(" + input2 + ")) = " + hugeForAdd);

	                    

        Huge hugeForAddArray = new Huge(input1);
        hugeForAddArray.add(hugeInput3);
        //System.out.println("Huge(" + input1 + ").add(Huge(" + hugeInput3 + ")) = " + hugeForAddArray);

        
        
    	Huge hugeForMult = new Huge(input1);
		hugeForMult.multiply(hugeInput2);
		System.out.println("Huge(" + input1 + ").multiply(Huge(" + input2 + ")) = " + hugeForMult);
        System.out.println(); 
        System.out.println();
        
        
        
        
        
        
        System.out.println("Built in code: ");
        BigInteger sumBigInt;
        BigInteger productBigInt;
        BigInteger a = new BigInteger(input1);
        BigInteger b = new BigInteger(input2);
        sumBigInt = a.add(b);
        productBigInt = a.multiply(b);
        System.out.println("BigInt Addition: " + a + " + " + b + " = " + sumBigInt);
        System.out.println("BigInt Multiplication: " + a + " * " + b + " = " + productBigInt);
        System.out.println();

                               

                                }

}