
public class HashCode {
	HashCode() {

	}

	public int GenerateHash(String s,int selection,int size) {
		int hashcode = 0;
		// Use the simple accumulation function
		if (selection==0) {
			int n = s.length();
			for (int i = 0; i < n; i++) {
				hashcode += (Character.getNumericValue(s.charAt(i)) - 9);

			}
		}
		//Use the polynomial accumulation function
		//Horners rule is applied to get modulus operation after every character
		else if (selection==1) {
			int n = s.length();
			for (int i = 0; i < n; i++) {
				hashcode += Math.abs((Character.getNumericValue(s.charAt(i)) - 9)) * Math.pow(37, n - 1 - i);
				hashcode=Compress(hashcode,size);
				
			}
		}
		return hashcode;
	}


	public int Compress(int x, int size) {
		int compressedvalue = 0;
		//Compress the hash code to fit in the table
		compressedvalue = x % size;

		return compressedvalue;
	}

}
