import java.util.LinkedList;

public class HashTable implements HashTableInterface {
	private int currentSize, size, maxSize;
	private int selection;
	private int probingselection;
	private String[] keys;
	private LinkedList<Reference>[] references;
	HashCode hc = new HashCode();

	private int loadfactor = 0;
	private long collisioncount = 0;

	@SuppressWarnings("unchecked")
	public HashTable(int capacity, int selection, int probingselection) {
		this.selection = selection;
		this.probingselection = probingselection;
		currentSize = 0;
		size = capacity;
		maxSize = capacity * loadfactor / 100;
		keys = new String[size];
		references = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			references[i] = new LinkedList<Reference>();
		}

	}

	public void setLoadfactor(int loadfactor) {
		this.loadfactor = loadfactor;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void put(String key, String val) {
		int tmp = hc.Compress(hc.GenerateHash(key, selection, size), size);
		int i = tmp;
		if (probingselection == 0) {
			do {
				if (keys[i] != null)
					collisioncount++;
				if (keys[i] == null) {
					keys[i] = key;
					references[i].add(new Reference(val, 1));
					currentSize++;
					return;
				}
				if (keys[i].equals(key)) {
					collisioncount--;
					boolean flag = true;
					for (int j = 0; j < references[i].size(); j++) {
						if (references[i].get(j).getFilename().equals(val)) {
							flag = false;
							references[i].get(j).setFrequency(references[i].get(j).getFrequency() + 1);
							return;
						}
					}
					if (flag) {
						references[i].add(new Reference(val, 1));
						return;
					}
				}

				i = (i + 1) % size;
			} while (i != tmp);

		} else if (probingselection == 1) {
			int lookup = 0;
			int q = getprimeForDoubleHashing();
			do {
				i = Math.abs(((i % size) + lookup * (q - i % q)) % size);
				if (keys[i] != null)
					collisioncount++;
				if (keys[i] == null) {
					keys[i] = key;
					references[i].add(new Reference(val, 1));
					currentSize++;
					return;
				}
				if (keys[i].equals(key)) {
					collisioncount--;
					boolean flag = true;
					for (int j = 0; j < references[i].size(); j++) {
						if (references[i].get(j).getFilename().equals(val)) {
							flag = false;
							references[i].get(j).setFrequency(references[i].get(j).getFrequency() + 1);
							return;
						}
					}
					if (flag) {
						references[i].add(new Reference(val, 1));
						return;
					}
				}
				lookup++;
			} while (lookup < size);
		}
		if (currentSize >= maxSize) {
			resize();
		}

	}

	private int getprimeForDoubleHashing() {
		//Returns a prime value smaller than table size to use for double hashing
		int prime = size;
		prime--;
		boolean flag = true;
		while (flag) {
			if (prime % 2 == 0) {
				prime--;
			} else {
				for (int i = 2; i < prime; i++) {
					if (prime % i == 0) {
						prime--;
						flag = true;
						break;

					}
					flag = false;
				}
			}
		}
		return prime;
	}

	private void reput(String key) {
		int tmp = hc.Compress(hc.GenerateHash(key, selection, size), size);
		int i = tmp;
		if (probingselection == 0) {
			do {
				if (keys[i] != null)
					collisioncount++;
				if (keys[i] == null) {
					keys[i] = key;
					currentSize++;
					return;
				}

				i = (i + 1) % size;
			} while (i != tmp);
		} else if (probingselection == 1) {
			int lookup = 0;
			int q = getprimeForDoubleHashing();
			do {
				i = ((i % size) + lookup * (q - i % q)) % size;
				if (keys[i] != null)
					collisioncount++;
				if (keys[i] == null) {
					keys[i] = key;
					currentSize++;
					return;
				}
				lookup++;
			} while (lookup < size);
		}
		if (currentSize >= maxSize) {
			resize();
		}

	}

	public void get(String key) {
		int i = getIndex(key,probingselection);
		if(i==-1) {
			//System.out.println("Search:" + key);
			//System.out.println("Not found!");
			return;
		}

		if (keys[i].equals(key)) {
			//System.out.println("Search: " +keys[i]);			
			//System.out.println(references[i].size() + " document(s) found");
			for (int j = 0; j < references[i].size(); j++) {
				//System.out.println(references[i].get(j).getFrequency() + "-" + references[i].get(j).getFilename());
			}

			return;
		}

		else {
			//System.out.println("No results found");
		}
	}

	public int getIndex(String word, int probingselection) {
		int i = hc.Compress(hc.GenerateHash(word, selection, size), size);
		if(keys[i]==null)
			return -1;
		if (probingselection == 0) {
			while (keys[i] != null) {
				if (keys[i].equals(word))
					return i;
				i = (i + 1) % size;
			}
		} else if (probingselection == 1) {
			int lookup = 0;
			int q = getprimeForDoubleHashing();
			while (keys[i] != null) {
				i = ((i % size) + lookup * (q - i % q)) % size;
				if(i<0||keys[i]==null)
					return -1;
				if (keys[i].equals(word))
					return i;
				lookup++;
			}
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public void resize() {
		// Finding next prime number larger than twice of the old size
		int newSize = 2 * keys.length;
		boolean flag = true;
		while (flag) {
			if (newSize % 2 == 0) {
				newSize++;
			} else {
				for (int i = 2; i < newSize; i++) {
					if (newSize % i == 0) {
						newSize++;
						flag = true;
						break;

					}
					flag = false;
				}
			}
		}
		maxSize = newSize * loadfactor / 100;
		// Setting new max size with load factor
		currentSize = 0;
		LinkedList<Reference>[] templist;
		templist = new LinkedList[size];
		for (int i = 0; i < templist.length; i++) {
			templist[i] = new LinkedList<Reference>();
		}
		for (int i = 0; i < templist.length; i++) {
			for (int j = 0; j < references[i].size(); j++) {
				templist[i].add(references[i].get(j));
			}

		}
		references = new LinkedList[newSize];

		setSize(newSize);
		String[] tempkeys = keys.clone();
		for (int i = 0; i < newSize; i++) {
			references[i] = new LinkedList<Reference>();

		}
		keys = new String[newSize];

		for (int i = 0; i < tempkeys.length; i++) {
			if (tempkeys[i] != null)
				reput(tempkeys[i]);
		}
		for (int i = 0; i < tempkeys.length; i++) {
			for (int j = 0; j < templist[i].size(); j++) {
				references[getIndex(tempkeys[i],probingselection)].add(templist[i].get(j));
			}

		}

	}

	public long getCollisioncount() {
		return collisioncount;
	}

}
