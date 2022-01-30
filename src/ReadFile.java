import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadFile {
	ReadFile() {
		
	}

	public File[] ReadContextFile(String context) {
		File directoryPath = new File("src\\bbc\\"+context);
		File[] files = directoryPath.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		
		return files;

	}
	public String[] getFoldersNames() {
		File directoryPath = new File("src\\bbc\\");
		String[] folders=new String[directoryPath.listFiles().length];
		int i=0;
		for (File file : directoryPath.listFiles()) {
			folders[i]=file.getName();
					i++;
		}
		return folders;
	}

	public String[] getSplitText(File filetosplit) {
		String DELIMITERS = "[-+=" +

				" " + // space

				"\r\n " + // carriage return line fit

				"1234567890" + // numbers

				"’'\"" + // apostrophe

				"(){}<>\\[\\]" + // brackets

				":" + // colon

				"," + // comma

				"‒–—―" + // dashes

				"…" + // ellipsis

				"!" + // exclamation mark

				"." + // full stop/period

				"«»" + // guillemets

				"-‐" + // hyphen

				"?" + // question mark

				"‘’“”" + // quotation marks

				";" + // semicolon

				"/" + // slash/stroke

				"⁄" + // solidus

				"␠" + // space?

				"·" + // interpunct

				"&" + // ampersand

				"@" + // at sign

				"*" + // asterisk

				"\\" + // backslash

				"•" + // bullet

				"^" + // caret

				"¤¢$€£¥₩₪" + // currency

				"†‡" + // dagger

				"°" + // degree

				"¡" + // inverted exclamation point

				"¿" + // inverted question mark

				"¬" + // negation

				"#" + // number sign (hashtag)

				"№" + // numero sign ()

				"%‰‱" + // percent and related signs

				"¶" + // pilcrow

				"′" + // prime

				"§" + // section sign

				"~" + // tilde/swung dash

				"¨" + // umlaut/diaeresis

				"_" + // underscore/understrike

				"|¦" + // vertical/pipe/broken bar

				"⁂" + // asterism

				"☞" + // index/fist

				"∴" + // therefore sign

				"‽" + // interrobang

				"※" + // reference mark

				"]";
		String file = null;
		String stopwordfile=null;
		try {
			file = Files.readString(Path.of(filetosplit.getPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file = file.toLowerCase();
		String[] text=file.split(DELIMITERS);
		try {
			stopwordfile = Files.readString(Path.of("src\\stop_words_en.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stopwordfile.replaceAll("\\s+", "");
		List<String> stopwordlist=new ArrayList<>();
		stopwordlist.addAll(Arrays.asList(stopwordfile.split("\\s+")));
		
		ArrayList<String> wordlist=new ArrayList<>();
		wordlist.addAll(Arrays.asList(file.split(DELIMITERS)));
		wordlist.removeAll(stopwordlist);
		//System.out.println(stopwordlist);
		text = wordlist.toString().split(DELIMITERS);
		//System.out.println(wordlist);
		
		return text;

	}
	public String[] readSearchText() {
		String[] searchwords = null;
		try {
			searchwords = (Files.readString(Path.of("src\\search.txt"))).split("\\s+");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return searchwords;
	}


	}

