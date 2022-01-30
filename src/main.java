import java.io.File;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashTableInterface invertedindex = null;
		ReadFile fileread = new ReadFile();
		String[] foldernames = fileread.getFoldersNames();

		int monitoring = 3;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("If you want to just search enter 0,if you want to monitor the table type 1");
			try {
				monitoring = sc.nextInt();
			} catch (Exception e) {
				// TODO: handle exception
				sc.next();
			}

		} while (monitoring != 0 && monitoring != 1);
		if (monitoring == 0) {
			System.out.println("Make sure printlns are not commented in hastables get function");
			int probing = 3;
			do {
				System.out.println("Type 0 for linear probing, type 1 for double hashing");
				try {
					probing = sc.nextInt();
				} catch (Exception e) {
					// TODO: handle exception
					sc.next();
				}

			} while (probing != 0 && probing != 1);
			int function = 3;
			do {
				System.out.println(
						"Type 0 for simple accumulation function, type 1 for polynomial accumulation function");
				try {
					function = sc.nextInt();
				} catch (Exception e) {
					// TODO: handle exception
					sc.next();
				}

			} while (function != 0 && function != 1);
			int loadfactor = -1;
			do {
				System.out.println("Set the load factor(1<load factor<100");
				try {
					loadfactor = sc.nextInt();
				} catch (Exception e) {
					// TODO: handle exception
					sc.next();
				}

			} while (loadfactor >= 100 && loadfactor <= 1);
			invertedindex = new HashTable(37, function, probing);
			invertedindex.setLoadfactor(loadfactor);
			int searchtype=-1;
			do {
				System.out.println("If you want to search using search.txt file,type 0,if you want to search yourself type 1");
				try {
					searchtype=sc.nextInt();
				} catch (Exception e) {
					// TODO: handle exception
					sc.next();
				}
			} while (searchtype!=0&&searchtype!=1);
			for (int i3 = 0; i3 < foldernames.length; i3++) {
				File[] files = fileread.ReadContextFile(foldernames[i3]);
				for (File file : files) {
					String[] text = fileread.getSplitText(file);
					for (int j3 = 0; j3 < text.length; j3++) {
						if (!text[j3].isBlank()) {
							invertedindex.put(text[j3], file.getParentFile().getName() + "_" + file.getName());
						}
					}
				}
			}
			if(searchtype==0) {
				String[] search = fileread.readSearchText();
				for (int i = 0; i < search.length; i++) {
					invertedindex.get(search[i]);
				}
			}
			else if(searchtype==1) {
				System.out.println("Enter the word to search,if you want to exit,type 0");
				String searchword = null;
				do {
					searchword = sc.nextLine();
				} while (searchword.isEmpty());
				while (!searchword.equals("0")) {
					invertedindex.get(searchword);
					System.out.println("Enter the word to search,if you want to exit,type 0");
					searchword = sc.nextLine();

				}
				if (searchword.equals("0"))
					System.out.println("Terminating the program");
			}


		} else if (monitoring == 1) {
			String[] search = fileread.readSearchText();
			long starttime = 0, endtime = 0, spenttime = 0, maxsearchtime = 0, minsearchtime = 0, totalsearchtime = 0;
			do {
				System.out.println(
						"If you want to automatically monitor the perfomance type 0,if you want to manually configure the table type 1");
				System.out
						.println("Make sure there are no printlns in hashtable functions to get the correct results!!");
				try {
					monitoring = sc.nextInt();
				} catch (Exception e) {
					// TODO: handle exception
					sc.next();
				}

			} while (monitoring != 0 && monitoring != 1);
			sc.close();
			if (monitoring == 0) {
				int[] probingandfunction = { 0, 1 };
				int[] loadfactor = { 50, 80 };
				for (int i = 0; i < loadfactor.length; i++) {
					for (int j = 0; j < loadfactor.length; j++) {
						for (int j2 = 0; j2 < loadfactor.length; j2++) {
							invertedindex = new HashTable(37, probingandfunction[j], probingandfunction[j2]);
							invertedindex.setLoadfactor(loadfactor[i]);
							// Indexing the words
							for (int i3 = 0; i3 < foldernames.length; i3++) {
								File[] files = fileread.ReadContextFile(foldernames[i3]);
								for (File file : files) {
									String[] text = fileread.getSplitText(file);
									for (int j3 = 0; j3 < text.length; j3++) {
										if (!text[j3].isBlank()) {
											starttime = System.nanoTime();
											invertedindex.put(text[j3],
													file.getParentFile().getName() + "_" + file.getName());
											endtime = System.nanoTime();
											spenttime += (endtime - starttime);
										}
									}
								}
							}
							starttime = 0;
							endtime = 0;
							maxsearchtime = 0;
							minsearchtime = 0;
							totalsearchtime = 0;
							for (int k = 0; k < search.length; k++) {
								starttime = System.nanoTime();
								invertedindex.get(search[k]);
								endtime = System.nanoTime();
								totalsearchtime += endtime - starttime;
								if (maxsearchtime == 0) {
									maxsearchtime = endtime - starttime;
								}
								if (minsearchtime == 0) {
									minsearchtime = endtime - starttime;
								}
								if (endtime - starttime > maxsearchtime)
									maxsearchtime = endtime - starttime;
								if (endtime - starttime < minsearchtime)
									minsearchtime = endtime - starttime;
							}
							String space = "                         ";
							System.out.print("Load Factor");
							System.out.print("   ||Hash Function");
							System.out.print("   ||Collision Handling");
							System.out.print("   ||Collision Count");
							System.out.print("   ||Indexing Time");
							System.out.print("   ||Average Search Time");
							System.out.print("   ||Min Search Time");
							System.out.println("   ||Max Search Time");
							System.out.print("      " + loadfactor[i] + "%");

							if (probingandfunction[j] == 0)
								System.out.print("     ||    SSF");
							else
								System.out.print("     ||    PAF");
							if (probingandfunction[j2] == 0)
								System.out.print("         ||       LP       ");
							else
								System.out.print("         ||       DH       ");

							System.out.print("     ||" + invertedindex.getCollisioncount());
							double spenttimesec=spenttime/1000000000;
							System.out.print(space.substring(0,
									9 - (String.valueOf(invertedindex.getCollisioncount()).length() - 9)) + "||"
									+ spenttimesec+" seconds");
							
							System.out.print(space.substring(0, 1 - (String.valueOf(spenttimesec).length() - 7)) + "||"
									+ (totalsearchtime / search.length)+" ns");
							System.out.print(space.substring(0,
									14 - (String.valueOf(totalsearchtime / search.length).length() - 5)) + "||"
									+ minsearchtime+" ns");
							System.out.println(space.substring(0, 4 - (String.valueOf(minsearchtime).length() - 11))
									+ "||" + maxsearchtime+" ns");

						}
					}
				}
			} else if (monitoring == 1) {
				int probing = 3;
				do {
					System.out.println("Type 0 for linear probing, type 1 for double hashing");
					try {
						probing = sc.nextInt();
					} catch (Exception e) {
						// TODO: handle exception
						sc.next();
					}

				} while (probing != 0 && probing != 1);
				int function = 3;
				do {
					System.out.println(
							"Type 0 for simple accumulation function, type 1 for polynomial accumulation function");
					try {
						function = sc.nextInt();
					} catch (Exception e) {
						// TODO: handle exception
						sc.next();
					}

				} while (function != 0 && function != 1);
				int loadfactor = -1;
				do {
					System.out.println("Set the load factor(1<loadfactor<100");
					try {
						loadfactor = sc.nextInt();
					} catch (Exception e) {
						// TODO: handle exception
						sc.next();
					}

				} while (loadfactor >= 100 && loadfactor <= 1);
				invertedindex = new HashTable(37, function, probing);
				sc.close();
				// Indexing the words
				invertedindex.setLoadfactor(loadfactor);
				for (int i3 = 0; i3 < foldernames.length; i3++) {
					File[] files = fileread.ReadContextFile(foldernames[i3]);
					for (File file : files) {
						String[] text = fileread.getSplitText(file);
						for (int j3 = 0; j3 < text.length; j3++) {
							if (!text[j3].isBlank()) {
								starttime = System.nanoTime();
								invertedindex.put(text[j3], file.getParentFile().getName() + "_" + file.getName());
								endtime = System.nanoTime();
								spenttime += (endtime - starttime);
							}
						}
					}
				}
				starttime = 0;
				endtime = 0;
				maxsearchtime = 0;
				minsearchtime = 0;
				totalsearchtime = 0;
				for (int k = 0; k < search.length; k++) {
					starttime = System.nanoTime();
					invertedindex.get(search[k]);
					endtime = System.nanoTime();
					totalsearchtime += endtime - starttime;
					if (maxsearchtime == 0) {
						maxsearchtime = endtime - starttime;
					}
					if (minsearchtime == 0) {
						minsearchtime = endtime - starttime;
					}
					if (endtime - starttime > maxsearchtime)
						maxsearchtime = endtime - starttime;
					if (endtime - starttime < minsearchtime)
						minsearchtime = endtime - starttime;
				}
				String space = "                         ";
				System.out.print("Load Factor");
				System.out.print("   ||Hash Function");
				System.out.print("   ||Collision Handling");
				System.out.print("   ||Collision Count");
				System.out.print("   ||Indexing Time");
				System.out.print("   ||Average Search Time");
				System.out.print("   ||Min Search Time");
				System.out.println("   ||Max Search Time");
				System.out.print("      " + loadfactor + "%");

				if (function == 0)
					System.out.print("     ||    SSF");
				else
					System.out.print("     ||    PAF");
				if (probing == 0)
					System.out.print("         ||       LP       ");
				else
					System.out.print("         ||       DH       ");

				System.out.print("     ||" + invertedindex.getCollisioncount());
				double spenttimesec=spenttime/1000000000;
				System.out.print(
						space.substring(0, 9 - (String.valueOf(invertedindex.getCollisioncount()).length() - 9))
								+ "||" + spenttimesec+" seconds");
				System.out.print(space.substring(0, 1 - (String.valueOf(spenttimesec).length() - 7)) + "||"
						+ (totalsearchtime / search.length)+" ns");
				System.out.print(space.substring(0, 14 - (String.valueOf(totalsearchtime / search.length).length() - 5))
						+ "||" + minsearchtime+" ns");
				System.out.println(
						space.substring(0, 4 - (String.valueOf(minsearchtime).length() - 11)) + "||" + maxsearchtime+" ns");

			}

		}
	}

}
