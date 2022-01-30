
public interface HashTableInterface {

	void put(String key,String val);
	void get(String key);
	int getIndex(String word,int probingselection);
	void resize();
	long getCollisioncount();
	void setLoadfactor(int loadfactor);
}
