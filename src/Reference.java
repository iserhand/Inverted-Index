
public class Reference {
private String filename;
private int frequency;
Reference(String filename,Integer frequency){
	this.setFilename(filename);
	this.setFrequency(frequency);
}
public int getFrequency() {
	return frequency;
}
public void setFrequency(int frequency) {
	this.frequency = frequency;
}
public String getFilename() {
	return filename;
}
public void setFilename(String filename) {
	this.filename = filename;
}

}
